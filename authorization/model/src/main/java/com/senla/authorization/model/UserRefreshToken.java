package com.senla.authorization.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserRefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User is mandatory!")
    @OneToOne(targetEntity = UserData.class, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private UserData userData;

    @NotNull(message = "Role name is mandatory!")
    @Size(min = 3, max = 64, message = "Role name must contains from 3 to 64 characters!")
    @Column(name = "refresh_token", length = 1000, nullable = false)
    private String refreshToken;

    public UserRefreshToken(UserData userData, String refreshToken) {
        this.userData = userData;
        this.refreshToken = refreshToken;
    }
}
