package com.example.demo.Service;

import com.example.demo.dto.Request.UserRequestLoginDto;
import com.example.demo.dto.Response.UserResponseDto;
import com.example.demo.entity.User;

public interface IUserService {
    UserResponseDto guardarUser(User user);

    UserResponseDto loginUser(UserRequestLoginDto user);

    void editarUsuario(User user);

    void eliminarUsuario(User user);
}
