package com.jafir.springboot;

import com.jafir.springboot.listener.MainBusListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jafir.springboot.service.dao")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication sa = new SpringApplication(DemoApplication.class);
		sa.addListeners(new MainBusListener());
		sa.run(args);

//		SpringApplication.run(DemoApplication.class, args);

	}
}
