package mx.edu.utez.sad.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.sad.entity.PackageEntity;
import mx.edu.utez.sad.repository.PackageRepository;

@Service
@Transactional
public class PackageService {
	@Autowired
	private PackageRepository pr;
	
	public List<PackageEntity> findAll(){
		return pr.findAllByOrderByIdDesc();
	}
	
	public List<PackageEntity> findByStatus(boolean status){
		return pr.findByStatusOrderByIdDesc(status);
	}
	
	public PackageEntity findById(long id) {
		Optional<PackageEntity> packagee =  pr.findById(id);
		return packagee.isPresent()?packagee.get():new PackageEntity();
	}
	
	public PackageEntity save(PackageEntity brand) {
		return pr.save(brand);
	}
}
