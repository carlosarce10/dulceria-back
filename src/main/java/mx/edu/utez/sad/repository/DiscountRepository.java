package mx.edu.utez.sad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.edu.utez.sad.entity.BrandEntity;
import mx.edu.utez.sad.entity.CategoryEntity;
import mx.edu.utez.sad.entity.DiscountEntity;
import mx.edu.utez.sad.entity.ProductEntity;

@Repository
public interface DiscountRepository extends JpaRepository<DiscountEntity, Long>{
	
	public boolean existsByProductOrderByIdDesc(ProductEntity product);
	
	public boolean existsByBrandOrderByIdDesc(BrandEntity brand);
	
	public boolean existsByCategoryOrderByIdDesc(CategoryEntity category);
	
	List<DiscountEntity> findAllByOrderByIdDesc();
}
