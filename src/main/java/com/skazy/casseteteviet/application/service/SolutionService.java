package com.skazy.casseteteviet.application.service;

import com.skazy.casseteteviet.infrastructure.entite.Solution;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("casseTeteViet/v1")
public interface SolutionService {

    @GetMapping("/solution/{id}")
    Solution getSolution(@PathVariable Long id);

    @GetMapping("/solutions")
    List<Solution> getSolutions();

    @DeleteMapping("/solution/{id}")
    void deleteSolution(@PathVariable Long id);

    @DeleteMapping("/solutions")
    void deleteSolutions();

    @PostMapping("/solution")
    Solution saveSolution(@RequestBody Solution solution);
}
