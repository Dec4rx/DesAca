package com.DesAca.DesAca.Authorizer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorizerDTO {
    private Long id;
    private String name;
    private String position;
    private String authKey;
    private String email;
    private String password;
}
