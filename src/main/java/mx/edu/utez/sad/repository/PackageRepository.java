package mx.edu.utez.sad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.edu.utez.sad.entity.PackageEntity;

@Repository
public interface PackageRepository extends JpaRepository<PackageEntity, Long>{

	List<PackageEntity> findByStatusOrderByIdDesc(boolean status);
	
	List<PackageEntity> findAllByOrderByIdDesc();

	
}
