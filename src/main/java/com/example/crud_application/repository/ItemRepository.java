package com.example.crud_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crud_application.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
