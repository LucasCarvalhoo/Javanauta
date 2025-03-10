package com.carvalho.aprendendospring.controller;

import com.carvalho.aprendendospring.business.UsuarioService;
import com.carvalho.aprendendospring.dtos.UsuarioDTO;
import com.carvalho.aprendendospring.infrastructure.entity.Usuario;
import com.carvalho.aprendendospring.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<Usuario> salvaUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuario));
    }

    @GetMapping
    public ResponseEntity<Usuario> buscaUsuarioPorEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuarioDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha())
        );
        return jwtUtil.generateToken(authentication.getName());
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email){
        usuarioService.deletaUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }
}
