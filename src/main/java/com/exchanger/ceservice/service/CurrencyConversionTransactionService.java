package com.exchanger.ceservice.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
			transaction = currencyConversionTransactionRepository.save(new CurrencyConversionTransaction(fromCurrency,
					amount, toCurrency, convertedValue, LocalDate.now()));
			return Pair.of(transaction.getId(), transaction.getConvertedAmount());
		}
		return null;
	}

	public CurrencyConversionTransaction save(CurrencyConversionTransaction entity) {
		return currencyConversionTransactionRepository.save(entity);
	}

	public CurrencyConversionTransaction findById(Long transactionId) {
		Optional<CurrencyConversionTransaction> //
		res = currencyConversionTransactionRepository.findById(transactionId);
		return res.isPresent() ? res.get() : null;

	}
	
	public BigDecimal getRate(String srcCurrency, String trgCurrency) {
		return currencyLayerService.getRate(srcCurrency, trgCurrency);
	}

	public Page<CurrencyConversionTransaction> loadByDate(LocalDate transactionDate, int page, int size) {
		return currencyConversionTransactionRepository.findByCreated(transactionDate, PageRequest.of(page, size));
	}

}
