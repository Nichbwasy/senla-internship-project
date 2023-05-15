package com.senla.car.dto.client;

import com.senla.car.dto.controllers.CarsCatalogFilterForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarsCatalogBodyFormDto {

    private Integer page;
    private CarsCatalogFilterForm carsCatalogFilterForm;

}
