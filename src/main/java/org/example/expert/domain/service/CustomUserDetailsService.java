package org.example.expert.domain.service;

import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userMail) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(userMail).orElseThrow();
		return org.springframework.security.core.userdetails.User.builder()
			.username(user.getEmail())
			.password(user.getPassword())
			.disabled(false)
			.accountExpired(false)
			.credentialsExpired(false)
			.authorities(new SimpleGrantedAuthority("ROLE_"+user.getUserRole().toString()))
			.build();
	}
}
