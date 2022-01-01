package com.upc.gzq.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upc.gzq.VO.OrderVO;
import com.upc.gzq.VO.ProductVO;
import com.upc.gzq.converter.Order;
import com.upc.gzq.converter.Page;
import com.upc.gzq.entity.OrderItem;
import com.upc.gzq.entity.OrderMaster;
import com.upc.gzq.entity.Product;
import com.upc.gzq.service.OrderService;
import com.upc.gzq.service.ProductService;
import com.upc.gzq.utils.KeyUtil;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;

	/**
	 * 加上synchronized关键字，同时只能有一个用户进行下单操作
	 * @param jsonObject
	 * @return
	 */
	@PostMapping("/createOrder")
	@ResponseBody
	public synchronized String createOrder(@RequestBody JSONObject jsonObject) {
		String bid = jsonObject.getString("buyer");
		if(bid==null) {
			return "login";
		}
		List<ProductVO> productVOList = jsonObject.getJSONArray("cart").toJavaList(ProductVO.class);
		OrderMaster orderMaster = new OrderMaster();
		orderMaster.setOid(KeyUtil.getUniqueKey());
		orderMaster.setBid(bid);
		
		//将订单状态设置为新订单
		orderMaster.setStatus(0);
		orderMaster.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		double count = 0;
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for (ProductVO productVO : productVOList) {
			if (productVO.getNum() != 0) {
				Product product = productService.getProductByPid(productVO.getPid());
				if(productVO.getNum()>product.getPstock()) {
					return product.getPname()+"数量不够！";
				}
				OrderItem orderItem = new OrderItem();
				orderItem.setIid(KeyUtil.getUniqueKey());
				orderItem.setOid(orderMaster.getOid());
				orderItem.setPid(productVO.getPid());
				orderItem.setPnum(productVO.getNum());
				orderItem.setPname(product.getPname());
				count += productVO.getNum() * product.getPprice();
				orderItem.setUpdateTime(orderMaster.getUpdateTime());
				orderItemList.add(orderItem);
			}

		}
		orderMaster.setAmount(count);
		Order order = new Order();
		BeanUtils.copyProperties(orderMaster, order);
		order.setOrderItems(orderItemList);
		boolean createOrder = orderService.createOrder(order);
		if (createOrder) {
			return "下单成功";
		}
		return "订单不能为空";
	}
	@GetMapping("/index")
	public String toOrderIndex(HttpServletRequest request) {
		Page<Order> orderPage = orderService.getOrderPage("1", "4");
		request.setAttribute("orderPage", orderPage.getDataList());
		request.setAttribute("page", orderPage);
		return "orderlist";
	}
	@GetMapping("/getOrderList")
	public String getOrderList(String pageNo,String pageSize,HttpServletRequest request) {
		Page<Order> orderPage = orderService.getOrderPage(pageNo, pageSize);
		request.setAttribute("orderPage", orderPage.getDataList());
		request.setAttribute("page", orderPage);
		return "orderlist";
	}
	@GetMapping(value = "/confirmorder",produces = "text/html;charset=utf-8")
	@ResponseBody
	public String confirmorder(String oid) {
		return orderService.confirmorder(oid);
		
	} 
	@GetMapping("/getOrderByBid")
	@ResponseBody
	public List<OrderVO> getOrderByBid(String bid){
		return orderService.getOrdersByBid(bid);
	} 
	
	@GetMapping("/buyergetorderitembyoid")
	@ResponseBody
	public List<OrderItem> buyergetorderitembyoid(String oid){
		return orderService.getOrderItemsByOid(oid);
	}
}