package com.cxf.client;

import com.tutorialspoint.helloworld.HelloWorldPortType;
import com.tutorialspoint.helloworld.HelloWorldService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
public class HelloController {

    /*public final static QName SERVICE = new QName("http://server.cxf.com/", "HelloWorldImplService");*/

    //private final HelloWorldPortType helloWorldClient;

    /*public HelloController(HelloWorldPortType helloWorldClient) {
        this.helloWorldClient = helloWorldClient;
    }*/

    /*public HelloController(ApplicationConfig applicationConfig, Bus bus){
        helloWorldClient = applicationConfig.helloWorldClient(bus);
    }*/

    private final HelloWorldPortType helloWorldPortType;

    public HelloController(@Qualifier("soapClient") HelloWorldPortType helloWorldPortType) {
		this.helloWorldPortType = helloWorldPortType;
	}


    @RequestMapping("/hello")
    public String hello() throws MalformedURLException {

        /*
       // Creating client directly here does not throw any error but native image app stops as soon as it starts with out any error
        final Service service = Service.create(new URL("http://localhost:8080/cxf/ws/helloworld?wsdl"),
                SERVICE);

        final HelloWorldPortType port = service.getPort(HelloWorldPortType.class);
        port.greetings("hi");
        return "hi";*/

        // The code is currently using JaxWsProxyFactoryBean to make client call and native image is failing with following error
        /*nested exception is java.lang.RuntimeException: java.lang.InternalError: java.lang.NoSuchMethodError: org.apache.cxf.binding.soap.wsdl.extensions.SoapBinding.setElementType(javax.xml.namespace.QName)*/
        return helloWorldPortType.greetings("S");
    }
}
