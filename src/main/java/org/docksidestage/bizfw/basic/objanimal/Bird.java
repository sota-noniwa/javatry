package org.docksidestage.bizfw.basic.objanimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author noniwa
 */
public class Bird extends Animal implements Flyable {

    private static final Logger logger = LoggerFactory.getLogger(Cat.class);

    @Override
    public String getBarkWord() {
        return "piyo";
    }

    @Override
    public void fly() {
        logger.debug("flying...");
    }
}
