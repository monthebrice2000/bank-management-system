package com.bank.bank_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class UserDto {

    private String email;
    private String password;
    private String role;
    private boolean enabled;

}
