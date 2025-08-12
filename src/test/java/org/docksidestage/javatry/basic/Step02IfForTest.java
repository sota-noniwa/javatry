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
 * The test of if-for. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author your_name_here
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
        // TODO jflute オブジェクトの状態を変更することについて、上の記事では「ラムダ式の中で副作用を伴う処理を行うことは 関数型言語の思想から外れるため、このような使い方は望ましくありません。」とありますが、Javaはオブジェクト指向言語ではないの？と疑問に思いましたが、どう思われますか？
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
