package com.senla.rental.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "request")
@NamedEntityGraphs({
        @NamedEntityGraph(name = "graph.request.fetches",
            attributeNodes = {
                @NamedAttributeNode(value = "requestRejection"),
                @NamedAttributeNode(value = "requestStatus")
            }
        )
})
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Request user id is mandatory!")
    @Min(value = 1, message = "Black list user id can't be lesser then 1!")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull(message = "Request car id is mandatory!")
    @Min(value = 0, message = "Request car id can't be negative!")
    @Column(name = "car_id", nullable = false)
    private Long carId;

    @NotNull(message = "Request start time is mandatory!")
    @Column(name = "start_time", nullable = false)
    private Timestamp startTime;

    @NotNull(message = "Request end time is mandatory!")
    @Column(name = "end_time", nullable = false)
    private Timestamp endTime;

    @NotNull(message = "Price is mandatory!")
    @Min(value = 0, message = "Price can not be lesser than 0!")
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @OneToOne(targetEntity = RequestRejection.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "requestrejection_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private RequestRejection requestRejection;

    @OneToOne(targetEntity = RequestStatus.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "requeststatus_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private RequestStatus requestStatus;

}
