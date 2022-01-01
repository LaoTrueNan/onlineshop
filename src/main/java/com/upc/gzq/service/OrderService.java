package com.upc.gzq.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upc.gzq.VO.OrderVO;
import com.upc.gzq.controller.MessageController;
import com.upc.gzq.converter.Order;
import com.upc.gzq.converter.Page;
import com.upc.gzq.dao.OrderMapper;
import com.upc.gzq.dao.ProductMapper;
import com.upc.gzq.entity.Buyer;
import com.upc.gzq.entity.OrderItem;
import com.upc.gzq.entity.OrderMaster;
import com.upc.gzq.entity.Product;
import com.upc.gzq.entity.ProductType;
import com.upc.gzq.utils.KeyUtil;

@Service
public class OrderService {
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private MessageController messageController;
	private final Logger logger = Logger.getLogger(this.getClass());
	private List<OrderItem> orderItemList;

	@Transactional
	public boolean createOrder(Order order) {

		if (order == null) {
			logger.error("创建订单失败，信息为空");
			return false;
		} else if (order.getOrderItems() == null || order.getOrderItems().size() == 0) {
			logger.error("创建订单失败");
			return false;
		} else {
			try {
				OrderMaster orderMaster = new OrderMaster();
				BeanUtils.copyProperties(order, orderMaster);
				int a = 10;
				int insertResult = orderMapper.insertOrder(orderMaster);
				for (OrderItem orderItem : order.getOrderItems()) {
					Product product = productMapper.selectProductByPid(orderItem.getPid());
					product.setPstock(product.getPstock()-orderItem.getPnum());
					productMapper.updateProduct(product);
					int insertOrderItem = orderMapper.insertOrderItem(orderItem);
				}
				if (insertResult > 0) {
					messageController.onMessage(1);
					return true;
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				return false;
			}
		}

		return false;
	}

	public Page<Order> getOrderPage(String pageNo, String pageSize) {
		Page<Order> orderPage = new Page<Order>();
		int pn = 1;
		int ps = 4;
		try {
			pn = Integer.parseInt(pageNo);
			ps = Integer.parseInt(pageSize);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		orderPage.setPageSize(ps);
		orderPage.setTotalItems(orderMapper.selectTotalOrderMasterNum());
		pn = pn > 0 ? pn : 1;
		pn = pn > orderPage.getTotalPages() ? orderPage.getTotalPages() : pn;
		orderPage.setCurrentNo(pn);
		List<OrderMaster> orderMasterList = orderMapper.selectOrderMasterList(orderPage.getPageIndex(),
				orderPage.getPageSize());
		List<Order> orderList = new ArrayList<Order>();
		for (OrderMaster orderMaster : orderMasterList) {
			Order order = new Order();
			BeanUtils.copyProperties(orderMaster, order);
			order.setOrderItems(orderMapper.selectOrderItemByOid(orderMaster.getOid()));
			orderList.add(order);
		}
		orderPage.setDataList(orderList);
		return orderPage;

	}

	public List<OrderVO> getOrdersByBid(String bid) {
		List<OrderMaster> bidsOrders = orderMapper.selectOrderMasterByBid(bid);
		List<OrderVO> orderVOList = new ArrayList<OrderVO>();
		for (OrderMaster orderMaster : bidsOrders) {
			OrderVO orderVO = new OrderVO();
			BeanUtils.copyProperties(orderMaster, orderVO);
			orderVO.setStatus(orderMaster.getStatus()==0?"已下单":"已完成");
			List<OrderItem> orderItems = orderMapper.selectOrderItemByOid(orderMaster.getOid());
			List<Product> products = new ArrayList<Product>();
			for (OrderItem orderItem : orderItems) {
				Product product = productMapper.selectProductByPid(orderItem.getPid());
				products.add(product);
			}
			orderVO.setProductList(products);
			orderVOList.add(orderVO);
		}
		return orderVOList;
	}

	public List<OrderItem> getOrderItemsByOid(String oid){
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		try{
			orderItemList = orderMapper.selectOrderItemByOid(oid);
			if(orderItemList.size()==0||orderItemList==null) {
				throw new NullPointerException("出错了，【"+oid+"】的订单项为空"); 
			}else {
				return orderItemList;
			}
		}catch (Exception e) {
			logger.error(e.getMessage());;
			return null;
		}
	}
	
	public String confirmorder(String oid) {
		OrderMaster orderMaster = orderMapper.selectOrderMasterByOid(oid);
		if(orderMaster.getStatus()==1) {
			return "订单已经完结，请您刷新界面";
		}
		orderMaster.setStatus(1);
		try {
			int updateOrder = orderMapper.updateOrder(orderMaster);
			if(updateOrder>0) {
				return "完结成功！";
			}else {
				throw new NullPointerException("修改订单状态失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "订单完结失败！";
		}
	}
}
