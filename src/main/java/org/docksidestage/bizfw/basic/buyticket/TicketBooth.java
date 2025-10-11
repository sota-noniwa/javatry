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

import java.util.EnumMap;
import java.util.Map;

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
    // #1on1: regularとnightを分けている理由は？ (2025/10/10)
    // → nightだけ夜だけ使える判定が必要だから？？？
    // → あまりBoothでlogicを持たせなくなった？？？
    //
    // チケット種別ごとに列挙してたのをMapに集約した理由:
    // → oneDay, twoDay, と列挙すると間違いやすいのでMap
    //    → それはGoodだけど、regularとnightを分けた理由は？
    //
    private final Map<TicketType, Integer> ticketQuantity = new EnumMap<>(TicketType.class);
    private int salesProceeds = 0;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBooth() {
        // #1on1: ここでどのチケット種別がnightなのか？を定義しているに等しい (2025/10/10)
        // 各チケットを10枚ずつ用意する
        for (TicketType type: TicketType.values()) {
            ticketQuantity.put(type, 10);
        }
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
    // done noniwa [いいね] e.g. ONE_DAY の例えば列挙がとてもわかりやすい by jflute (2025/10/10)
    /**
     * Buy a ticket, method for park guest.
     * @param type Type of ticket e.g. ONE_DAY.
     * @param handedMoney The money (amount) handed over from park guest. (NotNull, NotMinus)
     * @return An object contains information about bought ticket and change.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     * @throws TicketSoldOutException When ticket in booth is sold out.
     */
    public TicketBuyResult buyTicket(TicketType type, int handedMoney) {
        // done noniwa nightかどうかの判定を、enumだけでできないか？ by jflute (2025/10/10)
        // (現状、BoothのMapの割り振りでnightを判定している)
        // 本来、そのチケット(種別)が、nightかどうか？という情報はどこで持つのが自然だろうか？
        // TODO jflute nightかどうかの情報をTicketTypeに持たせることにしました
        //  そのおかげでファクトリーメソッドが１つだけで済むようになり、呼び出し側がチケット種別ごとに呼び出すファクトリーメソッドを意識しなくて良くなりました
        //  （常にissueメソッドを呼び出せばいい、チケットがnightかどうかは気にしなくて良い）
        Ticket ticket = Ticket.issue(type);
        if (handedMoney < type.getPrice()) {
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
        if (ticketQuantity.get(type) <= 0) {
            throw new TicketSoldOutException("Sold out");
        }
        ticketQuantity.put(type, ticketQuantity.get(type) - 1);
        salesProceeds += type.getPrice();
        return new TicketBuyResult(ticket, handedMoney - type.getPrice());
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getTicketQuantity(TicketType type) {
        return ticketQuantity.get(type);
    }

    public Integer getSalesProceeds() {
        return salesProceeds;
    }

    // ===================================================================================
    //                                                                           Exception
    //                                                                            ========
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
}
