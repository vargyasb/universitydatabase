package hu.vargyasb.highavailability.ws;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FreeSemestersMessage {

	private int externalStudentId;
	private int numberOfFreeSemesters;
}
