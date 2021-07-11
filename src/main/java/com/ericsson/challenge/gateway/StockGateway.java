package com.ericsson.challenge.gateway;

import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class StockGateway {

    private ConfigurationRequest configurationRequest;

    public StockGateway(ConfigurationRequest configurationRequest) {
        this.configurationRequest = configurationRequest;
    }

    public String validateStock(String stockCode) {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:8080/stock/";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl + stockCode, String.class);
        return response.getBody();
    }

    public ArrayList getAllStocks() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:8080/stock";
        ResponseEntity<ArrayList> response
                = restTemplate.getForEntity(fooResourceUrl, ArrayList.class);
        return response.getBody();
    }

    public String notifyCreation() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:8080/notification";
        System.out.println(configurationRequest);
        ResponseEntity<String> response
                = restTemplate.postForEntity(fooResourceUrl, configurationRequest, String.class);
        return response.getBody();
    }
}
