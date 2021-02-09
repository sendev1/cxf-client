package com.cxf.client;

import com.tutorialspoint.helloworld.HelloWorldPortType;
import com.tutorialspoint.helloworld.HelloWorldService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
public class HelloController {

    private final HelloWorldPortType helloWorldPortType;

    public HelloController(@Qualifier("soapClient") HelloWorldPortType helloWorldPortType) {
		this.helloWorldPortType = helloWorldPortType;
	}


    @RequestMapping("/hello")
    public String hello() throws MalformedURLException {
       
        return helloWorldPortType.greetings("S");
    }
}
