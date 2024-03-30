package com.exchanger.ceservice.web;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exchanger.ceservice.service.CurrencyConversionTransactionService;

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

	public static class CurrencyConversionResponce {

		private Long transactionId;
		private BigDecimal convertedAmount;
		private String currency;
		private String error;

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
