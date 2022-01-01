package com.upc.gzq.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.upc.gzq.VO.ProductVO;
import com.upc.gzq.converter.Page;
import com.upc.gzq.converter.ProductConverter;
import com.upc.gzq.converter.TypeConverter;
import com.upc.gzq.entity.Product;
import com.upc.gzq.entity.ProductType;
import com.upc.gzq.service.AdminService;
import com.upc.gzq.service.ProductService;
import com.upc.gzq.utils.KeyUtil;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/index")
	public String productIndex(@RequestParam(value = "pageNo",defaultValue = "1")String pageNo,HttpServletRequest request) {
		Page<Product> productPage = productService.getProductPage(pageNo, "4");
		request.setAttribute("productPage", productPage.getDataList());
		request.setAttribute("page",productPage);
		List<ProductType> productTypeList = productService.getAllProductTypes();
		request.setAttribute("productTypeList", productTypeList);
		return "index";
	}
	
	/**
	 * 为了给NewProduct.jsp界面传入所有的商品类型
	 * @param request
	 * @return
	 */
	@GetMapping("/toNewProduct")
	public String redirectToNewProductPage(HttpServletRequest request) {
		List<ProductType> productTypeList = productService.getAllProductTypes();
		request.setAttribute("productTypeList", productTypeList);
		return "NewProduct";
		
	}
	@PostMapping("/newproduct")
	public void newProduct(HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes) {
		try {
			request.setCharacterEncoding("utf-8");
			DiskFileItemFactory dfif = new DiskFileItemFactory();
			ServletFileUpload servletFileUpload = new ServletFileUpload(dfif);
			servletFileUpload.setHeaderEncoding("UTF-8");
			List<FileItem> parseRequest = servletFileUpload.parseRequest(request);
			Map<String, Object> productMap = new HashMap<String, Object>();
			String ppicture = "img/basicprofile.jpg";
			for(FileItem fi : parseRequest) {
				if(!fi.isFormField()) {
					String fileName = fi.getName();
					System.out.println(fileName);
					int i = fileName.lastIndexOf("\\");
					fileName = fileName.substring(i+1);
					ServletContext servletContext = request.getServletContext();
					if(!fileName.isEmpty()) {
						ppicture = UUID.randomUUID()+fileName;
						IOUtils.copy(fi.getInputStream(), new FileOutputStream(servletContext.getRealPath("/img")+"/"+ppicture));
						ppicture = "img/"+ppicture;
						
					}
				}else {
					productMap.put(fi.getFieldName(), new String(fi.getString("UTF-8")));
				}
			}
			productMap.put("ppicture", ppicture);
			Product product = new Product();
			product = JSON.parseObject(JSON.toJSONString(productMap), Product.class);
			product.setPid(KeyUtil.getUniqueKey());
			product.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			boolean addResult = productService.addProduct(product);
			if(addResult) {
				response.sendRedirect(request.getContextPath()+"/product/index?pageNo=999");
			}else {
				redirectAttributes.addAttribute("msg", "添加失败");
				response.sendRedirect(request.getContextPath()+"/product/toNewProduct");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/getproduct")
	public String showProductInfo(String pid,HttpServletRequest request) {
		Product product = productService.getProductByPid(pid);
		request.setAttribute("product", product);
		List<ProductType> productTypeList = productService.getAllProductTypes();
		request.setAttribute("productTypeList", productTypeList);
		return "product_info";
	}
	
	@GetMapping("/getProductPage")
	public String getProductPage(@RequestParam("pageNo")String pageNo,@RequestParam("pageSize")String pageSize,HttpServletRequest request) {
		Page<Product> productPage = productService.getProductPage(pageNo, pageSize);
		request.setAttribute("productPage", productPage.getDataList());
		request.setAttribute("page",productPage);
		List<ProductType> productTypeList = productService.getAllProductTypes();
		request.setAttribute("productTypeList", productTypeList);
		return "index";
	}
	
	@PostMapping("/changeproduct")
	public void changeProduct(HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes) {
		try {
			request.setCharacterEncoding("utf-8");
			DiskFileItemFactory dfif = new DiskFileItemFactory();
			ServletFileUpload servletFileUpload = new ServletFileUpload(dfif);
			servletFileUpload.setHeaderEncoding("UTF-8");
			List<FileItem> parseRequest = servletFileUpload.parseRequest(request);
			Map<String, Object> productMap = new HashMap<String, Object>();
			String ppicture = null;
			for(FileItem fi : parseRequest) {
				if("pid".equals(fi.getFieldName())) {
					ppicture = productService.getProductByPid(fi.getString()).getPpicture();
				}
			}
			for(FileItem fi : parseRequest) {
				if(!fi.isFormField()) {
					String fileName = fi.getName();
					System.out.println(fileName);
					int i = fileName.lastIndexOf("\\");
					fileName = fileName.substring(i+1);
					ServletContext servletContext = request.getServletContext();
					if(!fileName.isEmpty()) {
						ppicture = UUID.randomUUID()+fileName;
						IOUtils.copy(fi.getInputStream(), new FileOutputStream(servletContext.getRealPath("/img")+"/"+ppicture));
						ppicture = "img/"+ppicture;
					}
				}else {
					productMap.put(fi.getFieldName(), new String(fi.getString("UTF-8")));
				}
			}
			productMap.put("ppicture", ppicture);
			Product product = new Product();
			product = JSON.parseObject(JSON.toJSONString(productMap), Product.class);
			product.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			boolean updateResult = productService.changeProductInfo(product);
			if(updateResult) {
				response.sendRedirect(request.getContextPath()+"/product/index");
			}else {
				redirectAttributes.addAttribute("msg", "修改失败");
				response.sendRedirect(request.getContextPath()+"/product/getproduct?pid="+product.getPid());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}
	
	
	@GetMapping("/typeindex")
	public String getProductTypePage(@RequestParam(value = "pageNo",defaultValue = "1")String pageNo,@RequestParam(value="pageSize",defaultValue = "4") String pageSize,HttpServletRequest request) {
		Page<ProductType> productTypePage = productService.getProductTypePage(pageNo, pageSize);
		request.setAttribute("typePage", productTypePage.getDataList());
		request.setAttribute("page", productTypePage);
		return "typelist";
	}
	
	@GetMapping("/toNewType")
	public String forwardToNewTypePage(HttpServletRequest request,RedirectAttributes redirectAttributes) {
		request.setAttribute("types", productService.getAllProductTypes());
		return "NewType";
	}
	
	@PostMapping("/newtype")
	public String addNewType(TypeConverter typeConverter,HttpServletRequest request,HttpServletResponse response) {
		ProductType productType = new ProductType();
		BeanUtils.copyProperties(typeConverter, productType);
		productType.setTid(KeyUtil.getUniqueKey());
		productType.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		String addRes = productService.addNewProductType(productType);
			if("".equals(addRes)) {
//				response.sendRedirect(request.getContextPath()+"/product/typeindex?pageNo=999");
				return getProductPage("999", null, request);
			}else {
				request.setAttribute("typemsg", addRes);
				return "NewType";
			}
	}
	
	@PostMapping("/subChangeType")
	public String changeTypeInfo(ProductType productType,HttpServletRequest request) {
		productType.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		try{
			boolean updateRes = productService.changeProductTypeInfo(productType);
			if(updateRes) {
				request.setAttribute("msg", "修改成功");
				Page<ProductType> productTypePage = productService.getProductTypePage("1", "4");
				request.setAttribute("typePage", productTypePage.getDataList());
				request.setAttribute("page", productTypePage);
				return "typelist";
			}else {
				request.setAttribute("msg", "修改失败，分类码重复");
				Page<ProductType> productTypePage = productService.getProductTypePage("1", "4");
				request.setAttribute("typePage", productTypePage.getDataList());
				request.setAttribute("page", productTypePage);
				return "typelist";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			Page<ProductType> productTypePage = productService.getProductTypePage("1", "4");
			request.setAttribute("typePage", productTypePage.getDataList());
			request.setAttribute("page", productTypePage);
			return "typelist";
		}
	}
	
	@GetMapping("/buyergettypes")
	@ResponseBody
	public List<ProductType> buyerGetTypes(){
		return productService.getAllProductTypes();
	}
	@GetMapping("/buyergetproductbytype")
	@ResponseBody
	public List<Product> buyeGetProductByType(String tcode){
		int ptype = Integer.parseInt(tcode);
		return productService.getProductByPtype(ptype);
	}
	
	@GetMapping("/buyergetProducts")
	@ResponseBody
	public List<ProductVO> buyergetProducts(){
		List<Product> allProduct = productService.getAllProduct();
		List<ProductVO> productVOList = new ArrayList<ProductVO>();
		for(Product product:allProduct) {
			ProductVO productVO = new ProductVO();
			productVO.setPid(product.getPid());
			productVO.setNum(0);
			productVOList.add(productVO);
		}
		return productVOList;
	}
}