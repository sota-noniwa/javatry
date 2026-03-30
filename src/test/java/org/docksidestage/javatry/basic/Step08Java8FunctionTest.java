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
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.docksidestage.javatry.basic.st8.St8DbFacade;
import org.docksidestage.javatry.basic.st8.St8Member;
import org.docksidestage.javatry.basic.st8.St8Withdrawal;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of Java8 functions. <br>
 * Operate as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りに実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author noniwa
 */
public class Step08Java8FunctionTest extends PlainTestCase {

    // ===================================================================================
    //                                                                              Lambda
    //                                                                              ======
    // -----------------------------------------------------
    //                                              Callback
    //                                              --------
    /**
     * Are all the strings by log() methods in callback processes same? (yes or no) <br>
     * (コールバック処理の中で出力しているログの文字列はすべて同じでしょうか？ (yes or no))
     */
    public void test_java8_lambda_callback_basic() {
        String title = "over";

        // Consumer(functional interface)を継承している具象クラス(St8BasicConsumer)を引数として渡している
        // functional interface (FIとする) = 抽象メソッドを1つだけ持つインターフェース
        // クラスを引数として渡しており、helpCallbackConsumerが引数のクラスのメソッドを呼び出す(callback)
        // St8BasicConsumerでFIのメソッドをoverrideしている
        log("...Executing named class callback(!?)");
        helpCallbackConsumer(new St8BasicConsumer(title));

        // #1on1: A → B
        //        A ← B // 厳密にはA'
        // コールバックとは？
        
        // Anonymous class を引数として渡している
        // new ClassName() はコンストラクタ呼び出しによるインスタンス生成だが、new ClassName(){} は anonymous classを定義して、インスタンスを生成している
        // anonymous class の中でacceptメソッドをoverrideしている
        log("...Executing anonymous class callback");
        helpCallbackConsumer(new Consumer<String>() {
            @Override
            public void accept(String stage) {
                log(stage + ": " + title);
            }
        });

        // ラムダ式
        // FIの抽象メソッドをoverrideできる
        // この場合、acceptメソッドをoverrideしている
        log("...Executing lambda block style callback");
        helpCallbackConsumer(stage -> {
            log(stage + ": " + title);
        });

        // ラムダ式の省略記法
        // ステートメントが1つだけの場合は{}を省略できる
        log("...Executing lambda expression style callback");
        helpCallbackConsumer(stage -> log(stage + ": " + title));

        // your answer? => わかりませんでした => 同じ(o)

        // cannot reassign because it is used at callback process
        //title = "wave";
    }

    /**
     * What is order of strings by log(). (write answer as comma-separated) <br>
     * (ログに出力される文字列の順番は？ (カンマ区切りで書き出しましょう))
     */
    public void test_java8_lambda_callback_order() {
        log("harbor");
        helpCallbackConsumer(stage -> {
            log(stage);
        });
        log("lost river");
        // your answer? => harbor, broadway, dockside, hangar, lost river(o)
        // acceptメソッドをラムダ式でoverrideしているので、helpCallbackConsumer内部でacceptが呼ばれるタイミングでlog(stage);の処理が実行される
    }

    private class St8BasicConsumer implements Consumer<String> {

        private final String title;

        public St8BasicConsumer(String title) {
            this.title = title;
        }

        @Override
        public void accept(String stage) {
            log(stage + ": " + title);
        }
    }

    private void helpCallbackConsumer(Consumer<String> oneArgLambda) {
        log("broadway");
        oneArgLambda.accept("dockside");
        log("hangar");
    }

    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_java8_lambda_callback_valueRoad() {
        String label = "number";
        String sea = helpCallbackFunction(number -> {
            return label + ": " + number;
        });
        log(sea); // your answer? => number: 7(o)
        // function interface である Function の accept メソッドをラムダ式で override している
    }

    // #1on1: Genericの変数名のお話 // (2026/03/19)
    private String helpCallbackFunction(Function<Integer, String> oneArgLambda) {
        return oneArgLambda.apply(7);
    }

    // -----------------------------------------------------
    //                                         Convert Style
    //                                         -------------
    /**
     * Change callback style like this:
     * <pre>
     * o sea: to lambda block style
     * o land: to lambda expression style
     * o piari: to lambda block style
     * </pre>
     * (このようにコールバックスタイルを変えてみましょう:)
     * <pre>
     * o sea: BlockのLambda式に
     * o land: ExpressionのLambda式に
     * o piari: BlockのLambda式に
     * </pre>
     */
    public void test_java8_lambda_convertStyle_basic() {
        // #1on1: 1statementでも中が込み入ってる場合は、blockスタイルで見栄えを帰ることもある話 (2026/03/19)
        // e.g.
        //memberBhv.selectList(cb -> {
        //    cb.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
        //});
        helpCallbackSupplier(() -> {return "broadway";}); // sea

        helpCallbackSupplier(() -> "dockside"); // land

        helpCallbackSupplier(() -> {return "hangar";}); // piari
    }

    private void helpCallbackSupplier(Supplier<String> oneArgLambda) {
        String supplied = oneArgLambda.get();
        log(supplied);
    }

    // ===================================================================================
    //                                                                            Optional
    //                                                                            ========
    /**
     * Are the strings by two log() methods same? (yes or no) <br>
     * (二つのlog()によって出力される文字列は同じでしょうか？ (yes or no))
     */
    public void test_java8_optional_concept() {
        // #1on1: Optionalの良いところは？ (2026/03/19)
        // o 描きやすさ (nullチェックを最後にできる) by のにわさん
        // o nullの例外の回避 by のにわさん
        
        // 根源的なメリット:
        // o ないかもしれないことを意識させてくれる
        // o ないかもしれないことへの対処を強制させてくれる
        //  (nullチェックをコンパイルエラーで教えてくれる)
        //
        // JavaのOptionalが2015年あたりに入った。
        // なんで、こんなに導入が遅れたんだろう？話
        // Lambda式, ifPresent(), map()
        //
        // KotlinとJavaの違い。
        // Javaだと、Optionalじゃない戻り値のとき、絶対に存在するとは言い切れない。
        // (Optionalで戻ってきた時だけメリットがある、Javaは半分)
        //
        // KotlinとJavaの採用の話。
        // 言語の流行りの歴史の話。
        St8Member oldmember = new St8DbFacade().oldselectMember(1);
        if (oldmember != null) {
            log(oldmember.getMemberId(), oldmember.getMemberName());
        }
        Optional<St8Member> optMember = new St8DbFacade().selectMember(1);
        if (optMember.isPresent()) {
            St8Member member = optMember.get();
            log(member.getMemberId(), member.getMemberName());
        }
        // your answer? => 同じ(o)
    }

    /**
     * Are the strings by two log() methods same? (yes or no) <br>
     * (二つのlog()によって出力される文字列は同じでしょうか？ (yes or no))
     */
    public void test_java8_optional_ifPresent() {
        Optional<St8Member> optMember = new St8DbFacade().selectMember(1);
        if (optMember.isPresent()) {
            St8Member member = optMember.get();
            log(member.getMemberId(), member.getMemberName());
        }
        optMember.ifPresent(member -> {
            log(member.getMemberId(), member.getMemberName());
        });
        // your answer? => 同じ(o)
        // 2つ目のlogはConsumerのacceptをoverrideしている。ifPresentはreceiverであるoptMemberがnullでない時にacceptメソッドを呼ぶ。
    }

    /**
     * What string is sea, land, piari, bonvo, dstore, amba variables at the method end? <br>
     * (メソッド終了時の変数 sea, land, piari, bonvo, dstore, amba の中身は？)
     */
    public void test_java8_optional_map_flatMap() {
        St8DbFacade facade = new St8DbFacade();

        // traditional style
        St8Member oldmemberFirst = facade.oldselectMember(1);
        String sea;
        if (oldmemberFirst != null) {
            St8Withdrawal withdrawal = oldmemberFirst.oldgetWithdrawal();
            if (withdrawal != null) {
                sea = withdrawal.oldgetPrimaryReason();
                if (sea == null) {
                    sea = "*no reason1: the PrimaryReason was null";
                }
            } else {
                sea = "*no reason2: the Withdrawal was null";
            }
        } else {
            sea = "*no reason3: the selected Member was null";
        }

        Optional<St8Member> optMemberFirst = facade.selectMember(1);

        // #1on1: ないかもしれないことを、保留してプログラムを先に進める (2026/03/30)
        // 実際にはmap()の中にif文が隠されているみたいな感じ。
        // そして、どこで途切れたかは気にせず「一つのないかもしれない」に集約してる。
        // なので、厳密には等価ではない。どこで途切れたかという情報をロスしている。
        // なのでなので、どこで途切れたか次第で分岐が必要な場合は使いづらい。

        // done jflute 次回1on1, map/flatMap (2026/03/19)
        // https://dbflute.seasar.org/ja/manual/topic/programming/java/java8/mapandflat.html
        // じっくりお話。
        // コードが便利になった一方で、概念理解は求められるようになっている。

        // map style
        String land = optMemberFirst.map(mb -> mb.oldgetWithdrawal())
                .map(wdl -> wdl.oldgetPrimaryReason())
                .orElse("*no reason: someone was not present");

        // flatMap style
        String piari = optMemberFirst.flatMap(mb -> mb.getWithdrawal())
                .flatMap(wdl -> wdl.getPrimaryReason()) // 返り値がOptional<String>なのでflatMapを使う
                .orElse("*no reason: someone was not present");

        /* ベタに書くと
String piari = optMemberFirst.flatMap(mb -> {
    Optional<St8Withdrawal> optWithdrawal = mb.getWithdrawal();
    return optWithdrawal;
}).flatMap(wdl -> {
    Optional<String> optPrimaryReason = wdl.getPrimaryReason();
    return optPrimaryReason;
}) // → Optional<String> optPrimaryReason
        // memberやwithdrawalのないかもしれないも集約されてる
        .orElse("*no reason: someone was not present");
         */
        
        // flatMap and map style
        String bonvo = optMemberFirst.flatMap(mb -> mb.getWithdrawal()) // Optional<St8Withdrawal>をflatにしてSt8Withdrawal
                .map(wdl -> wdl.oldgetPrimaryReason())
                .orElse("*no reason: someone was not present");

        String dstore = facade.selectMember(2)
                .flatMap(mb -> mb.getWithdrawal())
                .map(wdl -> wdl.oldgetPrimaryReason())
                .orElse("*no reason: someone was not present");

        String amba = facade.selectMember(3)
                .flatMap(mb -> mb.getWithdrawal())
                .flatMap(wdl -> wdl.getPrimaryReason())
                .orElse("*no reason: someone was not present");

        int defaultWithdrawalId = -1;
        Integer miraco =
                facade.selectMember(2).flatMap(mb -> mb.getWithdrawal()).map(wdl -> wdl.getWithdrawalId()) // ID here
                        .orElse(defaultWithdrawalId);

        log(sea); // your answer? => music
        log(land); // your answer? => music
        log(piari); // your answer? => music
        log(bonvo); // your answer? => music
        log(dstore); // your answer? => *no reason: someone was not present
        log(amba); // your answer? => *no reason: someone was not present
        log(miraco); // your answer? => 12
    }

    /**
     * What string is sea variables at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_java8_optional_orElseThrow() {
        // TODO jflute 次回1on1, orElseThrow()のジレンマ (2026/03/19)
        Optional<St8Member> optMember = new St8DbFacade().selectMember(2);
        St8Member member = optMember.orElseThrow(() -> new IllegalStateException("over"));
        String sea = "the";
        try {
            String reason = member.getWithdrawal().map(wdl -> wdl.oldgetPrimaryReason()).orElseThrow(() -> {
                return new IllegalStateException("wave"); // Supplierのget()をoverride
            }); // orElseThrow()でIllegalStateException("wave") が投げられる
            sea = reason;
        } catch (IllegalStateException e) {
            sea = e.getMessage();
        }
        log(sea); // your answer? => wave
    }

    // ===================================================================================
    //                                                                          Stream API
    //                                                                          ==========
    /**
     * What string is sea, land variables at the method end? <br>
     * (メソッド終了時の変数 sea, land の中身は？)
     */
    public void test_java8_stream_concept() {
        List<St8Member> memberList = new St8DbFacade().selectMemberListAll();
        List<String> oldfilteredNameList = new ArrayList<>();
        for (St8Member member : memberList) {
            if (member.getWithdrawal().isPresent()) {
                oldfilteredNameList.add(member.getMemberName());
            }
        }
        String sea = oldfilteredNameList.toString();
        log(sea); // your answer? => [broadway, dockside]

        List<String> filteredNameList = memberList.stream() // Streamに変換
                .filter(mb -> mb.getWithdrawal().isPresent()) // memberId=1,2のもののみ
                .map(mb -> mb.getMemberName()) // broadway, dockside
                .collect(Collectors.toList());
        String land = filteredNameList.toString();
        log(land); // your answer? => [broadway, dockside]
    }

    /**
     * What string is sea, variables at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_java8_stream_map_flatMap() {
        // #1on1: Stream APIも便利な分、概念理解が求められるとも言える (2026/03/30)
        // なので、こういうのを採用しない言語もある。
        List<St8Member> memberList = new St8DbFacade().selectMemberListAll();
        int sea = memberList.stream()
                .filter(mb -> mb.getWithdrawal().isPresent())
                .flatMap(mb -> mb.getPurchaseList().stream())
                .filter(pur -> pur.getPurchaseId() > 100)
                .mapToInt(pur -> pur.getPurchasePrice())
                .distinct() // 100, 200, 300
                .sum();
        log(sea); // your answer? => 600
    }

    // *Stream API will return at Step12 again, it's worth the wait!
}
