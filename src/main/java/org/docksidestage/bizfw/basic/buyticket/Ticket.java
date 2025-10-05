/*
 * Copyright 2019-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.bizfw.basic.buyticket;

import static org.docksidestage.bizfw.basic.buyticket.TicketType.*;

import java.lang.invoke.WrongMethodTypeException;
import java.time.LocalTime;

/**
 * @author jflute
 */
public class Ticket {

    // ===================================================================================
    //                                                                          Definition
    //                                                                           =========
    // a night ticket can be used from NIGHT_START_TIME to NIGHT_END_TIME
    private static final LocalTime NIGHT_START_TIME = LocalTime.of(18, 0);
    private static final LocalTime NIGHT_END_TIME = LocalTime.of(22, 0);

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final TicketType type;
    private final int price; // written on ticket, park guest can watch this
    private final boolean isNightTicket;
    private int remainingDays; // remaining days that a park guest can enter with the ticket

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    private Ticket(TicketType type, int price, boolean isNightTicket, int remainingDays) {
        this.type = type;
        this.price = price;
        this.isNightTicket = isNightTicket;
        this.remainingDays = remainingDays;
    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                             =======
    /**
     * Use the ticket for one day.
     * @throws TicketAlreadyExpiredException When the ticket has already expired (no remaining days).
     * @throws InvalidTicketTimeException When a night ticket is used outside the allowed nighttime.
     */
    public void useForOneDay() {
        if (remainingDays <= 0) {
            throw new TicketAlreadyExpiredException("Ticket has already expired");
        }
        LocalTime now = LocalTime.now();
        if (isNightTicket && !(now.isAfter(NIGHT_START_TIME) && now.isBefore(NIGHT_END_TIME))) {
            throw new InvalidTicketTimeException("Night ticket can be used only at night time");
        }
        remainingDays--;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * @return The type of ticket e.g. ONE_DAY.
     */
    public TicketType getType() {
        return type;
    }

    /**
     * @return The price of the ticket.
     */
    public int getPrice() {
        return price;
    }

    /**
     * @return The remaining days of entry on the ticket.
     */
    public int getRemainingDays() {
        return remainingDays;
    }

    /**
     * @return True if the ticket is no longer valid, otherwise false.
     */
    public boolean hasExpired() {
        return remainingDays <= 0;
    }

    // ===================================================================================
    //                                                                      Factory method
    //                                                                            ========
    /**
     * Issues a regular ticket for the specific type.
     * Supported types are ONE_DAY, TWO_DAY, and FOUR_DAY.
     * @param type The type of regular ticket to issue.
     * @return A new Ticket instance for the specified type.
     * @throws WrongMethodTypeException When the specified type is not a regular ticket.
     */
    public static Ticket issueRegular(TicketType type) {
        int price;
        int remainingDays;
        if (type == ONE_DAY) {
            price = 7400;
            remainingDays = 1;
        } else if (type == TWO_DAY) {
            price = 13200;
            remainingDays = 2;
        } else if (type == FOUR_DAY) {
            price = 22400;
            remainingDays = 4;
        } else {
            throw new WrongMethodTypeException("Call issueNight method for a night ticket");
        }
        return new Ticket(type, price, false, remainingDays);
    }

    /**
     * Issues a night ticket for the specific type.
     * Supported types are NIGHT_ONLY_TWO_DAY.
     * @param type The type of night ticket to issue.
     * @return A new Ticket instance for the specified type.
     * @throws WrongMethodTypeException When the specified type is not a night ticket.
     */
    public static Ticket issueNight(TicketType type) {
        if (type == NIGHT_ONLY_TWO_DAY) {
            return new Ticket(type, 7400, true, 2);
        } else {
            throw new WrongMethodTypeException("Call issueRegular method for a regular ticket");
        }
    }

    // ===================================================================================
    //                                                                           Exception
    //                                                                            ========
    public static class InvalidTicketTimeException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public InvalidTicketTimeException(String msg) {
            super(msg);
        }
    }

    public static class TicketAlreadyExpiredException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketAlreadyExpiredException(String msg) {
            super(msg);
        }
    }
}
