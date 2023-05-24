package com.senla.car.model;

import com.senla.common.dao.model.ModelEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Color implements ModelEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Color name is mandatory!")
    @Size(min = 3, max = 255, message = "Color name must contains from 3 to 255 characters!")
    @Column(name = "name", length = 255, unique = true, nullable = false)
    private String name;
}
