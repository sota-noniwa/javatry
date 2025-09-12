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

// #1on1: ちょっとざつだん by noniwaさん
// o 低レイヤーの勉強が好き
// o ネットワーク/OS/データベースinternal
// o 即効性はなさそう？？？
//
// o JavaScript, Kotlin, DDD, SQLは即効性
// o そっちの方がいいかも？？？
//
// ↓↓↓ (with くぼ)
//
// o 低レイヤーはすべての土台になるので、あながち全く別の方向っていうわけじゃない
// o 業務時間外の勉強であれば自由だし...
// o 低レイヤーはできるときにやっておいたほうがいい (年取ってからなかなか大変)
// o 自分で興味持って勉強進めていけるものがあるというのは素晴らしい
// o 興味は変わってくるとは思う
//
// べてらんの人たちは何に興味を？ by noniwaさん
// o OSS？
// o 9割は即効性？ by jflute
// o OSSにフォーカス当てる人もある種の低レイヤー (アプリから見れば) by jflute
// o 年取ると...のお話 by jflute
//
// DBFluteを作ったきっかけは？ by noniwaさん
// o 炎上プロジェクトの連続でどうにかならないか？の気持ちが強かった
// o それでたまたまTorqueというO/Rマッパーを触る機会があって拡張し始めた

// done noniwa [読み物課題] 独学のきっかけ、技術欲、問題解決欲、自己成長欲 by jflute (2025/08/27)
// https://jflute.hatenadiary.jp/entry/20160210/selfgrowth
// done jflute [感想]
// 私は技術欲5、問題解決欲3、自己成長欲2だと思いました。自身の学んだことを実務を通してより深く理解できることが何よりも楽しいです。
// ただ、ビジネス全体を見る力やヒューマンスキルに関しては意識的に強化していく必要があると感じています。
// 技術は問題解決の手段ということを忘れずに、手段と目的を履き違えないようにしていきたいです。
// done noniwa [へんじ] おおぉ、ありがとうございます。自分のことを考えること自体が大切なのできっかけになって頂けたらと。 by jflute (2025/09/11)
// 自分の強みを活かしながらも少し弱いと思う部分への意識も忘れずにアプローチしていけば、
// 「強みは強い、弱みはたいしたことない」、そういうITエンジニアになれると思いますので、頑張ってください！(^^

// done noniwa [読み物課題] まず何より、目の前の道具を使いこなしてください by jflute (2025/08/27)
// https://jflute.hatenadiary.jp/entry/20180223/mastercurrent
// done jflute [感想]
// 「まずは目の前のチャーハン」耳が痛かったです。自身が興味があるが仕事で即効性のない勉強とかはショートケーキに当たるんだろうなと感じました。
// お金をもらっている身として、自身の好きな勉強と会社で成果を出すための勉強のバランスを考えていきたいです。
// done noniwa [へんじ] はっはっは、ありがとうございます。 by jflute (2025/09/11)
// まあ程度の問題ではあるのですが、目の前を置き去りにしないようにの意識を持ってもらえたらと。
// 一方で、少し応用編ですが、ショートケーキもただ楽しむだけではなく、そこから得られるものを抽象化して、
// チャーハンにつなげていけるなら両方ともwin-winになります。そういった意識もあると良いです。
// 「技術にかなり近いヒューマンスキル」がそれの一つだとは思っていて、ぼくのブログでそういったものたくさん書いています。
// ショートケーキの実装経験から、「ショートケーキのことだけ学ぶ」というのはもったいないので、
// ショートケーキの実装経験から、「他でも通じるプログラミングのことを学べる」というわけですね。
// javatryもそれに近いと言えば近いですから。これからもそういったところ意識してフォローしていけるようぼくも頑張ります(^^。

// done noniwa javadocのauthorお願いしますm(_ _)m by jflute (2025/08/15)
/**
 * The test of if-for. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author noniwa
 */
public class Step02IfForTest extends PlainTestCase {

    // ===================================================================================
    //                                                                        if Statement
    //                                                                        ============
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_if_basic() { // example, so begin from the next method
        int sea = 904;
        if (sea >= 904) {
            sea = 2001;
        }
        log(sea); // your answer? => 2001(o)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_if_else_basic() {
        int sea = 904;
        if (sea > 904) {
            sea = 2001;
        } else {
            sea = 7;
        }
        log(sea); // your answer? => 7(o)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_if_elseif_basic() {
        int sea = 904;
        if (sea > 904) {
            sea = 2001;
        } else if (sea >= 904) {
            sea = 7;
        } else if (sea >= 903) {
            sea = 8;
        } else {
            sea = 9;
        }
        log(sea); // your answer? => 7(o)
        // このコードの場合、上の条件から順に条件(ステートメント)がチェックされ、一度trueになると後の条件は無視される。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_if_elseif_nested() {
        boolean land = false;
        int sea = 904;
        if (sea > 904) {
            sea = 2001;
        } else if (land && sea >= 904) {
            sea = 7;
        } else if (sea >= 903 || land) {
            sea = 8;
            if (!land) {
                land = true;
            } else if (sea <= 903) {
                sea++;
            }
        } else if (sea == 8) {
            sea++;
            land = false;
        } else {
            sea = 9;
        }
        if (sea >= 9 || (sea > 7 && sea < 9)) {
            sea--;
        }
        if (land) {
            sea = 10;
        }
        log(sea); // your answer? => 10(o)
        // コントロールフローを意識して読み解く
        // done noniwa [いいね] コントロールフローが複雑ですからね。ぜひ1on1でさらに聞かせてください^^ by jflute (2025/08/14)
        // #1on1: 再び「漠然読みで構造を把握して、フォーカス読みでピンポイントで目的を知るための情報を得る」
    }

    // ===================================================================================
    //                                                                       for Statement
    //                                                                       =============
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_inti_basic() {
        List<String> stageList = prepareStageList();
        String sea = null;
        for (int i = 0; i < stageList.size(); i++) {
            String stage = stageList.get(i);
            if (i == 1) {
                sea = stage;
            }
        }
        log(sea); // your answer? => dockside(o)
        // stageList内のindexが1のエレメントをseaに代入する
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_foreach_basic() {
        List<String> stageList = prepareStageList();
        String sea = null;
        for (String stage : stageList) {
            sea = stage;
        }
        log(sea); // your answer? => magiclamp(o)
        // 順番にリストの要素を代入しているので、最終的には最後の要素がseaが参照するオブジェクトとなる。
        // done jflute 1on1にて、forと拡張for文のお話 (2025/08/14)
        // #1on1: 普通のfor文って言ったときどっち指すか？個人的には拡張for文を指す人が多い印象。
        // 文法用語と現場用語のお話、前回クラスメソッドの話をした、インスタンスメソッドも。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_foreach_continueBreak() {
        List<String> stageList = prepareStageList();
        String sea = null;
        for (String stage : stageList) {
            if (stage.startsWith("br")) {
                continue;
            }
            sea = stage;
            if (stage.contains("ga")) {
                break;
            }
        }
        log(sea); // your answer? => hangar(o)
        // 条件をよく見て最終的な値を求める
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_listforeach_basic() {
        List<String> stageList = prepareStageList();
        StringBuilder sb = new StringBuilder();
        stageList.forEach(stage -> {
            if (sb.length() > 0) {
                return;
            }
            if (stage.contains("i")) {
                sb.append(stage);
            }
        });
        String sea = sb.toString();
        log(sea); // your answer? => dockside(o)
        // done jflute 1on1にて、forEach()メソッドのお話 (2025/08/14)
        // 1995: いんとあいfor文
        // 2005あたり: 拡張for文
        // 2015あたり: forEach()メソッド
        // 拡張for文に比べて、forEach()メソッドの良いところ。
        // (forEach()は機能落ちしているが...)
        // 逆に、スコープを狭めることで安全性 by noniwaさん
        // + 可読性 :: immutableと似た話
        // できないことが、デメリットではなく、メリットになることもある。
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Make list containing "a" from list of prepareStageList() and show it as log by loop. (without Stream API) <br>
     * (prepareStageList()のリストから "a" が含まれているものだけのリストを作成して、それをループで回してログに表示しましょう。(Stream APIなしで))
     */
    public void test_iffor_making() {
        // write if-for here
        List<String> stageList = prepareStageList();
        List<String> stageListA = new ArrayList<>();
        for (String stage : stageList) {
            if (stage.contains("a")) {
                stageListA.add(stage);
            }
        }
        for (String stage : stageListA) {
            log(stage);
        }
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Change foreach statement to List's forEach() (keep result after fix) <br>
     * (foreach文をforEach()メソッドへの置き換えてみましょう (修正前と修正後で実行結果が同じになるように))
     */
    public void test_iffor_refactor_foreach_to_forEach() {
        List<String> stageList = prepareStageList();
        String sea = null;
        //        for (String stage : stageList) {
        //            if (stage.startsWith("br")) {
        //                continue;
        //            }
        //            sea = stage;
        //            if (stage.contains("ga")) {
        //                break;
        //            }
        //        }

        //        stageList.forEach(stage -> {sea = stage;}); // can't access sea within lambda expression
        // done noniwa [いいね] mutableなクラスを自作したのですごいです^^ by jflute (2025/08/14)
        // done noniwa StageFlagはflagだけじゃなく、stage自身も保持しているので、名前でFlagと限定すると紛らわしいので... by jflute (2025/08/14)
        // stageのループの状態を示すクラスということで、StageLoopState どうでしょう？(あくまで一例ということで)
        StageFlag stageFlag = new StageFlag();
        stageList.forEach(stage -> {
            if (stageFlag.getFlag())
                return;
            if (stage.startsWith("br"))
                return;
            stageFlag.setStage(stage);
            if (stage.contains("ga")) {
                stageFlag.setFlag(true);
            }
        });
        sea = stageFlag.getStage();
        log(sea); // should be same as before-fix
        // ラムダ式の中では、ラムダ式の外で定義されたローカル変数を参照することはできるが値を変更することはできない。
        // ラムダ式は非同期処理なので、ここで値の変更を許してしまうと最終的なseaの値が一意に定まらない。(thread safeでない)
        // 同様の理由で、ラムダ式の外でsea変数の値を変更する場合はラムダ式の中で参照することはできなくなる。
        // 参照先のオブジェクトの状態を変更することはできるが、推奨はされていない。
        // https://relearn-java.com/lambda/#%E3%83%A9%E3%83%A0%E3%83%80%E5%BC%8F%E3%81%AE%E5%A4%96%E3%81%AE%E3%83%AD%E3%83%BC%E3%82%AB%E3%83%AB%E5%A4%89%E6%95%B0%E3%81%B8%E3%81%AE%E3%82%A2%E3%82%AF%E3%82%BB%E3%82%B9
        // done jflute オブジェクトの状態を変更することについて、上の記事では「ラムダ式の中で副作用を伴う処理を行うことは 関数型言語の思想から外れるため、このような使い方は望ましくありません。」とありますが、Javaはオブジェクト指向言語ではないの？と疑問に思いましたが、どう思われますか？
        // done noniwa [へんじ] Javaはオブジェクト指向言語と銘打っていますが、実際には色々な思想が入り込んでいる言語です。 by jflute (2025/08/14)
        // 例えば、staticメソッドのようにオブジェクト指向とは少々離れて手続き型的な(と個人的には感じる)機能など。
        // Java8で導入されたラムダ式やforEach()メソッドは、関数型プログラミングの思想に影響されて導入されたものと解釈しています。
        // 極端な言い方すると、「メインはオブジェクト指向言語だけど、わりといいとこ取りしようとしてる言語 (できてるかどうかは別として)」
        // という感じではあると考えます。なので記事のように「関数型言語の思想」という面での解説もあるのだと思います。
        // 1on1にてもうちょい補足しますね(^^。
    }

    private class StageFlag {
        private String stage = null;
        private Boolean flag = false;

        public StageFlag() {
        }

        public String getStage() {
            return stage;
        }
        public void setStage(String stage) {
            this.stage = stage;
        }
        public Boolean getFlag() {
            return flag;
        }
        public void setFlag(Boolean flag) {
            this.flag = flag;
        }
    }

    /**
     * Make your original exercise as question style about if-for statement. <br>
     * (if文for文についてあなたのオリジナルの質問形式のエクササイズを作ってみましょう)
     * <pre>
     * _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
     * your question here (ここにあなたの質問を):
     * 出力結果を予想してください。
     * _/_/_/_/_/_/_/_/_/_/
     * </pre>
     */
    public void test_iffor_yourExercise() {
        // write your code here
        List<String> stageList = prepareStageList();
        int count = 0;
        for (String stage : stageList) {
            if (stage.length() > 7) {
                count++;
            }
        }
        log(count);
        // done noniwa [いいね] ふぅ、合ってた。目で数えました(^^。 by jflute (2025/08/14)
    }

    // ===================================================================================
    //                                                                        Small Helper
    //                                                                        ============
    private List<String> prepareStageList() {
        List<String> stageList = new ArrayList<>();
        stageList.add("broadway");
        stageList.add("dockside");
        stageList.add("hangar");
        stageList.add("magiclamp");
        return stageList;
    }
}
