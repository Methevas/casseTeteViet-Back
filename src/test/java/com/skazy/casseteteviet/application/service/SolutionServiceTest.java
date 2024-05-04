package com.skazy.casseteteviet.application.service;

import com.google.gson.Gson;
import com.skazy.casseteteviet.application.service.impl.SolutionServiceImpl;
import com.skazy.casseteteviet.infrastructure.dto.ListeSolutionsDto;
import com.skazy.casseteteviet.infrastructure.dto.SolutionDto;
import com.skazy.casseteteviet.infrastructure.entite.Solution;
import com.skazy.casseteteviet.infrastructure.exception.*;
import com.skazy.casseteteviet.infrastructure.mapper.SolutionMapper;
import com.skazy.casseteteviet.infrastructure.repository.SolutionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SolutionServiceTest {
    @Mock
    SolutionRepository solutionRepository;

    @InjectMocks
    SolutionServiceImpl solutionServiceImpl = new SolutionServiceImpl();

    @Mock
    SolutionMapper solutionMapper;

    Long ID_SOLUTION = 1L;
    String VALEURS_OK = "1,2,3,4,5,6,7,8,9";
    String VALEURS_MAUVAIS_FORMAT = "1,2,3,4,5,,7,8,9";
    String VALEURS_MAUVAIS_NB_VALEURS = "1,2,3,4,5,7,8,9";
    String VALEURS_PAS_AUTORISEE = "1,2,3,4,5,6,77,8,9";
    String VALEURS_DUPLIQUE = "1,2,3,7,5,6,7,8,9";

    String MESSAGE_OK = "la sauvegarde à bien été prise en compte";

    @Test
    void doitRenvoyerUneSolution() {
        Solution solution = new Solution();
        solution.setValeurs(VALEURS_OK);
        solution.setId(ID_SOLUTION);
        SolutionDto solutionDto = new SolutionDto();
        solutionDto.setValeurs(VALEURS_OK);
        solutionDto.setId(ID_SOLUTION);

        when(solutionRepository.findOneById(ID_SOLUTION)).thenReturn(solution);
        when(solutionMapper.solutionToSolutionDto(solution)).thenReturn(solutionDto);

        ResponseEntity<SolutionDto> responseEntityTemoin = new ResponseEntity<>(solutionDto, HttpStatus.OK);
        ResponseEntity<SolutionDto> responseEntityResult = solutionServiceImpl.getSolution(ID_SOLUTION);

        Assertions.assertEquals(responseEntityResult, responseEntityTemoin);
        Mockito.verify(solutionRepository, Mockito.times(1)).findOneById(any());
    }
    @Test
    void neDoitPasRenvoyerDeSolution() {

        when(solutionRepository.findOneById(ID_SOLUTION)).thenReturn(null);
        when(solutionMapper.solutionToSolutionDto(null)).thenReturn(null);

        ResponseEntity<SolutionDto> responseEntityTemoin = new ResponseEntity<>(new SolutionDto(), HttpStatus.NOT_FOUND);
        ResponseEntity<SolutionDto> responseEntityResult = solutionServiceImpl.getSolution(ID_SOLUTION);

        Assertions.assertEquals(responseEntityResult, responseEntityTemoin);
        Mockito.verify(solutionRepository, Mockito.times(1)).findOneById(any());
    }

    @Test
    void doitRenvoyerUneListeDeSolution_15() {
        List<Solution> solutions = remplissageListeSolution(15);
        List<SolutionDto> solutionsDto = remplissageListeSolutionDto(15);
        ListeSolutionsDto listeSolutionsDtoTemoin = new ListeSolutionsDto(solutionsDto);

        when(solutionRepository.findAll()).thenReturn(solutions);
        when(solutionMapper.listeSolutionToListeSolutionDto(solutions)).thenReturn(solutionsDto);

        ListeSolutionsDto listeSolutionsDtoResult = solutionServiceImpl.getSolutions();

        Assertions.assertEquals(listeSolutionsDtoResult, listeSolutionsDtoTemoin);
        Mockito.verify(solutionRepository, Mockito.times(1)).findAll();
    }

    @Test
    void doitModifierUneSolution() throws Exception {
        Gson gson = new Gson();
        Solution solution = new Solution();
        solution.setValeurs(VALEURS_OK);
        solution.setId(ID_SOLUTION);

        SolutionDto solutionDto = new SolutionDto();
        solutionDto.setValeurs(VALEURS_OK);
        solutionDto.setId(ID_SOLUTION);

        ResponseEntity<String> responseEntityTemoin = new ResponseEntity<>(gson.toJson(MESSAGE_OK), HttpStatus.OK);

        when(solutionRepository.existsSolutionById(ID_SOLUTION)).thenReturn(true);
        when(solutionMapper.solutionDtoToSolution(solutionDto)).thenReturn(solution);

        ResponseEntity<String> responseEntityResult = solutionServiceImpl.saveSolution(solutionDto);

        Assertions.assertEquals(responseEntityResult, responseEntityTemoin);
        Mockito.verify(solutionRepository, Mockito.times(1)).existsSolutionById(ID_SOLUTION);
        Mockito.verify(solutionRepository, Mockito.times(1)).save(solution);
    }

    @Test
    void doitThrowUneEXCEPTION_FormatSolutionException() throws Exception {
        Gson gson = new Gson();
        FormatSolutionException exception = new FormatSolutionException();
        Solution solution = new Solution();
        solution.setValeurs(VALEURS_MAUVAIS_FORMAT);
        solution.setId(ID_SOLUTION);

        SolutionDto solutionDto = new SolutionDto();
        solutionDto.setValeurs(VALEURS_MAUVAIS_FORMAT);
        solutionDto.setId(ID_SOLUTION);

        ResponseEntity<String> responseEntityTemoin = new ResponseEntity<>(gson.toJson(exception.getMessage()), HttpStatus.BAD_REQUEST);

        when(solutionMapper.solutionDtoToSolution(solutionDto)).thenReturn(solution);

        ResponseEntity<String> responseEntityResult = solutionServiceImpl.saveSolution(solutionDto);

        Assertions.assertEquals(responseEntityResult, responseEntityTemoin);
        Mockito.verify(solutionRepository, Mockito.times(0)).existsSolutionById(ID_SOLUTION);
        Mockito.verify(solutionRepository, Mockito.times(0)).save(solution);
    }
    @Test
    void doitThrowUneEXCEPTION_NombreDeValeursException() throws Exception {
        Gson gson = new Gson();
        NombreDeValeursException exception = new NombreDeValeursException();
        Solution solution = new Solution();
        solution.setValeurs(VALEURS_MAUVAIS_NB_VALEURS);
        solution.setId(ID_SOLUTION);

        SolutionDto solutionDto = new SolutionDto();
        solutionDto.setValeurs(VALEURS_MAUVAIS_NB_VALEURS);
        solutionDto.setId(ID_SOLUTION);

        ResponseEntity<String> responseEntityTemoin = new ResponseEntity<>(gson.toJson(exception.getMessage()), HttpStatus.BAD_REQUEST);

        when(solutionMapper.solutionDtoToSolution(solutionDto)).thenReturn(solution);

        ResponseEntity<String> responseEntityResult = solutionServiceImpl.saveSolution(solutionDto);

        Assertions.assertEquals(responseEntityResult, responseEntityTemoin);
        Mockito.verify(solutionRepository, Mockito.times(0)).existsSolutionById(ID_SOLUTION);
        Mockito.verify(solutionRepository, Mockito.times(0)).save(solution);
    }

    @Test
    void doitThrowUneEXCEPTION_NombreAutoriseException() throws Exception {
        Gson gson = new Gson();
        NombreAutoriseException exception = new NombreAutoriseException();
        Solution solution = new Solution();
        solution.setValeurs(VALEURS_PAS_AUTORISEE);
        solution.setId(ID_SOLUTION);

        SolutionDto solutionDto = new SolutionDto();
        solutionDto.setValeurs(VALEURS_PAS_AUTORISEE);
        solutionDto.setId(ID_SOLUTION);

        ResponseEntity<String> responseEntityTemoin = new ResponseEntity<>(gson.toJson(exception.getMessage()), HttpStatus.BAD_REQUEST);

        when(solutionMapper.solutionDtoToSolution(solutionDto)).thenReturn(solution);

        ResponseEntity<String> responseEntityResult = solutionServiceImpl.saveSolution(solutionDto);

        Assertions.assertEquals(responseEntityResult, responseEntityTemoin);
        Mockito.verify(solutionRepository, Mockito.times(0)).existsSolutionById(ID_SOLUTION);
        Mockito.verify(solutionRepository, Mockito.times(0)).save(solution);
    }
    @Test
    void doitThrowUneEXCEPTION_ValeurDupliqueeException() throws Exception {
        Gson gson = new Gson();
        ValeurDupliqueeException exception = new ValeurDupliqueeException();
        Solution solution = new Solution();
        solution.setValeurs(VALEURS_DUPLIQUE);
        solution.setId(ID_SOLUTION);

        SolutionDto solutionDto = new SolutionDto();
        solutionDto.setValeurs(VALEURS_DUPLIQUE);
        solutionDto.setId(ID_SOLUTION);

        ResponseEntity<String> responseEntityTemoin = new ResponseEntity<>(gson.toJson(exception.getMessage()), HttpStatus.BAD_REQUEST);

        when(solutionMapper.solutionDtoToSolution(solutionDto)).thenReturn(solution);

        ResponseEntity<String> responseEntityResult = solutionServiceImpl.saveSolution(solutionDto);

        Assertions.assertEquals(responseEntityResult, responseEntityTemoin);
        Mockito.verify(solutionRepository, Mockito.times(0)).existsSolutionById(ID_SOLUTION);
        Mockito.verify(solutionRepository, Mockito.times(0)).save(solution);
    }
    @Test
    void doitThrowUneEXCEPTION_SolutionNouvelleException() throws Exception {
        Gson gson = new Gson();
        SolutionNouvelleException exception = new SolutionNouvelleException();
        Solution solution = new Solution();
        solution.setValeurs(VALEURS_OK);
        solution.setId(ID_SOLUTION);

        SolutionDto solutionDto = new SolutionDto();
        solutionDto.setValeurs(VALEURS_DUPLIQUE);
        solutionDto.setId(ID_SOLUTION);

        ResponseEntity<String> responseEntityTemoin = new ResponseEntity<>(gson.toJson(exception.getMessage()), HttpStatus.BAD_REQUEST);

        when(solutionRepository.existsSolutionById(ID_SOLUTION)).thenReturn(false);
        when(solutionMapper.solutionDtoToSolution(solutionDto)).thenReturn(solution);

        ResponseEntity<String> responseEntityResult = solutionServiceImpl.saveSolution(solutionDto);

        Assertions.assertEquals(responseEntityResult, responseEntityTemoin);
        Mockito.verify(solutionRepository, Mockito.times(1)).existsSolutionById(ID_SOLUTION);
        Mockito.verify(solutionRepository, Mockito.times(0)).save(solution);
    }

    private List<Solution> remplissageListeSolution(int nbOfSolutions) {
        List<Solution> solutions = new ArrayList<>();
        for (int i = 0; i < nbOfSolutions; i++) {
            Solution solution = new Solution();
            solution.setValeurs(VALEURS_OK);
            solution.setId(ID_SOLUTION + i);
            solutions.add(solution);
        }
        return solutions;
    }

    private List<SolutionDto> remplissageListeSolutionDto(int nbOfSolutionsDto) {
        List<SolutionDto> solutions = new ArrayList<>();
        for (int i = 0; i < nbOfSolutionsDto; i++) {
            SolutionDto solution = new SolutionDto();
            solution.setValeurs(VALEURS_OK);
            solution.setId(ID_SOLUTION + i);
            solutions.add(solution);
        }
        return solutions;
    }

}