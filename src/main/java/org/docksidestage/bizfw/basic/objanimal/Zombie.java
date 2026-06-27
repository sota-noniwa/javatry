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

import org.docksidestage.bizfw.basic.objanimal.barking.BarkProcess;
import org.docksidestage.bizfw.basic.objanimal.barking.BarkProcess.DownHitPointコール;
import org.docksidestage.bizfw.basic.objanimal.barking.ZombieBarkProcess;

/**
 * The object for zombie(ゾンビ).
 * @author jflute
 * @author noniwa
 */
public class Zombie extends Animal {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final ZombieDiary zombieDiary = new ZombieDiary();

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Zombie() {
    }

    @Override
    protected int getInitialHitPoint() {
        return -1; // magic number for infinity hit point
    }

    public static class ZombieDiary {

        private int breatheInCount;

        public void countBreatheIn() {
            ++breatheInCount;
        }

        public int getBreatheInCount() {
            return breatheInCount;
        }
    }

    // ===================================================================================
    //                                                                               Bark
    //                                                                              ======
    // done noniwa 修行++: superのbreatheIn()がBarkProcessに移動しちゃったからコンパイルエラーになった by jflute (2026/05/15)
    // でも、このままだと、Zombieの「息を吸ったら日記に回数を記録する」という機能がロスしたままになっちゃう。
    // なので、どうにかして、Zombieがbark()して息を吸う時にcountする処理を付け足したい。
//    @Override
//    public void breatheIn() {
//        super.breatheIn();
//        zombieDiary.countBreatheIn();
//    }
    // ↑の代わりに↓
    @Override // Zombieインスタンスでbark()したときは、こっちのcreateが動いてZombieBarkProcessになる。
    protected BarkProcess createBarkProcess() {
        // 実際は、このコールバックはAnimalと共通化した方が良いが(あとそもそもLambda式でとか)、
        // Animal側の1on1フォローイングのコメントを残すために、ここでは辻褄合わせコピペ。 (2026/06/27)
        return new ZombieBarkProcess(this, new DownHitPointコール() {
            public void callDown() {
                downHitPoint();
            }
        });
        /* 無名インナークラスでちゃちゃっと済ませるならこんな感じ↓
           ちゃちゃっと済ませてもやってることはZombieBarkProcessであることには変わりはない。
        return new BarkProcess(this) {
            @Override
            public void breatheIn() {
                super.breatheIn();
                zombieDiary.countBreatheIn();
            }
        };
         */
    }

    @Override
    public String getBarkWord() {
        return "uooo"; // what in English?
    }

    // ===================================================================================
    //                                                                           Hit Point
    //                                                                           =========
//    @Override
//    protected void downHitPoint() {
//        // do nothing, infinity hit point
//    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public ZombieDiary getZombieDiary() {
        return zombieDiary;
    }
}
