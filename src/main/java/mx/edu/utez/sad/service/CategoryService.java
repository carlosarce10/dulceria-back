package mx.edu.utez.sad.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.sad.entity.CategoryEntity;
import mx.edu.utez.sad.repository.CategoryRepository;

@Service
@Transactional
public class CategoryService {
	@Autowired
	private CategoryRepository cr;
	
	public List<CategoryEntity> findAll(){
		return cr.findAllByOrderByIdDesc();
	}
	
	public List<CategoryEntity> findByStatus(boolean status){
		return cr.findByStatusOrderByIdDesc(status);
	}
	
	public CategoryEntity findById(long id) {
		Optional<CategoryEntity> category = cr.findById(id);
		return category.isPresent()?category.get():new CategoryEntity();
	}
	
	public CategoryEntity save(CategoryEntity category) {
		return cr.save(category);
	}
}
