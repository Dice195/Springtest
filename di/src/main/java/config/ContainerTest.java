package config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.my.exception.FindException;
import com.my.repository.CustomerRepository;
import com.my.repository.ProductRepository;
import com.my.service.CustomerService;
import com.my.service.ProductService;
import com.my.vo.A;
import com.my.vo.Customer;
import com.my.vo.Product;

public class ContainerTest {

	public static void main(String[] args) {
		ApplicationContext ctx;	//스프링 컨테이너용 라이브러리 
		//스프링 컨테이너를 시작, 설정파일의 <Bean>로 등록한 class타입의 객체가 자동 생성됨 
//		ctx = new ClassPathXmlApplicationContext(" myApplicationContext.xml"); 
//		ctx = new ClassPathXmlApplicationContext(" myApplicationContext2.xml"); 
		
		//스프링 컨테이너 XML대신 자바 설정클래스 사용 
		ctx = new AnnotationConfigApplicationContext(config.myApplicationContext.class);
		//스프링컨테이너에서 관리되는 객체찾기 A.class타입으로 형변환 가능한 객체확인 
		A a = ctx.getBean("a", A.class);    //A객체인지 확인
		System.out.println(a); //a.toString() 자동호출
	
		A a1 = ctx.getBean("a", A.class);   //2번쨰객체 찾기
		System.out.println(a1);
		System.out.println(a==a1); //true
	
		System.out.println("num=" + a.getNum());
		
		ProductRepository repository = ctx.getBean("productRepository", ProductRepository.class);
		try {
			Product p = repository.selectByProdNo("C0001");
			System.out.println("productRepository의 selectByprodNo의 반환값" + p);
		} catch (FindException e) {
			e.printStackTrace();
		}
		
		ProductService service = ctx.getBean("productService",ProductService.class);
		
			try {
				Product p = service.findByNo("C0001");
				System.out.println("productService"+ p);
			} catch (FindException e) {
				e.printStackTrace();
			}
			
			
			System.out.println("productRepository2의 (DataSource사용");
			ProductRepository repository2 = ctx.getBean("productRepository2", ProductRepository.class);
			
			try {
				Product p = repository2.selectByProdNo("C0001");
				System.out.println("productRepository의 selectByprodNo의 반환값" + p);
			} catch (FindException e) {
				e.printStackTrace();
			}
			
		
			CustomerRepository cRepository = ctx.getBean("customerRepository", CustomerRepository.class);
				try {
					Customer c = cRepository.selectById("id1");
					System.out.println(c.getName()); //n1
				}catch(FindException e) {
					e.printStackTrace();
				}
				
				CustomerService cService = 
					ctx.getBean("customerService", CustomerService.class);
				try {
					cService.login("id1", "p1");
					System.out.println("로그인 성공");
				}catch(FindException e) {
					e.printStackTrace();
				}

	}
	
}
