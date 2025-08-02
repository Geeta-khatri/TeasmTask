package com.swagger.p1.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectDTOResponse {
  private String name;
    private String description;
    private Long UserId;
    private Long id;
}
