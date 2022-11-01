package hu.vargyasb.universitydatabase.web;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.vargyasb.universitydatabase.api.model.CourseDto;
import hu.vargyasb.universitydatabase.mapper.CourseMapper;
import hu.vargyasb.universitydatabase.model.CourseStatistics;
import hu.vargyasb.universitydatabase.repository.CourseRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

	private final CourseRepository courseRepository;

	@GetMapping("/averageSemestersByCourses")
	@Async
	public CompletableFuture<List<CourseStatistics>> getAverageSemestersByCourses() {
		System.out.println("ReportController.getAverageSemestersByCourses called at thread " + Thread.currentThread().getName());
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return CompletableFuture.completedFuture(
				courseRepository.getAverageSemestersByCourses());
	}
	
}
