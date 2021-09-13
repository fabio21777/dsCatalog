package com.fsm.DSCatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsm.DSCatalog.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
