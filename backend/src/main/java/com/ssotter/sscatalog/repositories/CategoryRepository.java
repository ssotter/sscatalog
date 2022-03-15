package com.ssotter.sscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssotter.sscatalog.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
