package hu.vargyasb.universitydatabase.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.vargyasb.universitydatabase.api.TimetableControllerApi;
import hu.vargyasb.universitydatabase.api.model.TimeTableItemDto;
import hu.vargyasb.universitydatabase.mapper.TimeTableMapper;
import hu.vargyasb.universitydatabase.model.TimeTableItem;
import hu.vargyasb.universitydatabase.service.TimeTableService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TimeTableController implements TimetableControllerApi {

	private final TimeTableService timeTableService;
	private final TimeTableMapper timeTableMapper;

	@Override
	public ResponseEntity<List<TimeTableItemDto>> getApiTimetable(@Valid Integer studentId, @Valid Integer teacherId,
			@Valid LocalDate from, @Valid LocalDate until) {

		try {
			ArrayList<TimeTableItemDto> result = new ArrayList<>();

			if (studentId != null) {
				Map<LocalDate, List<TimeTableItem>> timeTableForStudent = timeTableService
						.getTimeTableForStudent(studentId, from, until);
				timeTableMappingUtil(result, timeTableForStudent);
				return ResponseEntity.ok(result);
			} else if (teacherId != null) {
				Map<LocalDate, List<TimeTableItem>> timeTableForTeacher = timeTableService
						.getTimeTableForTeacher(teacherId, from, until);
				timeTableMappingUtil(result, timeTableForTeacher);
				return ResponseEntity.ok(result);
			} else {
				return ResponseEntity.badRequest().build();
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
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

	@Override
	public ResponseEntity<TimeTableItemDto> searchTimeTable(@Valid Integer studentId, @Valid Integer teacherId,
			@Valid LocalDate from, @Valid String course) {
		Entry<LocalDate, TimeTableItem> foundTimeTableEntry = timeTableService.searchTimeTableOfStudent(studentId, from,
				course);
		if (foundTimeTableEntry == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		TimeTableItemDto timeTableItemDto = timeTableMapper.timeTableItemToDto(foundTimeTableEntry.getValue());
		timeTableItemDto.setDay(foundTimeTableEntry.getKey());

		return ResponseEntity.ok(timeTableItemDto);
	}

}
