package mx.edu.utez.sad.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.sad.entity.RoleEntity;
import mx.edu.utez.sad.entity.UserEntity;
import mx.edu.utez.sad.repository.UserRepository;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public List<UserEntity> getAll(){
		return userRepository.findAllByOrderByIdDesc();
	}
	
	public UserEntity findById(long id) {
		Optional<UserEntity> user = userRepository.findById(id);
		return user.isPresent()?user.get():new UserEntity();
	}
	
	public UserEntity save(UserEntity user) {
		return userRepository.save(user);
	}
	
	public List<UserEntity> findByStatus(boolean status){
		return userRepository.findByStatusOrderByIdDesc(status);
	}
	
	public List<UserEntity> findByStatusAndRole(boolean status, RoleEntity role){
		return userRepository.findByStatusAndRoleOrderByIdDesc(status, role);
	}
	
	public UserEntity findByUsername(String username) {
		return userRepository.findByUsernameOrderByIdDesc(username);
	}
	
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsernameOrderByIdDesc(username);
	}
}
