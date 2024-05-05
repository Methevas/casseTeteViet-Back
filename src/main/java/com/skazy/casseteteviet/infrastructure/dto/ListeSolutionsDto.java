package com.skazy.casseteteviet.infrastructure.dto;

import lombok.Data;

import java.util.List;
@Data
public class ListeSolutionsDto {

    private List<SolutionDto> solutions;
    private Long tempsCalcul;

    public ListeSolutionsDto(List<SolutionDto> solutionDtoList, Long tempsCalcul){
        this.solutions = solutionDtoList;
        this.tempsCalcul = tempsCalcul;
    }

}
