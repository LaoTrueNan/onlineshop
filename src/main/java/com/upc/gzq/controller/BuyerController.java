package com.upc.gzq.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.sql.Timestamp;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upc.gzq.converter.BuyerConverter;
import com.upc.gzq.entity.Buyer;
import com.upc.gzq.service.BuyerService;
import com.upc.gzq.service.ProductService;
import com.upc.gzq.utils.KeyUtil;

@Controller
@RequestMapping("/buyer")
public class BuyerController { 
	@Autowired
	private ProductService productService;
	@Autowired
	private BuyerService buyerService;
	
	@PostMapping("/regi")
	@ResponseBody
	public String regist(@RequestBody JSONObject jsonObject) {
		JSONObject buyer = jsonObject.getJSONObject("signupinfo");
		BuyerConverter buyerConverter = buyer.toJavaObject(BuyerConverter.class);
		Buyer buyer2 = new Buyer();
		BeanUtils.copyProperties(buyerConverter, buyer2);
		buyer2.setBid(KeyUtil.getUniqueKey());
		buyer2.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		boolean addResult = buyerService.addNewBuyer(buyer2);
		if(addResult) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	@PostMapping("/login")
	@ResponseBody
	public Buyer login(@RequestBody JSONObject jsonObject) {
		JSONObject buyer = jsonObject.getJSONObject("buyer");
		BuyerConverter buyerConverter = buyer.toJavaObject(BuyerConverter.class);
		try {
			Buyer buyerByBname = buyerService.getBuyerByBname(buyerConverter.getBname());
			if(buyerConverter.getBpassword().equals(buyerByBname.getBpassword())) {
				return buyerByBname;
			}else {
				return null;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}
}