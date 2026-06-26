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
package org.docksidestage.bizfw.basic.objanimal;

import java.util.function.Consumer;

import org.docksidestage.bizfw.basic.objanimal.barking.BarkProcess;
import org.docksidestage.bizfw.basic.objanimal.barking.BarkProcess.DownHitPointコール;
import org.docksidestage.bizfw.basic.objanimal.barking.BarkedSound;
import org.docksidestage.bizfw.basic.objanimal.loud.Loudable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The object for animal(動物).
 * @author jflute
 * @author noniwa
 */
public abstract class Animal implements Loudable {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    // done noniwa これもう unused の警告が出ているので削除でOKです by jflute (2026/05/15)
    //private static final Logger logger = LoggerFactory.getLogger(Animal.class);

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected int hitPoint; // is HP

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Animal() {
        hitPoint = getInitialHitPoint();
    }

    protected int getInitialHitPoint() {
        return 10; // as default
    }

    // ===================================================================================
    //                                                                               Bark
    //                                                                              ======
    public BarkedSound bark() {
        BarkProcess barkProcess = createBarkProcess(); // 実際にどんな具象クラスかは知らない
        return barkProcess.bark(getBarkWord());
    }

    // ↓protectedにして具象クラス側でオーバーライドできるようにする (橋渡しの自由をサブクラスに与える)
    protected BarkProcess createBarkProcess() { // どんな具象クラスかを決めるのはここ
        DownHitPointコール pointコール = new DownHitPointコール() {
            public void callDown() { // ここはpublic...だけど無名だから呼べない
                downHitPoint(); // これはAnimal本体のdownHitPoint()
            }
        };
        // ↑をLambda式で書くこともできる:
        // DownHitPointコール pointコール = () -> downHitPoint()
        //
        // pointコールの中には、↑無名の具象クラスのインスタンスが入っている
        // 無名なので、メソッドがpublicだろうが、classがpublicだろうが...
        // もう別の人はこれを呼び出すことはない。
        // でも、BarkProcessは、この具象クラスのインスタンスを引数で受け取ってるから、
        // 間接的にBarkProcessだけは、downHitPoint()を呼び出すことができている。
        return new BarkProcess(pointコール); // Zombieのときはここに来ない
        
        // というかこう書けちゃう。これは引数の型からinterfaceを推論して無名クラス作ってる。
        // return new BarkProcess(() -> downHitPoint());
        
        // #1on1: 引数を使って解決してるという点では、getBarkWord()と似てる部分ある (2026/06/26)
        // downHitPoint()を実行するというオブジェクトを引数で渡してあげている。
        // (そのオブジェクトが無名で他からnewできないものにしてるから限定的になっている)
        
        // #1on1: クロージャーという言葉のお話、ちょっとその場で一緒にお勉強 (2026/06/26)
        // #1on1: 無名クラス(Lambda式)は、違った角度で可視性を表現していると言える (2026/06/26)
        // (public,privateだと融通が利かないときがある。privateだと呼ばせてあげたい特定の人も呼べない)
    }

    // done noniwa 修行++: getBarkWord(), protectedに戻したい by jflute (2025/12/19)
    // リファクタリングで可視性を広げてしまっている。この場合、単なるStringなので、
    // 公開してもそこまで業務的な支障はないかもだけど、できれば隠したい。
    // hint1: getBarkWord()のpublicに関しては、step6までの文法知識でどうにかなる。
    // downHitPoint()とは違ってももっと単純な話。(解決方法が違う)
    // done jflute: 以下の方法で対応しました by noniwa
    // BarkWord が Animal クラスの getBarkWord() を呼ぶのではなく、
    // BarkWord は Animal クラスで生成し、 BarkProcess に引数として渡すようにした。
    // 結果、 getBarkWord() の可視性を public -> protected にすることができた。
    //
    // #1on1: こっちは、参照するだけ、selectなので、結果さえ渡ればOK (2026/05/15)
    // BarkProcessが求めているのは、getBarkWord()メソッドを呼ぶことではなく、その結果が欲しい。
    // 引数/戻り値デザイン、灯台下暗し。
    protected abstract String getBarkWord();
    
    // ↓これをなんとかして無名クラスにしたい
    //public class DownHitPoint仲介役 implements DownHitPointコール {
    //
    //    public void callDown() {
    //        downHitPoint(); // AnimalのdownHitPointを呼んでいる
    //    }
    //}

    // ===================================================================================
    //                                                                           Hit Point
    //                                                                           =========
    // done noniwa 修行#: downHitPoint(), protectedをキープしたい by jflute (2025/12/19)
    // (他のとぅどぅをやってると、publicにしたくなるときが来るはず)
    // (これは最悪、いったんpublicにして、後でゆっくり考えるでもOK)
    // #1on1: こっちは、downHitPoint()メソッドを呼ぶことが重要。Animalの状態を変えたい。updateしたい。 (2026/05/15)
    // o 仲介役を作って...でも仲介役が誰でもnewできると意味がない
    // o 仲介役を無名クラス(Lambda)で定義することで名無しになるのから誰もnewできない
    // o BarkProcessにだけそのインスタンスを渡せば、BarkProcessだけが呼べる
    protected void downHitPoint() {
        --hitPoint;
        if (hitPoint <= 0) {
            throw new IllegalStateException("I'm very tired, so I want to sleep" + getBarkWord());
        }
    }

    // ===================================================================================
    //                                                                               Loud
    //                                                                              ======
    @Override
    public String soundLoudly() {
        return bark().getBarkWord();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getHitPoint() {
        return hitPoint;
    }
}
