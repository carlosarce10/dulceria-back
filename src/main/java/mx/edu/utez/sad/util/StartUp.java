package mx.edu.utez.sad.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import mx.edu.utez.sad.entity.RoleEntity;
import mx.edu.utez.sad.entity.UserEntity;
import mx.edu.utez.sad.service.RoleService;
import mx.edu.utez.sad.service.UserService;

@Component
public class StartUp implements CommandLineRunner{

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception {
		
		String roleAdmin = "ROLE_ADMIN";
		String roleCashier = "ROLE_CASHIER";
		
		if(roleService.findByName(roleAdmin)==null) {
			RoleEntity role = new RoleEntity();
			role.setName(roleAdmin);
			roleService.save(role);
		}
		
		if(roleService.findByName(roleCashier)==null) {
			RoleEntity role = new RoleEntity();
			role.setName(roleCashier);
			roleService.save(role);
		}
		
		if(userService.findByUsername("admin")==null) {
			UserEntity admin = new UserEntity();
			admin.setStatus(true);
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("dulceria123"));
			admin.setRole(roleService.findByName(roleAdmin));
			
			userService.save(admin);
			
		}
	}

}
