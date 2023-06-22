package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Item;
import com.example.demo.model.Cart;
import com.example.demo.repository.ItemRepository;

@Controller
public class CartController {

	@Autowired
	Cart cart;

	@Autowired
	ItemRepository itemRepository;

	// カート内容を表示
	@GetMapping("/cart")
	public String index() {
		// cart.htmlの出力
		return "cart";
	}

	// 指定した商品をカートに追加する
	// 数量が未指定の場合は1とする
	@PostMapping("/cart/add")
	public String addCart(
			@RequestParam("itemId") int itemId,
			@RequestParam(name = "quantity", defaultValue = "1") Integer quantity) {

		// 商品コードをキーに商品情報を取得する
		Item item = itemRepository.findById(itemId).get();
		
		// 商品オブジェクトに個数をセット
		item.setQuantity(quantity);
		
		// カートに追加
		cart.add(item);
		
		// 「/cart」にリダイレクト
		return "redirect:/cart";
	}

	// 指定した商品をカートから削除
	@PostMapping("/cart/delete")
	public String deleteCart(@RequestParam("itemId") int itemId) {

		// カート情報から削除
		cart.delete(itemId);
		
		// 「/cart」にリダイレクト
		return "redirect:/cart";
	}
}
