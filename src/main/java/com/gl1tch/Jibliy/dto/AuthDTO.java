package com.gl1tch.Jibliy.dto;

import com.gl1tch.Jibliy.utils.CityEnum;
import com.gl1tch.Jibliy.utils.RoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthDTO {
    private String username;
    private String email;
    private CityEnum city;
    private RoleEnum role;
    private String token;
}
