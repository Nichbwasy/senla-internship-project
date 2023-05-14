package com.senla.authorization.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "userdata")
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "UserData login is mandatory!")
    @Size(min = 3, max = 128, message = "UserData login must contains from 3 to 128 characters!")
    @Column(name = "login", length = 128, unique = true, nullable = false)
    private String login;

    @NotNull(message = "UserData password is mandatory!")
    @Size(min = 3, max = 500, message = "UserData password must contains from 3 to 255 characters!")
    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "role_user",
            joinColumns = @JoinColumn(name =  "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

}