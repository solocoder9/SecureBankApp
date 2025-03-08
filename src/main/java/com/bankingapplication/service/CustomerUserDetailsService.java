package com.bankingapplication.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bankingapplication.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;
	
	/**
	 * Load user details by email (username).
	 * 
	 * @param username The email of the user to load.
	 * @return UserDetails if found.
	 * @throws UsernameNotFoundException If the user is not found.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
	}

}
