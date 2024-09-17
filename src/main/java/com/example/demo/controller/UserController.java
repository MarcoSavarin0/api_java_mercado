package com.example.demo.controller;

import com.example.demo.Service.implement.UserService;
import com.example.demo.dto.Request.UserRequestLoginDto;
import com.example.demo.dto.Response.UserResponseDto;
import com.example.demo.entity.User;
import com.example.demo.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<UserResponseDto> guardarUser(@Valid @RequestBody User user) {
        UserResponseDto guardarUser = userService.guardarUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardarUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> loginUser(@Valid @RequestBody UserRequestLoginDto user) {
        UserResponseDto loginUser = userService.loginUser(user);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(loginUser);
    }

    @PutMapping
    public ResponseEntity<?> editarUser(@Valid @RequestBody User user) {
        userService.editarUsuario(user);
        return ResponseEntity.status(HttpStatus.OK).body("editado");
    }

    @DeleteMapping
    public ResponseEntity<?> eliminarUser(@Valid @RequestBody User user) {
        userService.eliminarUsuario(user);
        return ResponseEntity.status(HttpStatus.OK).body("Eliminado");
    }
    @GetMapping("/auth")
    public ResponseEntity<?> authUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        return ResponseEntity.status(HttpStatus.OK).body(userService.authUser(token));
    }
}
