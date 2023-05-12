package com.senla.authorization.dao;

import com.senla.authorization.model.UserData;
import com.senla.authorization.model.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    Boolean existsByUserData(UserData user);
    UserRefreshToken findByUserData(UserData user);
}
