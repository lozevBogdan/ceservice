package com.exchanger.ceservice.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.exchanger.ceservice.dto.CurrencyLayerConvertResponceDto;

@Service
public class CurrencyLayerService {
	
	@Value("${api.currencylayer.base}")
	private String baseUrl;
	
	@Value("${api.currencylayer.access.key}")
	private String accessKey;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public BigDecimal getRate(String srcCurrency, String trgCurrency) {
		String url;
		if (srcCurrency != null && trgCurrency != null) {
			url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/convert").queryParam("access_key", accessKey)
					.queryParam("from", srcCurrency).queryParam("to", trgCurrency).queryParam("amount", 1).build()
					.toUriString();

			CurrencyLayerConvertResponceDto //
			res = restTemplate.getForObject(url, CurrencyLayerConvertResponceDto.class);

			return res.getResult();
		}
		return null;

	}

}
