package com.senla.car.model;

import com.senla.common.dao.model.ModelEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraphs({
        @NamedEntityGraph(name = "graph.car"),
        @NamedEntityGraph(name = "graph.car.with.all.fetches",
                attributeNodes = {
                        @NamedAttributeNode("condition"),
                        @NamedAttributeNode("status"),
                        @NamedAttributeNode(value = "registration", subgraph = "subgraph.car.registration.color.type")},
                subgraphs = {
                        @NamedSubgraph(
                                name = "subgraph.car.registration.color.type",
                                attributeNodes = {
                                    @NamedAttributeNode("color"),
                                    @NamedAttributeNode("type")
                                }
                        )
                }
        )
    }
)
@Entity
public class Car implements ModelEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", length = 2000)
    private String description;

    @NotNull(message = "Car mileage is mandatory!")
    @Min(value = 0, message = "Car mileage can't be lesser then 0!")
    @Column(name = "mileage", nullable = false, columnDefinition = "double precision default 0")
    private Double mileage;

    @NotNull(message = "Car registration is mandatory!")
    @OneToOne(targetEntity = Registration.class, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Registration registration;

    @NotNull(message = "Car condition is mandatory!")
    @OneToOne(targetEntity = Condition.class, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Condition condition;

    @NotNull(message = "Car registration is mandatory!")
    @OneToOne(targetEntity = Status.class, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Status status;

}
