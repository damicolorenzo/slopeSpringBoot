package com.lorenzoproject.slope.service.user;

import com.lorenzoproject.slope.exceptions.AlreadyExistsException;
import com.lorenzoproject.slope.exceptions.ResourceNotFoundException;
import com.lorenzoproject.slope.model.User;
import com.lorenzoproject.slope.repository.UserRepository;
import com.lorenzoproject.slope.request.CreateUserRequest;
import com.lorenzoproject.slope.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setEmail(request.getEmail());
                    user.setPhoneNumber(request.getPhoneNumber());
                    user.setBirthDate(request.getBirthDate());
                    user.setUsername(request.getUsername());
                    user.setPassword(request.getPassword()); // FIXME need to encode this
                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException(request.getEmail() + " already exists"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setFirstName(request.getFirstName());
                    existingUser.setLastName(request.getLastName());
                    existingUser.setEmail(request.getEmail());
                    existingUser.setPhoneNumber(request.getPhoneNumber());
                    existingUser.setBirthDate(request.getBirthDate());
                    existingUser.setUsername(request.getUsername());
                    existingUser.setPassword(request.getPassword());
                    return userRepository.save(existingUser);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete, () -> {
                    throw new ResourceNotFoundException("User not found");
                });
    }
}
