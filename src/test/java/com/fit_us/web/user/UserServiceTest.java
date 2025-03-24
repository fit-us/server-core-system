package com.fit_us.web.user;

import com.fit_us.web.user.application.UserService;
import com.fit_us.web.user.application.dto.CreateUserCommand;
import com.fit_us.web.user.domain.User;
import com.fit_us.web.user.infrastructure.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void clearUsers() {
        userRepository.deleteAll();
    }

    private CreateUserCommand createTestUserCommand() {
        return new CreateUserCommand(
                "testName", "testNickname", "testOauthId", "testProvider", "testEmail",
                new CreateUserCommand.ProfileInfo("url", "2001-11-07", "MALE", "RH_PLUS_B")
        );
    }

    @Test
    void 유저_생성_테스트() {
        // given
        CreateUserCommand command = createTestUserCommand();

        // when
        User user = userService.create(command);

        // then
        assertThat(user.getId()).isNotNull();
        assertThat(user.getName()).isEqualTo(command.getName());
        assertThat(user.getNickname()).isEqualTo(command.getNickname());
        assertThat(user.getEmail()).isEqualTo(command.getEmail());
    }

    @Test
    void 유저_삭제_테스트() {
        // given
        User user = userService.create(createTestUserCommand());

        // when
        userService.delete(user.getId());

        // then
        assertThat(userRepository.findById(user.getId()).orElse(null))
                .extracting(User::getDeletedAt)
                .isNotNull();
    }

    @Test
    void 존재하지_않는_유저_삭제_시_예외발생() {
        // given
        Long nonExistentUserId = 9999L;

        // when & then
        assertThatThrownBy(() -> userService.delete(nonExistentUserId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cannot found user");
    }
}
