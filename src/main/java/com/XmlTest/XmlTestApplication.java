package com.XmlTest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@ComponentScan(basePackages={"com.XmlTest.controller","com.XmlTest.service"})
@MapperScan(basePackages="com.XmlTest.dao")
@EnableAutoConfiguration
@SpringBootApplication
public class XmlTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(XmlTestApplication.class, args);
	}
}
