package com.upc.gzq.service;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upc.gzq.dao.LoginMapper;
import com.upc.gzq.entity.Admin;
@Service
public class AdminService {

	@Autowired
	private LoginMapper loginMapper;
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	public Admin getAdminByAid(String aid) {
		return loginMapper.getAdminByAid(aid);
	}
	
	public boolean regist(Admin admin) {
		if(admin==null) {
			logger.error("信息不合法");
			return false;
		}else if(getAdminByUsername(admin.getUsername())!=null){
			logger.error("用户已存在");
			return false;
		}
		return loginMapper.insertAdminByAid(admin)>0;
	}
	
	public Admin getAdminByUsername(String username) {
		return loginMapper.getAdminByUsername(username);
	}
	
	public Admin getAdminByUsernameAndPassword(String username,String password) {
		return loginMapper.getAdminByUsernameAndPassword(username, password);
	}
}
