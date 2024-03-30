package com.exchanger.ceservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exchanger.ceservice.entity.CurrencyConversionTransaction;

@Repository
public interface CurrencyConversionTransactionRepository extends JpaRepository<CurrencyConversionTransaction, Long> {

}
