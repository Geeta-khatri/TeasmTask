package com.swagger.p1.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class authRequestDTO {

    private String username;
    private String password;
}
