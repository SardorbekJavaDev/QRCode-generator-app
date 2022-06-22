package com.company.dto.response;

import com.company.dto.AttachSimpleDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponceDTO {
    private String id;
    private String phone;
    private String name;
    private String jwt;
    private LocalDateTime createdDate;
}
