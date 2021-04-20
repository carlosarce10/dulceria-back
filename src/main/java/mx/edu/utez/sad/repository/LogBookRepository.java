package mx.edu.utez.sad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.edu.utez.sad.entity.LogBookEntity;

@Repository
public interface LogBookRepository extends JpaRepository<LogBookEntity, Long>{
	
	List<LogBookEntity> findAllByOrderByIdDesc();

}
