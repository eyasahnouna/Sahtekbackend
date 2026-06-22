package com.sahtek.sahtek.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Users {

    private String uid;
    private String email;
    private String nomComplet;
    private LocalDateTime createdAt;
}
