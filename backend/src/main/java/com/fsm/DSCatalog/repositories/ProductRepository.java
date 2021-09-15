package com.fsm.DSCatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsm.DSCatalog.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
