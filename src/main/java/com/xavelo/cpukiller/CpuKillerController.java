package com.xavelo.cpukiller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CpuKillerController {

    private static final Logger logger = LoggerFactory.getLogger(CpuKillerController.class);

    @Autowired
    CpuKillerService service;

    @GetMapping("/ping")
    public String ping() {
        logger.info("ping received");
        logger.info("pong returned");
        return "poooooong";
    }

    @GetMapping("/kill/")
    public String kill(@RequestParam int threads) {
        logger.info("kill received with %s threads", threads);
        try {
          service.kill(threads);
        }
        catch(InterruptedException ie) {
            logger.error("InterruptedException: ", ie);
        }
        return "done";
    }

}

