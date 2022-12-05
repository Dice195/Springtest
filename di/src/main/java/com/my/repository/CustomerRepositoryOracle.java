package com.my.repository;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.vo.Customer;

@Repository("customerRepository")
public class CustomerRepositoryOracle implements CustomerRepository {
	
	@Autowired
	private DataSource ds;
	
	@Override
	public void insert(Customer c) throws AddException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("JDBC드라이버로드 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}
			Connection conn = null;
			try {
				conn = ds.getConnection();
				System.out.println("DB연결성공");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AddException(e.getMessage());
			}
			PreparedStatement pstmt = null;
			String insertSQL = "INSERT INTO customer(id,pwd,name) VALUES(?,?,?)";
			try {
				pstmt = conn.prepareStatement(insertSQL);
				pstmt.setString(1, c.getId());
				pstmt.setString(2, c.getPwd());
				pstmt.setString(3, c.getName());
				int rowCnt = pstmt.executeUpdate();
				System.out.println(rowCnt +"건이 추가되었습니다.");
			} catch (SQLException e) {
				if(e.getErrorCode()==1) {
					throw new AddException("이미 사용중인 아이디입니다.");						//SQL오류코드 번호를 얻어올수있다.
				}
				e.printStackTrace();
				throw new AddException(e.getMessage());
			} finally {	
				if(pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
						throw new AddException(e.getMessage());
					}
				}
				
				if(conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
						throw new AddException(e.getMessage());
					}
				}
			}
		
			
	}

	@Override
	public Customer selectById(String id) throws FindException {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("JDBC드라이버 로드성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		Connection conn = null;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String selectSQL = "SELECT * "
					+ "FROM customer "
					+ "WHERE id=?";
	
			pstmt = conn.prepareStatement(selectSQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){			
				return new Customer(rs.getString("id"),rs.getString("pwd"),rs.getString("name"));
			}
			throw new FindException("아이디에 해당하는 고객이없습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			if(rs!= null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new FindException(e.getMessage());
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new FindException(e.getMessage());
				}
			}
			
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new FindException(e.getMessage());
				}
			}
		}
	
}
}
