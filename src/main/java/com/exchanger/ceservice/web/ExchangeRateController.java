package com.exchanger.ceservice.web;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exchanger.ceservice.service.CurrencyConversionTransactionService;
import com.fasterxml.jackson.annotation.JsonInclude;

@RestController
public class ExchangeRateController {

	@Autowired
	private CurrencyConversionTransactionService currencyConversionTransactionService;

	@GetMapping("/exchange-rate")
	public ResponseEntity<ResponceRate> exchangeRate(@RequestParam("sourceCurrency") String sourceCurrency,
			@RequestParam("targetCurrency") String targetCurrency) {

		BigDecimal //
		rate = currencyConversionTransactionService.getRate(sourceCurrency, targetCurrency);
		if (rate != null) {
			return new ResponseEntity<ResponceRate>(new ResponceRate(sourceCurrency, targetCurrency, rate, null),
					HttpStatusCode.valueOf(200));
		}
		return new ResponseEntity<ResponceRate>(new ResponceRate(sourceCurrency, targetCurrency, rate,
				"Not found rate by given currencies! Please ensure that both the source and target currencies are valid and supported for conversion."),
				HttpStatusCode.valueOf(400));
	}

	public static class ResponceRate {
		@JsonInclude(JsonInclude.Include.NON_NULL)
		private String from;
		@JsonInclude(JsonInclude.Include.NON_NULL)
		private String to;
		@JsonInclude(JsonInclude.Include.NON_NULL)
		private BigDecimal rate;
		@JsonInclude(JsonInclude.Include.NON_NULL)
		private String errorMessage;

		public ResponceRate(String from, String to, BigDecimal rate, String errorMessage) {
			super();
			this.from = from;
			this.to = to;
			this.rate = rate;
			this.errorMessage = errorMessage;
		}

		public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
			this.from = from;
		}

		public String getTo() {
			return to;
		}

		public void setTo(String to) {
			this.to = to;
		}

		public BigDecimal getRate() {
			return rate;
		}

		public void setRate(BigDecimal rate) {
			this.rate = rate;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

	}

}
