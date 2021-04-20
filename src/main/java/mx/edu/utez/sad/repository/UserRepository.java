package mx.edu.utez.sad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.edu.utez.sad.entity.RoleEntity;
import mx.edu.utez.sad.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	
	public List<UserEntity> findByStatusOrderByIdDesc(boolean status);
	
	public List<UserEntity> findByStatusAndRoleOrderByIdDesc(boolean status, RoleEntity role);
	
	public UserEntity findByUsernameOrderByIdDesc(String username);
	
	public boolean existsByUsernameOrderByIdDesc(String username);
	
	public List<UserEntity> findAllByOrderByIdDesc();
	
}
