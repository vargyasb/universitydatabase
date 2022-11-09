package hu.vargyasb.universitydatabase.freesemestermock.xmlws;

import javax.jws.WebService;

@WebService
public interface FreeSemesterXmlWs {

	public int getUsedFreeSemesters(int externalStudentId);
}
