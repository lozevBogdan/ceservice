package com.exchanger.ceservice.repo;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exchanger.ceservice.entity.CurrencyConversionTransaction;

@Repository
public interface CurrencyConversionTransactionRepository extends JpaRepository<CurrencyConversionTransaction, Long> {
	Page<CurrencyConversionTransaction> findByCreated(LocalDate created, Pageable pageable);
}
