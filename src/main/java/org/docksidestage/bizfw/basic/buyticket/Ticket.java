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

/**
 * @author jflute
 */
public class Ticket {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final TicketType type;
    private final int price; // written on ticket, park guest can watch this
    private int remainingDays; // remaining days that a park guest can enter with the ticket

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    private Ticket(TicketType type, int price, int remainingDays) {
        this.type = type;
        this.price = price;
        this.remainingDays = remainingDays;
    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                             =======
    public void useForOneDay() {
        if (remainingDays <= 0) {
            throw new IllegalStateException("Ticket has already expired: displayedPrice: " + price);
        }
        remainingDays--;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public TicketType getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public int getRemainingDays() {
        return remainingDays;
    }

    public boolean hasExpired() {
        return remainingDays <= 0;
    }

    // ===================================================================================
    //                                                                      Factory method
    //                                                                            ========
    public static Ticket issue(TicketType type) {
        int price;
        int remainingDays;
        if (type == ONE_DAY_PASSPORT) {
            price = 7400;
            remainingDays = 1;
        } else if (type == TWO_DAY_PASSPORT) {
            price = 13200;
            remainingDays = 2;
        } else if (type == FOUR_DAY_PASSPORT) {
            price = 22400;
            remainingDays = 4;
        } else {
            throw new IllegalStateException("Ticket type not supported: " + type);
        }
        return new Ticket(type, price, remainingDays);
    }
}
