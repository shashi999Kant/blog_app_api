package com.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blog.entities.PaymentEntity;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {
    // Add custom queries if needed
	
	@Query("SELECT SUM(amount) AS total_sum FROM PaymentEntity")
	public int getAllDonations();
	
	
}
