package mx.edu.utez.sad.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.sad.entity.BrandEntity;
import mx.edu.utez.sad.repository.BrandRepository;

@Service
@Transactional
public class BrandService {
	@Autowired
	private BrandRepository brandRepository;
	
	public List<BrandEntity> findAll(){
		return brandRepository.findAllByOrderByIdDesc();
	}
	
	public List<BrandEntity> findByStatus(boolean status){
		return brandRepository.findByStatusOrderByIdDesc(status);
	}
	
	public BrandEntity findById(long id) {
		Optional<BrandEntity> brand = brandRepository.findById(id);
		return brand.isPresent()?brand.get():new BrandEntity();
	}
	
	public BrandEntity save(BrandEntity brand) {
		return brandRepository.save(brand);
	}
	
}