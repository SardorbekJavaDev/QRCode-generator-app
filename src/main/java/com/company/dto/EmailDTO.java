package com.company.dto;

import com.company.enums.EmailType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailDTO {
    private String id;
    private String toEmail;
    private EmailType type;
    private LocalDateTime sendDate = LocalDateTime.now();
}
