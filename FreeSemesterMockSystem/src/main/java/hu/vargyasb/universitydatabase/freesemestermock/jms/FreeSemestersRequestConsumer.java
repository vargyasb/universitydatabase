package hu.vargyasb.universitydatabase.freesemestermock.jms;

import javax.jms.Topic;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import hu.vargyasb.jms.dto.FreeSemesterRequest;
import hu.vargyasb.jms.dto.FreeSemesterResponse;
import hu.vargyasb.universitydatabase.freesemestermock.xmlws.FreeSemesterXmlWs;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FreeSemestersRequestConsumer {
	
	private final FreeSemesterXmlWs freeSemesterXmlWs;
	private final JmsTemplate jmsTemplate;

	@JmsListener(destination = "free_semester_requests")
	public void onFreeSemestersRequest(Message<FreeSemesterRequest> message) {
		int studentId = message.getPayload().getExternalStudentId();
		int usedFreeSemesters = freeSemesterXmlWs.getUsedFreeSemesters(studentId);
		
		FreeSemesterResponse freeSemesterResponse = new FreeSemesterResponse();
		freeSemesterResponse.setNumberOfFreeSemesters(usedFreeSemesters);
		freeSemesterResponse.setExternalStudentId(studentId);
		
		jmsTemplate.convertAndSend(
				(Topic)message.getHeaders().get(JmsHeaders.REPLY_TO),
				freeSemesterResponse);
	}
}
