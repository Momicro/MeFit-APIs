package com.experis.de.MeFit.repositories;

import com.experis.de.MeFit.models.Profile;
import com.experis.de.MeFit.models.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//In this repository the ProgramModel gets extended with JPA functions
@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    Program findByName(String name);

}
