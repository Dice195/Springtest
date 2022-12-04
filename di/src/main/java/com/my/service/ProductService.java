package com.my.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.my.dto.PageBean;
import com.my.exception.FindException;
import com.my.repository.ProductRepository;
import com.my.vo.Product;

@Service("productService")
public class ProductService {
	
	@Autowired
	@Qualifier("productRepository")
	private ProductRepository repository;
	public ProductService() {}
	public ProductService(ProductRepository repository) {
		this.repository = repository;
	}
	public ProductRepository getRepository() {
		return repository;
	}
	public void setRepository(ProductRepository repository) {
		this.repository = repository;
	}
	
	
	public PageBean<Product> getPageBean(int currentPage) throws FindException{
		List<Product> list = findAll(currentPage, PageBean.CNT_PER_PAGE);
		int totalCnt = repository.selectCount();
		PageBean<Product> pb = new PageBean<>(currentPage, list, totalCnt);
		
		return pb;
	}
	/**
	 * 총페이지수 반환한다
	 * @param cntPerPage  한페이지당 보여줄 상품목록수
	 * @return
	 * @throws FindException
	 */
	public int getTotalPage(int cntPerPage) throws FindException{
		int totalCnt = repository.selectCount(); //총상품수
		int totalPage;
//		int a = totalCnt / cntPerPage; //몫
//		int b = totalCnt % cntPerPage; //나머지값
//		totalPage = b>0?a+1:a;
		totalPage = (int)(Math.ceil((double)totalCnt/cntPerPage));		
		return totalPage;
	}
	
	public List<Product> findAll(int currentPage, int cntPerPage) throws FindException{
		return repository.selectAll(currentPage, cntPerPage);
	}	
	public List<Product> findAll() throws FindException{
		return repository.selectAll();
	}
	public List<Product> search(String word) throws FindException{
		return repository.selectByNoORNameORPriceORInfo(word);
	}
	public Product findByNo(String prodNo) throws FindException{
		return repository.selectByProdNo(prodNo);
	}
}
