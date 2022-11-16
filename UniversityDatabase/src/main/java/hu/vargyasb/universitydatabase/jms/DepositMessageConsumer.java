package hu.vargyasb.universitydatabase.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import hu.vargyasb.universitydatabase.service.StudentService;
import hu.vargyasb.universitydatabase.ws.DepositMessage;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class DepositMessageConsumer {

	private final StudentService studentService;
	
//	@JmsListener(destination = "deposits", containerFactory = "myFactory")
	public void onDepositMessage(DepositMessage depositMessage) {
//		System.out.println(depositMessage);
		if (depositMessage != null) {
			studentService.updateBalanceForStudent(depositMessage.getStudentId(), depositMessage.getDeposit());
		}
	}
}
