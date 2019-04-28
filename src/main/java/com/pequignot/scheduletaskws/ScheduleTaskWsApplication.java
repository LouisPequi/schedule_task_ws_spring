package com.pequignot.scheduletaskws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScheduleTaskWsApplication {

	public static void main(String[] args) {
		org.springframework.context.ConfigurableApplicationContext run = SpringApplication.run(ScheduleTaskWsApplication.class, args);
	}

}
