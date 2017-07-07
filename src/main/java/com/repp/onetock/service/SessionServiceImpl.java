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

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Value("${onetok.api.key}")
    String apiKey;

    Session currentSession;

    @Autowired
    OpenTok openTok;

    @Override
    public void unsubscribe() {
        logger.debug("clearing queue for client is gone");
        currentSession = null;
    }

    @Override
    public ConnectionInfo putInQueue() {
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
            ConnectionInfo connectionInfo = new ConnectionInfo(apiKey, currentSession.getSessionId(), token);
            return connectionInfo;
        } else {
            logger.debug("a client is in queue");
            try {
                logger.debug("generating token");
                token = currentSession.generateToken();
            } catch (OpenTokException e){
                e.printStackTrace();
                return  null;
            }
            ConnectionInfo connectionInfo = new ConnectionInfo(apiKey, currentSession.getSessionId(), token);
            logger.debug("clearing queue");
            currentSession = null;
            return connectionInfo;
        }
    }
}
