package com.xavelo.cpukiller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/kill/{threads}")
    public String kill(@PathVariable String threads) {
        logger.info("kill received with {} threads", threads);
        try {
          service.kill(Integer.parseInt(threads));
        }
        catch(InterruptedException ie) {
            logger.error("InterruptedException: ", ie);
        }
        return "done";
    }

}

