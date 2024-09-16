package com.example.demo.Service.implement;

import com.example.demo.Service.IUserService;
import com.example.demo.dto.Request.UserRequestLoginDto;
import com.example.demo.dto.Response.UserResponseDto;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.jwt.UserPrincipal;
import com.example.demo.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {
    private IUserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserResponseDto guardarUser(User user) {
        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        User userSave = userRepository.save(user);
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(userSave.getId());
        responseDto.setNombre(userSave.getNombre());
        responseDto.setApellido(userSave.getApellido());
        responseDto.setEmail(userSave.getEmail());
        responseDto.setToken(" ");
        return responseDto;
    }

    @Override
    public UserResponseDto loginUser(UserRequestLoginDto user) {
        User userExist = userRepository.findByEmail(user.getEmail());
        if (userExist != null) {
            boolean passwordMatch = passwordEncoder.matches(user.getPassword(), userExist.getPassword());
            if (passwordMatch) {
                UserPrincipal userPrincipal = new UserPrincipal(userExist);
                String jwt = jwtUtil.generateToken(userPrincipal);
                UserResponseDto responseDto = new UserResponseDto();
                responseDto.setId(userExist.getId());
                responseDto.setNombre(userExist.getNombre());
                responseDto.setApellido(userExist.getApellido());
                responseDto.setEmail(userExist.getEmail());
                responseDto.setToken(jwt);
                return responseDto;
            } else {
                throw new BadRequestException("La contraseña o email es incorrecto");
            }
        } else {
            throw new BadRequestException("La contraseña o email es incorrecto");
        }
    }


    @Override
    public void editarUsuario(User user) {
        Optional<User> userMatch = userRepository.findById(user.getId());
        if (userMatch.isPresent()) {
            logger.info("Se actualizo el usuario");
            userRepository.save(user);
        } else {
            logger.info("no se encontro el usuario");
            throw new ResourceNotFoundException("No se pudo editar el usuario");
        }
    }

    @Override
    public void eliminarUsuario(User user) {
        Optional<User> userMatch = userRepository.findById(user.getId());
        if (userMatch.isPresent()) {
            logger.info("Se actualizo el usuario");
            userRepository.deleteById(user.getId());
        } else {
            logger.info("no se encontro el usuario");
            throw new ResourceNotFoundException("No se pudo editar el usuario");
        }
    }

    @Override
    public User authUser(String token) {
        String email = jwtUtil.extractUserName(token);
        logger.info("Entro al service");
        if (email != null) {
            Optional<User> userMatch = Optional.ofNullable(userRepository.findByEmail(email));
            if (userMatch.isPresent()) {
                logger.info("Se encontró el usuario con email: " + email);
                return userMatch.get();
            } else {
                logger.info("No se encontró el usuario con email: " + email);
                throw new ResourceNotFoundException("No se encontró el usuario");
            }
        } else {
            logger.info("El token no es válido o no contiene email");
            throw new BadRequestException("Token no válido");
        }
    }
}
