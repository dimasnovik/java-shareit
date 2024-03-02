package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.validation.CreateInfo;
import ru.practicum.shareit.validation.NullOrEmail;
import ru.practicum.shareit.validation.UpdateInfo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class UserDto {
    private int id;
    @NotBlank(groups = CreateInfo.class)
    private String name;
    @NotBlank(groups = CreateInfo.class)
    @Email(groups = CreateInfo.class)
    @NullOrEmail(groups = UpdateInfo.class)
    private String email;
}
