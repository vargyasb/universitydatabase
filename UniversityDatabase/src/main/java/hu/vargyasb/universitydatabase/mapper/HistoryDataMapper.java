package hu.vargyasb.universitydatabase.mapper;

import org.mapstruct.Mapper;

import hu.vargyasb.universitydatabase.api.model.HistoryDataCourseDto;
import hu.vargyasb.universitydatabase.model.Course;
import hu.vargyasb.universitydatabase.model.HistoryData;

@Mapper(componentModel = "spring")
public interface HistoryDataMapper {

	HistoryDataCourseDto courseHistoryDataToDto(HistoryData<Course> hd);
}
