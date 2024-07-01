package com.xavelo.cpukiller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.LongAdder;


@Service
public class CpuKillerService {

    private static final Logger logger = LoggerFactory.getLogger(CpuKillerService.class);
    

    public void kill(int numThreads) throws InterruptedException {

        logger.info("killing cpu with {} threads", numThreads);
        
        LongAdder counter = new LongAdder();

        List<CalculationThread> runningCalcs = new ArrayList<>();
        List<Thread> runningThreads = new ArrayList<>();

        logger.info("Starting {} threads", numThreads);

        for (int i = 0; i < numThreads; i++) {
            CalculationThread r = new CalculationThread(counter);
            Thread t = new Thread(r);
            runningCalcs.add(r);
            runningThreads.add(t);
            t.start();
        }

        for (int i = 0; i < 15; i++) {
            counter.reset();
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                break;
            }
            logger.debug("[%d] Calculations per second: %d (%.2f per thread)\n", i, counter.longValue(), (double)(counter.longValue())/numThreads);
        }

        for (int i = 0; i < runningCalcs.size(); i++)
        {
            runningCalcs.get(i).stop();
            runningThreads.get(i).join();
        }

        
    }    
    
    public static class CalculationThread implements Runnable
    {
        private final Random rng;
        private final LongAdder calculationsPerformed;
        private boolean stopped;
        private double store;

        public CalculationThread(LongAdder calculationsPerformed)
        {
            this.calculationsPerformed = calculationsPerformed;
            this.stopped = false;
            this.rng = new Random();
            this.store = 1;
        }

        public void stop()
        {
            this.stopped = true;
        }

        @Override
        public void run()
        {
            while (! this.stopped)
            {
                double r = this.rng.nextFloat();
                double v = Math.sin(Math.cos(Math.sin(Math.cos(r))));
                this.store *= v;
                this.calculationsPerformed.add(1);
            }
        }
    }

}
