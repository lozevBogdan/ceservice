package com.exchanger.ceservice.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.exchanger.ceservice.entity.CurrencyConversionTransaction;
import com.exchanger.ceservice.repo.CurrencyConversionTransactionRepository;

@Service
public class CurrencyConversionTransactionService {

	@Autowired
	private CurrencyConversionTransactionRepository currencyConversionTransactionRepository;

	@Autowired
	private CurrencyLayerService currencyLayerService;

	public Pair<Long, BigDecimal> convert(String fromCurrency, BigDecimal amount, String toCurrency) {
		BigDecimal //
		convertedValue = currencyLayerService.convert(fromCurrency, amount, toCurrency);
		if (convertedValue != null) {
			CurrencyConversionTransaction //
			transaction = currencyConversionTransactionRepository
					.save(new CurrencyConversionTransaction(fromCurrency, amount, toCurrency, convertedValue));
			return Pair.of(transaction.getId(), transaction.getConvertedAmount());
		}
		return null;
	}

	public CurrencyConversionTransaction save(CurrencyConversionTransaction entity) {
		return currencyConversionTransactionRepository.save(entity);
	}

}
