package hu.vargyasb.universitydatabase.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.vargyasb.universitydatabase.api.model.TimeTableItemDto;
import hu.vargyasb.universitydatabase.model.TimeTableItem;

@Mapper(componentModel = "spring")
public interface TimeTableMapper {

	@Mapping(target = "courseName", source="course.name")
	public TimeTableItemDto timeTableItemToDto(TimeTableItem item);
	
	public List<TimeTableItemDto> timeTableItemsToDtos(List<TimeTableItem> items);
}
