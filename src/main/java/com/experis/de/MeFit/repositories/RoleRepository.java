package com.experis.de.MeFit.repositories;

import com.experis.de.MeFit.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//In this repository the RoleModel gets extended with JPA functions
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
