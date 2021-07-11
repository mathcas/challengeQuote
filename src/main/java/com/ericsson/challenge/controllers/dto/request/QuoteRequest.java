package com.ericsson.challenge.controllers.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuoteRequest {

    private String stockId;

    private Integer value;
}
