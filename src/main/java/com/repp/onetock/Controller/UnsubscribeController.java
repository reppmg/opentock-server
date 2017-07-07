package com.repp.onetock.Controller;

import com.repp.onetock.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by 1 on 07.07.2017.
 */
@RestController
@RequestMapping("/unsubscribe")
public class UnsubscribeController {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private SessionService sessionService;

    @GetMapping("/{sessionId}")
    public void unsubscribe(@PathVariable String sessionId){
        logger.debug("New /unsubscribe request with sessionId = " + sessionId);
        sessionService.unsubscribe(sessionId);
    }
}
