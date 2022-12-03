package hu.vargyasb.universitydatabase.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import hu.vargyasb.jms.dto.FreeSemesterResponse;
import hu.vargyasb.universitydatabase.service.ExternalMockSystemService;
import hu.vargyasb.universitydatabase.service.StudentService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FreeSemestersResponseConsumer {

	private final StudentService studentService;
	
	@JmsListener(destination = ExternalMockSystemService.DEST_FREE_SEMESTER_RESPONSES, containerFactory = "educationFactory")
	public void onFreeSemestersResponse(FreeSemesterResponse response) {
		studentService.updateFreeSemesterForStudent(response.getExternalStudentId(), response.getNumberOfFreeSemesters());
	}
}
