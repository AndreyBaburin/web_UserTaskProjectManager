package com.aston.andrey_baburin.entity.dto;

import lombok.*;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private int id;
    private String title;
    private UserDto userDto;
}
