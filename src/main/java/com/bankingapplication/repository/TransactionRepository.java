package com.bankingapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankingapplication.entity.Transaction;

/**
 * Repository interface for managing Transaction entities in the database.
 */
public interface TransactionRepository extends JpaRepository<Transaction, String> {
	// JpaRepository provides CRUD operations for Transaction entities with String as the ID type.
}
