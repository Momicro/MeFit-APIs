package com.experis.de.MeFit.repositories;

import com.experis.de.MeFit.models.WorkoutExercises;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutExercisesRepository extends JpaRepository<WorkoutExercises, Long> {
}
