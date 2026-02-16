package com.swagger.p1.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDTOResponse {

	private String jwtToken;
	private String refreshTokenUUID;
}
