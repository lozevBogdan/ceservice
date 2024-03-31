package com.exchanger.ceservice.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CurrencyConversionTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal sourceAmount;
	private String sourceCurrency;
	private String targetCurrency;
	private BigDecimal convertedAmount;
	@Column(name = "created")
	private LocalDate created;

	public CurrencyConversionTransaction() {
		super();
	}

	public CurrencyConversionTransaction(
			String sourceCurrency,
			BigDecimal sourceAmount, 
			String targetCurrency,
			BigDecimal convertedAmount,
			LocalDate created) {
		super();
		this.sourceAmount = sourceAmount;
		this.sourceCurrency = sourceCurrency;
		this.targetCurrency = targetCurrency;
		this.convertedAmount = convertedAmount;
		this.created = created;
	}

	public LocalDate getCreated() {
		return created;
	}

	public void setCreated(LocalDate created) {
		this.created = created;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getSourceAmount() {
		return sourceAmount;
	}

	public void setSourceAmount(BigDecimal sourceAmount) {
		this.sourceAmount = sourceAmount;
	}

	public String getSourceCurrency() {
		return sourceCurrency;
	}

	public void setSourceCurrency(String sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}

	public String getTargetCurrency() {
		return targetCurrency;
	}

	public void setTargetCurrency(String targetCurrency) {
		this.targetCurrency = targetCurrency;
	}

	public BigDecimal getConvertedAmount() {
		return convertedAmount;
	}

	public void setConvertedAmount(BigDecimal convertedAmount) {
		this.convertedAmount = convertedAmount;
	}

}
