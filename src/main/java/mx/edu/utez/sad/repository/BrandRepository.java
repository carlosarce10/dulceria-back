package mx.edu.utez.sad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.edu.utez.sad.entity.BrandEntity;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long>{
	
	List<BrandEntity> findByStatusOrderByIdDesc(boolean status);
	
	List<BrandEntity> findAllByOrderByIdDesc();
}
