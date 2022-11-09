package hu.vargyasb.universitydatabase.freesemestermock.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hu.vargyasb.universitydatabase.freesemestermock.xmlws.FreeSemesterXmlWs;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebServiceConfig {
	
	private final Bus bus;
	private final FreeSemesterXmlWs freeSemesterXmlWs;

	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, freeSemesterXmlWs);
		endpoint.publish("/freeSemester");
		
		return endpoint;
	}
}
