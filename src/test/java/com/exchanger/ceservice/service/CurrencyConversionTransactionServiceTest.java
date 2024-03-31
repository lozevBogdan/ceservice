package com.exchanger.ceservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;

import com.exchanger.ceservice.entity.CurrencyConversionTransaction;
import com.exchanger.ceservice.repo.CurrencyConversionTransactionRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CurrencyConversionTransactionServiceTest {

	private String fromCurrency;
	private BigDecimal amount;
	private String toCurrency;
	private BigDecimal convertedValue;
	private LocalDate currentDate;
	private Long testId;

	@Mock
	private CurrencyConversionTransactionRepository currencyConversionTransactionRepository;
	@Mock
	private CurrencyLayerService currencyLayerService;

	@InjectMocks
	private CurrencyConversionTransactionService currencyConversionTransactionService;

	@BeforeEach
	void init() {
		fromCurrency = "EUR";
		amount = BigDecimal.valueOf(1);
		toCurrency = "BGN";
		convertedValue = BigDecimal.valueOf(1.957878);
		currentDate = LocalDate.now();
		testId = 1l;
	}

	@Test
	public void testLoadByDate_EmptyPage() {
		// Arrange
		int page = 0;
		int size = 10;
		List<CurrencyConversionTransaction> //
		expectedTransactions = Collections.emptyList();
		Page<CurrencyConversionTransaction> //
		expectedPage = new PageImpl<>(expectedTransactions);

		when(currencyConversionTransactionRepository.findByCreated(currentDate, PageRequest.of(page, size)))
				.thenReturn(expectedPage);

		// Act
		Page<CurrencyConversionTransaction> //
		result = currencyConversionTransactionService.loadByDate(currentDate,page, size);

		// Assert
		assertEquals(expectedPage, result);

	}

	@Test
	public void testGetRate() {
	    // Arrange
	    when(currencyLayerService.getRate(fromCurrency, toCurrency)).thenReturn(convertedValue);

	    // Act
	    BigDecimal //
	    result = currencyConversionTransactionService.getRate(fromCurrency, toCurrency);

	    // Assert
	    assertEquals(convertedValue, result);

	}

	@Test
	public void testSave_NullEntity() {
		// Act
		CurrencyConversionTransaction //
		result = currencyConversionTransactionService.save(null);

		// Assert
		assertNull(result);

	}

	@Test
	public void testFindById_TransactionFound() {
		// Arrange
		CurrencyConversionTransaction //
		expectedTransaction = new CurrencyConversionTransaction(fromCurrency, amount,toCurrency, convertedValue, currentDate);
		expectedTransaction.setId(testId);

		when(currencyConversionTransactionRepository.findById(testId)).thenReturn(Optional.of(expectedTransaction));

		// Act
		CurrencyConversionTransaction //
		actualTransaction = currencyConversionTransactionService.findById(testId);

		// Assert
		assertEquals(expectedTransaction, actualTransaction);

	}

	@Test
	public void testFindById_TransactionNotFound() {
	    // Arrange
        when(currencyConversionTransactionRepository.findById(testId)).thenReturn(Optional.empty());

	    // Act
	    CurrencyConversionTransaction //
	    actualTransaction = currencyConversionTransactionService.findById(testId);

	    // Assert
	    assertNull(actualTransaction);

	}

	@Test
	public void testConvertSuccessfully() {
		// Arrange
		when(currencyLayerService.convert(fromCurrency, amount, toCurrency)).thenReturn(convertedValue);

		CurrencyConversionTransaction transactionAfterSave = new CurrencyConversionTransaction(fromCurrency, amount,
				toCurrency, convertedValue, currentDate);
		transactionAfterSave.setId(testId);

		when(currencyConversionTransactionRepository.save(any(CurrencyConversionTransaction.class)))
				.thenReturn(transactionAfterSave);

		// Act
		Pair<Long, BigDecimal> //
		result = currencyConversionTransactionService.convert(fromCurrency, amount, toCurrency);

		// Assert
		assertNotNull(result);
		assertEquals(testId, result.getFirst());
		assertEquals(convertedValue, result.getSecond());
	}

	@Test
	public void testConvert_ConversionFails() {
		// Arrange
		when(currencyLayerService.convert(fromCurrency, amount, toCurrency)).thenReturn(null);

		// Act
		Pair<Long, BigDecimal> //
		result = currencyConversionTransactionService.convert(fromCurrency, amount, toCurrency);

		// Assert
		Assertions.assertNull(result);

	}

	@Test
	public void saveCurrencyConversionTransaction_Successfully() {
		// Arrange
		CurrencyConversionTransaction //
		transactionBeforeSave = new CurrencyConversionTransaction(fromCurrency, amount, toCurrency, convertedValue,
				currentDate);

		CurrencyConversionTransaction //
		transactionAfterSave = new CurrencyConversionTransaction(fromCurrency, amount, toCurrency, convertedValue,
				currentDate);
		transactionAfterSave.setId(testId);

		when(currencyConversionTransactionRepository.save(transactionBeforeSave)).thenReturn(transactionAfterSave);

		// Act
		CurrencyConversionTransaction //
		savedTransaction = currencyConversionTransactionService.save(transactionBeforeSave);

		// Assert
		assertEquals(savedTransaction, transactionAfterSave);

	}

}
