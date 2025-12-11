package com.example.bds.modules.iam.service;

import com.example.bds.modules.iam.entity.User;
import com.example.bds.modules.iam.repository.UserRepository;
import com.example.bds.modules.iam.dto.User.UserDTO;
import com.example.bds.modules.iam.dto.User.CreateUserDTO;
import com.example.bds.modules.iam.dto.User.UpdateUserDTO;
import com.example.bds.modules.iam.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO createUser(CreateUserDTO createUserDTO) {
        if(userRepository.existsByUsername(createUserDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if(userRepository.existsByEmail(createUserDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if(userRepository.existsByPhone(createUserDTO.getPhone())) {
            throw new IllegalArgumentException("Phone number already exists");
        }
        User user = userMapper.toEntity(createUserDTO);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public UserDTO updateUser(UpdateUserDTO updateUserDTO) {
        User user = userRepository.findById(updateUserDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Cập nhật các field từ DTO
        userMapper.updateEntity(user, updateUserDTO);

        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }

    public void deleteUser(Long userId) {
        if(!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(userId);
    }
}
