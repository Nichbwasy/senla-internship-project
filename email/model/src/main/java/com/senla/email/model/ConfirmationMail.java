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
@Entity(name = "confirmationmail")
public class ConfirmationMail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Title is mandatory!")
    @Size(min = 3, max = 512, message = "Title must contains from 3 to 512 characters!")
    @Column(name = "title", length = 512, nullable = false)
    private String title;

    @Size(max = 2048, message = "Text can't be larger than 2048 characters!")
    @Column(name = "text", length = 2048, nullable = false)
    private String text;

    @NotNull(message = "Recipient email is mandatory!")
    @Size(min = 3, max = 128, message = "Recipient email must contains from 3 to 128 characters!")
    @Column(name = "recipient_email", length = 128, nullable = false)
    private String recipientEmail;

}
