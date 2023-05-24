package com.senla.rental.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RefundCompensation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Refund compensation title is mandatory!")
    @Size(min = 5, max = 255, message = "Request rejection must contains from 5 to 255 characters!")
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Size(max = 1000, message = "Refund compensation can't be larger then 1000 characters!")
    @Column(name = "text", length = 1000, nullable = false)
    private String text;

    @NotNull(message = "Refund compensation is paid can't be null!")
    @Column(name = "is_paid", nullable = false, columnDefinition = "boolean default false")
    private Boolean isPaid;

    @NotNull(message = "Refund compensation is paid can't be null!")
    @Min(value = 0, message = "Refund compensation cost can't be negative!")
    @Column(name = "cost", nullable = false, columnDefinition = "double precision default 0")
    private Double cost;

}
