package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Item;
import com.example.demo.model.Account;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;

@Controller
public class ItemController {

	@Autowired
	Account account;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ItemRepository itemRepository;

	// 商品一覧表示
	@GetMapping("/items")
	public String index(
			@RequestParam(value = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(name= "keyword", defaultValue= "") String keyword,
			@RequestParam(name= "maxPrice", defaultValue="") Integer maxPrice,
			Model model) {


		// 商品一覧情報の取得
		List<Item> itemList = null;
		
		if (categoryId != null) {
			itemList =itemRepository.findByCategoryId(categoryId);
		
		}else if(keyword != null && maxPrice != null) {
			itemList = itemRepository.findByPriceLessThanEqualAndNameLike(maxPrice, "%"+keyword+"%");
		
		} else if(keyword != null){
			itemList = itemRepository.findByNameLike("%"+keyword+"%");
			
		}else {
			// itemsテーブルをカテゴリーIDを指定して一覧を取得
			itemList = itemRepository.findAll();
		}
			model.addAttribute("items", itemList);
			model.addAttribute("keyword", keyword);
			model.addAttribute("maxPrice", maxPrice);

		return "items";
	}
	
	//商品詳細画面
	@GetMapping("/items/{id}")
	public String show(Model m,
			@PathVariable("id") Integer id
			) {
		Item item = null;
		item= itemRepository.findById(id).get();
		m.addAttribute("item", item);
		
				return "itemDetail";
		
	}
}
