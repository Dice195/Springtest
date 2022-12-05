package config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.my.vo.A;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = {"com.my.repository","com.my.service","com.my.customer"})
public class myApplicationContext {
	@Bean
	public com.my.vo.A a(){
		return new A(99);
			
	}
//	@Bean
//	SimpleDriverDataSource dataSource(){
//		SimpleDriverDataSource sds = new SimpleDriverDataSource();
//		sds.setDriverClass(oracle.jdbc.driver.OracleDriver.class);
//		sds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
//		sds.setUsername("hr");
//		sds.setPassword("hr");
//		return sds;
//	}
	@Bean
	public HikariConfig hikariConfig() {
		HikariConfig config = new HikariConfig();
//		config.setDriverClassName("oracle.jdbc.OracleDriver");
//		config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
		config.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");		//spy내부구조를 본다.
		config.setJdbcUrl("jdbc:log4jdbc:oracle:thin:@127.0.0.1:1521:XE");
		
		config.setUsername("hr");
		config.setPassword("hr");
//		config.setMaximumPoolSize(10);			//커넥션을 최대 몇개까지 쓸것이다. ex)커넥션을 10개까지 관리하겠다.
		config.setMinimumIdle(2);  				//초기커넥션을 몇개를 만들것인가.
		return config;
	}	
	
	@Bean
	public DataSource dataSoruce() {
		return new HikariDataSource(hikariConfig());
	}
	
	//<bean id="hikariConfig" class="HikariConfig">
		//  <property name="driverClassName" value="oracle.jdbc.driver.OracleDriver"/>
		//</bean>
		//<bean id="dataSource" class="HikariDataSource">
		//   <constructor-arg ref="hikariConfig"/>
		//</bean>

}
