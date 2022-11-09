package hu.vargyasb.universitydatabase.freesemestermock.xmlws;

import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FreeSemesterXmlWsImpl implements FreeSemesterXmlWs {
	
	private Random random = new Random();
	
	@Override
	public int getUsedFreeSemesters(int externalStudentId) {
		int r = random.nextInt(0, 2);
		System.out.println("In FreeSemesterMockSystem App getUsedFreeSemesters: random = " + r + "for extStudentId: " + externalStudentId);
		if (r == 0) {
			System.out.println("Throw in FreeSemesterMockSystem App getUsedFreeSemesters");
			throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			r = random.nextInt(0, 10);
			System.out.println("r in else: " + r);
		}
		return r;
	}
	
}
