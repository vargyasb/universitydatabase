package hu.vargyasb.universitydatabase.xmlws;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hu.vargyasb.universitydatabase.api.model.TimeTableItemDto;
import hu.vargyasb.universitydatabase.mapper.TimeTableMapper;
import hu.vargyasb.universitydatabase.model.TimeTableItem;
import hu.vargyasb.universitydatabase.service.TimeTableService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimeTableXmlWsImpl implements TimeTableXmlWs {
	
	private final TimeTableService timeTableService;
	private final TimeTableMapper timeTableMapper;

	@Override
	public List<TimeTableItemDto> getApiTimetable(@Valid Integer studentId, @Valid Integer teacherId,
			@Valid LocalDate from, @Valid LocalDate until) {
		
		try {
			ArrayList<TimeTableItemDto> result = new ArrayList<>();

			if (studentId != null) {
				Map<LocalDate, List<TimeTableItem>> timeTableForStudent = timeTableService
						.getTimeTableForStudent(studentId, from, until);
				timeTableMappingUtil(result, timeTableForStudent);
				return result;
			} else if (teacherId != null) {
				Map<LocalDate, List<TimeTableItem>> timeTableForTeacher = timeTableService
						.getTimeTableForTeacher(teacherId, from, until);
				timeTableMappingUtil(result, timeTableForTeacher);
				return result;
			} else {
				return null;
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void timeTableMappingUtil(ArrayList<TimeTableItemDto> result,
			Map<LocalDate, List<TimeTableItem>> timeTableForStudent) {
		List<TimeTableItem> items;
		List<TimeTableItemDto> itemDtos;
		for (Map.Entry<LocalDate, List<TimeTableItem>> entry : timeTableForStudent.entrySet()) {
			LocalDate day = entry.getKey();
			items = entry.getValue();
			itemDtos = timeTableMapper.timeTableItemsToDtos(items);
			itemDtos.forEach(i -> i.setDay(day));
			result.addAll(itemDtos);
		}
	}

}
