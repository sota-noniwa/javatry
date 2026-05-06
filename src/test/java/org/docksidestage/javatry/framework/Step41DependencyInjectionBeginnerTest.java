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
package org.docksidestage.javatry.framework;

import java.lang.reflect.Field;

import org.docksidestage.bizfw.basic.objanimal.Cat;
import org.docksidestage.bizfw.basic.objanimal.Dog;
import org.docksidestage.bizfw.di.container.SimpleDiContainer;
import org.docksidestage.bizfw.di.nondi.NonDiDirectFirstAction;
import org.docksidestage.bizfw.di.nondi.NonDiDirectSecondAction;
import org.docksidestage.bizfw.di.nondi.NonDiFactoryMethodAction;
import org.docksidestage.bizfw.di.nondi.NonDiIndividualFactoryAction;
import org.docksidestage.bizfw.di.usingdi.UsingDiAccessorAction;
import org.docksidestage.bizfw.di.usingdi.UsingDiAnnotationAction;
import org.docksidestage.bizfw.di.usingdi.UsingDiDelegatingAction;
import org.docksidestage.bizfw.di.usingdi.UsingDiWebFrameworkProcess;
import org.docksidestage.bizfw.di.usingdi.settings.UsingDiModule;
import org.docksidestage.unit.PlainTestCase;
import sun.java2d.pipe.SpanShapeRenderer;

/**
 * The test of Dependency Injection (DI) as beginner level. <br>
 * Show answer by log() or write answer on comment for question of javadoc.
 * @author jflute
 * @author your_name_here
 */
public class Step41DependencyInjectionBeginnerTest extends PlainTestCase {

    // ===================================================================================
    //                                                                        Precondition
    //                                                                        ============
    /**
     * Search "Dependency Injection" by internet and learn it in thirty minutes. (study only) <br>
     * ("Dependency Injection" をインターネットで検索して、30分ほど学んでみましょう。(勉強のみ))
     */
    public void test_whatis_DependencyInjection() {
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // What is Dependency Injection?
        // - - - - - (your answer?)
        //
        // クラスAがクラスBに依存しているとき、クラスB（依存）のオブジェクトの生成・管理を外部に任せて、コンストラクタなどでクラスAに注入すること。
        // クラスAが直接クラスBを生成する = 密結合, DIを利用する = 疎結合
        // _/_/_/_/_/_/_/_/_/_/
    }

    // ===================================================================================
    //                                                                 Non DI Code Reading
    //                                                                 ===================
    /**
     * What is the difference between NonDiDirectFirstAction and NonDiDirectSecondAction? <br>
     * (NonDiDirectFirstAction と NonDiDirectSecondAction の違いは？)
     */
    public void test_nondi_difference_between_first_and_second() {
        // your answer? => NonDiDirectFirstAction は必要なオブジェクトの生成を自身で行っている
        // NonDiDirectSecondAction はそれに加えて、オブジェクト生成に必要なセットアップメソッドの呼び出しも行なっている
        // どちらも1つのクラス内部でオブジェクト生成・セットアップ・ビジネスロジックの実行の3つの責務を持っているので、コードが複雑になる
        // and your confirmation code here freely
        NonDiDirectFirstAction nonDiDirectFirstAction = new NonDiDirectFirstAction();
        nonDiDirectFirstAction.callFriend();
        System.out.println("-------------------------");
        NonDiDirectSecondAction nonDiDirectSecondAction = new NonDiDirectSecondAction();
        nonDiDirectSecondAction.callFriend();
    }

    /**
     * What is the difference between NonDiDirectSecondAction and NonDiFactoryMethodAction? <br>
     * (NonDiDirectSecondAction と NonDiFactoryMethodAction の違いは？)
     */
    public void test_nondi_difference_between_second_and_FactoryMethod() {
        // your answer? => NonDiFactoryMethodAction はオブジェクトの生成をファクトリーメソッドに切り出している
        // 重複コードが少し減ったが、ファクトリーメソッドも同じクラス内にあるため、1クラスの責務が多いままになっている
        // and your confirmation code here freely
        NonDiDirectSecondAction nonDiDirectSecondAction = new NonDiDirectSecondAction();
        nonDiDirectSecondAction.callFriend();
        System.out.println("-------------------------");
        NonDiFactoryMethodAction nonDiFactoryMethodAction = new NonDiFactoryMethodAction();
        nonDiFactoryMethodAction.callFriend();
    }

    /**
     * What is the difference between NonDiFactoryMethodAction and NonDiIndividualFactoryAction? <br>
     * (NonDiFactoryMethodAction と NonDiIndividualFactoryAction の違いは？)
     */
    public void test_nondi_difference_between_FactoryMethod_and_IndividualFactory() {
        // your answer? => NonDiIndividualFactoryAction はオブジェクトの生成をファクトリークラスに切り出している
        // NonDiIndividualFactoryAction クラスはオブジェクト生成・セットアップの責務のみを持つようになったが、具象クラスである
        // NonDiAnimalFactory を直接生成しているため、密結合になっている
        // and your confirmation code here freely
        NonDiFactoryMethodAction nonDiFactoryMethodAction = new NonDiFactoryMethodAction();
        nonDiFactoryMethodAction.callFriend();
        System.out.println("-------------------------");
        NonDiIndividualFactoryAction nonDiIndividualFactoryAction = new NonDiIndividualFactoryAction();
        nonDiIndividualFactoryAction.callFriend();
    }

    // ===================================================================================
    //                                                               Using DI Code Reading
    //                                                               =====================
    /**
     * What is the difference between UsingDiAccessorAction and UsingDiAnnotationAction? <br>
     * (UsingDiAccessorAction と UsingDiAnnotationAction の違いは？)
     */
    public void test_usingdi_difference_between_Accessor_and_Annotation() {
        // your answer? => UsingDiAccessorAction はセッターで依存注入しているので、callFriendメソッドなどを呼ぶ前に
        // 呼び出し側でセッターメソッドを呼び出す必要がある。（依存管理も呼び出し側の責務）
        // UsingDiAnnotationAction はフィールドにアノテーションを付与して依存注入しており、呼び出し側ではなくDIが依存を管理する。
        // and your confirmation code here freely
        UsingDiAccessorAction usingDiAccessorAction = new UsingDiAccessorAction();
        usingDiAccessorAction.setAnimal(new Dog());
        usingDiAccessorAction.callFriend();
        System.out.println("-------------------------");
        // instantiate & setup DI container
        SimpleDiContainer container = SimpleDiContainer.getInstance();
        container.registerModule(new UsingDiModule());
        container.resolveDependency();
        // get a component from DI container
        UsingDiAnnotationAction action =
                (UsingDiAnnotationAction) container.getComponent(UsingDiAnnotationAction.class);
        action.callFriend();

        // #1on1: アプリの業務コード、アプリの仕組みコード、DIコンテナの三者 (2026/05/01)
        // 疎結合にしたいのはアプリの業務コードだけ。ここでいうとActionクラス、そこでDIしたい。
        // そのための土台として、アプリの仕組みコードがDIコンテナに働きかけてDIをしてもらう。
        // (仕組みコードがDIコンテナにインスタンスのnewとセットアップを教えてあげる)
        //
        // そういう意味では、NonDiAnimalFactory が消えたと言うよりも、
        // DIコンテナに合わせた形に集約された、とも言える。
        // それにより、アプリの業務コードが自分でFactory経由で取りに行くのではなく、
        // 待っていればDIコンテナがインスタンスをくれるという形になる。

        // #1on1: DIコンテナーのコードに興味を持ったのはなぜ？ (2026/05/01)
        // Seasarの話。そして、Lasta Diの話。
        // そもそもフレームワークのコードを追うようになったのはなぜ？

        // #1on1: フレームワークのコードを読む意義 (2026/05/01)
        // AIが出てきたけど、差がつくところは床の下がどうなってるか？わかる人？ by のにわさん＆くぼ
        // 昔もそのときの床の下を知ってる人がトラブルや特殊案件では強かった。

        // #1on1: AIで業務を効率化はするはするけど、意識としては...
        // AIで自分の能力を上げることにフォーカスを当てて欲しい (2026/05/01)
        // AIでアウトプットを効率化するではなく、インプットを効率化 by のにわさん
        // 一方で、書く時間が圧倒的に少ないジレンマ by くぼ
    }

    /**
     * What is the difference between UsingDiAnnotationAction and UsingDiDelegatingAction? <br>
     * (UsingDiAnnotationAction と UsingDiDelegatingAction の違いは？)
     */
    public void test_usingdi_difference_between_Annotation_and_Delegating() {
        // your answer? => UsingDiAnnotationAction は caller が呼ぶためのメソッドを持ち、ロジックもそのメソッド内に直接書いてある。
        // UsingDiDelegatingAction は caller が呼ぶためのメソッドを持ち、ロジックは別クラス (UsingDiDelegatingLogic) に委譲している。
        // and your confirmation code here freely
        SimpleDiContainer container = SimpleDiContainer.getInstance();
        container.registerModule(new UsingDiModule());
        container.resolveDependency();

        UsingDiAnnotationAction action1 =
                (UsingDiAnnotationAction) container.getComponent(UsingDiAnnotationAction.class);
        action1.callFriend();
        System.out.println("-------------------------");
        UsingDiDelegatingAction action2 =
                (UsingDiDelegatingAction) container.getComponent(UsingDiDelegatingAction.class);
        action2.goToOffice();
    }

    // ===================================================================================
    //                                                           Execute like WebFramework
    //                                                           =========================
    /**
     * Execute callFriend() of accessor action by UsingDiWebFrameworkProcess. (Animal as TooLazyDog) <br>
     * (accessor の Action の callFriend() を UsingDiWebFrameworkProcess 経由で実行してみましょう (Animal は TooLazyDog として))
     */
    public void test_usingdi_UsingDiWebFrameworkProcess_callfriend_accessor() {
        // execution code here
        SimpleDiContainer diContainer = SimpleDiContainer.getInstance();
        diContainer.registerModule(new UsingDiModule());
        diContainer.resolveDependency();

        UsingDiWebFrameworkProcess diFrameWork = new UsingDiWebFrameworkProcess();
        diFrameWork.requestAccessorCallFriend();
        // DI コンテナからコンポーネントを取得して、 callFriend() を呼び出す作業を framework が代わりにやってくれている
    }

    /**
     * Execute callFriend() of annotation and delegating actions by UsingDiWebFrameworkProcess. <br>
     *  (Animal as TooLazyDog...so you can increase hit-points of sleepy cat in this method) <br>
     * <br>
     * (annotation, delegating の Action の callFriend() を UsingDiWebFrameworkProcess 経由で実行してみましょう <br>
     *  (Animal は TooLazyDog として...ということで眠い猫のヒットポイントをこのメソッド内で増やしてもOK))
     */
    public void test_usingdi_UsingDiWebFrameworkProcess_callfriend_annotation_delegating() {
        // execution code here
        SimpleDiContainer container = SimpleDiContainer.getInstance();
        UsingDiModule module = new UsingDiModule() {
            @Override
            protected Cat createPlayingCat() {
                return new Cat() {
                    @Override
                    protected int getInitialHitPoint() {
                        return 20;
                    }
                };
            }
        };
        container.registerModule(module);
        container.resolveDependency();

        UsingDiWebFrameworkProcess framework = new UsingDiWebFrameworkProcess();
        framework.requestAnnotationCallFriend();
        framework.requestDelegatingCallFriend();
    }

    /**
     * What is concrete class of instance variable "animal" of UsingDiAnnotationAction? (when registering UsingDiModule) <br>
     * (UsingDiAnnotationAction のインスタンス変数 "animal" の実体クラスは？ (UsingDiModuleを登録した時))
     */
    public void test_usingdi_whatis_animal() throws NoSuchFieldException, IllegalAccessException {
        // your answer? => TooLazyDog. UsingDiModule.bind() で実体クラスを作成している
        // and your confirmation code here freely
        SimpleDiContainer container = SimpleDiContainer.getInstance();
        container.registerModule(new UsingDiModule());
        container.resolveDependency();

        UsingDiAnnotationAction action =
                (UsingDiAnnotationAction) container.getComponent(UsingDiAnnotationAction.class);
        Field field = action.getClass().getDeclaredField("animal");
        field.setAccessible(true);
        Object animal = field.get(action);
        System.out.println(animal.getClass());
    }

    // ===================================================================================
    //                                                                        DI Container
    //                                                                        ============
    /**
     * What is DI container? <br>
     * (DIコンテナとは？)
     */
    public void test_whatis_DIContainer() {
        // your answer? => 登録したコンポーネントの生成・管理を行い、必要な時にコンポーネントを提供してくれる箱。
        // サービスを使う側が依存するコンポーネントの生成・初期化・管理をしなくて済むので、使いやすくなる。
        // 責務を分離できるので、コードを変更する際にどこを見れば良いかわかりやすい。（変更しやすい・可読性向上）
        // and your confirmation code here freely
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * What is class or file of DI settings that defines MemberBhv class as DI component in the following Lasta Di application? <br>
     * (以下のLasta DiアプリケーションでMemberBhvクラスをDIコンポーネントとして定義しているDI設定クラスもしくはファイルは？) <br>
     *
     * https://github.com/lastaflute/lastaflute-example-harbor
     */
    public void test_zone_search_component_on_LastaDi() {
        // your answer? => src/main/resources/dbflute.xml at line 16
        // 同ディレクトリの app.xml でコンポーネントの登録ができると書いてある。
        // https://dbflute.seasar.org/ja/lastaflute/lastadi/index.html
        // app.xml から dbflute.xml を参照している。
    }

    /**
     * What is class or file of DI settings that defines MemberBhv class as DI component in the following Spring application? <br>
     * (以下のSpringアプリケーションでMemberBhvクラスをDIコンポーネントとして定義しているDI設定クラスもしくはファイルは？) <br>
     *
     * https://github.com/dbflute-example/dbflute-example-on-springboot
     */
    public void test_zone_search_component_on_Spring() {
        // your answer? => src/main/java/org/docksidestage/dbflute/allcommon/DBFluteBeansJavaConfig.java
        // @ComponentScan(value="org.docksidestage.dbflute.exbhv", lazyInit=true) で exbhv ディレクトリ配下のコンポーネントを
        // スキャンする設定をしている。
        // MemberBhv クラスには @org.springframework.stereotype.Component("memberBhv") を付与し、スキャン対象としている。
    }
}
