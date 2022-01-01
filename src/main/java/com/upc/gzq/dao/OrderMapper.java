package com.upc.gzq.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.upc.gzq.entity.OrderItem;
import com.upc.gzq.entity.OrderMaster;

@Repository
public interface OrderMapper {

	public int insertOrder(OrderMaster orderMaster);
	public int deleteOrder(OrderMaster orderMaster);
	public int updateOrder(OrderMaster orderMaster);
	public OrderMaster selectOrderMasterByOid(@Param("oid")String oid);
	public List<OrderMaster> selectOrderMasterByBid(@Param("bid")String bid);
	public List<OrderMaster> selectAllOrderMaster();
	public List<OrderMaster> selectOrderMasterList(@Param("index")int index,@Param("size")int size);
	public int selectTotalOrderMasterNum();
	
	public int insertOrderItem(OrderItem orderItem);
	public int deleteOrderItem(OrderItem orderItem);
	public int updateOrderItem(OrderItem orderItem);
	public List<OrderItem> selectOrderItemByOid(@Param("oid")String oid);
	public List<OrderItem> selectOrderItemByPid(@Param("pid")String pid);
}
