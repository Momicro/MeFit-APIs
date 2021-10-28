package com.experis.de.MeFit.repositories;

import com.experis.de.MeFit.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//In this repository the ExerciseModel gets extended with JPA functions
@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    boolean existsByName(String name);

}
