package com.capstone2.factory_system.Service;

import com.capstone2.factory_system.Api.RAGResponse;
import com.capstone2.factory_system.Api.SQLResponse;
import com.capstone2.factory_system.Model.QueryRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class ChatBotService {
    @Value("${python.api.url}")
    private String pythonApiUrl;
    private final RestTemplate restTemplate;

    public RAGResponse askRAG(String question) {
        String url = pythonApiUrl + "/ask-rag";

        QueryRequest request = new QueryRequest(question);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<QueryRequest> entity = new HttpEntity<>(request, headers);

        return restTemplate.postForObject(url, entity, RAGResponse.class);
    }

    public SQLResponse askSQL(String question) {
        String url = pythonApiUrl + "/ask-sql";
        QueryRequest request = new QueryRequest(question);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<QueryRequest> entity = new HttpEntity<>(request, headers);

        return restTemplate.postForObject(url, entity, SQLResponse.class);
    }


}
