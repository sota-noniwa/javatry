package org.docksidestage.bizfw.basic.objanimal.barking;

import org.docksidestage.bizfw.basic.objanimal.Animal;

/**
 * @author noniwa
 */
public class BarkProcess {

    private final Animal animal;

    public BarkProcess(Animal animal) {
        this.animal = animal;
    }

    public BarkedSound bark(String barkWord) {
        animal.breatheIn();
        animal.prepareAbdominalMuscle();
        BarkedSound barkedSound = animal.doBark(barkWord);
        return barkedSound;
    }
}
