package hu.vargyasb.universitydatabase.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hu.vargyasb.universitydatabase.xmlws.TimeTableXmlWs;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebServiceConfig {

	private final Bus bus;
	private final TimeTableXmlWs timeTableXmlWs;
	
	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, timeTableXmlWs);
		endpoint.publish("/timeTable");
		
		return endpoint;
	}
}
