package com.senla.rental.model;

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
public class RequestStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Request status name is mandatory!")
    @Size(min = 3, max = 128, message = "Request status name must contains from 3 to 128 characters!")
    @Column(name = "name", length = 128, unique = true, nullable = false)
    private String name;


}
