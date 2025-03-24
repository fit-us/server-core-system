package com.fit_us.web.user.application;

import com.fit_us.web.user.application.dto.CreateUserCommand;
import com.fit_us.web.user.domain.Profile;
import com.fit_us.web.user.domain.User;
import com.fit_us.web.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User create(CreateUserCommand command) {
        User newUser = createUser(command);
        Profile newProfile = createProfile(command.getProfile());
        newUser.addProfile(newProfile);
        return userRepository.save(newUser);
    }
    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("cannot found user"));
        user.delete();
        userRepository.save(user);
    }
    private User createUser(CreateUserCommand command) {
        if(command == null){
            throw new IllegalArgumentException("CreateUserCommand cannot be null.");
        }
        return User.create(
                command.getName(),
                command.getNickname(),
                command.getOauthId(),
                command.getProvider(),
                command.getEmail()
        );
    }

    private Profile createProfile(CreateUserCommand.ProfileInfo command) {
        if(command == null){
            throw new  IllegalArgumentException("Profile information cannot be null.");
        }
        return Profile.create(
                command.getProfileImageUrl(),
                command.getBirthDate(),
                command.getGender(),
                command.getBloodType()
        );
    }
}
