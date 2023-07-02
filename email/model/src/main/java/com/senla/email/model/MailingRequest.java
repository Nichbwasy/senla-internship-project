package com.senla.email.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "mailingrequest")
public class MailingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Recipient email is mandatory!")
    @Size(min = 3, max = 128, message = "Recipient email must contains from 3 to 128 characters!")
    @Column(name = "recipient_email", length = 128, nullable = false, unique = true)
    private String recipientEmail;

    @NotNull(message = "Response queue name is mandatory!")
    @Size(max = 128, message = "Response queue name can't be larger than 128 characters!")
    @Column(name = "response_queue_name", length = 128, nullable = false)
    private String responseQueueName;

}
