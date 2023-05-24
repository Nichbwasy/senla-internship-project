package com.senla.car.dto.controllers;

import com.senla.car.dto.controllers.ordering.CarOrderingFieldDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarsCatalogFilterForm {

    private Boolean filterByMileage = false;
    private Boolean filterByReleaseYear = false;
    private Boolean filterByCondition = false;
    private Boolean filterByStatus = false;
    private Boolean filterByModel = false;
    private Boolean filterByType = false;
    private Boolean filterByColor = false;
    private Boolean enableOrdering = false;

    private Double mileageMin;
    private Double mileageMax;

    private Integer releaseYearMin;
    private Integer releaseYearMax;

    private String conditionName;

    private String modelName;

    private String typeName;

    private String statusName;

    private String colorName;

    private List<CarOrderingFieldDto> orderingFields;

}
