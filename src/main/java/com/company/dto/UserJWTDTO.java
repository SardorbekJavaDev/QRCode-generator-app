package com.company.dto;

import com.company.enums.UserRole;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserJWTDTO {
    private String email;
}
