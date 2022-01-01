package com.upc.gzq.service;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upc.gzq.dao.BuyerMapper;
import com.upc.gzq.entity.Buyer;

@Service
public class BuyerService {
	@Autowired
	private BuyerMapper buyerMapper;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	public boolean addNewBuyer(Buyer buyer) {
		
		if(buyer==null) {
			logger.error("买家信息为空，新增买家失败");
			return false;
		}else if(buyerMapper.selectBuyerByBname(buyer.getBname())!=null) {
				logger.error("买家名字重复，新增买家失败");
				return false;
		}else {
			try {
				int insertResult = buyerMapper.insertBuyer(buyer);
				if(insertResult>0) {
					return true;
				}
			} catch (Exception e) {
				logger.error("新增买家信息失败"+e.getMessage());
				return false;
			}
		}
		return false;
	}
	
	public Buyer getBuyerByBid(String bid) {
		if(bid==null) {
			logger.error("查找买家失败，信息不能为空");
			return null;
		}else {
			try {
				Buyer selectResult = buyerMapper.selectBuyerByBid(bid);
				if(selectResult!=null) {
					return selectResult;
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				return null;
			}
		}
		return null;
	}
	
	public Buyer getBuyerByBname(String bname) {
		if(bname==null) {
			logger.error("查找买家失败，信息不能为空");
			return null;
		}else {
			try {
				Buyer selectResult = buyerMapper.selectBuyerByBname(bname);
				if(selectResult!=null) {
					return selectResult;
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				return null;
			}
		}
		return null;
	}
	
	public List<Buyer> getBuyerList(){
		try {
			List<Buyer> selectResult = buyerMapper.selectAllBuyer();
			if(selectResult!=null) {
				return selectResult;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		return null;
	}
	
}