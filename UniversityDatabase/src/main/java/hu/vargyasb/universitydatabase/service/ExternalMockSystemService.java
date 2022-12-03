package hu.vargyasb.universitydatabase.service;

import java.util.Random;

import javax.jms.Topic;

import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import hu.vargyasb.jms.dto.FreeSemesterRequest;
import hu.vargyasb.universitydatabase.aspect.Recall;
import hu.vargyasb.universitydatabase.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

@Service
@Recall
@RequiredArgsConstructor
public class ExternalMockSystemService{

	public static final String DEST_FREE_SEMESTER_REQUESTS = "free_semester_requests";
	public static final String DEST_FREE_SEMESTER_RESPONSES = "free_semester_responses";
	
	private final JmsTemplate educationJmsTemplate;
	private final StudentRepository studentRepository;
	
	private Random random = new Random();
	
	public int getUsedFreeSemesters(int externalStudentId) {
		int r = random.nextInt(0, 2);
		System.out.println("In getUsedFreeSemesters: random = " + r + "for extStudentId: " + externalStudentId);
		if (r == 0) {
			System.out.println("Throw in getUsedFreeSemesters");
			throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			r = random.nextInt(0, 10);
			System.out.println("r in else: " + r);
		}
		return r;
	}

	public void askNumFreeSemestersForStudent(Integer externalId) {
		FreeSemesterRequest freeSemesterRequest = new FreeSemesterRequest();
		freeSemesterRequest.setExternalStudentId(externalId);
		
		Topic topic = educationJmsTemplate.execute(session -> session.createTopic(DEST_FREE_SEMESTER_RESPONSES));
		
		educationJmsTemplate.convertAndSend(
				DEST_FREE_SEMESTER_REQUESTS,
				freeSemesterRequest,
				message -> {
					message.setJMSReplyTo(topic);
					return message;
				}
			);
	}
}
