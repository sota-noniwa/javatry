package org.docksidestage.javatry.basic.st6.dbms;

// #1on1: Database と DBMS の違い。DBMSは「データベース製品」と捉えて良い。
// よくあるSqlというスーパークラスのエピソード。
// スーパークラスの適切な名前を考えるって意外と難しいもの。
// 適切な名前を考えるコツの一つ: まだ見ぬ具象クラスを想像する
// この場合だと、Oracle, DB2, SQLServer

public abstract class Dbms {

    // #1on1: 処理の流れの再利用にフォーカスを当てていってほしい (2026/01/09)
    public String buildPagingQuery(int pageSize, int pageNumber) {
        int offset = calculateOffset(pageSize, pageNumber);
        return generateQueryString(pageSize, offset);
    }

    private int calculateOffset(int pageSize, int pageNumber) {
        return pageSize * (pageNumber - 1);
    }

    protected abstract String generateQueryString(int pageSize, int offset);
    // PostgreSQLのデフォルト実装を入れるならこちら
    // #1on1: もしPostgreSQLの仕様が、DBMSの標準仕様と言えるのであれば、デフォルトにしてもOK
    //protected String generateQueryString(int pageSize, int offset) {
    //    return "offset " + offset + " limit " + pageSize;
    //}
    // #1on1: デフォルト論に通じる話。どこの世界のデフォルトでも、このジレンマがある。(2026/01/09)
    
    // ===================================================================================
    //                                                                             おもいで
    //                                                                             =======
    // done noniwa ↓実際にできるquery文字列が PostgreSQL 想定のものになっている by jflute (2026/01/09)
    // (それがデフォルト仕様であると決め打ちするならあながち間違いではないけど...)
    // #1on1: 場合分けするなら、インターフェースの方が良い？ by のにわさん
    // したら、まずインターフェースで作ってもOKです。(Dbmsをinterfaceに変更する) by jflute
    // #1on1: ライブコーディングでインターフェース方式やりながらabstract方式を導き出してもらった (2026/01/09)
    // abstractだと、ポリモーフィズムだけじゃなく、実装の最適化、流れの再利用がしやすくなる。
}
