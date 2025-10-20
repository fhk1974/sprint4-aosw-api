package com.fiap.aosw.user;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    @Test
    void register_encodesPassword_andPersists() {
        UserRepository repo = mock(UserRepository.class);
        PasswordEncoder enc = mock(PasswordEncoder.class);
        when(repo.existsByUsername("fabio")).thenReturn(false);
        when(enc.encode("123")).thenReturn("ENC");
        when(repo.save(Mockito.any())).thenAnswer(inv -> inv.getArgument(0));

        UserService service = new UserServiceImpl(repo, enc);
        User u = service.register("fabio", "123");

        assertEquals("fabio", u.getUsername());
        assertEquals("ENC", u.getPassword());
        verify(repo).save(Mockito.any());
    }
}
