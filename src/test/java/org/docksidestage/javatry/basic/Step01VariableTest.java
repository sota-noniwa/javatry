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
        // helpInstanceVariableViaMethodで引数として渡されるinstanceHangarはローカル変数なので変更されない。
        // TODO noniwa [いいね] Yes, 引数はあくまでローカル変数で、ある意味詰め替えられるわけですね by jflute (2025/07/15)
        // 変数とインスタンスの関係性をよく理解されているようで何よりです。ここ間違える人多いので(^^。
        
        // TODO jflute #1on1 にて、インスタンスとは？話 (2025/07/15)
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
        log(sea); // your answer? => 
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
        log(sea); // your answer? => 
    }

    private void helpMethodArgumentMethodcall(StringBuilder sea, int land) {
        ++land;
        sea.append(land);
    }

    // -----------------------------------------------------
    //                                   Variable Assignment
    //                                   -------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_variable_assignment() {
        StringBuilder sea = new StringBuilder("harbor");
        int land = 415;
        helpMethodArgumentVariable(sea, land);
        log(sea); // your answer? => 
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
    public void test_variable_writing() {
        // define variables here
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
     * 
     * _/_/_/_/_/_/_/_/_/_/
     * </pre>
     */
    public void test_variable_yourExercise() {
        // write your code here
    }
}
