package com.muratprojects;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackExample {
    private static final Logger logger = LoggerFactory.getLogger(LogbackExample.class);

    public static void main(String[] args){
        logger.info("Example log from {}", LogbackExample.class.getSimpleName());
    }
}
