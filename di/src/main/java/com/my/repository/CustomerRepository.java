package com.my.repository;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.vo.Customer;		//ctrl + shift + o 

public interface CustomerRepository {
	/**
	 * 고객을 저장소에 추가한다.
	 * @param c 고객
	 * @exception AddException ID가 중복될 경우 AddException발생한다.
	 */
	void insert(Customer c) throws AddException;

	/**
	 * 아이디에 해당하는 고객을 반환한다.
	 * @param id 아이디
	 * @return 고객
	 * @exception FindException 아이디에 해당하는 고객이 없으면 FindException 발생한다.
	 */
	Customer selectById(String id) throws FindException;
}
