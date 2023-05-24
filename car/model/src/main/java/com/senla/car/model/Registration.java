package com.senla.car.model;

import com.senla.common.dao.model.ModelEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Registration implements ModelEntity<Long>  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Registration number is mandatory!")
    @Size(min = 32, max = 32, message = "Registration number must contains 32 characters!")
    @Column(name = "number", length = 32, unique = true, nullable = false)
    private String number;

    @NotNull(message = "Registration model name is mandatory!")
    @Size(min = 3, max = 255, message = "Registration number must contains from 3 to 255 characters!")
    @Column(name = "model", length = 255, nullable = false)
    private String model;

    @NotNull(message = "Registration model name is mandatory!")
    @Min(value = 1900, message = "Registration year can't be lesser then 1900!")
    @Column(name = "release_year", nullable = false)
    private Integer releaseYear;

    @NotNull(message = "Registration body number is mandatory!")
    @Size(min = 3, max = 64, message = "Registration body number must contains from 3 to 64 characters!")
    @Column(name = "body_number", length = 64, nullable = false)
    private String bodyNumber;

    @NotNull(message = "Registration allowed max mass is mandatory!")
    @Min(value = 0, message = "Registration allowed max mass can't be lesser then 0!")
    @Column(name = "allowed_max_mass", nullable = false)
    private Integer allowedMaxMass;

    @NotNull(message = "Registration unladen mass is mandatory!")
    @Min(value = 0, message = "Registration unladen mass can't be lesser then 0!")
    @Column(name = "unladen_mass", nullable = false)
    private Integer unladenMass;

    @NotNull(message = "Registration color is mandatory!")
    @OneToOne(targetEntity = Color.class, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Color color;

    @NotNull(message = "Registration type is mandatory!")
    @OneToOne(targetEntity = Type.class, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Type type;

    public Registration(Long id) {
        this.id = id;
    }

}
