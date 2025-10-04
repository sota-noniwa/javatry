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

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // #1on1: 在庫の共有の話 (2025/09/26)
    // 在庫の共有は現実的ではないと思った by のにわさん
    // 細かい業務の選択肢を想像できる開発者になってほしいので素晴らしい。
    // #1on1: 一緒に読んだ
    // // 既存コードちょい直したい、いつやる？
    // https://jflute.hatenadiary.jp/entry/20250913/whenrefactor
    //
    private int oneDayPassportQuantity = 10;
    private int twoDayPassportQuantity = 10;
    private int fourDayPassportQuantity = 10;
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
     * Buy a ticket, method for park guest.
     * @param ticketType Type of ticket e.g. ONE_DAY_PASSPORT.
     * @param handedMoney The money (amount) handed over from park guest. (NotNull, NotMinus)
     * @throws TicketSoldOutException When ticket in booth is sold out.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     * @throws InvalidTicketTypeException When ticket type is not supported.
     */
    public TicketBuyResult buyTicket(TicketType ticketType, int handedMoney) {
        Ticket ticket = Ticket.issue(ticketType);
        if (handedMoney < ticket.getPrice()) {
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
        consumeTicket(ticket.getType());
        return new TicketBuyResult(ticket,  handedMoney - ticket.getPrice());
    }

    private void consumeTicket(TicketType ticketType) {
        if (ticketType == TicketType.ONE_DAY_PASSPORT) {
            if (oneDayPassportQuantity == 0) {
                throw new TicketSoldOutException("Sold out");
            }
            oneDayPassportQuantity--;
        } else if (ticketType == TicketType.TWO_DAY_PASSPORT) {
            if (twoDayPassportQuantity == 0) {
                throw new TicketSoldOutException("Sold out");
            }
            twoDayPassportQuantity--;
        } else if (ticketType == TicketType.FOUR_DAY_PASSPORT) {
            if (fourDayPassportQuantity == 0) {
                throw new TicketSoldOutException("Sold out");
            }
            fourDayPassportQuantity--;
        } else {
            throw new InvalidTicketTypeException("Ticket type not supported: " + ticketType);
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
