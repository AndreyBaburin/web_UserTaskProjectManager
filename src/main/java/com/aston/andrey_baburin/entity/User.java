package com.aston.andrey_baburin.entity;
import lombok.*;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int id;
    private String name;
    private String email;
}
