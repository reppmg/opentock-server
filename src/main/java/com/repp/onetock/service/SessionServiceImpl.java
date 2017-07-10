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

    private volatile Session currentSession;

    @Autowired
    private OpenTok openTok;

    @Override
    public synchronized boolean unsubscribe(String sessionId) {
        logger.info("clearing queue for client is gone + sid = " + sessionId);
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
        logger.info("new client");
        if (currentSession == null) {
            logger.info("queue is empty");
            try {
                logger.info("creating session");
                currentSession = openTok.createSession();
                logger.info("generating token + sid = " + currentSession.getSessionId());
                token = currentSession.generateToken();
            } catch (OpenTokException e) {
                e.printStackTrace();
                return null;
            }
            return new ConnectionInfo(apiKey, currentSession.getSessionId(), token);
        } else {
            logger.info("a client is in queue + sid = " + currentSession.getSessionId());
            try {
                logger.info("generating token");
                token = currentSession.generateToken();
            } catch (OpenTokException e) {
                e.printStackTrace();
                return null;
            }
            ConnectionInfo connectionInfo = new ConnectionInfo(apiKey, currentSession.getSessionId(), token);
            logger.info("clearing queue");
            currentSession = null;
            return connectionInfo;
        }
    }
}
