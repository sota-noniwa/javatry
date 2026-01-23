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

import static org.docksidestage.bizfw.basic.buyticket.TicketType.ONE_DAY;

import java.util.Comparator;
import java.util.stream.Stream;

import org.docksidestage.bizfw.basic.buyticket.Ticket;
import org.docksidestage.bizfw.basic.buyticket.TicketBooth;
import org.docksidestage.bizfw.basic.objanimal.Animal;
import org.docksidestage.bizfw.basic.objanimal.Bird;
import org.docksidestage.bizfw.basic.objanimal.Cat;
import org.docksidestage.bizfw.basic.objanimal.Dog;
import org.docksidestage.bizfw.basic.objanimal.Zombie;
import org.docksidestage.bizfw.basic.objanimal.barking.BarkedSound;
import org.docksidestage.bizfw.basic.objanimal.flying.Flyable;
import org.docksidestage.bizfw.basic.objanimal.loud.AlarmClock;
import org.docksidestage.bizfw.basic.objanimal.loud.Loudable;
import org.docksidestage.bizfw.basic.objanimal.runner.FastRunner;
import org.docksidestage.javatry.basic.st6.dbms.Dbms;
import org.docksidestage.javatry.basic.st6.dbms.InterfaceDbms;
import org.docksidestage.javatry.basic.st6.dbms.St6MySql;
import org.docksidestage.javatry.basic.st6.dbms.St6PostgreSql;
import org.docksidestage.javatry.basic.st6.os.MacOs;
import org.docksidestage.javatry.basic.st6.os.OldWindows;
import org.docksidestage.javatry.basic.st6.os.St6OperationSystem;
import org.docksidestage.javatry.basic.st6.os.Windows;
import org.docksidestage.unit.PlainTestCase;
import org.docksidestage.unit.flute.util.Srl;

// done noniwa javadocのauthor by jflute (2025/10/21)

/**
 * The test of object-oriented. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author noniwa
 */
public class Step06ObjectOrientedTest extends PlainTestCase {

    // ===================================================================================
    //                                                                        About Object
    //                                                                        ============
    // -----------------------------------------------------
    //                                        Against Object
    //                                        --------------
    /**
     * Fix several mistakes (except simulation) in buying one-day passport and in-park process. <br>
     * (OneDayPassportを買って InPark する処理の中で、(simulationを除いて)間違いがいくつかあるので修正しましょう)
     */
    public void test_objectOriented_aboutObject_againstObject() {
        //
        // [ticket booth info]
        //
        // simulation: actually these variables should be more wide scope
        int oneDayPrice = 7400;
        int quantity = 10;
        // fixed: primitive int is enough instead of Integer
        // fixed: initial value 0 is more suitable for calculation instead of null
        int salesProceeds = 0;

        // done noniwa ここから残り3つ間違いが残っています by jflute (2025/10/21)
        // done noniwa この辺に残り1つ間違いが。それぞれの行だけでは間違いにはならない。 by jflute (2025/11/18)
        // (逆に、その行だけで間違いと言えるものは、e.g. int displayPrice = quantity)
        // done jflute 受け取った金額が不足しているかどうかのチェックをする前にチケット枚数を減らしていましたね
        // 実際にブースにお客様が来てチケットを買う姿を想像することで気づくことができました
        //
        // [buy one-day passport]
        //
        // simulation: actually this money should be from customer
        int handedMoney = 10000;
        if (quantity <= 0) {
            throw new IllegalStateException("Sold out");
        }
        if (handedMoney < oneDayPrice) {
            throw new IllegalStateException("Short money: handedMoney=" + handedMoney);
        }
        // fixed: wrong calculation
        salesProceeds += oneDayPrice;
        // fixed: quantity was reduced before checking exception
        --quantity;

        //
        // [ticket info]
        //
        // simulation: actually these variables should be more wide scope
        // fixed: wrong assignment
        int displayPrice = oneDayPrice;
        boolean alreadyIn = false;

        // other processes here...
        // ...
        // ...

        //
        // [do in park now!!!]
        //
        // simulation: actually this process should be called by other trigger
        if (alreadyIn) {
            // fixed: wrong variable was used
            throw new IllegalStateException("Already in park by this ticket: displayPrice=" + oneDayPrice);
        }
        alreadyIn = true;

        //
        // [final process]
        //
        // fixed: wrong order of parameters
        // #1on1: 作り側の立場だったら...
        // o オブジェクト指向の恩恵を受けて、オブジェクト単位でデータの引き渡しをする
        // o なんかしらの方法で、int, int, int にはならないように工夫する
        // #1on1: 呼び出し側の立場だったら...
        // o 実装した直後に5秒指差し確認
        // o 経験で、間違えやすいポイントを知ってる
        //   (自分のバグ、他人のバグをたくさん見てきてつど解釈して積み重ねる)
        //   (→ バグを見る体験を、経験をするかどうかは本人の意思)
        // o さらに、自分の間違えやすいポイントを知ってる
        //   (自分の間違いの思い出をいかに解釈して積み重ねるか)
        //
        // → 技術スキルというよりは、開発スキル (ものづくりスキル)
        //
        saveBuyingHistory(quantity, salesProceeds, displayPrice, alreadyIn);
    }

    private void saveBuyingHistory(int quantity, Integer salesProceeds, int displayPrice, boolean alreadyIn) {
        if (alreadyIn) {
            // simulation: only logging here (normally e.g. DB insert)
            // fixed: wrong parameters were passed
            showTicketBooth(quantity, salesProceeds);
            showYourTicket(displayPrice, alreadyIn);
        }
    }

    private void showTicketBooth(int quantity, Integer salesProceeds) {
        log("Ticket Booth: quantity={}, salesProceeds={}", quantity, salesProceeds);
    }

    private void showYourTicket(int displayPrice, boolean alreadyIn) {
        log("Ticket: displayPrice={}, alreadyIn={}", displayPrice, alreadyIn);
    }

    // -----------------------------------------------------
    //                                          Using Object
    //                                          ------------
    /**
     * Read (analyze) this code and compare with the previous test method, and think "what is object?". <br>
     * (このコードを読んで(分析して)、一つ前のテストメソッドと比べて、「オブジェクトとは何か？」を考えてみましょう)
     */
    public void test_objectOriented_aboutObject_usingObject() {
        //
        // [ticket booth info]
        //
        TicketBooth booth = new TicketBooth();

        // *booth has these properties:
        //int oneDayPrice = 7400;
        //int quantity = 10;
        //Integer salesProceeds = null;

        //
        // [buy one-day passport]
        //
        // if step05 has been finished, you can use this code by jflute (2019/06/15)
        Ticket ticket = booth.buyTicket(ONE_DAY, 10000).getTicket();
        //        TicketBuyResult receipt = booth.buyTicket(ONE_DAY, 10000); // as temporary, remove if you finished step05
        //        Ticket ticket = receipt.getTicket(); // also here

        // *buyOneDayPassport() has this process:
        //if (quantity <= 0) {
        //    throw new TicketSoldOutException("Sold out");
        //}
        //if (handedMoney < oneDayPrice) {
        //    throw new TicketShortMoneyException("Short money: handedMoney=" + handedMoney);
        //}
        //--quantity;
        //salesProceeds = handedMoney;

        // *ticket has these properties:
        //int displayPrice = oneDayPrice;
        //boolean alreadyIn = false;

        // other processes here...
        // ...
        // ...

        //
        // [do in park now!!!]
        //
        ticket.useForOneDay();

        // *doInPark() has this process:
        //if (alreadyIn) {
        //    throw new IllegalStateException("Already in park by this ticket: displayPrice=" + displayPrice);
        //}
        //alreadyIn = true;

        //
        // [final process]
        //
        saveBuyingHistory(booth, ticket);
    }

    private void saveBuyingHistory(TicketBooth booth, Ticket ticket) {
        if (ticket.hasExpired()) {
            // only logging here (normally e.g. DB insert)
            doShowTicketBooth(booth);
            doShowYourTicket(ticket);
        }
    }

    private void doShowTicketBooth(TicketBooth booth) {
        log("Ticket Booth: quantity={}, salesProceeds={}", booth.getTicketQuantity(ONE_DAY), booth.getSalesProceeds());
    }

    private void doShowYourTicket(Ticket ticket) {
        log("Your Ticket: displayPrice={}, alreadyIn={}", ticket.getType().getPrice(), ticket.hasExpired());
    }

    // done jflute 次回1on1ここから (2025/10/21)

    // write your memo here:
    // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
    // what is object?
    // クラスを概念・物質の鋳型とするならば、オブジェクトはその鋳型から作られた1つ1つの実体
    // 同じクラスから作られたオブジェクトは同じ性質を持つが、同じ値を持つわけではない
    // colorというメンバー変数を持つBallクラスから作られたオブジェクトは、あるものは赤色であるものは青色
    // つまり同じcolorという性質を持つがその値はそれぞれ異なる
    // オブジェクトはビジネスロジックを内包することもできる、例えばpaintメソッドなど
    // colorをprivateにすることでオブジェクトの性質を外側から隠すこともできる
    // _/_/_/_/_/_/_/_/_/_/
    // #1on1: カプセル化の話が的を得ているのでGoodです。
    // オブジェクトという言葉は、インスタンスと、クラスと、若干曖昧になりやすい。
    // オブジェクトという言葉の使い方の難しさ話。
    // (プロジェクトという言葉も)
    // 
    // #1on1: オブジェクトの概念の導き、データ同士の関連性と概念化
    // 概念を正確に捉えるのが非常に重要。第一歩が一番大事。
    // 「間違った構造で作ったプログラムは、ベタ書きよりつらい」と言っても過言ではない。
    // 1位: 正しい構造で作ったプログラム
    // 2位: (最低限綺麗に書いた)ベタ書き
    // 3位: 間違った構造で作ったプログラム
    //  (by jflute)
    //
    // まだ実装してない段階で、正しい構造を当てるのは難しそう by のにわさん
    // なので、あってる前提で進めないほうが良い (リファクタリングしやすい環境) by jflute
    // ただ、リファクタリングしやすい環境ってのを作るのが難しい by jflute
    // 
    // #1on1: 一緒に読み合わせてみた
    // // リファクタリングは思考のツール
    // https://jflute.hatenadiary.jp/entry/20121202/1354442627

    // done jflute 次回1on1ここから (2025/11/07)
    // ===================================================================================
    //                                                              Polymorphism Beginning
    //                                                              ======================
    /**
     * What string is sea and land variable at the method end? <br>
     * (メソッド終了時の変数 sea, land の中身は？)
     */
    public void test_objectOriented_polymorphism_1st_concreteOnly() {
        Dog dog = new Dog();
        BarkedSound sound = dog.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => wan(o)
        int land = dog.getHitPoint();
        log(land); // your answer? => 7(o)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_2nd_asAbstract() {
        Animal animal = new Dog();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => wan(o)
        int land = animal.getHitPoint();
        log(land); // your answer? => 7(o)
        // DogをAnimal型として定義しているが、DogはAnimalを継承しているのでエラーは起こらない
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_3rd_fromMethod() {
        Animal animal = createAnyAnimal();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => wan(o)
        int land = animal.getHitPoint();
        log(land); // your answer? => 7(o)
        // createAnyAnimalはDogインスタンスを返しているので、ログの結果は前の質問と同じ
        // #1on1: とうとう、test_メソッドが、(直接的に)Dogに依存しなくなった。
    }

    private Animal createAnyAnimal() {
        return new Dog();
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_4th_toMethod() {
        Dog dog = new Dog();
        doAnimalSeaLand_for_4th(dog);
    }

    private void doAnimalSeaLand_for_4th(Animal animal) {
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => wan(o)
        int land = animal.getHitPoint();
        log(land); // your answer? => 7(o)
        // #1on1: (直接的に)Dogに依存してないってところは同じ
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_5th_overrideWithSuper() {
        Animal animal = new Cat();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => nya-(o)
        int land = animal.getHitPoint();
        log(land); // your answer? => 5(o)
        //CatオブジェクトはdownHitPointメソッドの仕様が他と違う
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_6th_overriddenWithoutSuper() {
        Animal animal = new Zombie();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => uooo(o)
        int land = animal.getHitPoint();
        log(land); // your answer? => -1(o)
        // downHitPointで何もしていないのでhitPointは初期値のまま
    }

    /**
     * What is happy if you can assign Dog or Cat instance to Animal variable? <br>
     * (Animal型の変数に、DogやCatなどのインスタンスを代入できると何が嬉しいのでしょう？)
     */
    public void test_objectOriented_polymorphism_7th_whatishappy() {
        // write your memo here:
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // what is happy?
        // DogやCatなどAnimalに依存するクラスを同じ型として扱うことができるので、変更容易性が向上する
        // 例えば、Dogの代わりにCatを使う仕様に変更したい場合、instantiationの部分をDog()からCat()に変えるだけで良い
        // DogとCatが共通のAnimal abstract classに依存していなかった場合、彼らの持つフィールドや
        // メソッドが共通の名前、引数の数・型・順序、戻り値の型などが同じだと約束されていないため
        // Dogのinstantiation部分だけでなく、メソッド呼び出しやフィールドへのアクセスをしている部分も変更しなければならない
        // _/_/_/_/_/_/_/_/_/_/
        // #1on1: 再利用性もさることながら、(実体を変えるとき)処理のロジックに手を入れずに済む、
        // というが、開発実務上とても嬉しい。(1行でも手を入れたら、変な間違いが入る可能性がある)
        // #1on1: 日常でもポリモーフィズム、それをプログラムでも使いたい。
    }

    // ===================================================================================
    //                                                              Polymorphism Interface
    //                                                              ======================
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_interface_dispatch() {
        Loudable loudable = new Zombie();
        String sea = loudable.soundLoudly();
        log(sea); // your answer? => uooo(o)
        String land = ((Zombie) loudable).bark().getBarkWord();
        log(land); // your answer? => uooo(o)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_interface_hierarchy() {
        Loudable loudable = new AlarmClock();
        String sea = loudable.soundLoudly();
        log(sea); // your answer? => jiri jiri jiri---(o)
        boolean land = loudable instanceof Animal;
        log(land); // your answer? => false(o)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_interface_partImpl() {
        Animal seaAnimal = new Cat();
        Animal landAnimal = new Zombie();
        boolean sea = seaAnimal instanceof FastRunner;
        log(sea); // your answer? => true(o)
        boolean land = landAnimal instanceof FastRunner;
        log(land); // your answer? => false(o)
        // Animal doesn't implement FastRunner
        // Cat implements FastRunner while Zombie doesn't
    }

    /**
     * Make Dog class implement FastRunner interface. (the method implementation is same as Cat class) <br>
     * (DogもFastRunnerインターフェースをimplementsしてみましょう (メソッドの実装はCatと同じで))
     */
    public void test_objectOriented_polymorphism_interface_runnerImpl() {
        // your confirmation code here
        Animal dog = new Dog();
        boolean isDogFastRunner = dog instanceof FastRunner;
        log(isDogFastRunner);
    }

    /**
     * What is difference as concept between abstract class and interface? <br>
     * (抽象クラスとインターフェースの概念的な違いはなんでしょう？)
     */
    public void test_objectOriented_polymorphism_interface_whatisdifference() {
        // write your memo here:
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // what is difference?
        // Abstract class                           | Interface
        // can have concrete & abstract methods     | can only have abstract methods
        // can have instance variables & constants  | can only have constants
        // can have constructor                     | can not have constructor
        // a class can extends one abstract class   | a class can implement multiple interfaces
        // can depend on abstract class & interfaces| can only depend on interfaces
        // _/_/_/_/_/_/_/_/_/_/
        // #1on1: ↑は正しくて丁寧でGood, ただ上記は文法的な違い。
        // 概念的な違い、例えば上記の文法的な違いはなぜ生まれるのか？
        //
        // abstractは、なにをするべきか + どう動かすか？も示す
        // interfaceは、なにをするべきかを示す
        //
        // ↑abstract の方が機能が多くて、interfaceを包含しているように見えちゃうけど？
        // interfaceは、複数定義できるので細かく分けられる？？？ by のにわさん
        // そう、包含と言っちゃいましたが、実際には包含ではなく、複数継承できるできないがある。
        // でも、もうちょい頑張れば、複数abstract継承できたらinterface要らないのに!?
        //
        // pp まずコンセプトが違う
        // o interfaceが操作中心のコンセプト (呼ばれることを最初から意識)
        // o abstractはあくまでオブジェクト中心のコンセプト (結局、呼ばれるけどね)
        //
        // extendsは is-aの関係: Dog is a(n) Animal (いぬは動物である)
        // implementsは can-doの関係: Dog can loud, Dog can fast-run
        //
        // 別のコンセプトなので、似た部分は出てくる (e.g. ポリモーフィズム)
        // だからこそ、以下のようにかぶる部分が出てくる:
        //  abstractは、なにをするべきか + どう動かすか？も示す
        //  interfaceは、なにをするべきかを示す
        //
        // Javaは、実は100%オブジェクト指向言語じゃない。多重継承ができない。(わざとできないようにしてる)
        // C++などで、ダイヤモンド継承などとにかく多重継承がなかなか大変だったという人類の体験。
        // interfaceを導入すれば、多重ポリモーフィズム(造語)できるけど、多重実装継承は抑えられる。
        // Javaのオブジェクト指向の足らないところをinterfaceが補完する。
        //
        // ↑ここまで基本の考え方
        // done jflute interfaceの特殊な使われ方は次回1on1にて (ColorBox) (2025/11/18)
        // public abstract class AbstractColorBox implements ColorBox {
        //
        // インターフェース: ポリモーフィズム
        // 抽象クラス: ポリモーフィズム + 具象クラスの形付け
        //
        // ColorBoxの場合、抽象クラスがポリモーフィズムを捨てて、具象クラスの形付けにだけ集中している。
        // インターフェースは、本来抽象クラスだけで足りるところを、ポリモーフィズムの役割を担っている。
        //
        // メリット:
        // 1. 呼べるもの一覧があってわかりやすい (ただ小さな理由)
        // 2. 内部処理用のpublicメソッドをインターフェースで隠蔽できる (100%じゃないけど)
        // 3. ラッパークラスやダミークラスなどを作るときは抽象クラスの実装が邪魔なのでポリモーフィズムが独立してると良い
        //
        // 2の実例で、LastaFluteのTimeManager, SimpleTimeManagerを見た
        // 3の実例で、DBFlute の ListResultBean を見た
        //
        // 抽象クラスは「内政」に集中して、インターフェースが「外交」をする、みたいな。
    }

    // done jflute 次回1on1ここから (2025/12/05)
    // ===================================================================================
    //                                                                 Polymorphism Making
    //                                                                 ===================
    /**
     * Make concrete class of Animal, which is not FastRunner, in "objanimal" package. (implementation is as you like) <br>
     * (FastRunnerではないAnimalクラスのコンクリートクラスをobjanimalパッケージに作成しましょう (実装はお好きなように))
     */
    public void test_objectOriented_polymorphism_makeConcrete() {
        // your confirmation code here
        Animal bird = new Bird();
        log(bird.bark().getBarkWord());
    }

    /**
     * Make interface implemented by part of Animal concrete class in new package under "objanimal" package. (implementation is as you like) <br>
     * (Animalクラスの一部のコンクリートクラスだけがimplementsするインターフェースをobjanimal配下の新しいパッケージに作成しましょう (実装はお好きなように))
     */
    public void test_objectOriented_polymorphism_makeInterface() {
        // your confirmation code here
        // done noniwa Flyable, loud/runnerみたいに、操作コンセプトのpackageを作って配置しましょう by jflute (2025/12/19)
        Flyable bird = new Bird();
        bird.fly();
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Extract St6MySql, St6PostgreSql (basic.st6.dbms)'s process to abstract class (as super class and sub-class) <br>
     * (St6MySql, St6PostgreSql (basic.st6.dbms) から抽象クラスを抽出してみましょう (スーパークラスとサブクラスの関係に))
     */
    public void test_objectOriented_writing_generalization_extractToAbstract() {
        // your confirmation code here
        Integer maxLength = calculateDbmsClassNameMaxLength();

        log("");
        log("[abstract way]");
        executeAbstractPagingQuery(new St6MySql(), 10, 1, maxLength);
        executeAbstractPagingQuery(new St6PostgreSql(), 15, 3, maxLength);

        log("");
        log("[interface way]");
        executeInterfacePagingQuery(new St6MySql(), 10, 1, maxLength);
        executeInterfacePagingQuery(new St6PostgreSql(), 15, 3, maxLength);
    }

    private Integer calculateDbmsClassNameMaxLength() {
        // 具象クラスが増えた時は、ここに追加すること by jflute (2026/01/09)
        Class<?>[] dbmsTypes = new Class<?>[] { St6MySql.class, St6PostgreSql.class };
        Integer maxLength = Stream.of(dbmsTypes).map(dbmsType -> {
            return dbmsType.getSimpleName().length();
        }).max(Comparator.naturalOrder()).get();
        log("maxLength: " + maxLength);
        return maxLength;
    }

    private void executeAbstractPagingQuery(Dbms dbms, int pageSize, int pageNumber, Integer maxLength) {
        String dbmsLabel = convertToDbmsLabel(maxLength, dbms.getClass().getSimpleName());
        String pagingQuery = dbms.buildPagingQuery(pageSize, pageNumber);
        log(dbmsLabel + " : " + pagingQuery);
    }

    private void executeInterfacePagingQuery(InterfaceDbms interfaceDbms, int pageSize, int pageNumber, Integer maxLength) {
        String dbmsLabel = convertToDbmsLabel(maxLength, interfaceDbms.getClass().getSimpleName());
        String pagingQuery = interfaceDbms.interfaceBuildPagingQuery(pageSize, pageNumber);
        log(dbmsLabel + " : " + pagingQuery);
    }

    private String convertToDbmsLabel(Integer maxLength, String dbmsName) {
        return Srl.rfill(dbmsName, maxLength, ' ');
    }

    /**
     * Extract St6OperationSystem (basic.st6.os)'s process to concrete classes (as super class and sub-class) <br>
     * (St6OperationSystem (basic.st6.os) からコンクリートクラスを抽出してみましょう (スーパークラスとサブクラスの関係に))
     */
    public void test_objectOriented_writing_specialization_extractToConcrete() {
        // your confirmation code here
        // done noniwa 単なる勘違いだったようですが、Windowsクラスとかも作ってみましょう by jflute (2025/12/19)
        St6OperationSystem mac = new MacOs("1234");
        String macPath = mac.buildUserResourcePath("../dbms/MySql.java");
        St6OperationSystem windows = new Windows("abcd");
        String WindowsPath = windows.buildUserResourcePath("../dbms/MariaDb.java");
        St6OperationSystem oldWindows = new OldWindows("password");
        String OldWindowsPath = oldWindows.buildUserResourcePath("../dbms/MongoDb.java");
        log("mac:        " + macPath);
        log("windows:    " + WindowsPath);
        log("oldWindows: " + OldWindowsPath);

        // #1on1: DIの本の話とつながった by のにわさん (2026/01/23)
        // DIの方の前提のニュアンスはたぶんこんな感じ:
        //@Resource
        //private OS os;
        //
        //protected String getFileSeparator() {
        //    return os.getFileSeparator();
        //}
        // 若干前提が違うけど、でも根本は似たところある。
        
        // #1on1: フレームワークのコンセプトの話 (2026/01/23)
        // 何を重視したプロダクトなのか？Spring と LastaFlute の例。
        // (DIコンテナ作ってる話から、作ったきっかけは？の質問)
        //
        // // フレームワークの思想、意識して使っていますか？
        // https://jflute.hatenadiary.jp/entry/20181014/fwthought
        //
        // DIコンテナのコンポーネント登録のコンセプトと実装の歴史。
        // Spring Framework, Google Guice, Seasar, Lasta Di
        
        // #1on1: 思考トレーニング1:  (2026/01/23)
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Extract Animal's bark() process as BarkingProcess class to also avoid large abstract class. <br>
     * (抽象クラス肥大化を抑制するためにも、Animalのbark()のプロセス(処理)をBarkingProcessクラスとして切り出しましょう)
     */
    public void test_objectOriented_writing_withDelegation() {
        // your confirmation code here
        Animal dog = new Dog();
        log(dog.bark().getBarkWord());
    }

    /**
     * Put barking-related classes, such as BarkingProcess and BarkedSound, into sub-package. <br>
     * (BarkingProcessやBarkedSoundなど、barking関連のクラスをサブパッケージにまとめましょう)
     * <pre>
     * e.g.
     *  objanimal
     *   |-barking
     *   |  |-BarkedSound.java
     *   |  |-BarkingProcess.java
     *   |-loud
     *   |-runner
     *   |-Animal.java
     *   |-Cat.java
     *   |-Dog.java
     *   |-...
     * </pre>
     */
    public void test_objectOriented_writing_withPackageRefactoring() {
        // your confirmation code here
        Animal dog = new Dog();
        log(dog.bark().getBarkWord());
    }

    /**
     * Is Zombie correct as sub-class of Animal? Analyze it in thirty seconds. (thinking only) <br>
     * (ゾンビは動物クラスのサブクラスとして適切でしょうか？30秒だけ考えてみましょう (考えるだけでOK))
     */
    public void test_objectOriented_zoo() {
        // write your memo here:
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // is it correct?
        // 現状はゾンビの状態や振る舞いがAnimalとかけ離れていないので適切だと思います。
        // 例えばゾンビはhitPointを持たず、吠えもせず、吠えるための言葉も持たないが、代わりにゾンビに
        // なってからの日数を保持していて、噛みついたり腐ったりするなど他のAnimalとは違う性質が多いので
        // あればAnimalのsubclassとしては適切ではなくなると思います。
        // ただ、これだと、かけ離れているかどうかの判断基準は人それぞれになってしまうと思いました
        // _/_/_/_/_/_/_/_/_/_/
        // #1on1: "噛みついたり腐ったりするなど他のAnimalとは違う性質が多い" のところは... (2025/12/19)
        // これは単にZombieクラスで実装すれば良いだけの振る舞いと言えるので、不適切の要因にはならないかなと。
        //
        // 一方で、"吠えもせず" のところはその通りで、「(実装からすると) Animalは吠える」わけなので、
        // Zombieが吠えないのであれば、そこで矛盾が発生する。
        //
        // そう考えると、現状はhitPointを打ち消しているので、コード量は少なくても、
        // 特性を打ち消していることで、矛盾が発生しているとも言える。
        //
        // ↑ここまでは、ボトムアップで要所要所で判断を考えてみましたが...
        // トップダウンだと？
        // 「ゾンビは動物である Zombie is an Animal」というのが自然かどうか？
        // (これが俗に言うと「is-aの関係」)
        // → その概念にフォーカス当てて考える
        // 
        // それでも迷うのは、Zombieという概念の定義が曖昧。
        //
        // バイオハザードの例、犬も狼もゾンビになる。
        // すべての動物はゾンビになりうるのでは？であれば、動物の特性の一つ。
        // 状態として実装したほうが良いのかも。
        //
        // 一方で、ゾンビ王国の復讐、ゾンビの王様とゾンビの王妃がいて、
        // かっこいいゾンビの王子様が生まれて、冒険に出るみたいなストーリー。
        // ゾンビという種族がいるみたいな扱いをしているのであれば、今の実装でもいいかも。
        //
        // 業務概念を正確に捉えるって難しいし、曖昧だと実装がへんてこりんになる。
        // (DDDの話も少し)
        // (DB設計の話も少し、JJUGでのDB設計話、業務知識大事)
    }
}
