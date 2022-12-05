package com.my.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
		private String prodNo;
		private String prodName;
		private int prodPrice;
		private String prodInfo;
		public Product(String prodNo2, String prodName2, int prodPridce, String prodInfo2) {
			// TODO Auto-generated constructor stub
		}
		@Override
		public String toString() {
			return "Product [prodNo=" + prodNo + ", prodName=" + prodName + ", prodPrice=" + prodPrice + ", prodInfo="
					+ prodInfo + "]";
		}
		
}
