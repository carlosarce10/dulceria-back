package mx.edu.utez.sad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.edu.utez.sad.entity.PackageDetailsEntity;
import mx.edu.utez.sad.entity.PackageEntity;

@Repository
public interface PackageDetailsRepository extends JpaRepository<PackageDetailsEntity, Long>{

	List<PackageDetailsEntity> findByPackageeOrderByIdDesc(PackageEntity packagee);
	
	List<PackageDetailsEntity> findAllByOrderByIdDesc();
	
	public void deleteByPackagee(PackageEntity packagee);

}
