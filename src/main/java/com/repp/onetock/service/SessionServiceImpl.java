package com.repp.onetock.service;

import com.opentok.OpenTok;
import com.opentok.Session;
import com.opentok.exception.OpenTokException;
import com.repp.onetock.dto.ConnectionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by 1 on 05.07.2017.
 */

@Service
public class SessionServiceImpl implements SessionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Value("${onetok.api.key}")
    String apiKey;

    private Session currentSession;

    @Autowired
    private OpenTok openTok;

    @Override
    public synchronized boolean unsubscribe(String sessionId) {
        logger.debug("clearing queue for client is gone");
        if (currentSession == null) {
            return false;
        } else {
            if (currentSession.getSessionId().equals(sessionId)) {
                currentSession = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized ConnectionInfo putInQueue() {
        String token = null;
        logger.debug("new client");
        if (currentSession == null) {
            logger.debug("queue is empty");
            try {
                logger.debug("creating session");
                currentSession = openTok.createSession();
                logger.debug("generating token");
                token = currentSession.generateToken();
            } catch (OpenTokException e) {
                e.printStackTrace();
                return null;
            }
            return new ConnectionInfo(apiKey, currentSession.getSessionId(), token);
        } else {
            logger.debug("a client is in queue");
            try {
                logger.debug("generating token");
                token = currentSession.generateToken();
            } catch (OpenTokException e) {
                e.printStackTrace();
                return null;
            }
            ConnectionInfo connectionInfo = new ConnectionInfo(apiKey, currentSession.getSessionId(), token);
            logger.debug("clearing queue");
            currentSession = null;
            return connectionInfo;
        }
    }
}
