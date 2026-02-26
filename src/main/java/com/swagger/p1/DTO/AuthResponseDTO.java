package com.swagger.p1.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponseDTO {

   // private String token;

	private TokenDTOResponse TokenDTOResponse;
//    public AuthResponseDTO(String token) {
//        this.token = token;
//    }
    

}
