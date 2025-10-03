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

/**
 * @author jflute
 */
public class Ticket {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private TicketType type;
    private final int displayPrice; // written on ticket, park guest can watch this
    private int remainingDays; // remaining days that a park guest can enter with the ticket

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========

    public Ticket(TicketType type) {
        if (type == TicketType.ONE_DAY_PASSPORT) {
            this.type = type;
            this.displayPrice = 7400;
            remainingDays = 1;
        } else if (type == TicketType.TWO_DAY_PASSPORT) {
            this.type = type;
            this.displayPrice = 13200;
            remainingDays = 2;
        } else {
            throw new IllegalStateException("Ticket type not supported: " + type);
        }
    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                             =======
    public void useForOneDay() {
        if (remainingDays <= 0) {
            throw new IllegalStateException("Ticket has already expired: displayedPrice: " + displayPrice);
        }
        remainingDays--;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public TicketType getType() {
        return type;
    }

    public int getDisplayPrice() {
        return displayPrice;
    }

    public boolean hasExpired() {
        return remainingDays <= 0;
    }

    public int getRemainingDays() {
        return remainingDays;
    }
}
