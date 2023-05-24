package com.senla.common.constants.pagination;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDto {

    @NotNull(message = "Page is mandatory!")
    @Min(value = 1, message = "Page can not be lesser than 1!")
    private Integer page;

    @NotNull(message = "Page size is mandatory!")
    @Min(value = 1, message = "Page size can not be lesser than 1!")
    private Integer pageSize;

}
