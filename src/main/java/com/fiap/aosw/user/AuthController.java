package com.fiap.aosw.user;

import com.fiap.aosw.common.ApiResponse;
import com.fiap.aosw.security.jwt.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

record AuthRequest(String username, String password) {}
record AuthResponse(String token) {}

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Registro e login com JWT")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtService jwt;

    public AuthController(UserService userService, AuthenticationManager authManager, JwtService jwt) {
        this.userService = userService;
        this.authManager = authManager;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário")
    public ResponseEntity<ApiResponse> register(@RequestBody AuthRequest req) {
        userService.register(req.username(), req.password());
        return ResponseEntity.ok(new ApiResponse("Usuário registrado"));
    }

    @PostMapping("/login")
    @Operation(summary = "Autenticar e obter token JWT")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(req.username(), req.password()));
            String token = jwt.generate(req.username());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).build();
        }
    }
}
