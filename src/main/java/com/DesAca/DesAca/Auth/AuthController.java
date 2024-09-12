package com.DesAca.DesAca.Auth;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
private final AuthService authService;

    // Endpoint para simular login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Boolean>> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        boolean isAuthenticated = authService.login(email, password);

        // Retornar 200 OK con el resultado del login
        Map<String, Boolean> response = new HashMap<>();
        response.put("authenticated", isAuthenticated);

        return ResponseEntity.ok(response);
    }
}
