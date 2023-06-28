package com.senla.authorization.dao;

import com.senla.authorization.model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserDataRepositoryTests {

    @Autowired
    private UserDataRepository userDataRepository;

    @Test
    public void selectUsersWithLoginMatchesTest() {
        userDataRepository.save(new UserData(null, "User1", "pass", null));
        userDataRepository.save(new UserData(null, "User2", "pass", null));
        userDataRepository.save(new UserData(null, "Login1", "pass", null));

        List<UserData> result = userDataRepository.selectUsersWithLoginMatches("User");
        Assertions.assertEquals(2, result.size());
        result.forEach(ud -> Assertions.assertTrue(ud.getLogin().contains("User")));
    }

}
