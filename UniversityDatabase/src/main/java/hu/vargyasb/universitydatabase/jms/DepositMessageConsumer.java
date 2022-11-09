package hu.vargyasb.universitydatabase.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import hu.vargyasb.universitydatabase.ws.DepositMessage;

@Component
public class DepositMessageConsumer {

	@JmsListener(destination = "deposits"/*, containerFactory = "myFactory"*/)
	public void onDepositMessage(DepositMessage depositMessage) {
		System.out.println("aaa");
		System.out.println(depositMessage);
	}
}
