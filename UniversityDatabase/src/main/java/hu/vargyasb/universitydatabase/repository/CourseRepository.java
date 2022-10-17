package hu.vargyasb.universitydatabase.repository;

import java.util.Iterator;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import hu.vargyasb.universitydatabase.model.Course;
import hu.vargyasb.universitydatabase.model.QCourse;

public interface CourseRepository extends JpaRepository<Course, Integer>, QuerydslPredicateExecutor<Course>,
		QuerydslBinderCustomizer<QCourse>, QuerydslWithEntityGraphRepository<Course, Integer> {

	@Override
	default void customize(QuerydslBindings bindings, QCourse course) {
		bindings.bind(course.name).first((path, value) -> path.startsWithIgnoreCase(value));
		bindings.bind(course.teachers.any().name).first((path, value) -> path.startsWithIgnoreCase(value));
		bindings.bind(course.students.any().id).first((path, value) -> path.eq(value));

		bindings.bind(course.students.any().semester).all((path, values) -> {
			if (values.size() != 2) {
				return Optional.empty();
			}

			Iterator<? extends Integer> iterator = values.iterator();
			Integer from = iterator.next();
			Integer to = iterator.next();

			return Optional.of(path.between(from, to));
		});
	}

}