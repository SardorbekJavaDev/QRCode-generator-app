package com.company.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthRequestDTO {
        @NotNull(message = "Email Required !")
        @Email(message = "Email Not Valid !")
        private String email;
        @NotBlank(message = "Password required")
        @Size(min = 4, max = 15, message = "Password Not Valid")
        private String password;
}
