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
public class RequestRejection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Request rejection title is mandatory!")
    @Size(min = 5, max = 255, message = "Request rejection must contains from 5 to 255 characters!")
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Size(max = 1000, message = "Request rejection can't be larger then 1000 characters!")
    @Column(name = "text", length = 1000, nullable = false)
    private String text;

}
