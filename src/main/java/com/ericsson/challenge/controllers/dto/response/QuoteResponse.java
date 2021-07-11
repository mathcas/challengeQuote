package com.ericsson.challenge.controllers.dto.response;

import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuoteResponse {
    private String id;

    private String stockId;

    private HashMap<String, String> quotes;
}
