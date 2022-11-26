package com.anonimos.springboot.app.rents.resource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Lessor {
    private Long id;
    private String name;
    private String email;
    private String username;
    private String password;
}
