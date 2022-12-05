package control;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.vo.Customer;

@Controller
public class TestController {
	
	@GetMapping("a")
	@ResponseBody
	public String a() {
		return "a...";
	}
	
	//http://localhost:8888/mvc/b?one=hello&num=3
	//http://localhost:8888/mvc/b?num=3
	//http://localhost:8888/mvc/b?one=hi
	@GetMapping("b")
	@ResponseBody
	public String b(@RequestParam(defaultValue="hello")String one, @RequestParam(defaultValue="0")int num) {
//		Integer.parseInt(num)*10; 
		return "요청전달데이터는 one=" + one + ", num=" + num;
	}
	
	//http://localhost:8888/mvc/c?one=a&one=b&one=c&two=d
	//http://localhost:8888/mvc/c?two=d
	
	
//	http://localhost:8888/mvc1/c?one=a&one=b&two=c
//	http://localhost:8888/mvc1/c?two=c

	String returnValue;
	
	@GetMapping("c")
	@ResponseBody
	public String c(@RequestParam(name = "one" )Optional<String[]>optOne, String two) {
		returnValue = "요청전달데이터";
//		if(optOne.isPresent()) {
//			String[] arr = optOne.get();
//			for(String o: arr) {
//				returnValue += "one=" + o +", ";
//			}
//		}		
		optOne.ifPresent(arr ->{
			for(String o: arr) {
				returnValue += "one=" + o +", ";
			}
		});
		returnValue += "two=" + two;
		return returnValue;
	}
	
	
	//http://localhost:8888/mvc/d?id=id1&pwd=p1&name=n1
	@GetMapping("d")
	@ResponseBody
	public String d(Customer customer) {		//커맨드 객체 
		return "요청된 고객정보는" + customer.getId() + ":" + customer.getName();
	}
	
	//json 문자혈 형태로 응답이된다.
	@GetMapping("e")
	@ResponseBody
	public Customer e() {
		return new Customer("id1","p1","김태현");
	}
	
	@GetMapping("f")
	@ResponseBody
	public List<Customer> f() {
		List<Customer> list = new ArrayList<>();
		list.add(new Customer("id1", "p1", "김태현"));
		list.add(new Customer("id2", "p2", "김태현"));
		list.add(new Customer("id3", "p3", "김태현"));
		return list;
		
	}
	@GetMapping("g")
	public ResponseEntity<String>  g() {
//		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		ResponseEntity<String> re = new ResponseEntity<String>(
				"이미 존재하는 아이디입니다.",HttpStatus.INTERNAL_SERVER_ERROR);
		return re;
		//응답코드를 조작 한다.
	}
	

}
