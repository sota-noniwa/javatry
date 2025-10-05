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

import static org.docksidestage.bizfw.basic.buyticket.TicketType.*;

import org.docksidestage.bizfw.basic.buyticket.Ticket;
import org.docksidestage.bizfw.basic.buyticket.TicketBooth;
import org.docksidestage.bizfw.basic.buyticket.TicketBooth.TicketShortMoneyException;
import org.docksidestage.bizfw.basic.buyticket.TicketBuyResult;
import org.docksidestage.bizfw.basic.buyticket.TicketType;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of class. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう) <br>
 *
 * If ambiguous requirement exists, you can determine specification that seems appropriate. <br>
 * (要件が曖昧なところがあれば、適切だと思われる仕様を決めても良いです)
 *
 * @author jflute
 * @author noniwa
 */
public class Step05ClassTest extends PlainTestCase {

    // ===================================================================================
    //                                                                          How to Use
    //                                                                          ==========
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_class_howToUse_basic() {
        TicketBooth booth = new TicketBooth();
        booth.buyTicket(ONE_DAY, 7400);
        int sea = booth.getTicketQuantity(ONE_DAY);
        log(sea); // your answer? => 9(o)
        // TicketBoothクラスの実装を見て判断する
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_overpay() {
        TicketBooth booth = new TicketBooth();
        booth.buyTicket(ONE_DAY, 10000);
        Integer sea = booth.getSalesProceeds();
        log(sea); // your answer? => 10000(o)
        // 本来お釣りを返してチケットの代金を売上金額に計上するべきだが、受け取った金額を全てお釣りに計上している
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_nosales() {
        TicketBooth booth = new TicketBooth();
        Integer sea = booth.getSalesProceeds();
        log(sea); // your answer? => null(o)
        // salesProceedsはオブジェクト生成時はnull, そしてnullはlogメソッドでStringとして出力される
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_wrongQuantity() {
        Integer sea = doTest_class_ticket_wrongQuantity();
        log(sea); // your answer? => 9(o)
        // OneDayPassportは買えていないがquantityのデクリメントは処理されている
        // 現実世界の意味としてはチケットを売っていないのにブースにあるチケットが1枚減っているということになるので
        // "パスポートを売る&チケットの数を減らす"の処理は一つのトランザクションとして処理する必要がある
    }

    private Integer doTest_class_ticket_wrongQuantity() {
        TicketBooth booth = new TicketBooth();
        int handedMoney = 7399; // insufficient amount of money
        try {
            booth.buyTicket(ONE_DAY, handedMoney);
            fail("always exception but none");
        } catch (TicketShortMoneyException continued) {
            log("Failed to buy one-day passport: money=" + handedMoney, continued);
        }
        return booth.getTicketQuantity(ONE_DAY);
    }

    // ===================================================================================
    //                                                                           Let's fix
    //                                                                           =========
    /**
     * Fix the problem of ticket quantity reduction when short money. (Don't forget to fix also previous exercise answers) <br>
     * (お金不足でもチケットが減る問題をクラスを修正して解決しましょう (以前のエクササイズのanswerの修正を忘れずに))
     */
    public void test_class_letsFix_ticketQuantityReduction() {
        Integer sea = doTest_class_ticket_wrongQuantity();
        log(sea); // should be max quantity, visual check here
        // fixed: チケット枚数を示すquantityをデクリメントする処理を例外処理の後に移動させた

        // #1on1: 順番違いのバグ (それぞれの行は正しい) (2025/09/26)
        // システムが分かれたり、クラスが分かれたりすると、見つけにくいバグになりえる。
        /* だからこそ、流れをわかりやすくするプログラミングデザインを重視しています。
        @Execute
        public HtmlResponse signup(SignupForm form) {
            validate(form, messages -> moreValidate(form, messages), () -> {
                return asHtml(path_Signup_SignupHtml);
            });
            Member member = insertProvisionalMember(form);
            String token = signupTokenAssist.saveSignupToken(member);
            sendSignupMail(form, token);
            return redirect(MypageAction.class).afterTxCommit(() -> { // for asynchronous DB access
                loginAssist.identityLogin(member.getMemberId(), op -> {}); // #simple_for_example no remember for now
            });
        }
         */
    }

    /**
     * Fix the problem of sales proceeds increased by handed money. (Don't forget to fix also previous exercise answers) <br>
     * (受け取ったお金の分だけ売上が増えていく問題をクラスを修正して解決しましょう (以前のエクササイズのanswerの修正を忘れずに))
     */
    public void test_class_letsFix_salesProceedsIncrease() {
        TicketBooth booth = new TicketBooth();
        booth.buyTicket(ONE_DAY, 10000);
        Integer sea = booth.getSalesProceeds();
        log(sea); // should be same as one-day price, visual check here
        // fixed: salesProceedsの初期値をnullから0に変え、メソッド呼び出ごとにONE_DAY_PRICEをsalesProceedsに足していく
        // salesProceedsとbuyOneDayPassportの引数の型がIntegerである必要がないため、int型に変える
    }

    /**
     * Make method for buying two-day passport (price is 13200). (which can return change as method return value)
     * (TwoDayPassport (金額は13200) も買うメソッドを作りましょう (戻り値でお釣りをちゃんと返すように))
     */
    public void test_class_letsFix_makeMethod_twoday() {
        // uncomment after making the method
        TicketBooth booth = new TicketBooth();
        int money = 14000;
        int change = booth.buyTicket(TWO_DAY, money).getChange();
        Integer sea = booth.getSalesProceeds() + change;
        log(sea); // should be same as money
        // and show two-day passport quantity here
        // buyTwoDayPassportメソッドを作る。実装はほぼbuyOneDayPassportと同じ
        // クラス変数TWO_DAY_PRICEを定義し、quantityを消してoneDayPassportQuantityとtwoDayPassportQuantityでそれぞれのチケット枚数を管理する
        // Memo: コードの変更点を比較しやすいように一旦ここでcommitしておく
    }

    // TODO jflute 次回1on1ここから (2025/09/26)
    /**
     * Recycle duplicate logics between one-day and two-day by e.g. private method in class. (And confirm result of both before and after) <br>
     * (OneDayとTwoDayで冗長なロジックがあったら、クラス内のprivateメソッドなどで再利用しましょう (修正前と修正後の実行結果を確認))
     */
    public void test_class_letsFix_refactor_recycle() {
        TicketBooth booth = new TicketBooth();
        booth.buyTicket(ONE_DAY, 10000);
        log(booth.getTicketQuantity(ONE_DAY), booth.getSalesProceeds()); // should be same as before-fix
        // 1つ前の問題でoneDayPassportQuantityとtwoDayPassportQuantityを作成したので、クラス変数のMAX_QUANTITYがいらないことに気付き、削除した
        // (もしoneDayPassとtwoDayPassのMAX_QUANTITYを一緒にしたいならあっても良いが、そこを揃えたいという要望はないと仮定する)
        // enumのTicketTypeを作って、この値に良ってprivateメソッド内で条件分岐して処理を行う
        // 定義されていないTicketTypeのエラーとしてInvalidTicketTypeExceptionを作った
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Now you cannot get ticket if you buy one-day passport, so return Ticket class and do in-park. <br>
     * (OneDayPassportを買ってもチケットをもらえませんでした。戻り値でTicketクラスを戻すようにしてインしましょう)
     */
    public void test_class_moreFix_return_ticket() {
        // uncomment out after modifying the method
        TicketBooth booth = new TicketBooth();
        Ticket oneDayPassport = booth.buyTicket(ONE_DAY, 10000).getTicket();
        log(oneDayPassport.getPrice()); // should be same as one-day price
        log(oneDayPassport.hasExpired()); // should be false
        oneDayPassport.useForOneDay();
        log(oneDayPassport.hasExpired()); // should be true
        // 戻り値をTicketクラスにしてTicketTypeごとに対応する金額をTicketクラスのコンストラクターの引数として渡す
    }

    /**
     * Now also you cannot get ticket if two-day passport, so return class that has ticket and change. <br>
     * (TwoDayPassportもチケットをもらえませんでした。チケットとお釣りを戻すクラスを作って戻すようにしましょう)
     */
    public void test_class_moreFix_return_whole() {
        // uncomment after modifying the method
        TicketBooth booth = new TicketBooth();
        int handedMoney = 20000;
        TicketBuyResult buyResult = booth.buyTicket(TWO_DAY, handedMoney);
        Ticket twoDayPassport = buyResult.getTicket();
        int change = buyResult.getChange();
        log(twoDayPassport.getPrice() + change); // should be same as money
    }

    /**
     * Now you can use only one in spite of two-day passport, so fix Ticket to be able to handle plural days. <br>
     * (TwoDayPassportなのに一回しか利用できません。複数日数に対応できるようにTicketを修正しましょう)
     */
    public void test_class_moreFix_usePluralDays() {
        // your confirmation code here
        TicketBooth booth = new TicketBooth();
        int handedMoney = 20000;
        TicketBuyResult buyResult = booth.buyTicket(TWO_DAY, handedMoney);
        Ticket twoDayPassport = buyResult.getTicket();
        twoDayPassport.useForOneDay();
        twoDayPassport.hasExpired(); // should be false
        twoDayPassport.useForOneDay();
        twoDayPassport.hasExpired(); // should be false
        assertException(Ticket.TicketAlreadyExpiredException.class, () -> twoDayPassport.useForOneDay()); // should throw an Exception
        // TicketのコンストラクターでTicketTypeを引数として渡すようにした
        // Ticketクラスのフィールドの各初期値はTicketTypeによって決まる仕様にした
        // 後何日チケットを使用することができるかをremainingDaysという変数で管理した
    }

    /**
     * Accurately determine whether type of bought ticket is two-day passport or not by if-statemet. (fix Ticket classes if needed) <br>
     * (買ったチケットの種別がTwoDayPassportなのかどうかをif文で正確に判定してみましょう。(必要ならTicketクラスたちを修正))
     */
    public void test_class_moreFix_whetherTicketType() {
        // uncomment when you implement this exercise
        TicketBooth booth = new TicketBooth();
        Ticket oneDayPassport = booth.buyTicket(ONE_DAY, 10000).getTicket();
        showTicketIfNeeds(oneDayPassport);
        TicketBuyResult buyResult = booth.buyTicket(TWO_DAY, 15000);
        Ticket twoDayPassport = buyResult.getTicket();
        showTicketIfNeeds(twoDayPassport);
    }

    // uncomment when you implement this exercise
    private void showTicketIfNeeds(Ticket ticket) {
        TicketType ticketType = ticket.getType();
        for (TicketType type : TicketType.values()) {
            if (ticketType == type) {
                log("Ticket type: " + type);
            }
        }
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Fix it to be able to buy four-day passport (price is 22400). <br>
     * (FourDayPassport (金額は22400) のチケットも買えるようにしましょう)
     */
    public void test_class_moreFix_wonder_four() {
        // your confirmation code here
        TicketBooth booth = new TicketBooth();
        TicketBuyResult buyResult = booth.buyTicket(FOUR_DAY, 22400);
        assertEquals(FOUR_DAY, buyResult.getTicket().getType());
        // TicketクラスでTicketType, priceを管理する仕様に変更し、constructorをprivateにしてfactoryメソッドを作成した
        // 理由はBoothクラスはクライアント（ゲスト）へのサービス提供を責務として持ち、TicketクラスにはTicketTypeと対応するpriceの整合性や自身のオブジェクト生成を担当してほしいから
    }

    /**
     * Fix it to be able to buy night-only two-day passport (price is 7400), which can be used at only night. <br>
     * (NightOnlyTwoDayPassport (金額は7400) のチケットも買えるようにしましょう。夜しか使えないようにしましょう)
     */
    public void test_class_moreFix_wonder_night() {
        // your confirmation code here
        // This test fails if it's not nighttime right now.
        TicketBooth booth = new TicketBooth();
        TicketBuyResult buyResult = booth.buyTicket(NIGHT_ONLY_TWO_DAY, 7500);
        log(buyResult.getTicket().getType());
        buyResult.getTicket().useForOneDay();
    }

    // ===================================================================================
    //                                                                         Bonus Stage
    //                                                                         ===========
    /**
     * Refactor the code to the best readable code you can think of. <br>
     * (自分の中で思う最高に可読性の高いコードにリファクタリングしてみましょう)
     */
    public void test_class_moreFix_yourRefactoring() {
        // your confirmation code here
        TicketBooth booth = new TicketBooth();
        log("TWO_DAY ticket's quantity: " + booth.getTicketQuantity(TWO_DAY));
        TicketBuyResult buyResult = booth.buyTicket(TWO_DAY, 20000);
        log("A customer bought " + buyResult.getTicket().getType() + " ticket and received " + buyResult.getChange() + " yen in change");
        log("TWO_DAY ticket's quantity: " + booth.getTicketQuantity(TWO_DAY));
        log("Sales proceeds: " + booth.getSalesProceeds());
        Ticket ticket = buyResult.getTicket();
        log("The customer checks the ticket...");
        log("Remaining days: " + ticket.getRemainingDays() + ", price: " + ticket.getPrice() + " yen");
        log("The customer uses ticket");
        ticket.useForOneDay();
        log("The ticket has expired?: " + ticket.hasExpired());
        log("Remaining days: " + ticket.getRemainingDays());
        log("The customer uses ticket");
        ticket.useForOneDay();
        log("The ticket has expired?: " + ticket.hasExpired());
        // TicketBoothクラスでチケットごとの枚数をMapデータ構造で管理する仕様にした
        // constructorでそれぞれの枚数を引数として取得し、boothごとに枚数をコントロールできる仕様も考えたが、YAGNI原則に従って現状は固定値を入れている
        // consumeTicketメソッドを一般化してコンパクトにした
        // getOneDayPassportQuantityメソッドを一般化し、メソッド名をgetTicketQuantityに変更した
        // ticketとpassportが同じ意味として使われている気がしたので、統一してticketと呼ぶことにした
    }

    /**
     * Write intelligent JavaDoc comments seriously on the public classes/constructors/methods of the Ticket class. <br>
     * (Ticketクラスのpublicなクラス/コンストラクター/メソッドに、気の利いたJavaDocコメントを本気で書いてみましょう) <br>
     * <br>
     * Seriously → With the intention that the Ticket class (for example) becomes open source and is used by hundreds of people. <br>
     * (本気で → Ticketクラスが(例えば)オープンソースになって何百人の人から利用される想定のつもりで。)
     */
    public void test_class_moreFix_yourSuperJavaDoc() {
        // your confirmation code here
        // done
    }

    // ===================================================================================
    //                                                                         Devil Stage
    //                                                                         ===========
    /**
     * If your specification is to share inventory (quantity) between OneDay/TwoDay/...,
     * change the specification to separate inventory for each OneDay/TwoDay/.... <br>
     * (もし、OneDay/TwoDay/...で在庫(quantity)を共有する仕様になってたら、
     * OneDay/TwoDay/...ごとに在庫を分ける仕様に変えてみましょう)
     */
    public void test_class_moreFix_zonedQuantity() {
        // your confirmation code here
        // already done
    }
}
