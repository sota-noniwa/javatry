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
    private int remainingDays; // remaining days that a park guest can enter with the ticket

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    private Ticket(TicketType type, int remainingDays) {
        this.type = type;
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
        // done jflute 次回1on1, UnitTestでの挙動の話 (2025/10/10)
        // done noniwa 18時ぴったりが弾かれるけど意図しているか？してなければ自然な形に by jflute (2025/10/10)
        // done jflute 18:00:00〜21:59:59まではNightTicketが使える仕様にしました
        if (type.isNightTicket() && !isNightTicketInValidTime()) {
            throw new InvalidTicketTimeException("Night ticket can be used only at night time");
        }
        remainingDays--;
    }

    private boolean isNightTicketInValidTime() {
        // ======================================== Test cases
//        LocalTime now = LocalTime.of(17, 59, 59); // false
//        LocalTime now = LocalTime.of(18, 0, 0); // true
//        LocalTime now = LocalTime.of(21, 59, 59); // true
//        LocalTime now = LocalTime.of(22, 0, 0); // false
        // ===================================================
        LocalTime now = LocalTime.now();
        return (!now.isBefore(NIGHT_START_TIME) && now.isBefore(NIGHT_END_TIME));
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    // #1on1: getは取ることが目的なので、概要を省略して、@returnだけ付けるのGood (2025/10/10)
    // #1on1: 質問: くぼさんどんくらいコメント書く？ 
    // ケースバイケースだけど、他人がメンテする想定なら NxBatchRecorder みたいに書く。
    // DBFluteのAbstractSqlClauseもちょこっとだけ見て、if文の補足コメントなど。
    /**
     * @return The type of ticket e.g. ONE_DAY.
     */
    public TicketType getType() {
        return type;
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
    // done jflute 次回1on1にて、Ticketのコメントもうちょいじっくり (2025/10/10)
    // ONE_DAY, TWO_DAY, and FOUR_DAY.
    // 列挙断定してしまうと追加されたときにちょっと誤解を生みやすい文章になっちゃう。
    // e.g.
    //  Supported types are ONE_DAY, TWO_DAY, and FOUR_DAY, ...
    //  Supported types are ONE_DAY, TWO_DAY, and FOUR_DAY など
    //  Supported types are e.g. ONE_DAY, TWO_DAY, and FOUR_DAY
    //  Supported types are 例えば ONE_DAY, TWO_DAY, and FOUR_DAY
    // というように一部列挙方式にして、概念の説明はしつつ、個数は断定しないやり方。
    // (AbstractSqlClause での e.g. の例)
    // TODO noniwa ↑の通り、何かしらちょっと表現を変えてみてください by jflute (2025/10/21)
    /**
     * Issues a regular ticket for the specific type.
     * Supported types are ONE_DAY, TWO_DAY, and FOUR_DAY.
     * @param type The type of regular ticket to issue.
     * @return A new Ticket instance for the specified type.
     * @throws WrongMethodTypeException When the specified type is not a regular ticket.
     */
    public static Ticket issue(TicketType type) {
        // done noniwa チケット種別が増えた時、できるだけenumの追加だけで済ませたい by jflute (2025/10/10)
        // done jflute TicketType内で固定値を定義してあげたことでファクトリーメソッドがすっきりしました！
        return new Ticket(type, type.getValidDays());
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
