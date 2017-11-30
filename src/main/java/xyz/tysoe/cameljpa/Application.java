package xyz.tysoe.cameljpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Produce;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class Application {

	
	interface CreateFamilyProxy {
		public String createFamily(@Body String body);
	}
	
	@Produce(uri="direct:createFamily")
	CreateFamilyProxy createFamilyProxy;
	
	@RequestMapping("/create")
	@ResponseBody
	String home() {
		
		return createFamilyProxy.createFamily("Nothing exciting...");
		
	}
	
	@Component
	class CamelJpaRouteBuilder extends RouteBuilder {

		@Override
		public void configure() {
			
			from("direct:createFamily")
				.log("I am creating a new family")
				.setBody().simple("{\"output:\":\"true\"}");
		}

	}
	
	public static void main(String[] args) throws Exception {
	    SpringApplication.run(Application.class, args);
	}
	
}
