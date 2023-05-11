package com.senla.authorization.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Role name is mandatory!")
    @Size(min = 3, max = 64, message = "Role name must contains from 3 to 64 characters!")
    @Column(name = "name", length = 64, unique = true, nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "role_authority",
            joinColumns = @JoinColumn(name =  "role_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Authority> authorities = new ArrayList<>();

}