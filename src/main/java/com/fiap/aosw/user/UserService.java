package com.fiap.aosw.user;

import java.util.Optional;

public interface UserService {
    User register(String username, String rawPassword);
    Optional<User> findByUsername(String username);
}
