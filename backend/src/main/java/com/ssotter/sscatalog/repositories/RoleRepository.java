package com.ssotter.sscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssotter.sscatalog.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

}
