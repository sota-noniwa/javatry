package org.docksidestage.bizfw.basic.objanimal.barking;

import org.docksidestage.bizfw.basic.objanimal.Animal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author noniwa
 */
public class BarkProcess {

    private static final Logger logger = LoggerFactory.getLogger(BarkProcess.class);

    private final Animal animal;

    public BarkProcess(Animal animal) {
        this.animal = animal;
    }

    public BarkedSound bark(String barkWord) {
        breatheIn();
        prepareAbdominalMuscle();
        BarkedSound barkedSound = animal.doBark(barkWord);
        return barkedSound;
    }

    // done noniwa bark専用のロジックなので、BarkProcessに持っていきたいところ by jflute (2025/12/19)
    // とりあえず、持っていくはやってみてください。すると、downHitPoint()をpublicをせざるを得なくなる。
    // それはそれで、downHitPoint()の課題にするので、publicにしてもいいからBarkProcessに持っていく。
    // ただ、持っていくとZombieがコンパイルエラーになると思うので、それはそれで課題にするので一旦はエラー放置でOK。
    // TODO jflute: 持ってきたが、downHitPoint() を public にしてしまいました。
    public void breatheIn() { // actually depends on barking
        logger.debug("...Breathing in for barking"); // dummy implementation
        animal.downHitPoint();
    }

    // done noniwa こちらもとりあえずBarkProcessに持っていきましょう by jflute (2026/05/01)
    public void prepareAbdominalMuscle() { // also actually depends on barking
        logger.debug("...Using my abdominal muscle for barking"); // dummy implementation
        animal.downHitPoint();
    }
}
