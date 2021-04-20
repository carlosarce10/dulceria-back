package mx.edu.utez.sad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.edu.utez.sad.entity.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{

	List<CategoryEntity> findByStatusOrderByIdDesc(boolean status);
	
	List<CategoryEntity> findAllByOrderByIdDesc();
	
}
