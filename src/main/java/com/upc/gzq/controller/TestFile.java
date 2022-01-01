package com.upc.gzq.controller;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class TestFile {
	@PostMapping("/testfile")
	public String getFile(HttpServletRequest request) {
		try {
			ServletInputStream inputStream = request.getInputStream();
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
			byte[] buf = new byte[1024];
			int len = bufferedInputStream.read(buf);
			while(len!=-1) {
				System.out.println(buf);
				len = bufferedInputStream.read(buf, 0, len); 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "index";
	}
}