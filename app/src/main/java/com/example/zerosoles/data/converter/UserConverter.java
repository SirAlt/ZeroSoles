package com.example.zerosoles.data.converter;

import com.example.zerosoles.data.dto.UserDto;
import com.example.zerosoles.data.entity.User;

public class UserConverter extends Converter<User, UserDto> {

    public UserConverter() {
        super(UserConverter::convertToEntity, UserConverter::convertToDto);
    }

    private static UserDto convertToDto(User user) {
        return new UserDto(
                user.getUsername(),
                user.getPasswordHash(),
                user.getFullname(),
                user.getEmail(),
                user.getGender().toString(),
                user.getRole().toString(),
                user.isCanShip(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.isActive());
    }

    private static User convertToEntity(UserDto dto) {
        return new User.Builder()
                .withUsername(dto.getUsername())
                .withFullname(dto.getFullname())
                .withEmail(dto.getEmail())
                .withGender(User.Gender.valueOf(dto.getGender()))
                .withRole(User.Role.valueOf(dto.getRole()))
                .withCanShip(dto.isCanShip())
                .withCreatedAt(dto.getCreatedAt())
                .withUpdatedAt(dto.getUpdatedAt())
                .withActive(dto.isActive())
                .build();
    }
}
