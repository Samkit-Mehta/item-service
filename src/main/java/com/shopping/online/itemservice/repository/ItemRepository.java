package com.shopping.online.itemservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.online.itemservice.model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
	
	List<Item> findByName(String itemName);
}
