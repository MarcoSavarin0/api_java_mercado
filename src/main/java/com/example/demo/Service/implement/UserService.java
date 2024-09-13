package com.example.demo.Service.implement;

import com.example.demo.Service.IUserService;
import com.example.demo.dto.Request.UserRequestLoginDto;
import com.example.demo.dto.Response.UserResponseDto;
import com.example.demo.entity.User;

public class UserService implements IUserService {
    @Override
    public UserResponseDto guardarUser(User user) {
        return null;
    }

    @Override
    public UserResponseDto loginUser(UserRequestLoginDto user) {
        return null;
    }

    @Override
    public void editarUsuario(User user) {

    }

    @Override
    public void eliminarUsuario(User user) {

    }
}
