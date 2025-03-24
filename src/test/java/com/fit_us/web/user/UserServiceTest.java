package com.fit_us.web.user;

import com.fit_us.web.user.application.UserService;
import com.fit_us.web.user.application.dto.CreateUserCommand;
import com.fit_us.web.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Test
    void 유저_생성_테스트(){
        //given
        CreateUserCommand.ProfileInfo profileInfo = new CreateUserCommand.ProfileInfo(
                "url",
                "2001-11-07",
                "MALE",
                "RH_PLUS_B"
        );
        CreateUserCommand command = new CreateUserCommand(
                "testName",
                "testNickname",
                "testOauthId",
                "testProvider",
                "testEmail",
                profileInfo
        );
        //when
        User user = userService.create(command);

        //then
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(command.getName(), user.getName());
        Assertions.assertEquals(command.getNickname(), user.getNickname());
        Assertions.assertEquals(command.getEmail(), user.getEmail());
    }
}
