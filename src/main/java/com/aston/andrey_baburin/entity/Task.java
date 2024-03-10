package com.aston.andrey_baburin.entity;

import lombok.*;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private int id;
    private String title;
    private User user;
}
