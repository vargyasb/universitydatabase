package hu.vargyasb.universitydatabase.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;

import com.querydsl.core.types.Predicate;

public interface QuerydslWithEntityGraphRepository<T, ID> {

	List<T> findAll(Predicate predicate, String entityGraphName, EntityGraph.EntityGraphType egType, Sort sort);
}
