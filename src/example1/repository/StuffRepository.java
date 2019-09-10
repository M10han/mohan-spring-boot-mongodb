package repository;

import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.data.jdbc.repository.query.Query;

import domain.Stuff;

public interface StuffRepository extends MongoRepository<Stuff, String> {
	@Query(value = "{'name' : ?0")
	Stuff findByName(String name);
	
	@Query(value = "{'size' : ?0")
	List<Stuff> findBySize(Size size);
}
