package com.repp.onetock.service;

import com.repp.onetock.dto.ConnectionInfo;

public interface SessionService {
    ConnectionInfo putInQueue();

    boolean unsubscribe(String session);
}
