package com.senla.authorization.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "authority")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Authority name is mandatory!")
    @Size(min = 3, max = 64, message = "Authority name must contains from 3 to 64 characters!")
    @Column(name = "name", length = 64, unique = true, nullable = false)
    private String name;

}
