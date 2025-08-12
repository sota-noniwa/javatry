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
package org.docksidestage.javatry.basic;

import java.math.BigDecimal;

import org.docksidestage.unit.PlainTestCase;

/**
 * The test of variable. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author sota_noniwa
 */
public class Step01VariableTest extends PlainTestCase {

    // ===================================================================================
    //                                                                      Local Variable
    //                                                                      ==============
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_variable_basic() { // example, so begin from the next method
        String sea = "mystic";
        log(sea); // your answer? => mystic(o)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_initial() {
        String sea = "mystic";
        Integer land = 8;
        String piari = null;
        String dstore = "mai";
        sea = sea + land + piari + ":" + dstore;
        log(sea); // your answer? => NullPointerException(x) => mystic8null:mai(o)
        // nullを連結するとNullPointerExceptionが発生すると予想したが、Javaではnullを文字列に連結すると"null"という文字列が生成されるようだ。
        // 裏でStringBuilderが呼び出されている。
        // このようなデザインになっている理由はデバッグやログ出力がしやすくなるためらしい。
        // https://stackoverflow.com/questions/4260723/concatenating-null-strings-in-java
        // done noniwa おお、良い記事を見つけましたね！ by jflute (2025/07/15)
        // 確かに NullPointerの方がミスを防ぎやすいというのはありますが、つどつどチェックするってのがさすがに面倒かもですね。
        // C#とかは空文字になるんですが、ログという点では空文字よりもnullって出たほうがわかりやすいというのはあります。
        // ただ、昔のインターネット画面ではよく「こんにちは、nullさん」とか表示されることありました(^^。
        // メール文面は今でも null って超時々ですが見かけますね。
        // もし仮にNullPointer仕様だったら発生しないことなんでしょうけど...
        // #1on1: メールのジレンマ: あんまり気合が(比較的)入らない領域なんだけど、慎重さは求められる
        // #1on1: null文字列ポリシーのお話
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_basic() {
        String sea = "mystic";
        String land = "oneman";
        sea = land;
        land = land + "'s dreams";
        log(sea); // your answer? => oneman(o)
        // String class は定数。つまり、初期化の後変更できない。
        // String classのobjectを保持する変数はobjectが格納されているメモリ領域を参照している。
        // そのため、sea変数はland変数が参照しているメモリ領域に格納されている"oneman"を参照する。
        // https://docs.oracle.com/javase/8/docs/api/java/lang/String.html
        // done noniwa [いいね] yes, 変数はインスタンス自体を保持してるわけではなく、あくまで参照してるだけって感じで by jflute (2025/07/15)
        // とても正確に表現できてると思います。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_int() {
        int sea = 94;
        int land = 415;
        sea = land;
        land++;
        log(sea); // your answer? => 415(o)
        // Objectを代入した変数はその参照を保持し、primitive型の変数は値を直接持つ。
        // https://zenn.dev/yo__shi/articles/10a6562668d85e
        // done noniwa [ざつだん] Javaはプリミティブ型があるところがちょっとややこしいところです by jflute (2025/07/15)
        // ただ、超絶スピードを求める場面であれば、できるだけプリミティブ型で書くってのが使えるってのはありあす。
        // #1on1: プリミティブ型の現場での利用バランスのお話
        // https://dbflute.seasar.org/ja/manual/topic/programming/java/beginners.html#primitivewrapper
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_BigDecimal() {
        BigDecimal sea = new BigDecimal(94);
        BigDecimal land = new BigDecimal(415);
        sea = land;
        sea = land.add(new BigDecimal(1));
        sea.add(new BigDecimal(1));
        log(sea); // your answer? => 417(x) => 416(o)
        // BigDecimalはimmutableなクラスであるため、addメソッドをは新しいオブジェクトを返す。
        // seaにaddメソッドの戻り値を代入しないと、seaの値は変わらない。
        // done noniwa [いいね] immutableという言葉が出てきたの素晴らしいです by jflute (2025/07/15)
        // done noniwa [お試し課題] 試しに、IDE上でBigDecimal (変数の型宣言) にカーソルを当てると... by jflute (2025/07/15)
        // ツールチップでjavadocが表示されるので、その一言目の単語を見てみてください(^^。

        // done jflute #1on1 にて、さらに深堀りしていく予定 (2025/07/15)
        // (↑これはくぼ用のtodoということで、そのままにしておいてください)
        // immutableのメリットの話: 人間の都合の話、可読性、状態管理など
        // Javaでのimmutable: (くぼの解釈) バランス指向
        // JavaDocの見方の話: メソッド補完時にcontrol+Jで表示
        // #1on1: 質問 94のインスタンスはいつ破棄されるのか？
        //  => ガベージコレクションでいつか

        // TODO jflute じかい #1on1 にソースコードリーディングも (2025/07/17)
        // #1on1: add()のソースコードリーディング、インスタンス変数の宣言を見たり、
        // staticのadd()の方を見たりなど。
        // 「漠然読みで構造を把握して、フォーカス読みでピンポイントで目的を知るための情報を得る」
    }

    // ===================================================================================
    //                                                                   Instance Variable
    //                                                                   =================
    private String instanceBroadway;
    private int instanceDockside;
    private Integer instanceHangar;
    private String instanceMagiclamp;

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_String() {
        String sea = instanceBroadway;
        log(sea); // your answer? => "null"(o)
        // nullはlogする時に文字列の"null"として出力される。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_int() {
        int sea = instanceDockside;
        log(sea); // your answer? => 0(o)
        // String型の変数はnull->"null"として出力されるので、int型の変数はnull->0ではないかと予想した。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_Integer() {
        Integer sea = instanceHangar;
        log(sea); // your answer? => 0(x) => "null"(o)
        // int型の変数はnull->0として出力されるので、Integer型の変数はnull->0ではないかと予想した。
        // referenceタイプ(String, Integer, etc)はnull->"null"として出力される。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_via_method() {
        instanceBroadway = "bbb";
        instanceMagiclamp = "magician";
        helpInstanceVariableViaMethod(instanceMagiclamp);
        String sea = instanceBroadway + "|" + instanceDockside + "|" + instanceHangar + "|" + instanceMagiclamp;
        log(sea); // your answer? => bigbang|1|null|magician(o)
        // helpInstanceVariableViaMethodで引数として渡されるinstanceMagiclampはローカル変数なので変更されない。
        // done noniwa [いいね] Yes, 引数はあくまでローカル変数で、ある意味詰め替えられるわけですね by jflute (2025/07/15)
        // 変数とインスタンスの関係性をよく理解されているようで何よりです。ここ間違える人多いので(^^。

        // done jflute #1on1 にて、インスタンスとは？話 (2025/07/15)
        // #1on1: 設計図から実際のメモリ上に展開した(物体!?データ!?)もの (一軒家の例え話)
    }

    private void helpInstanceVariableViaMethod(String instanceMagiclamp) {
        instanceBroadway = "bigband";
        ++instanceDockside;
        instanceMagiclamp = "burn";
    }

    // ===================================================================================
    //                                                                     Method Argument
    //                                                                     ===============
    // -----------------------------------------------------
    //                                 Immutable Method-call
    //                                 ---------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_immutable_methodcall() {
        String sea = "harbor";
        int land = 415;
        helpMethodArgumentImmutableMethodcall(sea, land);
        log(sea); // your answer? => harbor(o)
        // Stringはimmutableなので、一度インスタンス化されたら変わらない。
        // sea.concat()では新しいインスタンスが生成され、helpMethodArgumentImmutableMethodcallメゾットのスコープ
        // を抜けるとそのインスタンスへの参照は不可能になる。
        // done noniwa [いいね] yes, immutableしっかり捉えていますね by jflute (2025/07/31)
    }

    private void helpMethodArgumentImmutableMethodcall(String sea, int land) {
        ++land;
        String landStr = String.valueOf(land); // is "416"
        sea.concat(landStr);
    }

    // -----------------------------------------------------
    //                                   Mutable Method-call
    //                                   -------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_mutable_methodcall() {
        StringBuilder sea = new StringBuilder("harbor");
        int land = 415;
        helpMethodArgumentMethodcall(sea, land);
        log(sea); // your answer? => harbor416(o)
        // StringBuilderはmutableなので、インスタンス化された後も変更可能。
        // StringBuilderクラスを見てみると、例えばnew StringBuilder("harbor")のようにStringを引数にとる場合は
        // 引数の文字列の長さ+16(initial capacity)の長さのバッファを確保する。
        // つまりこの場合、22文字分のバッファを確保する。
        // 文字列の長さがこのバッファを超えると、古い文字列の長さの2倍+2文字分のバッファを確保し、
        // 古い文字列をコピーして、新しいバッファに格納することで新しいオブジェクトを作ることなく、concatenationを実現している。
        // 新しくバッファを作る際にcapacityを2倍にしているのだが、その際に掛け算ではなく処理がより早いbitwise left shiftを使っているのが面白い。
        // done noniwa [いいね] すごい！内部の挙動まで調べてるの素晴らしいです。 by jflute (2025/07/31)
        // 自分、bitwise left shift まで意識したことなかったです(^^。1on1でぜひ一緒にソースコード追ってみましょう。
        //String[] value = new String[] { "a", "b", "c", "d" };
        //int result = (value.length << 1);
        //log(result);
        // #1on1: ソースコード追ってみた。ArrayListのコードも追ってみた。
        // 業務で事前にサイズがわかっていれば、initial capacityを指定してnewすることもできる。
        // ただ、事前にわかる場面も少ないし、現代だとインフラが高性能なので、細かいところ気にしないでいい場面が多い。
        // #1on1: 妥協の精度。わかってて妥協するのと、よくわかってなくてなんとなく妥協するのでは、全然違う。 (2025/07/31)
        // AとBの切り捨てのメリット/デメリットの判断のジレンマ、理由のある妥協 by noniwa
        // #1on1: 実際にStringBuilder, ArrayListのこの仕組みが直接役に立つことは少ないかもしれないけど...
        // こういった仕組みをしてってて、パフォーマンスの観点を持っていることが、応用につながる。
        // 動的な(拡張できる)データの特徴をここで学んだと捉えてもらえたらと。
        // 学んだことを抽象化して汎用的な知識にする習慣を。
    }

    private void helpMethodArgumentMethodcall(StringBuilder sea, int land) {
        ++land;
        sea.append(land);
    }

    public void test_StringBuilder_append_performance_by_capacity() {
        long startSmall = System.nanoTime();
        StringBuilder sbWithSmallCapacity = new StringBuilder(6);
        sbWithSmallCapacity.append("Hello world!");
        long endSmall = System.nanoTime();

        long startLarge = System.nanoTime();
        StringBuilder sbWithLargeCapacity = new StringBuilder(30);
        sbWithLargeCapacity.append("Hello world!");
        long endLarge = System.nanoTime();

        log("capacityを超えた場合の処理時間: " + (endSmall - startSmall) + "ns");
        log("capacityを超えない場合の処理時間: " + (endLarge - startLarge) + "ns");
        // done noniwa [いいね] おおぉ、結果違いいますね。ナノ秒で見ると差がわかりやすいですね by jflute (2025/07/31)
    }

    // -----------------------------------------------------
    //                                   Variable Assignment
    //                                   -------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_variable_assignment() {
        StringBuilder sea = new StringBuilder("harbor");
        int land = 415;
        helpMethodArgumentVariable(sea, land);
        log(sea); // your answer? => harbor(o)
        // Javaでは、メゾットの引数は常に値渡し(pass by value)である。
        // この場合、helperメゾットに渡されるのはseaのメモリアドレスのコピーである。
        // つまり、このメゾット内のseaとhelperメゾット内のseaの2つの変数が同じStringBuilderインスタンスを参照している。
        // helperメゾット内での再代入は、コピーしたsea変数の参照先を書き換えているだけなので、元のsea変数の参照先は変わらない
        // helperメゾットの呼び出し時:
        // sea(original) -> "harbor" <- sea(copy or local variable in helper method)
        // helperメゾット内での再代入時:
        // sea(original) -> "harbor", sea(copy) -> "harbor416"(新しく作成されたStringBuilderインスタンス)
        // helperメゾット内でsea.append(land)を実行すればoriginalのsea変数の参照先を変更することができる。(2つのsea変数が同じメモリアドレスを参照しているため)
        // done noniwa [いいね] 詳しい変数とインスタンスの関係性の挙動をしっかり把握されていますね！ by jflute (2025/07/31)
        // done jflute #1on1 にて、値渡しと参照渡しという言葉について (2025/07/31)
        // この記事の場合:
        // 　値渡し: intなどのプリミティブ型の値をコピー
        // 　参照渡し: オブジェクト型のインスタンスへのアドレス(参照)をコピー
        // https://zenn.dev/yutabeee/articles/3512fe4001d8d3
        // ※個人的には↑なイメージで、ただ言葉の定義としてはちょっと世の中ブレがあるのかも？？？
    }

    private void helpMethodArgumentVariable(StringBuilder sea, int land) {
        ++land;
        String seaStr = sea.toString(); // is "harbor"
        sea = new StringBuilder(seaStr).append(land);
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Define variables as followings:
     * <pre>
     * o local variable named sea typed String, initial value is "mystic"
     * o local variable named land typed Integer, initial value is null
     * o instance variable named piari typed int, without initial value
     * o show all variables by log() as comma-separated
     * </pre>
     * (変数を以下のように定義しましょう):
     * <pre>
     * o ローカル変数、名前はsea, 型はString, 初期値は "mystic"
     * o ローカル変数、名前はland, 型はInteger, 初期値は null
     * o インスタンス変数、名前はpiari, 型はint, 初期値なし
     * o すべての変数をlog()でカンマ区切りの文字列で表示
     * </pre>
     */
    int piari;
    public void test_variable_writing() {
        // define variables here
        String sea = "mystic";
        Integer land = null;
        log(sea + ", " + land + ", " + piari);
        // compile error: variable piari might not have been initialized
        // 初期化してない変数はコンパイルエラーになる。
        // done noniwa [ふぉろー] piariはインスタンス変数ということで、メソッドの外に宣言してみてください by jflute (2025/07/31)
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Make your original exercise as question style about variable. <br>
     * (変数についてあなたのオリジナルの質問形式のエクササイズを作ってみましょう)
     * <pre>
     * _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
     * your question here (ここにあなたの質問を):
     * (メソッド終了時の変数 sb の中身は？)
     * _/_/_/_/_/_/_/_/_/_/
     * </pre>
     */
    public void test_variable_yourExercise() {
        // write your code here
        StringBuilder sb = new StringBuilder("Hello");
        myAppend(sb);
        log(sb); // your answer? =>
    }
    // done noniwa [いいね] 変数とインスタンスの本質的なことを問うエクササイズでいいですね^^ by jflute (2025/07/31)

    private void myAppend(StringBuilder sb) {
        sb.append("World");
    }
}
