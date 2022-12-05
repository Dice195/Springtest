package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.my.vo.A;

@Configuration
public class myApplicationContext {
	@Bean
	com.my.vo.A a(){
		return new A(99);
			
	}
	
}
