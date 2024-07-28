package org.example.controller;

import org.example.httpclient.ApiClient;
import org.example.httpclient.QueryParam;
import org.example.httpclient.ResponseMapper;
import org.example.httpclient.UriTemplate;

import java.time.YearMonth;

@ApiClient(value = "myassetApiClient", baseUrl = "http://myasset-api-alpha.fams.svc.xed1.io.navercorp.com")
public interface MyassetApiClient {
    @UriTemplate("/asset/card/{nidNo}/cardSummary?month={month}")
    String getCardSummary(String nidNo, YearMonth month);

    @UriTemplate("/asset/card/{nidNo}/cardSummary?month={month}")
    String getCardSummary(String nidNo, YearMonth month, QueryParam queryParam, ResponseMapper<String> mapper);
}
