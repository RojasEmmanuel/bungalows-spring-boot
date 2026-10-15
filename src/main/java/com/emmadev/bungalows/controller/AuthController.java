package com.emmadev.bungalows.controller;

import com.emmadev.bungalows.DTO.auth.LoginRequest;
import com.emmadev.bungalows.DTO.auth.UsuarioResponse;
import com.emmadev.bungalows.security.UsuarioPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponse> login(
            @Valid @RequestBody LoginRequest req,
            HttpServletRequest request,
            HttpServletResponse response) {

        // 1. Creamos un token "sin autenticar" con las credenciales
        var token = new UsernamePasswordAuthenticationToken(req.username(), req.password());

        // 2. AuthenticationManager verifica contra el UserDetailsService + PasswordEncoder.
        //    Si falla, lanza BadCredentialsException.
        Authentication auth = authenticationManager.authenticate(token);

        // 3. Guardamos la autenticación en el SecurityContext (por hilo)
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        // 4. Persistimos el contexto en la HttpSession, para que las siguientes
        //    peticiones con la cookie JSESSIONID lo recuperen.
        new HttpSessionSecurityContextRepository().saveContext(context, request, response);

        UsuarioPrincipal principal = (UsuarioPrincipal) auth.getPrincipal();
        var u = principal.getUsuario();

        return ResponseEntity.ok(new UsuarioResponse(u.getId(), u.getUsername(), u.getNombre()));
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> me(Authentication auth) {
        if (auth == null || !(auth.getPrincipal() instanceof UsuarioPrincipal p)) {
            return ResponseEntity.status(401).build();
        }
        var u = p.getUsuario();
        return ResponseEntity.ok(new UsuarioResponse(u.getId(), u.getUsername(), u.getNombre()));
    }

    // Logout: lo maneja Spring Security en la SecurityFilterChain
    // (POST /api/auth/logout ya está mapeado arriba)
}