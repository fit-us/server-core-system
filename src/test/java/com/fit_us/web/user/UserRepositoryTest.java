package com.fit_us.web.user;

import com.fit_us.web.user.domain.User;
import com.fit_us.web.user.infrastructure.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@Slf4j
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;
    @BeforeEach
    void setup(){
        //Test 용 사용자 생성
        user = User.create(
                "testName",
                "testNickname",
                "testOauthId",
                "testProvider",
                "testEmail"
        );
    }
    @Test
    void 유저_생성에_따른_PK_부여(){
        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo(user.getName());
        assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
    }

}
