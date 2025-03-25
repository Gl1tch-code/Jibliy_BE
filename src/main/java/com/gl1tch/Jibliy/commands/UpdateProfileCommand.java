package com.gl1tch.Jibliy.commands;

import com.gl1tch.Jibliy.utils.CityEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.regex.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileCommand {
    private static final String PHONE_NUMBER_REGEX = "^(\\+2126|06)\\d{8}$";
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);

    private Long id;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    private CityEnum city;

    private String phoneNumber;

    private String location;

    public boolean isValidPhoneNumber() {
        if (this.phoneNumber == null || this.phoneNumber.isEmpty()) {
            return true; // or false if you want to make it mandatory.
        }
        return PHONE_NUMBER_PATTERN.matcher(this.phoneNumber).matches();
    }

}