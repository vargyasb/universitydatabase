package hu.vargyasb.universitydatabase.freesemestermock.config;

import javax.jms.ConnectionFactory;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JmsConfig {

	@Bean
	public MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setObjectMapper(objectMapper);
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}
	
//	@Bean
//	public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
//		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//		((SingleConnectionFactory) connectionFactory).setClientId("freesemestermock");
//		
//		configurer.configure(factory, connectionFactory);
//		factory.setSubscriptionDurable(true);
//		return factory;
//	}
	
	@Bean
	public JmsTemplate jmsTemplate(ObjectMapper objectMapper, ConnectionFactory connectionFactory) {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(connectionFactory);
		jmsTemplate.setMessageConverter(jacksonJmsMessageConverter(objectMapper));
		jmsTemplate.setPubSubDomain(true);
		return jmsTemplate;
	}
}
