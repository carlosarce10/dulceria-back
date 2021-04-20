package mx.edu.utez.sad.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.sad.entity.BrandEntity;
import mx.edu.utez.sad.entity.CategoryEntity;
import mx.edu.utez.sad.entity.DiscountEntity;
import mx.edu.utez.sad.entity.ProductEntity;
import mx.edu.utez.sad.repository.DiscountRepository;

@Service
@Transactional
public class DiscountService {
	@Autowired
	private DiscountRepository dr;
	
	public List<DiscountEntity> findAll(){
		return dr.findAllByOrderByIdDesc();
	}
	
	public DiscountEntity findById(long id) {
		Optional<DiscountEntity> discount = dr.findById(id);
		return discount.isPresent()?discount.get():new DiscountEntity();
	}
	
	public DiscountEntity save(DiscountEntity brand) {
		return dr.save(brand);
	}

	public DiscountEntity deleteById(long id) {
		DiscountEntity discount = findById(id);
		
		dr.deleteById(id);
		
		return discount;
	}

	public boolean productHasDiscount(ProductEntity product) {
		return dr.existsByProductOrderByIdDesc(product);
	}
	
	public boolean brandHasDiscount(BrandEntity brand) {
		return dr.existsByBrandOrderByIdDesc(brand);
	}
	
	public boolean categoryHasDiscount(CategoryEntity category) {
		return dr.existsByCategoryOrderByIdDesc(category);
	}
}
