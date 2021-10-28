package com.experis.de.MeFit.repositories;

import com.experis.de.MeFit.models.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

//In this repository the goal model gets extended with JPA functions
@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    Goal findByStartDate(Date startDate);
}
