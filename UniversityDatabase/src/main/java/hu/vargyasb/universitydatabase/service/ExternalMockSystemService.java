package hu.vargyasb.universitydatabase.service;

import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import hu.vargyasb.universitydatabase.aspect.Recall;

@Service
@Recall
public class ExternalMockSystemService{

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
}
