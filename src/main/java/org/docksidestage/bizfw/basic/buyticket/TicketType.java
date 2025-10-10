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

public enum TicketType {
    ONE_DAY,
    TWO_DAY,
    FOUR_DAY,
    NIGHT_ONLY_TWO_DAY
}
// 暗黙的にEnumクラスを継承している。(extends Enum)
// 質問: ordinalを使うことあるか？
// 回答: 表示順的な意味合いで使うことはあるかもだけど、実際はDBで管理するとenumのordinalはあまり使わない。

