package com.senla.authorization.dto.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddOrRemoveAuthoritiesDto implements Serializable {

    private Long roleId;
    private List<Long> authoritiesIds;

}
