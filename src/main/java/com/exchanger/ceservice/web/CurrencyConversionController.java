package com.exchanger.ceservice.web;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exchanger.ceservice.entity.CurrencyConversionTransaction;
import com.exchanger.ceservice.service.CurrencyConversionTransactionService;
import com.fasterxml.jackson.annotation.JsonInclude;

@RestController
public class CurrencyConversionController {

	@Autowired
	private CurrencyConversionTransactionService currencyConversionTransactionService;

	@GetMapping("/convert")
	public ResponseEntity<CurrencyConversionResponce> exchangeRate(
			@RequestParam("sourceCurrency") String sourceCurrency,
			@RequestParam("sourceAmount") BigDecimal sourceAmount,
			@RequestParam("targetCurrency") String targetCurrency) {

		Pair<Long, BigDecimal> //
		res = currencyConversionTransactionService.convert(sourceCurrency, sourceAmount, targetCurrency);
		if (res != null) {
			return new ResponseEntity<CurrencyConversionResponce>(
					new CurrencyConversionResponce(res.getFirst(), res.getSecond(), targetCurrency),
					HttpStatusCode.valueOf(200));
		}
		return new ResponseEntity<CurrencyConversionResponce>(new CurrencyConversionResponce(null, null, targetCurrency,
				"Currency Conversion Error: Unable to convert between the specified currencies. Please ensure that both the source and target currencies are valid and supported for conversion."),
				HttpStatusCode.valueOf(400));
	}

	@GetMapping("/conversion-history")
	public ResponseEntity<List<CurrencyConversionResponce>> getConversionHistory(
			@RequestParam(name = "transactionId", required = false) String transactionId,
			@RequestParam(name = "transactionDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate transactionDate,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		if (transactionId == null && transactionDate == null) {
			return new ResponseEntity<List<CurrencyConversionResponce>>(
					List.of(new CurrencyConversionResponce(null, null, null,
							"At least one of transactionId or transactionDate must be provided.")),
					HttpStatusCode.valueOf(400));
		}

		if (transactionId != null) {
			CurrencyConversionTransaction //
			transaction = currencyConversionTransactionService.findById(Long.valueOf(transactionId));
			if (transaction != null) {
				return new ResponseEntity<List<CurrencyConversionResponce>>(
						List.of(new CurrencyConversionResponce(transaction.getId(), transaction.getConvertedAmount(),
								transaction.getTargetCurrency())),
						HttpStatusCode.valueOf(200));
			} else if (transactionDate == null) {
				return new ResponseEntity<List<CurrencyConversionResponce>>(List.of(new CurrencyConversionResponce(null,
						null, null,
						"Transaction Not Found: No transaction was found with the provided transaction ID. Please verify the transaction ID and try again.")),
						HttpStatusCode.valueOf(404));
			}
		}

		if (transactionDate != null) {
			Page<CurrencyConversionTransaction> //
			res = currencyConversionTransactionService.loadByDate(transactionDate, page, size);
			if (!res.isEmpty()) {
				List<CurrencyConversionResponce> //
				responce = res.getContent().stream().map((e) -> mapToCurrencyConversionResponce(e)).toList();

				return new ResponseEntity<List<CurrencyConversionResponce>>(responce, HttpStatusCode.valueOf(200));
			}
		}
		return new ResponseEntity<List<CurrencyConversionResponce>>(List.of(new CurrencyConversionResponce(null, null,
				null,
				"Information Not Found: No information was found based on the provided criteria. Please check your input and try again.")),
				HttpStatusCode.valueOf(400));
	}

	private CurrencyConversionResponce mapToCurrencyConversionResponce(CurrencyConversionTransaction e) {
		if (e != null) {
			return new CurrencyConversionResponce(e.getId(), e.getConvertedAmount(), e.getTargetCurrency());
		}
		return null;
	}

	public static class CurrencyConversionResponce {

		@JsonInclude(JsonInclude.Include.NON_NULL)
		private Long transactionId;
		@JsonInclude(JsonInclude.Include.NON_NULL)
		private BigDecimal convertedAmount;
		@JsonInclude(JsonInclude.Include.NON_NULL)
		private String currency;
		@JsonInclude(JsonInclude.Include.NON_NULL)
		private String error;

		public CurrencyConversionResponce() {
			super();

		}

		public CurrencyConversionResponce(Long transactionId, BigDecimal convertedAmount, String currency) {
			super();
			this.transactionId = transactionId;
			this.convertedAmount = convertedAmount;
			this.currency = currency;
		}

		public CurrencyConversionResponce(Long transactionId, BigDecimal convertedAmount, String currency,
				String error) {
			super();
			this.transactionId = transactionId;
			this.convertedAmount = convertedAmount;
			this.currency = currency;
			this.error = error;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

		public Long getTransactionId() {
			return transactionId;
		}

		public void setTransactionId(Long transactionId) {
			this.transactionId = transactionId;
		}

		public BigDecimal getConvertedAmount() {
			return convertedAmount;
		}

		public void setConvertedAmount(BigDecimal convertedAmount) {
			this.convertedAmount = convertedAmount;
		}

		public String getCurrency() {
			return currency;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}

	}

}
