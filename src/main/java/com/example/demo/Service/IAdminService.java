package com.example.demo.Service;

import com.example.demo.dto.Response.UserResponseDto;

public interface IAdminService {
    UserResponseDto buscarTodosLosUsuarios();

    UserResponseDto buscarPorId(Long id);

}
