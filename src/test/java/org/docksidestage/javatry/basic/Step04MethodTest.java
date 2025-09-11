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

import java.util.ArrayList;
import java.util.List;

import org.docksidestage.unit.PlainTestCase;

/**
 * The test of method. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author noniwa
 */
public class Step04MethodTest extends PlainTestCase {

    // ===================================================================================
    //                                                                         Method Call
    //                                                                         ===========
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_method_call_basic() {
        String sea = supplySomething();
        log(sea); // your answer? => over(o)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_method_call_many() {
        String sea = functionSomething("mystic"); // sea変数に影響するのはここだけ
        consumeSomething(supplySomething());
        runnableSomething();
        log(sea); // your answer? => mysmys(o)
    }

    private String functionSomething(String name) {
        String replaced = name.replace("tic", "mys");
        log("in function: {}", replaced);
        return replaced;
    }

    private String supplySomething() {
        String sea = "over";
        log("in supply: {}", sea);
        return sea;
    }

    private void consumeSomething(String sea) {
        log("in consume: {}", sea.replace("over", "mystic"));
    }

    private void runnableSomething() {
        String sea = "outofshadow";
        log("in runnable: {}", sea);
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_method_object() {
        St4MutableStage mutable = new St4MutableStage();
        int sea = 904;
        boolean land = false;
        helloMutable(sea - 4, land, mutable); // sea = 904, land = false, mutable.getStageName() = mystic
        if (!land) {
            sea = sea + mutable.getStageName().length();
        }
        log(sea); // your answer? => 910(o)
        // 引数は値渡しなのでオリジナルの変数の値を変えないが、setterによる変更はメソッド外でも反映される
    }

    private int helloMutable(int sea, Boolean land, St4MutableStage piari) {
        sea++;
        land = true;
        piari.setStageName("mystic"); // setterによる変更
        return sea;
    }

    private static class St4MutableStage {

        private String stageName;

        public String getStageName() {
            return stageName;
        }

        public void setStageName(String stageName) {
            this.stageName = stageName;
        }
    }

    // ===================================================================================
    //                                                                   Instance Variable
    //                                                                   =================
    private int inParkCount;
    private boolean hasAnnualPassport;

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_method_instanceVariable() {
        hasAnnualPassport = true;
        int sea = inParkCount; // 影響なし
        offAnnualPassport(hasAnnualPassport); // 影響なし
        for (int i = 0; i < 100; i++) {
            goToPark();
        }
        ++sea; // 影響なし
        sea = inParkCount;
        log(sea); // your answer? => 100(o)
    }

    private void offAnnualPassport(boolean hasAnnualPassport) {
        hasAnnualPassport = false;
    }

    private void goToPark() {
        if (hasAnnualPassport) {
            ++inParkCount;
        }
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    // write instance variables here
    /**
     * Make private methods as followings, and comment out caller program in test method:
     * <pre>
     * o replaceAwithB(): has one argument as String, returns argument replaced "A" with "B" as String 
     * o replaceCwithB(): has one argument as String, returns argument replaced "C" with "B" as String 
     * o quote(): has two arguments as String, returns first argument quoted by second argument (quotation) 
     * o isAvailableLogging(): no argument, returns private instance variable "availableLogging" initialized as true (also make it separately)  
     * o showSea(): has one argument as String argument, no return, show argument by log()
     * </pre>
     * (privateメソッドを以下のように定義して、テストメソッド内の呼び出しプログラムをコメントアウトしましょう):
     * <pre>
     * o replaceAwithB(): 一つのString引数、引数の "A" を "B" に置き換えたStringを戻す 
     * o replaceCwithB(): 一つのString引数、引数の "C" を "B" に置き換えたStringを戻す 
     * o quote(): 二つのString引数、第一引数を第二引数(引用符)で囲ったものを戻す 
     * o isAvailableLogging(): 引数なし、privateのインスタンス変数 "availableLogging" (初期値:true) を戻す (それも別途作る)  
     * o showSea(): 一つのString引数、戻り値なし、引数をlog()で表示する
     * </pre>
     */
    public void test_method_making() {
        // use after making these methods
        String replaced = replaceCwithB(replaceAwithB("ABC"));
        String sea = quote(replaced, "'");
        if (isAvailableLogging()) {
            showSea(sea);
        }
    }

    // TODO jflute 1on1にて、オブジェクトのBooleanのお話 (2025/09/11)
    private Boolean availableLogging = true;

    // replaceAwithB, replaceCwithBメソッドを一般化するならreplaceXwithY(String s, String x, String y)を作るのもあり
    // TODO tabata その思いつき自体が素晴らしいです。実際やってみます？（＾＾ by jflute (2025/09/11)
    private String replaceAwithB(String s) {
        StringBuilder replaced = new StringBuilder();
        for (char c: s.toCharArray()) {
            if (c == 'A') {
                replaced.append('B');
            } else {
                replaced.append(c);
            }
        }
        return replaced.toString();
    }

    private String replaceCwithB(String s) {
        StringBuilder replaced = new StringBuilder();
        for (char c: s.toCharArray()) {
            if (c == 'C') {
                replaced.append('B');
            } else {
                replaced.append(c);
            }
        }
        return replaced.toString();
    }

    // ２回同じループがあるので、ヘルパーメソッドとして抜き出すのもあり
    // TODO jflute 確かに。ということで1on1でふぉろー予定 (2025/09/11)
    private String quote(String s, String quotation) {
        StringBuilder quoted = new StringBuilder();
        for (char c: quotation.toCharArray()) {
            quoted.append(c);
        }
        for (char c: s.toCharArray()) {
            quoted.append(c);
        }
        for (char c: quotation.toCharArray()) {
            quoted.append(c);
        }
        return quoted.toString();
    }

    private Boolean isAvailableLogging() {
        return availableLogging;
    }

    private void showSea(String s) {
        log(s);
    }
}
