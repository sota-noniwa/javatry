package org.docksidestage.bizfw.basic.objanimal.barking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author noniwa
 */
public class BarkProcess {

    private static final Logger logger = LoggerFactory.getLogger(BarkProcess.class);

    // #1on1: 論理的循環参照の話 (2026/06/12)
    // Animal逆参照があると、汎用性としては低くなる。
    // ただ、それだけと言えばそれだけなので、すごく悪いというわけでもない。
    // 追記: コールinterfaceを入れたことで、Animalへの依存がなくなった。
    private final DownHitPointコール downHitPointコール;

    public BarkProcess(DownHitPointコール downHitPointkコール) {
        this.downHitPointコール = downHitPointkコール;
    }
    
    public interface DownHitPointコール {
        
        void callDown();
    }

    public BarkedSound bark(String barkWord) {
        breatheIn();
        prepareAbdominalMuscle();
        BarkedSound barkedSound = doBark(barkWord);
        return barkedSound;
    }

    // done noniwa bark専用のロジックなので、BarkProcessに持っていきたいところ by jflute (2025/12/19)
    // とりあえず、持っていくはやってみてください。すると、downHitPoint()をpublicをせざるを得なくなる。
    // それはそれで、downHitPoint()の課題にするので、publicにしてもいいからBarkProcessに持っていく。
    // ただ、持っていくとZombieがコンパイルエラーになると思うので、それはそれで課題にするので一旦はエラー放置でOK。
    // done jflute: 持ってきたが、downHitPoint() を public にしてしまいました。
    // 想定通りでOKです by jflute (2026/05/15)
    // done noniwa breatheIn()とprepareAbdominalMuscle()はpublicじゃなくて良い by jflute (2026/05/15)
    protected void breatheIn() { // actually depends on barking
        logger.debug("...Breathing in for barking"); // dummy implementation
        downHitPointコール.callDown();

        // #1on1: もしベタにZombie問題を解決するとしたら... (2026/06/12)
        //if (animal instanceof Zombie) { // ぞんびだったら
        //    ZombieDiary zombieDiary = ((Zombie)animal).getZombieDiary();
        //    zombieDiary.countBreatheIn();
        //}
        // #1on1: Animalを付け足すたびに、ここに分岐が増える by のにわさん (2026/06/12)
        // (BarkProcessは)Animalという抽象クラスを持っているけれども、具象の存在に依存している。
        //
        // Animalという世界の中ではBarkProcessは汎用的にはなっている。
        // つまり、BarkProcessはAnimalに対してポリモーフィズムしていると言える。
        // Animalの実体が、DogなのかCatなのか気にせず呼び出して抽象化の恩恵を受けている。
        // それってつまり、BarkProcessはAnimalの具象クラスに依存しないで済んでいる。
        //
        // ここで忘れてはいけないのは。BarkProcessも、(ミクロなレベルで)Animalのユーザーである。
        // ユーザー: Animalクラスのメソッドを呼び出す人。
        // 
        // でも、Zombieという型(クラス)に依存してしまう。具象に依存している。
        // これって、OSクラスでMacとかWindowsを意識してベタベタにif文書いていたのと同じ。
        //
        //
        // 元々のAnimalとZombieの関係でも、Animalにあった(と想定される)if文が、
        // メソッドオーバーライドを使ってZombie側に移動したと解釈しても良い。
        //  if (this instanceof Zombie) {
        //      // Zombie固有の処理
        //      ((Zombie) this).getZombieDiary().countBreatheIn();
        //  }
        //
        //
        // じゃあ、ここでの(仮想の)Zombieのif文も「オーバーライドで表現して」、
        // BarkProcessのAnimalへの抽象依存を純粋なものにしたいところ。
        //
        //
        //
        // +------------------+ <-------------------+
        // |      Animal      | ------+             |
        // +------------------+       |     +------------------+
        //          ^                 +---> |   BarkProcess    |
        //          |                       |                  |
        //          |               +------ |    breatheIn()   |
        //          |     +---------+       +------------------+
        //          |     | (↑これどうにかしたい)      ^
        //  -------/|\---/|\-----------------------/|\----- ↓Zombieワールド -------
        //          |     v                         |      
        // +------------------+                     |
        // |      Zombie      |             +------------------------+
        // +------------------+             |  ZombieBarkProcess     | by のにわさん
        //               |                  | override breatheIn() { |
        //               +----------------> |   super.breatheIn()    |
        //                      new         |   (ぞんびの日記カウント)  |
        //                     橋渡し        | }                      |
        //  (createメソッドをオーバーライド)    +------------------------+
        //
        // o breatheIn()でのif文をオーバーライドで具象実装で表現したい
        // o でも、ZombieとBarkProcessは上下関係にない (is-aではないので継承するのおかしい)
        //
        // breatheIn()に戻すという案(by のにわさん)、これも選択肢の一つだけど...
        // bark固有のbreatheInなので、Barkの世界 (Barkワールド) に置いておきたい。
        // し、publicメソッドになってしまって、別問題が発生する。
        // ので、やはり BarkProcess に置いたままで解決したいところ。
        //
        // interfaceを用意して...の案(by のにわさん)、おおお。
        // (オーバーライドの案とは別の) 一つの選択肢ではある。
        // 依存の排除の手段というのは一つではないので。
        // ただ、オーバーライドというオブジェクト指向のオードソックスなやり方も考えて欲しい。
        //
        // hint1: オブジェクト指向は、もっと自由 (何かを忘れてしまっている)
        // → 抽象クラスと具象だけの世界ではない。step41でやったように具象to具象もある。
        // → Animalだけのものではない。誰でも継承関係は作れる。
        //
        // hint2: Dogの処理はDogに...じゃなくて、Dog系に固めたい (2026/06/12)
        // Zombieの処理は、Zombie系のクラスに固めたい。
        // (絶対Zombieクラスだけじゃないといけないわけじゃない。Zombie関連クラスがあって良い)
        //
        //
        // 具象to具象に対して、謎に抵抗感があったので思いつけなかった by のにわさん
        // もしZombieだけじゃなくわりとDogなど他もBarkProcessを拡張するのであれば、
        // 抽象to具象にリファクタリングした方が良いという面もある。 by jflute
        //
        // new Zombie().bark() したときのインスタンス的には、
        // Zombieインスタンスと、ZombieBarkProcessインスタンスの二つしかない。by のにわさん
    }

    // done noniwa こちらもとりあえずBarkProcessに持っていきましょう by jflute (2026/05/01)
    protected void prepareAbdominalMuscle() { // also actually depends on barking
        logger.debug("...Using my abdominal muscle for barking"); // dummy implementation
        
        // #1on1: なんとかして、ここで downHitPoint() の処理を動かさないといけない (2026/06/26)
        // hint:
        // 処理を呼びたい (自分で実行するわけじゃない。Animalにやって欲しい)
        // 処理が実行されるように打診したい (何かしらを経由して最終的にやってもらえばいい)
        // interface経由にすることで、処理の具体的な呼び方に対して依存がなくなる。
        downHitPointコール.callDown();
    }

    protected BarkedSound doBark(String barkWord) {
        downHitPointコール.callDown();
        return new BarkedSound(barkWord);
    }
}
