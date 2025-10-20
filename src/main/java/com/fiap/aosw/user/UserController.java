package com.fiap.aosw.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuários")
public class UserController {
    @GetMapping("/me")
    @Operation(summary = "Dados do usuário autenticado")
    public String me(Authentication auth) {
        return auth.getName();
    }
}
