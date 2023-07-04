package com.senla.email.model;

import com.senla.common.constants.email.RestorePasswordRequestStatuses;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "restorepasswordrequest")
public class RestorePasswordRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Login is mandatory!")
    @Size(min = 3, max = 128, message = "Login's length must be from 3 to 128 characters!")
    @Column(name = "login", length = 128, nullable = false)
    private String login;

    @NotNull(message = "Email is mandatory!")
    @Size(min = 3, max = 128, message = "Email's length must be from 3 to 128 characters!")
    @Column(name = "email", length = 128, nullable = false)
    private String email;

    @NotNull(message = "Time is mandatory!")
    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @NotNull(message = "Sending status is mandatory!")
    @Size(max = 64, message = "Sending status can't contains more than 64 characters!")
    @Column(name = "sending_status", length = 64, nullable = false, columnDefinition = RestorePasswordRequestStatuses.NOT_SEND)
    private String sendingStatus;

    @NotNull(message = "Response queue name is mandatory!")
    @Size(max = 128, message = "Response queue name can't be larger than 128 characters!")
    @Column(name = "response_queue_name", length = 128, nullable = false)
    private String responseQueueName;


}
