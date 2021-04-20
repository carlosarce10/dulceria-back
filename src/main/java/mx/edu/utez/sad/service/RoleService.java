package mx.edu.utez.sad.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.edu.utez.sad.entity.RoleEntity;
import mx.edu.utez.sad.repository.RoleRepository;

@Service
@Transactional
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;
	
	public List<RoleEntity> getAll(){
		return roleRepository.findAllByOrderByIdDesc();
	}
	
	public RoleEntity findById(long id) {
		Optional<RoleEntity> role = roleRepository.findById(id);
		return role.isPresent()?role.get():new RoleEntity();
	}
	
	public RoleEntity save(RoleEntity role) {
		return roleRepository.save(role);
	}
	
	public RoleEntity findByName(String name) {
		return roleRepository.findByNameOrderByIdDesc(name);
	}

}
