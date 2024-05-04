package com.skazy.casseteteviet.infrastructure.mapper;

import com.skazy.casseteteviet.infrastructure.dto.SolutionDto;
import com.skazy.casseteteviet.infrastructure.entite.Solution;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface SolutionMapper {


    SolutionDto solutionToSolutionDto(Solution solution);

    Solution solutionDtoToSolution(SolutionDto solutionDto);

    List<SolutionDto> listeSolutionToListeSolutionDto(List<Solution> listeSolution);

}
