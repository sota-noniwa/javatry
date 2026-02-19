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

import java.io.File;
import java.io.IOException;

import org.docksidestage.bizfw.basic.supercar.SupercarClient;
import org.docksidestage.javatry.basic.st7.St7BasicExceptionThrower;
import org.docksidestage.javatry.basic.st7.St7ConstructorChallengeException;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of variable. <br>
 * Operate as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りに実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author noniwa
 */
public class Step07ExceptionTest extends PlainTestCase {

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_exception_basic_catchfinally() {
        St7BasicExceptionThrower thrower = new St7BasicExceptionThrower();
        StringBuilder sea = new StringBuilder();
        try {
            thrower.land();
            sea.append("dockside");
        } catch (IllegalStateException e) {
            sea.append("hangar");
        } finally {
            sea.append("broadway");
        }
        log(sea); // your answer? => "hangerbroadway"(o)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_basic_message() {
        St7BasicExceptionThrower thrower = new St7BasicExceptionThrower();
        String sea = null;
        try {
            thrower.land();
            fail("no way here");
        } catch (IllegalStateException e) {
            sea = e.getMessage();
        }
        log(sea); // your answer? => "null"(x), "oneman at showbase"(o)
        // 間違えた理由：
        // e.getMessageから飛んで、これがThrowable classが保持しているdetailMessageを返すものだと認識
        // St7BasicExceptionThrowerでdetailMessageフィールドを保持していないのでnullになると判断
        // 正解に気づくためのアクション：
        // e.getMessage -> Throwable classでdetailMessageを返している
        // thrower.land -> St7BasicExceptionThrower classのonemanメソッドでIllegalStateExceptionのコンストラクタを読んでいる
        // IllegalStateException -> RuntimeException -> Exception -> Throwableの順で継承しており、
        // 順番に親のコンストラクタを呼び出し、最終的にはThrowableのコンストラクタでdetailMessageに引数がセットされていることがわかる
        // #1on1: ↑を一緒にたどって再確認した (2026/01/09)
    }

    /**
     * What class name and method name and row number cause the exception? (you can execute and watch logs) <br>
     * (例外が発生したクラス名とメソッド名と行番号は？(実行してログを見て良い))
     */
    public void test_exception_basic_stacktrace() {
        St7BasicExceptionThrower thrower = new St7BasicExceptionThrower();
        try {
            thrower.land();
            fail("no way here");
        } catch (IllegalStateException e) {
            log(e);
        }
        // your answer? =>
        // class name: St7BasicExceptionThrower.java
        // method name: oneman
        // row number: 40
        // ログから判断
        // java.lang.IllegalStateException: oneman at showbase
        //	at org.docksidestage.javatry.basic.st7.St7BasicExceptionThrower.oneman(St7BasicExceptionThrower.java:40)
    }

    // ===================================================================================
    //                                                                           Hierarchy
    //                                                                           =========
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_exception_hierarchy_Runtime_instanceof_RuntimeException() {
        Object exp = new IllegalStateException("mystic");
        boolean sea = exp instanceof RuntimeException;
        log(sea); // your answer? => true(o)
        // IllegalStateException は RuntimeException の subclass
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_hierarchy_Runtime_instanceof_Exception() {
        Object exp = new IllegalStateException("mystic");
        boolean sea = exp instanceof Exception;
        log(sea); // your answer? => true(o)
        // IllegalStateException は Exception の subclass
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_hierarchy_Runtime_instanceof_Error() {
        Object exp = new IllegalStateException("mystic");
        boolean sea = exp instanceof Error;
        log(sea); // your answer? => false(o)
        // Error は RuntimeException の subclass ではない
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_hierarchy_Runtime_instanceof_Throwable() {
        Object exp = new IllegalStateException("mystic");
        boolean sea = exp instanceof Throwable;
        log(sea); // your answer? => true(o)
        // IllegalStateException は Throwable の subclass
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_hierarchy_Throwable_instanceof_Exception() {
        Object exp = new Throwable("mystic");
        boolean sea = exp instanceof Exception;
        log(sea); // your answer? => false(o)
        // Throwable は Exception の subclass ではない
        
        // #1on1: パッケージ名の中のドメインの話 (2026/02/06)
    }

    // ===================================================================================
    //                                                                         NullPointer
    //                                                                         ===========
    /**
     * What variable (is null) causes the NullPointerException? And what row number? (you can execute and watch logs) <br>
     * (NullPointerが発生する変数(nullだった変数)と、発生する行番号は？(実行してログを見ても良い))
     */
    public void test_exception_nullpointer_basic() {
        try {
            String sea = "mystic";
            String land = sea.equals("mystic") ? null : "oneman";
            String lowerCase = land.toLowerCase();
            log(lowerCase);
        } catch (NullPointerException e) {
            log(e);
        }
        // your answer? =>
        // variable: land
        // row number: 153
        // java.lang.NullPointerException: null
        //	at org.docksidestage.javatry.basic.Step07ExceptionTest.test_exception_nullpointer_basic(Step07ExceptionTest.java:153)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_nullpointer_headache() {
        try {
            String sea = "mystic";
            String land = !!!sea.equals("mystic") ? null : "oneman";
            String piari = !!!sea.equals("mystic") ? "plaza" : null;
            int sum = land.length() + piari.length();
            log(sum);
        } catch (NullPointerException e) {
            log(e);
        }
        // your answer? =>
        // variable: land
        // row number: 171
        // java.lang.NullPointerException: null
        //	at org.docksidestage.javatry.basic.Step07ExceptionTest.test_exception_nullpointer_headache(Step07ExceptionTest.java:171)
    }

    /**
     * Refactor to immediately understand what variable (is null) causes the NullPointerException by row number in stack trace. <br>
     * (どの変数がNullPointerを引き起こしたのか(nullだったのか)、スタックトレースの行番号だけでわかるようにリファクタリングしましょう)
     */
    public void test_exception_nullpointer_refactorCode() {
        try {
            String sea = "mystic";
            String land = !!!sea.equals("mystic") ? null : "oneman";
            String piari = !!!sea.equals("mystic") ? "plaza" : null;
            int landLength = land.length();
            int piariLength = piari.length();
            int sum = landLength + piariLength;
            log(sum);
        } catch (NullPointerException e) {
            log(e);
        }
        // #1on1: 最新のJavaだと、NullPointerExceptionがメッセージを出力するようになって... (2026/02/06)
        // どの変数がnullだったかを教えてくれる。
        //
        // 別の例:
        // assertTrue(sea && land);
        //  ↓
        // assertTrue(sea);
        // assertTrue(land);
        //
        // 質問: 実際に変数出して行を分けるか？ (NullPointerの例) (2026/02/06)
        // ケースバイケース、危ない変数かどうか？コアな場面のプログラムかどうか？
    }

    // ===================================================================================
    //                                                                   Checked Exception
    //                                                                   =================
    /**
     * Show canonical path of new java.io.File(".") by log(), and if I/O error, show message and stack-trace instead <br>
     * (new java.io.File(".") の canonical path を取得してログに表示、I/Oエラーの時はメッセージとスタックトレースを代わりに表示)
     */
    public void test_exception_checkedException_basic() {
        try {
            File file = new File(".");
            String canonicalPath = file.getCanonicalPath();
            log("Canonical path: \"" + canonicalPath + "\"");
        } catch (IOException e) {
            log("Error message: " + e.getMessage());
            log("Stack trace: " + e);
        }
        // #1on1: チェック例外の話 (2026/02/06)
        // 思想としては、例外ハンドリングを強制させることができる。コンパイルセーフでcatchさせることができる (2026/02/06)
        // ただ、このチェック例外、流行ってない。
        // o 節操のないチェック例外の使われ方が多くて、呼び出し側が面倒だった (こともある)
        // o 時代が進んで、インフラ周りのでエラーの可能性がすごく少なくなった (隕石のこと気にしない)
        // o 止めをさされたのが、Lambda式で究極に相性が悪い
        // 一応、1問だけ体験してもらう。
        // 一方で、機能の思想自体は悪くないものだし、本当にフィットする場面なら便利。
        // (jfluteは、3,4回便利だなと思ったことがある)
        // 他の言語での話。Go言語やKotlinなど。
    }

    // ===================================================================================
    //                                                                               Cause
    //                                                                               =====
    /**
     * What string is sea variable in the catch block?
     * And What is exception class name displayed at the last "Caused By:" of stack trace? <br>
     * (catchブロックの変数 sea, land の中身は？また、スタックトレースの最後の "Caused By:" で表示されている例外クラス名は？)
     */
    public void test_exception_cause_basic() {
        String sea = "mystic";
        String land = "oneman";
        try {
            throwCauseFirstLevel(); // IllegalStateException is thrown
            fail("always exception but none"); // unreachable
        } catch (IllegalStateException e) {
            // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
            // IllegalStateException       // e
            //  |-IllegalArgumentException // e.getCause()
            //     |-NumberFormatException // e.getCause().getCause()
            // _/_/_/_/_/_/_/_/
            Throwable cause = e.getCause();
            sea = cause.getMessage(); // IllegalArgumentExceptionの例外メッセージ
            land = cause.getClass().getSimpleName();
            log("sea: " + sea); // your answer? => "Failed to call the second help method: symbol=1"(x)
            // "Failed to call the third help method: symbol=-1"(o)
            log("land: " + land); // your answer? => "IllegalArgumentException"(o)
            log("e: " + e); // your answer? => "java.lang.IllegalStateException: Failed to call the second help method: symbol=1"(x)
            // cause.getMessage() はIllegalStateExceptionのコンストラクタを使って作成されたもので、
            // cause.getClass().getSimpleName() はIllegalArgumentExceptionなのか…
            // Exceptionの呼ばれ方を勉強する必要がありそう

            // #1on1: ThrowableがThrowableを持って数珠繋ぎにすることができる (2026/02/06)
            // 一つの「落ちた」というイベント付き、複数の例外インスタンスをthrowすることができる。
            // (Javaはthrowできるのは一つの例外だけだけど、数珠繋ぎにすれば実質複数の例外を投げられる)
            // 元の例外を別の例外で「翻訳」して、(レイヤーごとの)情報を付け足している。
            // (例外の翻訳)
            log(e);
        }
    }

    private void throwCauseFirstLevel() {
        int symbol = Integer.MAX_VALUE - 0x7ffffffe; // symbol = 1
        try {
            throwCauseSecondLevel(symbol);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Failed to call the second help method: symbol=" + symbol, e);
        }
    }

    private void throwCauseSecondLevel(int symbol) {
        try {
            --symbol;  // 0
            symbol--; // -1
            if (symbol < 0) {
                throwCauseThirdLevel(symbol);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Failed to call the third help method: symbol=" + symbol, e);
        }
    }

    private void throwCauseThirdLevel(int symbol) {
        if (symbol < 0) {
            Integer.valueOf("piari"); // throw NumberFormatException
        }
    }

    // ===================================================================================
    //                                                                         Translation
    //                                                                         ===========
    /**
     * Execute this test and read exception message and write situation and cause on comment for non-programmer. <br>
     * テストを実行して例外メッセージを読んで、プログラマーでない人にもわかるように状況と原因をコメント上に書きましょう。
     */
    public void test_exception_translation_debugChallenge() {
        try {
            new SupercarClient().buySupercar();
            fail("always exception but none");
        } catch (RuntimeException e) {
            log("*No hint here for training.", e);
            // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
            // What happens? Write situation and cause here. (何が起きた？状況と原因をここに書いてみましょう)
            // - - - - - - - - - -
            // 車の Steering wheel (ハンドル?) を作るときに必要な可愛い顔文字のスクリュードライバーがサポート外なので、例外が発生した
            // 可愛い顔文字 -> \(^_^)/
            // _/_/_/_/_/_/_/_/_/_/
        }
    }

    /**
     * Improve exception handling in supercar's classes to understand the situation
     * by only exception information as far as possible. <br>
     * できるだけ例外情報だけでその状況が理解できるように、Supercarのクラスたちの例外ハンドリングを改善しましょう。
     */
    public void test_exception_translation_improveChallenge() {
        try {
            new SupercarClient().buySupercar(); // you can fix the classes
            fail("always exception but none");
        } catch (RuntimeException e) {
            log("*No hint here for training.", e);
        }
        // cause をパラメーターで渡してあげて、例外を投げることで スタックトレースに "Caused by" と表示され、
        // 例外情報が追いやすくなった (Chained Exception)
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Fix terrible (YABAI in Japanese) exception handling. (you can modify exception class) <br>
     * (やばい例外ハンドリングがあるので修正しましょう (例外クラスを修正してOK))
     */
    public void test_exception_writing_constructorChallenge() {
        try {
            helpSurprisedYabaiCatch();
        } catch (St7ConstructorChallengeException e) {
            log("Thrown by help method", e); // should show also "Caused-by" information
        }
        // Chained Exception を使っていなかったので、最後に投げられた例外以外はスタックトレースに表示されていなかった
        // cause を例外のコンストラクタで渡せるようにしたので、例外情報が追えるようになった
    }

    private void helpSurprisedYabaiCatch() {
        try {
            helpThrowIllegalState();
        } catch (IllegalStateException e) {
            throw new St7ConstructorChallengeException("Failed to do something.", e);
        }
    }

    private void helpThrowIllegalState() {
        if (true) { // simulate something illegal
            String importantValue = "dummy"; // important to debug
            throw new IllegalStateException("something illegal: importantValue=" + importantValue);
        }
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What is the concept difference between Exception and Error? Write it on comment. <br>
     * (ExceptionとErrorのコンセプトの違いはなんでしょうか？コメント上に書きましょう)
     */
    public void test_exception_zone_differenceExceptionError() {
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // Write here. (ここに書いてみましょう)
        // - - - - - - - - - -
        // Error - これが投げられる時はアプリケーションがクラッシュする時を想定している。
        //         Error をハンドルすることは想定していないし、すべきでない。
        // Exception - 予期できる不具合が発生した時に投げることを想定している。
        //             Exception はキャッチされ、適切に処理されるべき。
        // _/_/_/_/_/_/_/_/_/_/
    }
}
