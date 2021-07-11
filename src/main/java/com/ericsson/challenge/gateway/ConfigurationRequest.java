package com.ericsson.challenge.gateway;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfigurationRequest {

    @Value("${server.address}")
    private String host;

    @Value("${server.port}")
    private Integer port;
}
