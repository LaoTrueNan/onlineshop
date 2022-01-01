package com.upc.gzq.converter;

import lombok.Data;

@Data
public class ProductConverter {
	private String pname;
	private Integer pstock;
	private Double pprice;
	private Integer ptype=1;
	private Integer pstatus;
}
