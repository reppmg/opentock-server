package com.repp.onetock.Controller;

import com.repp.onetock.dto.ConnectionInfo;
import com.repp.onetock.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 1 on 05.07.2017.
 */
@RestController
@RequestMapping("/session")
public class OnetockController {

    @Autowired
    SessionService sessionService;

    @GetMapping
    public ConnectionInfo getConnectionInfo(){
        return sessionService.putInQueue();
    }
}
