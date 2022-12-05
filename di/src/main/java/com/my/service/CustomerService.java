package com.my.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.repository.CustomerRepository;
import com.my.vo.Customer;


@Service("customerService")
public class CustomerService {
	@Autowired
	@Qualifier("customerRepository")
	private CustomerRepository repository;
	public CustomerService() {}
	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}
	public CustomerRepository getRepository() {
			return repository;
	}
	public void setRepository(CustomerRepository repository) {
		this.repository = repository;
	}
	
	/**
	 * 아이디와 비밀번호가 일치하지않으면 FindException예외 발생한다
	 * @param id 아이디
	 * @param pwd 비밀번호 
	 */
	public void login(String id ,String pwd) throws FindException{
		Customer c = repository.selectById(id);		
		if(!c.getPwd().equals(pwd)) {
			throw new FindException("로그인 실패");
		}
	}
	/**
	 * 아이디가 이미 존재하는지 확인한다.
	 * 아이디가 있는 경우에는 FindException 발생한다.
	 * @param id 아이디
	 */
	public void idDupchk(String id) throws FindException {
			repository.selectById(id);
	}
	public Customer info(String id) throws FindException {
		return repository.selectById(id);
	}
	/**
	 * 고객정보를 가입한다.
	 * 가입실패된 경우 AddException 발생한다.
	 * @param c 고객정보
	 */
	public void signup(Customer c) throws AddException {
		repository.insert(c);
	}

}
