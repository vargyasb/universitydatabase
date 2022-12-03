package hu.vargyasb.universitydatabase.xmlws;

import java.time.LocalDate;
import java.util.List;

import javax.jws.WebService;
import javax.validation.Valid;

import hu.vargyasb.universitydatabase.api.model.TimeTableItemDto;

@WebService
public interface TimeTableXmlWs {

	public List<TimeTableItemDto> getApiTimetable(@Valid Integer studentId, @Valid Integer teacherId,
			@Valid LocalDate from, @Valid LocalDate until);
}
