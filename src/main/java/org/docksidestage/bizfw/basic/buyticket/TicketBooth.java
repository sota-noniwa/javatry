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
public class TicketBooth {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final int ONE_DAY_PRICE = 7400; // when 2019/06/15
    private static final int TWO_DAY_PRICE = 13200;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private int oneDayPassportQuantity = 10;
    private int twoDayPassportQuantity = 10;
    private int salesProceeds = 0;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBooth() {
    }

    // ===================================================================================
    //                                                                          Buy Ticket
    //                                                                          ==========
    // you can rewrite comments for your own language by jflute
    // e.g. Japanese
    // /**
    // * 1Dayパスポートを買う、パークゲスト用のメソッド。
    // * @param handedMoney パークゲストから手渡しされたお金(金額) (NotNull, NotMinus)
    // * @throws TicketSoldOutException ブース内のチケットが売り切れだったら
    // * @throws TicketShortMoneyException 買うのに金額が足りなかったら
    // */
    /**
     * Buy one-day passport, method for park guest.
     * @param handedMoney The money (amount) handed over from park guest. (NotNull, NotMinus)
     * @throws TicketSoldOutException When ticket in booth is sold out.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     */
    public TicketBuyResult buyOneDayPassport(int handedMoney) {
        Ticket ticket = processTicketPurchase(handedMoney, TicketType.ONE_DAY_PASSPORT);
        return new TicketBuyResult(ticket, handedMoney - ONE_DAY_PRICE);
    }

    public TicketBuyResult buyTwoDayPassport(int handedMoney) {
        Ticket ticket = processTicketPurchase(handedMoney, TicketType.TWO_DAY_PASSPORT);
        return new TicketBuyResult(ticket, handedMoney - TWO_DAY_PRICE);
    }

    private Ticket processTicketPurchase(int handedMoney, TicketType ticketType) {
        if (ticketType == TicketType.ONE_DAY_PASSPORT) {
            if (oneDayPassportQuantity <= 0) {
                throw new TicketSoldOutException("Sold out");
            }
            if (handedMoney < ONE_DAY_PRICE) {
                throw new TicketShortMoneyException("Short money: " + handedMoney);
            }
            salesProceeds += ONE_DAY_PRICE;
            --oneDayPassportQuantity;
            return new Ticket(TicketType.ONE_DAY_PASSPORT);
        } else if (ticketType == TicketType.TWO_DAY_PASSPORT) {
            if (twoDayPassportQuantity <= 0) {
                throw new TicketSoldOutException("Sold out");
            }
            if (handedMoney < TWO_DAY_PRICE) {
                throw new TicketShortMoneyException("Short money: " + handedMoney);
            }
            salesProceeds += TWO_DAY_PRICE;
            --twoDayPassportQuantity;
            return new Ticket(TicketType.TWO_DAY_PASSPORT);
        } else {
            throw new InvalidTicketTypeException("Invalid ticket type: " + ticketType);
        }
    }

    public static class TicketSoldOutException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketSoldOutException(String msg) {
            super(msg);
        }
    }

    public static class TicketShortMoneyException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketShortMoneyException(String msg) {
            super(msg);
        }
    }

    public static class InvalidTicketTypeException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public InvalidTicketTypeException(String msg) {
            super(msg);
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getOneDayPassportQuantity() {
        return oneDayPassportQuantity;
    }

    public Integer getSalesProceeds() {
        return salesProceeds;
    }
}
