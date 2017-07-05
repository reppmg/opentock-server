package com.repp.onetock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Defines the data returned to client for connection
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionInfo {

    private String apiKey;
    private String sessionId;
    private String token;
}
