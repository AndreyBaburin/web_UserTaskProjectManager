package com.aston.andrey_baburin.entity;

import lombok.*;

import java.util.List;
@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    private int id;
    private String definition;
    private List<Task> tasksOfProject;
}
