package com.upc.gzq.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.upc.gzq.entity.Buyer;

@Repository
public interface BuyerMapper {
	public int insertBuyer(Buyer buyer);
	public Buyer selectBuyerByBid(@Param("bid")String bid);
	public Buyer selectBuyerByBname(@Param("bname")String bname);
	public List<Buyer> selectAllBuyer();
}
