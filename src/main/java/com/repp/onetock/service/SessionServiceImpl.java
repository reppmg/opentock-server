package com.repp.onetock.service;

import com.opentok.OpenTok;
import com.opentok.Session;
import com.opentok.exception.OpenTokException;
import com.repp.onetock.dto.ConnectionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by 1 on 05.07.2017.
 */

@Service
public class SessionServiceImpl implements SessionService {

    @Value("${onetok.api.key}")
    String apiKey;

    Session currentSession;

    @Autowired
    OpenTok openTok;

    @Override
    public ConnectionInfo putInQueue() {
        String token = null;
        if (currentSession == null) {
            try {
                currentSession = openTok.createSession();
                token = currentSession.generateToken();
            } catch (OpenTokException e) {
                e.printStackTrace();
                return null;
            }
            ConnectionInfo connectionInfo = new ConnectionInfo(apiKey, currentSession.getSessionId(), token);
            return connectionInfo;
        } else {
            try {
                token = currentSession.generateToken();
            } catch (OpenTokException e){
                e.printStackTrace();
                return  null;
            }
            ConnectionInfo connectionInfo = new ConnectionInfo(apiKey, currentSession.getSessionId(), token);
            currentSession = null;
            return connectionInfo;
        }
    }
}
