package com.gl1tch.Jibliy.service.order;

import com.gl1tch.Jibliy.commands.OrderCommand;
import com.gl1tch.Jibliy.configuration.JwtTokenService;
import com.gl1tch.Jibliy.domain.*;
import com.gl1tch.Jibliy.dto.GiftDTO;
import com.gl1tch.Jibliy.dto.OrderDTO;
import com.gl1tch.Jibliy.dto.mappers.GiftMapper;
import com.gl1tch.Jibliy.dto.mappers.OrderMapper;
import com.gl1tch.Jibliy.repository.*;
import com.gl1tch.Jibliy.utils.OrderStatusEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final giftRepository giftRepository;
    private final GiftMapper giftMapper;

    public Long createOrder(List<OrderCommand> command, String authHeader) {
        Order order = new Order();
        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);
        List<OrderProduct> orderProducts = new ArrayList<>();

        command.forEach(currCommand -> {
            Product product = productRepository.findProductById(currCommand.getProductId()).orElseThrow();
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setQuantite(currCommand.getQuantity());
            orderProduct.setProduct(product);
            totalPrice.set(
                    totalPrice.get().add(
                            product.getPrice().multiply(BigDecimal.valueOf(currCommand.getQuantity()))
                    )
            );
            orderProducts.add(orderProduct);
        });

        String email = jwtTokenService.extractEmail(authHeader.substring(7));

        User user = userRepository.findByEmail(email).orElseThrow();

        order.setUser(user);
        order.setOrderProducts(orderProducts);
        order.setTotalPrice(totalPrice.get());
        order.setStatus(OrderStatusEnum.PROCESSING);

        Long orderId = orderRepository.save(order).getId();

        orderProductRepository.saveAll(orderProducts);

        return orderId;
    }

    public List<OrderDTO> getAllByUserEmail(String authHeader) {
        String email = jwtTokenService.extractEmail(authHeader.substring(7));

        List<Order> orders = orderRepository.findAllByUserEmail(email);

        return orderMapper.toListOrderDTO(orders);
    }

    public Integer getNumberOfDeliveredOrders(String authHeader) {
        String email = jwtTokenService.extractEmail(authHeader.substring(7));

        User user = userRepository.findByEmail(email).orElseThrow();

        return orderRepository.countByUserIdAndStatus(user.getId(), OrderStatusEnum.DELIVERED) + 3;
    }

    public List<GiftDTO> getMyGifts(String authHeader) {
        String email = jwtTokenService.extractEmail(authHeader.substring(7));

        User user = userRepository.findByEmail(email).orElseThrow();

        List<Gift> gifts = giftRepository.findAllByUserId(user.getId());
        return giftMapper.toListDto(gifts);

    }

}
