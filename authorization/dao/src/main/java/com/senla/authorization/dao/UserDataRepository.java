package com.senla.authorization.dao;

import com.senla.authorization.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDataRepository extends JpaRepository<UserData, Long> {

    @Query(value = "select * from userdata where userdata.login like %?1%", nativeQuery = true)
    List<UserData> selectUsersWithLoginMatches(String login);
    Boolean existsByLogin(String login);
    UserData findByLogin(String login);
}
