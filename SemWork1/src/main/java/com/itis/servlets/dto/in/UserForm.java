package com.itis.servlets.dto.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserForm {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Long avatarId;
    private Long age;
}