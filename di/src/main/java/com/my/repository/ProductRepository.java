package com.my.repository;

import java.util.List;

import com.my.exception.FindException;
import com.my.vo.Product;

public interface ProductRepository {
	
	
	/**
	 * 전체 상품수를 반환한다.
	 * @return
	 * @throws FindException
	 */
	int selectCount() throws FindException;
	
	/**
	 * @상품 전체 검색한다.
	 * @return 상품들
	 * @throws Findexception 검색시 문제 발생한 경우 FindException 발생한다.
	 */
	List<Product> selectAll() throws FindException; //product들이 반환된다. 배열들은 크기가 고정되어있다. list는 크기를 가변적으로 사용할수있다.
	/**
	 * 전체 상품을 페이지별로 검색한다.
	 * @param currentPage 검색할 페이지 
	 * @param cntPerPage 페이지별 보여줄 목록수
	 * @return 페이지에 해당하는 상품들만 반환한다 
	 * @throws FindException 검색시 문제가 발생한 경우 FindException 발생한다.
	 */
	List<Product> selectAll(int currentPage, int cntPrePage) throws FindException;
	/**
	 * 검색어에 해당하는 상품을 검색한다.
	 * @param word 검색어 
	 * @return 상품들 
	 * @throws FindException 검색시 문제발생한 경우 FindException발생한다.
	 */
	List<Product> selectByNoORNameORPriceORInfo(String word) throws FindException;
	/**
	 * 상품번호에 해당하는 상품을 검색한다.
	 * @param prodNo 상품번호
	 * @return 상품
	 * @throws FindException 검색시 문제발생한 경우와 상품이 없는 경우  FindException발생한다.
	 */
	Product selectByProdNo(String prodNo) throws FindException;
	

	
	
	//size가 0인 리스트를 반환한다.
}
