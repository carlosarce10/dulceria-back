package mx.edu.utez.sad.configuration;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mx.edu.utez.sad.entity.UserEntity;
import mx.edu.utez.sad.service.UserService;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity user = userService.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("Username not found.");
		}
		
		return CustomUserDetails.build(user);
	}
	
	
	
}
