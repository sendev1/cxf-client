package com.cxf.client;

import com.tutorialspoint.helloworld.HelloWorldPortType;
import com.tutorialspoint.helloworld.HelloWorldService;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

@Configuration(proxyBeanMethods = false)
@ImportResource("classpath:META-INF/cxf/cxf.xml")
public class ApplicationConfig {

   /* @Bean(name=Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new  SpringBus();
    }*/

    // If @Bean method is used to create code then following error is thrown while running native image
    /*nested exception is java.lang.RuntimeException: java.lang.InternalError: java.lang.NoSuchMethodError: org.apache.cxf.binding.soap.wsdl.extensions.SoapBinding.setElementType(javax.xml.namespace.QName)*/
    /*@Bean
    public HelloWorldPortType helloWorldClient() throws MalformedURLException {
        final QName SERVICE = new QName("http://server.cxf.com/", "HelloWorldImplService");
        final Service service = Service.create(new URL("http://localhost:8080/cxf/ws/helloworld?wsdl"),
                SERVICE);
       return service.getPort(HelloWorldPortType.class);
    }*/

	@Bean("soapClient")
    public HelloWorldPortType helloWorldClient(Bus bus){
        final JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setBus(bus);
        factory.setServiceClass(HelloWorldPortType.class);
        factory.setAddress("http://localhost:8080/cxf/ws/helloworld");
        return (HelloWorldPortType) factory.create();
    }

}
