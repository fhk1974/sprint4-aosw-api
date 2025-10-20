package com.fiap.aosw.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override @Transactional
    public User register(String username, String rawPassword) {
        if (repository.existsByUsername(username)) {
            throw new IllegalArgumentException("Usuário já existe");
        }
        User u = new User(username, encoder.encode(rawPassword), Role.USER);
        return repository.save(u);
    }

    @Override
    public Optional<User> findByUsername(String username) { return repository.findByUsername(username); }
}
