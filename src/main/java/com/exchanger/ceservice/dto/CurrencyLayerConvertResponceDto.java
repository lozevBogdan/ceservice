package com.exchanger.ceservice.dto;

import java.math.BigDecimal;

public class CurrencyLayerConvertResponceDto extends CurrencyLayerBaseResponceDto {

	private Query query;
	private Info info;
	private BigDecimal result;

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public BigDecimal getResult() {
		return result;
	}

	public void setResult(BigDecimal result) {
		this.result = result;
	}

	public static class Query {
		private String from;
		private String to;
		private BigDecimal amount;

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

		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}
	}

	public static class Info {
		private long timestamp;
		private BigDecimal quote;

		public long getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}

		public BigDecimal getQuote() {
			return quote;
		}

		public void setQuote(BigDecimal quote) {
			this.quote = quote;
		}

	}

}
