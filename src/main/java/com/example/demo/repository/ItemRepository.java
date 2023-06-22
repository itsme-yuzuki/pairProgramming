package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
	// SELECT * FROM items WHERE category_id = ?
	List<Item> findByCategoryId(Integer categoryId);
	
	//SELECT * FROM items WHERE name like %?%
	List<Item> findByNameLike(String Keyword);
	
	//SELECT * FROM items WHERE name like %?% and price<=?
	List<Item> findByPriceLessThanEqualAndNameLike(Integer maxPrice, String Keyword);
	
	//SELECT * FROM items WHERE price <=?
	List<Item> findByPriceLessThanEqual(Integer maxPrice);
	
	//SELECT* FROM items WHERE id = ?
	Optional<Item> findById(Integer id);
}
