package mx.edu.utez.sad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.edu.utez.sad.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>{

	List<ProductEntity> findByStatusOrderByIdDesc(boolean status);
	
	List<ProductEntity> findAllByOrderByIdDesc();
	
}
