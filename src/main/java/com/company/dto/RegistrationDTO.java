package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationDTO {
    @NotBlank(message = "Name required")
    private String name;
    @NotBlank(message = "SurName required")
    private String surname;
    @Email
    private String email;
    @NotBlank(message = "Password required")
    @Size(min = 4, max = 15, message = "About Me must be between 4 and 15 characters")
    private String password;
}
