package com.senla.authorization.dto;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto implements Serializable {
    private Long id;
    private String name;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<AuthorityDto> authorities;

    public RoleDto(Long id, String name) {
        this.id = id;
        this.name = name;
        authorities = new ArrayList<>();
    }
}
