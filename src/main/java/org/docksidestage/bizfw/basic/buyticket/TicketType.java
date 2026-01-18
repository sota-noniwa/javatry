package org.docksidestage.bizfw.basic.buyticket;

// #1on1: (Javaの) enumとは？ (2025/10/10)
// Java5から導入された、2005年くらい、でもJavaは1995年から、つまり、最初の10年enumなかった。
// Java5より前は、クラスで種別などのenumっぽい概念を表現していた。
// (種別ごとシングルトン)
//class TraditionalTicketType {
//    public static final TraditionalTicketType ONE_DAY = new TraditionalTicketType();
//    public static final TraditionalTicketType TWO_DAY = new TraditionalTicketType();
//
//    private TraditionalTicketType() {
//    }
//}
// 使用感は、enumと全く(ほぼ)同じになる。
// ↓ そして、これが文法になる。ということで、実はクラス。

// done noniwa javadocお願いします by jflute (2025/10/21)

/**
 * 購入可能なチケットの種類を示します。
 * @author noniwa
 */
public enum TicketType {
    ONE_DAY(false, 7400, 1),
    TWO_DAY(false, 13200, 2),
    FOUR_DAY(false, 22400, 4),
    NIGHT_ONLY_TWO_DAY(true, 7400, 2);

    private final boolean isNightTicket;
    private final int price;
    private final int validDays;

    TicketType(boolean isNightTicket, int price, int validDays){
        this.isNightTicket = isNightTicket;
        this.price = price;
        this.validDays = validDays;
    }

    public boolean isRegularTicket() {
        return !isNightTicket;
    }

    // #1on1: 内部はnightTicketにするパターンもある (ここは好み)
    public boolean isNightTicket() {
        return isNightTicket;
    }

    public int getPrice() {
        return price;
    }

    public int getValidDays() {
        return validDays;
    }
}
// 暗黙的にEnumクラスを継承している。(extends Enum)
// 質問: ordinalを使うことあるか？
// 回答: 表示順的な意味合いで使うことはあるかもだけど、実際はDBで管理するとenumのordinalはあまり使わない。

