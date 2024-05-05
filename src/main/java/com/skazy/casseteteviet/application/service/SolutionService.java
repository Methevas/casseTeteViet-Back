package com.skazy.casseteteviet.application.service;

import com.skazy.casseteteviet.infrastructure.dto.ListeSolutionsDto;
import com.skazy.casseteteviet.infrastructure.dto.SolutionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("casseTeteViet/v1")
public interface SolutionService {

    @GetMapping("/solution/{id}")
    ResponseEntity<SolutionDto> getSolution(@PathVariable("id") Long id);

    @GetMapping("/solutions")
    ListeSolutionsDto getSolutions();

    @DeleteMapping("/solution/{id}")
    void deleteSolution(@PathVariable("id") Long id);

    @DeleteMapping("/solutions")
    void deleteSolutions();

    @PutMapping("/solution")
    ResponseEntity<String> saveSolution(@RequestBody SolutionDto solutionDto) throws Exception;

    @GetMapping("/solutions/calcul")
    ResponseEntity<String> caclulerSolution() throws Exception;

    @GetMapping("/solutions/tempsExecution")
    ResponseEntity<String> getTempsDecalcul() throws Exception;

    @GetMapping("/solutions/exist")
    boolean solutionDejaGener();
}
