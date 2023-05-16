package com.senla.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlackListDto implements Serializable {
    private Long id;
    private Long userId;
    private String cause;

}
