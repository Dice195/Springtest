package com.my.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.my.exception.FindException;
import com.my.sql.MyConnection;
import com.my.vo.Product;

@Repository("productRepository")
public class ProductRepositoryOracle implements ProductRepository {
	

	@Override
	public int selectCount() throws FindException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = MyConnection.getConnection();
			String selectCountSQL = "select count(*) from product";
			pstmt = conn.prepareStatement(selectCountSQL);
			rs = pstmt.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(rs, pstmt, conn);
		}
		
		
	}

	@Override
	public List<Product> selectAll(int currentPage, int cntPrePage) throws FindException {
		List<Product> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MyConnection.getConnection();
			String selectAllPageSQL = "SELECT *\r\n"
					+ "FROM (  SELECT rownum rn , a.*\r\n"
					+ "               FROM ( SELECT * FROM product ORDER BY prod_no\r\n"
					+ ") a \r\n"
					+ ") WHERE rn BETWEEN ? AND ?                         \r\n";

			pstmt = conn.prepareStatement(selectAllPageSQL);
			int startRow = currentPage * cntPrePage - cntPrePage+1 ;		
			int endRow = currentPage * cntPrePage;
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String prodNo = rs.getString("prod_no");
				String prodName = rs.getString("prod_name");
				int prodPridce = rs.getInt("prod_price");
				String prodInfo = rs.getString("prod_info");
				Product p = new Product(prodNo, prodName, prodPridce, prodInfo);
				list.add(p);
			}
			return list;	
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		
		
		
		
		
	}
	
	@Override
	public List<Product> selectAll() throws FindException {
		List<Product> list = new ArrayList<>();
		//DB연결
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MyConnection.getConnection();
			String selectAllSQL = "SELECT * FROM product ORDER BY prod_no";
			pstmt = conn.prepareStatement(selectAllSQL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String prodNo = rs.getString("prod_no");
				String prodName = rs.getString("prod_name");
				int prodPridce = rs.getInt("prod_price");
				String prodInfo = rs.getString("prod_info");
				Product p = new Product(prodNo, prodName, prodPridce, prodInfo);
				list.add(p);
			}
			return list;			
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			//DB연결닫기
			MyConnection.close(rs, pstmt, conn);
		}
	}

	@Override
	public List<Product> selectByNoORNameORPriceORInfo(String word) throws FindException {
		List<Product> list = new ArrayList<>();
		//DB연결
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MyConnection.getConnection();
			String selectByWorlSQL = "SELECT * \r\n"
					+ "FROM product \r\n"
					+ "WHERE prod_no LIKE ? \r\n"
					+ "  OR prod_name LIKE ? \r\n"
					+ "  OR TO_CHAR(prod_price) LIKE ? \r\n"
					+ "  OR prod_info LIKE ?";
			pstmt = conn.prepareStatement(selectByWorlSQL);
			pstmt.setString(1, "%" + word + "%");
			pstmt.setString(2, "%" + word + "%");
			pstmt.setString(3, "%" + word + "%");
			pstmt.setString(4, "%" + word + "%");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String prodNo = rs.getString("prod_no");
				String prodName = rs.getString("prod_name");
				int prodPridce = rs.getInt("prod_price");
				String prodInfo = rs.getString("prod_info");
				Product p = new Product(prodNo, prodName, prodPridce, prodInfo);
				list.add(p);
			}
			return list;	
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			//DB연결닫기
			MyConnection.close(rs, pstmt, conn);
		}
	}

	@Override
	public Product selectByProdNo(String prodNo) throws FindException {
		//DB연결
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MyConnection.getConnection();
			String selectByProdNoSQL = "SELECT * FROM product WHERE prod_no = ?"; 
			pstmt = conn.prepareStatement(selectByProdNoSQL);
			pstmt.setString(1, prodNo);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String prodName = rs.getString("prod_name");
				int prodPridce = rs.getInt("prod_price");
				String prodInfo = rs.getString("prod_info");
				Product p = new Product(prodNo, prodName, prodPridce, prodInfo);
				return p;
			}else {
				throw new FindException("상품이 없습니다");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			//DB연결닫기
			MyConnection.close(rs, pstmt, conn);
		}
	}


	public static void main(String[] args) {		
		ProductRepository repository = new ProductRepositoryOracle();
		System.out.println("페이지별 상품 목록");
		int cp = 3;
		int cntPerPage = 4;
		try {
			List<Product>list = repository.selectAll(cp,cntPerPage);
			for(Product p: list) {
				System.out.println(p);
			}
		} catch (FindException e1) {
			e1.printStackTrace();
		}
		System.out.println("--------");
		try {
			List<Product>list = repository.selectAll();
			System.out.println("총 상품수 : " + list.size());
			for(Product p: list) {
				System.out.println(p); //p.toString()자동호출됨
			}
		} catch (FindException e) {
			e.printStackTrace();
		}
		
		String prodNo = "C0001"; //XXXX
		try {
			Product p = repository.selectByProdNo(prodNo);
			System.out.println(p); //OK
		} catch (FindException e) {
			e.printStackTrace(); 
		}
		
		String word = "아";
		try {
			List<Product>list = repository.selectByNoORNameORPriceORInfo(word);
			System.out.println(word+"단어를 포함한 상품수 : " + list.size());
			for(Product p: list) {
				System.out.println(p); //p.toString()자동호출됨
			}
		} catch (FindException e) {
			e.printStackTrace();	
		
	}
}

}