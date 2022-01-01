package com.upc.gzq.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upc.gzq.converter.Page;
import com.upc.gzq.dao.OrderMapper;
import com.upc.gzq.dao.ProductMapper;
import com.upc.gzq.entity.OrderItem;
import com.upc.gzq.entity.Product;
import com.upc.gzq.entity.ProductType;

@Service
public class ProductService {
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private OrderMapper orderMapper;

	private final Logger logger = Logger.getLogger(this.getClass());

	public List<Product> getAllProduct(){
		return productMapper.getAllProducts();
	}
	public Page<Product> getProductPage(String pageNo, String pageSize) {
		Page<Product> productPage = new Page<Product>();
		int pn = 1;
		int ps = productPage.getPageSize();
		try {
			pn = Integer.parseInt(pageNo);
			ps = Integer.parseInt(pageSize);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		productPage.setPageSize(ps);
		productPage.setTotalItems(productMapper.getTotalProductNo());
		pn = pn > 0 ? pn : 1;
		pn = pn > productPage.getTotalPages() ? productPage.getTotalPages() : pn;
		productPage.setCurrentNo(pn);
		productPage.setDataList(productMapper.getProducts(productPage.getPageIndex(), productPage.getPageSize()));
		return productPage;

	}

	public boolean addProduct(Product product) {
		if (product == null) {
			logger.error("商品信息不合法，新增商品失败");
			return false;
		} else {
			return productMapper.insertNewProduct(product) > 0;
		}
	}

	public Product getProductByPid(String pid) {
		if (pid == null || "".equals(pid)) {
			logger.error("商品信息不合法，查询失败");
			return null;
		}
		return productMapper.selectProductByPid(pid);
	}

	public String addNewProductType(ProductType productType) {
		if (productType == null) {
			logger.error("新增商品类型失败，参数不合法");
			return "参数为空";
		} else {
			ProductType selectRes = productMapper.selectProductTypeByTcode(productType.getTcode());
			ProductType selectProductTypeByTname = productMapper.selectProductTypeByTname(productType.getTname());
			if (selectRes != null) {
				logger.error("新增商品类型失败，类型代码【" + productType.getTcode() + "】已存在");
				return "类型代码【" + productType.getTcode() + "】已存在";
			}else if (selectProductTypeByTname != null) {
				logger.error("新增商品类型失败，类型名称【" + productType.getTname() + "】已存在");
				return "类型名称【" + productType.getTname() + "】已存在";
			}
			int res = productMapper.insertNewProductType(productType);
			if (res < 1) {
				logger.error("新增分类失败！");
				return "数据库操作失败！";
			}
			
		}
		return "";
	}

	public Page<ProductType> getProductTypePage(String pageNo, String pageSize) {
		Page<ProductType> productTypePage = new Page<ProductType>();
		int pn = 1;
		int ps = 4;
		try {
			pn = Integer.parseInt(pageNo);
			ps = Integer.parseInt(pageSize);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		productTypePage.setPageSize(ps);
		productTypePage.setTotalItems(productMapper.getTotalTypeNum());
		pn = pn > 0 ? pn : 1;
		pn = pn > productTypePage.getTotalPages() ? productTypePage.getTotalPages() : pn;
		productTypePage.setCurrentNo(pn);
		productTypePage.setDataList(
				productMapper.selectProductTypeList(productTypePage.getPageIndex(), productTypePage.getPageSize()));
		return productTypePage;
	}

	public List<ProductType> getAllProductTypes() {
		return productMapper.selectAllTypes();
	}
	@Transactional
	public boolean changeProductInfo(Product product) {
		List<OrderItem> orderItemByPid = orderMapper.selectOrderItemByPid(product.getPid());
		try {
			for(OrderItem orderItem: orderItemByPid) {
				orderItem.setPname(product.getPname());
				orderMapper.updateOrderItem(orderItem);
			}
			int updateProduct = productMapper.updateProduct(product);
			if(updateProduct>0) {
				return true;
			}else {
				throw new Exception("修改商品信息失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean changeProductTypeInfo(ProductType productType) {
		int updateRes;
		try {
			updateRes = productMapper.updateProductType(productType);
			if (updateRes > 0) {
				return true;
			} else {
				logger.error("更新分类信息失败，分类码【" + productType.getTcode() + "】重复");
				return false;
			}
		} catch (Exception e) {
			logger.error("更新分类信息失败，分类码【" + productType.getTcode() + "】重复");
			return false;
		}

	}

	public ProductType getProductTypeByTcode(Integer tcode) {
		return productMapper.selectProductTypeByTcode(tcode);
	}
	
	public List<Product> getProductByPtype(Integer ptype){
		return productMapper.selectProductByPtype(ptype);
	}
}
