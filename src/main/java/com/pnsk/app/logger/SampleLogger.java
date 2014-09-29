package com.pnsk.app.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by okuda_junko on 14/09/16.
 */
public class SampleLogger {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public void error(String message) {
        log.error(message);
    }

    public void info(String message) {
        log.info(message);
    }

    public void debug(String message) {
        log.debug(message);
    }
}
