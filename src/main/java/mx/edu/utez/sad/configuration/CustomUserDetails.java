package mx.edu.utez.sad.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import mx.edu.utez.sad.entity.UserEntity;

public class CustomUserDetails implements UserDetails, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	public CustomUserDetails() {
		super();
	}
	
	public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}
	
	public static CustomUserDetails build(UserEntity user) {
		List<GrantedAuthority> auths = new ArrayList<>();
		SimpleGrantedAuthority anAuthoritie = new SimpleGrantedAuthority(user.getRole().getName());
		auths.add(anAuthoritie);
		
		return new CustomUserDetails(user.getUsername(), user.getPassword(), auths);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {		
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
	

}
