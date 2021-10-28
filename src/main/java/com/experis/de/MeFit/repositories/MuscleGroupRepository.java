package com.experis.de.MeFit.repositories;

import com.experis.de.MeFit.models.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//In this repository the MuscleGroupModel gets extended with JPA functions
@Repository
public interface MuscleGroupRepository extends JpaRepository<MuscleGroup, Long> {
    MuscleGroup findByName(String name);

}
