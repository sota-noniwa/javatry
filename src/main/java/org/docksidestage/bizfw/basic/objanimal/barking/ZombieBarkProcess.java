package org.docksidestage.bizfw.basic.objanimal.barking;

import org.docksidestage.bizfw.basic.objanimal.Zombie;

/**
 * @author jflute
 */
public class ZombieBarkProcess extends BarkProcess {

    private final Zombie zombie;

    public ZombieBarkProcess(Zombie zombie, DownHitPointコール downHitPointkコール) {
        super(downHitPointkコール);
        this.zombie = zombie;
    }

    @Override
    public void breatheIn() {
        super.breatheIn();
        zombie.getZombieDiary().countBreatheIn(); // 付け足しできた
    }
}
