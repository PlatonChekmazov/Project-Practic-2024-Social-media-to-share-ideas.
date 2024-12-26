package com.website.idea.services;

import com.website.idea.models.Users.User;
import com.website.idea.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User register(String login, String password, String email, String username) {
        if (userRepository.findByLogin(login).isPresent() || userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Логин или электронная почта уже используеться");
        }

        User user = new User();
        user.setLogin(login);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }

    public User login(String loginOrEmail, String password) {
        User user;
        user = userRepository.findByLogin(loginOrEmail)
                .orElseGet(() -> userRepository.findByEmail(loginOrEmail)
                        .orElseThrow(() -> new IllegalArgumentException("Неверный логин или электронная почта!")));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Неверный пароль!");
        }

        return user;
    }

    public User updateUser(Long userId, String username, String email, String password, String login) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        if (username != null && !username.isEmpty()) {
            updateUsername(user, username);
        }

        if (email != null && !email.isEmpty()) {
            updateEmail(user, email);
        }

        if (password != null && !password.isEmpty()) {
            updatePassword(user, password);
        }

        if (login != null && !login.isEmpty()) {
            updateLogin(user, login);
        }

        return userRepository.save(user);
    }

    private void updateUsername(User user, String username) {
        user.setUsername(username);
    }

    private void updateEmail(User user, String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        user.setEmail(email);
    }

    private void updatePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
    }

    private void updateLogin(User user, String login) {
        if (userRepository.findByLogin(login).isPresent()) {
            throw new IllegalArgumentException("Login already in use");
        }
        user.setLogin(login);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Пользователь не найден с id: " + id));
        userRepository.delete(user);
    }
}