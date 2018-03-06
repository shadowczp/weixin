package com.example.weixin;

import com.example.weixin.schedule.Scheduler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@MapperScan("com.example.weixin.dao")
public class WeixinApplication {

	public static void main(String[] args) {
		ApplicationContext context =SpringApplication.run(WeixinApplication.class, args);
		context.getBean(Scheduler.class).refreshToken();
	}
}
