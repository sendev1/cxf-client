package com.cxf.client;

import com.tutorialspoint.helloworld.HelloWorldPortType;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.extension.ExtensionManagerBus;
import org.apache.cxf.common.spi.GeneratedNamespaceClassLoader;
import org.apache.cxf.common.spi.NamespaceClassCreator;
import org.apache.cxf.endpoint.dynamic.ExceptionClassCreator;
import org.apache.cxf.endpoint.dynamic.ExceptionClassLoader;
import org.apache.cxf.jaxb.FactoryClassCreator;
import org.apache.cxf.jaxb.FactoryClassLoader;
import org.apache.cxf.jaxb.WrapperHelperClassLoader;
import org.apache.cxf.jaxb.WrapperHelperCreator;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.spi.WrapperClassCreator;
import org.apache.cxf.jaxws.spi.WrapperClassLoader;
import org.apache.cxf.wsdl.ExtensionClassCreator;
import org.apache.cxf.wsdl.ExtensionClassLoader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
//@ImportResource("classpath:META-INF/cxf/cxf.xml")
public class ApplicationConfig {

	@Bean
	public Bus bus() {
		final Bus bus = new ExtensionManagerBus();
		BusFactory.setDefaultBus(bus);

		bus.setExtension(new WrapperHelperClassLoader(bus), WrapperHelperCreator.class);
		bus.setExtension(new ExtensionClassLoader(bus), ExtensionClassCreator.class);
		bus.setExtension(new ExceptionClassLoader(bus), ExceptionClassCreator.class);
		bus.setExtension(new WrapperClassLoader(bus), WrapperClassCreator.class);
		bus.setExtension(new FactoryClassLoader(bus), FactoryClassCreator.class);
		bus.setExtension(new GeneratedNamespaceClassLoader(bus), NamespaceClassCreator.class);

		return bus;
	}


	@Bean("soapClient")
	public HelloWorldPortType helloWorldClient(Bus bus){
        final JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setBus(bus);
		factory.setServiceClass(HelloWorldPortType.class);
		factory.setAddress("http://localhost:8080/cxf/ws/helloworld");
		return (HelloWorldPortType) factory.create();
	}

}
