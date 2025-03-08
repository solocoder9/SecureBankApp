package com.bankingapplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankingapplication.entity.User;

/**
 * Repository interface for managing User entities in the database.
 */
public interface UserRepository extends JpaRepository<User, Long> {
	
	// Check if a user with the given email exists
	Boolean existsByEmail(String email);
	
	// Find a user by their email
	Optional<User> findByEmail(String email);

	// Check if a user with the given account number exists
	Boolean existsByAccountNumber(String accountNumber);

	// Find a user by their account number
	User findByAccountNumber(String accountNumber);
	
}
