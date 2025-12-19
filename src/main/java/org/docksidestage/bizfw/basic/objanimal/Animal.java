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
    private static final Logger logger = LoggerFactory.getLogger(Animal.class);

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
        return new BarkProcess(this).bark();
    }

    // TODO noniwa 修行++: bark専用のロジックなので、BarkProcessに持っていきたいところ by jflute (2025/12/19)
    public void breatheIn() { // actually depends on barking
        logger.debug("...Breathing in for barking"); // dummy implementation
        downHitPoint();
    }

    public void prepareAbdominalMuscle() { // also actually depends on barking
        logger.debug("...Using my abdominal muscle for barking"); // dummy implementation
        downHitPoint();
    }

    // TODO noniwa 修行++: getBarkWord(), protectedに戻したい by jflute (2025/12/19)
    // リファクタリングで可視性を広げてしまっている。この場合、単なるStringなので、
    // 公開してもそこまで業務的な支障はないかもだけど、できれば隠したい。
    public abstract String getBarkWord();

    public BarkedSound doBark(String barkWord) {
        downHitPoint();
        return new BarkedSound(barkWord);
    }

    // ===================================================================================
    //                                                                           Hit Point
    //                                                                           =========
    // TODO noniwa 修行#: downHitPoint(), protectedをキープしたい by jflute (2025/12/19)
    // (他のとぅどぅをやってると、publicにしたくなるときが来るはず)
    // (これは最悪、いったんpublicにして、後でゆっくり考えるでもOK)
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
