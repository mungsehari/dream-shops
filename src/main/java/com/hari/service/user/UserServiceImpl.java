package com.hari.service.user;

import com.hari.exception.AlreadyExistsException;
import org.springframework.stereotype.Service;

import com.hari.exception.ResourceNotFoundException;
import com.hari.model.User;
import com.hari.repository.UserRepository;
import com.hari.request.CreateUserRequest;
import com.hari.request.UserUpdateRequest;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;;

    @Override
    public User getUserById(Long UserId) {
        return userRepository.findById(UserId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User CreateUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user->!userRepository.existsByEmail(request.getEmail()))
                .map(req->{
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setPassword(request.getPassword());
                    return  userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException("Oops!"+ request.getEmail()+"already exists"));

    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);

        }).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
            throw new ResourceNotFoundException("User not found");
        });

    }

}
