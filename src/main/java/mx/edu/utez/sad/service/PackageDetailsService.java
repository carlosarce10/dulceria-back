package mx.edu.utez.sad.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.sad.entity.PackageDetailsEntity;
import mx.edu.utez.sad.entity.PackageEntity;
import mx.edu.utez.sad.repository.PackageDetailsRepository;

@Service
@Transactional
public class PackageDetailsService {
	@Autowired
	private PackageDetailsRepository pdr;
	
	public List<PackageDetailsEntity> findAll(){
		return pdr.findAllByOrderByIdDesc();
	}
	
	public List<PackageDetailsEntity> findByPackagee(PackageEntity packagee){
		return pdr.findByPackageeOrderByIdDesc(packagee);
	}
	
	public PackageDetailsEntity findById(long id) {
		Optional<PackageDetailsEntity> packageDetails = pdr.findById(id);
		return packageDetails.isPresent()?packageDetails.get():new PackageDetailsEntity();
	}
	
	public PackageDetailsEntity save(PackageDetailsEntity brand) {
		return pdr.save(brand);
	}

	public PackageDetailsEntity deleteById(long id) {
		PackageDetailsEntity packageDetails = findById(id);
		pdr.deleteById(id);
		return packageDetails;
	}
		
	public void deleteByPackagee(PackageEntity packagee) {
		pdr.deleteByPackagee(packagee);
	}
}
