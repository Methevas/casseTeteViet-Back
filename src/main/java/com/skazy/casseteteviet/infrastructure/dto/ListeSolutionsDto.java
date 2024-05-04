package com.skazy.casseteteviet.infrastructure.dto;

import lombok.Data;

import java.util.List;
@Data
public class ListeSolutionsDto {

    private List<SolutionDto> solutions;

    public ListeSolutionsDto(List<SolutionDto> solutionDtoList){
        this.solutions = solutionDtoList;
    }

}
