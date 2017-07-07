package com.repp.onetock.Controller;

import com.repp.onetock.dto.ConnectionInfo;
import com.repp.onetock.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 1 on 05.07.2017.
 */
@RestController
@RequestMapping("/session")
public class SessionController {
    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private SessionService sessionService;

    @GetMapping
    public ConnectionInfo getConnectionInfo(){
        logger.debug("new /session request");
        return sessionService.putInQueue();
    }
}
