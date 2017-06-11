package com.example.demo;

import com.example.demo.model.BchBlock;
import com.example.demo.service.eth.node.EthNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import javax.annotation.PostConstruct;
import java.util.function.Consumer;

@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(DemoApplication.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
}
