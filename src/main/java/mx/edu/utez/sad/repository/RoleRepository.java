package mx.edu.utez.sad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.edu.utez.sad.entity.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>{

	public RoleEntity findByNameOrderByIdDesc(String name);
	
	public List<RoleEntity> findAllByOrderByIdDesc();
	
}
