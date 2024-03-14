package com.aston.andrey_baburin.entity.dto;


import lombok.*;

import java.util.List;
@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {

    private int id;
    private String definition;
    private List<TaskDto> tasksOfProject;


}
