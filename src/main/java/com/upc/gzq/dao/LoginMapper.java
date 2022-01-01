package com.upc.gzq.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.upc.gzq.entity.Admin;
@Repository
public interface LoginMapper {
	public Admin getAdminByAid(@Param("aid")String aid);
	public Admin getAdminByUsername(@Param("username")String username);
	public int insertAdminByAid(Admin admin);
	public int updateAdminByAid(Admin admin);
	public Admin getAdminByUsernameAndPassword(@Param("username")String username,@Param("password")String password);
}
