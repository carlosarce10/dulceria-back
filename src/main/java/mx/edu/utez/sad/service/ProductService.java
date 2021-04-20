package mx.edu.utez.sad.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.sad.entity.ProductEntity;
import mx.edu.utez.sad.repository.ProductRepository;

@Service
@Transactional
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	
	public List<ProductEntity> findByStatus(boolean status){
		return productRepository.findByStatusOrderByIdDesc(status);
	}
	
	public List<ProductEntity> findAll(){
		return productRepository.findAllByOrderByIdDesc();
	}
	
	public ProductEntity findById(long id) {
		Optional<ProductEntity> product = productRepository.findById(id);
		return product.isPresent()?product.get():new ProductEntity();
	}
	
	public ProductEntity save(ProductEntity product) {
		return productRepository.save(product);
	}
}
