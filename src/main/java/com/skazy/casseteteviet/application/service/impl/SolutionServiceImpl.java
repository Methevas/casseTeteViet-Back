package com.skazy.casseteteviet.application.service.impl;

import com.skazy.casseteteviet.application.service.SolutionService;
import com.skazy.casseteteviet.infrastructure.entite.Solution;
import com.skazy.casseteteviet.infrastructure.repository.SolutionRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class SolutionServiceImpl implements SolutionService {

    @Autowired
    private SolutionRepository solutionRepository;

    public Solution getSolution(Long id) {
        return solutionRepository.findOneById(id);
    }

    public List<Solution> getSolutions() {
        return solutionRepository.findAll();
    }

    public void deleteSolution(Long id){
        solutionRepository.deleteById(id);
    }

    public void deleteSolutions() {
        solutionRepository.deleteAll();
    }

    public Solution saveSolution(Solution solution){
        return solutionRepository.save(solution);
    }
}

