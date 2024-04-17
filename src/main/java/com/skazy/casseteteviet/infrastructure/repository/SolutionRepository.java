package com.skazy.casseteteviet.infrastructure.repository;

import com.skazy.casseteteviet.infrastructure.entite.Solution;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolutionRepository extends CrudRepository<Solution, Long> {

    Solution findOneById(Long id);

    List<Solution> findAll();
}
