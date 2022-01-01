package com.upc.gzq.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.upc.gzq.converter.AdminConverter;
import com.upc.gzq.entity.Admin;
import com.upc.gzq.service.AdminService;
import com.upc.gzq.service.ProductService;
import com.upc.gzq.utils.KeyUtil;

@Controller
@RequestMapping("/user")
public class LoginController {
	
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/login")
	public String login(AdminConverter adminConverter,HttpServletRequest request) {
		Admin admin = adminService.getAdminByUsernameAndPassword(adminConverter.getUsername(), adminConverter.getPassword());
		
		if(admin==null) {
			request.setAttribute("msg", "用户不存在或密码错误");
			request.setAttribute("last", adminConverter.getUsername());
			return "login";
		}
		request.setAttribute("productPage", productService.getProductPage("1", "4").getDataList());
		request.setAttribute("page", productService.getProductPage("1", "4"));
		request.getSession().setAttribute("username", admin.getUsername());
		request.setAttribute("productTypeList", productService.getAllProductTypes());
		return "index";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "index";
	}
	
	@PostMapping("/regi")
	public String regi(AdminConverter adminConverter,HttpServletRequest request) {
		Admin admin = new Admin(KeyUtil.getUniqueKey(), adminConverter.getUsername(), adminConverter.getPassword(), new Timestamp(System.currentTimeMillis()));
		boolean res = adminService.regist(admin);
		if(res) {
			return "login";
		}else {
			request.setAttribute("msg", "用户名已被占用");
			request.setAttribute("failedusername", adminConverter.getUsername());
			return "regi";
		}
	}
}