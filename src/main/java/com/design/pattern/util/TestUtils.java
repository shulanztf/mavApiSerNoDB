package com.design.pattern.util;

import com.design.pattern.abstractFactory.Provider;
import com.design.pattern.abstractFactory.Sender;
import com.design.pattern.abstractFactory.impl.SendMailFactory;
import com.design.pattern.adapter.Sourceable;
import com.design.pattern.adapter.Targetable;
import com.design.pattern.adapter.impl.Adapter;
import com.design.pattern.adapter.impl.SourceSub1;
import com.design.pattern.adapter.impl.SourceSub2;
import com.design.pattern.builder.Builder;
import com.design.pattern.decorator.impl.Source;
import com.design.pattern.facade.Computer;
import com.design.pattern.proxy.impl.Proxy;

/**
 * 
 * @Title: TestUtils
 * @Description:设计模式测试入口
 * @Author: zhaotf
 * @Since:2017年6月1日 下午1:51:13
 * @Version:1.0
 */
public class TestUtils {

	/**
	 * 主测试入口
	 * 
	 * @param args
	 *            void
	 */
	public static void main(String[] args) {
		TestUtils tu = new TestUtils();
		tu.checkProxy();
	}

	/**
	 * facade
	 * 
	 * @see 外观模式是为了解决类与类之家的依赖关系的，像spring一样，可以将类和类之间的关系配置到配置文件中，
	 *      而外观模式就是将他们的关系放在一个Facade类中， 降低了类类之间的耦合度，该模式中没有涉及到接口。 void
	 */
	public void checkFacade() {
		Computer computer = new Computer();
		computer.startup();
		computer.shutdown();
	}

	/**
	 * 代理模式（Proxy）
	 * 
	 * @see 代理模式的应用场景： 如果已有的方法在使用的时候需要对原有的方法进行改进，此时有两种办法：
	 *      1、修改原有的方法来适应。这样违反了“对扩展开放，对修改关闭”的原则。
	 *      2、就是采用一个代理类调用原有的方法，且对产生的结果进行控制。这种方法就是代理模式。
	 *      使用代理模式，可以将功能划分的更加清晰，有助于后期维护！ void
	 */
	public void checkProxy() {
		com.design.pattern.proxy.Sourceable source = new Proxy();
		source.method();
	}

	/**
	 * 装饰模式（Decorator）
	 * 
	 * @see 顾名思义，装饰模式就是给一个对象增加一些新的功能，而且是动态的，要求装饰对象和被装饰对象实现同一个接口，装饰对象持有被装饰对象的实例，
	 *      关系图如下： 装饰器模式的应用场景： 1、需要扩展一个类的功能。
	 *      2、动态的为一个对象增加功能，而且还能动态撤销。（继承不能做到这一点，继承的功能是静态的，不能动态增删。）
	 *      缺点：产生过多相似的对象，不易排错！ void
	 */
	public void checkDecorator() {
		com.design.pattern.decorator.Sourceable source = new Source();
		com.design.pattern.decorator.Sourceable obj = new com.design.pattern.decorator.impl.Decorator(source);
		obj.method();
	}

	/**
	 * 适配器模式（Adapter）
	 * 
	 * @see 适配器模式将某个类的接口转换成客户端期望的另一个接口表示，目的是消除由于接口不匹配所造成的类的兼容性问题。主要分为三类：类的适配器模式、
	 *      对象的适配器模式、接口的适配器模式。首先，我们来看看类的适配器模式，先看类图：
	 *      核心思想就是：有一个Source类，拥有一个方法，待适配，目标接口时Targetable，通过Adapter类，
	 *      将Source的功能扩展到Targetable里，看代码： void
	 */
	public void checkAdapter() {
		Targetable target = new Adapter();
		target.method1();
		target.method2();

		Sourceable source1 = new SourceSub1();
		Sourceable source2 = new SourceSub2();

		source1.method1();
		System.out.println("------");
		source1.method2();
		System.out.println("------");
		source2.method1();
		System.out.println("------");
		source2.method2();
		System.out.println("------");

	}

	/**
	 * 测试 建造者模式（Builder）
	 * 
	 * @see 工厂类模式提供的是创建单个类的模式，而建造者模式则是将各种产品集中起来进行管理，用来创建复合对象，所谓复合对象就是指某个类具有不同的属性
	 *      ，其实建造者模式就是前面抽象工厂模式和最后的Test结合起来得到的。
	 *      我们看一下代码：还和前面一样，一个Sender接口，两个实现类MailSender和SmsSender。最后，建造者类如下：
	 *      从这点看出，建造者模式将很多功能集成到一个类里，这个类可以创造出比较复杂的东西。所以与工程模式的区别就是：工厂模式关注的是创建单个产品，
	 *      而建造者模式则关注创建符合对象，多个部分。因此，是选择工厂模式还是建造者模式，依实际情况而定。
	 * 
	 * 
	 *      void
	 */
	public void testBuilder() {
		Builder builder = new Builder();
		builder.produceMailSender(10);
	}

	/**
	 * 测试 抽象工厂模式（Abstract Factory）
	 * 
	 * @see http://www.cnblogs.com/maowang1991/archive/2013/04/15/3023236.html
	 *      工厂方法模式有一个问题就是，类的创建依赖工厂类，也就是说，如果想要拓展程序，必须对工厂类进行修改，这违背了闭包原则，所以，
	 *      从设计角度考虑，有一定的问题，如何解决？
	 *      就用到抽象工厂模式，创建多个工厂类，这样一旦需要增加新的功能，直接增加新的工厂类就可以了，不需要修改之前的代码。
	 *      因为抽象工厂不太好理解，我们先看看图，然后就和代码，就比较容易理解。
	 *      其实这个模式的好处就是，如果你现在想增加一个功能：发及时信息，则只需做一个实现类，实现Sender接口，同时做一个工厂类，
	 *      实现Provider接口，就OK了，无需去改动现成的代码。这样做，拓展性较好！
	 */
	public void testAbstractFactory() {

		Provider provider = new SendMailFactory();
		Sender sender = provider.produce();
		sender.Send();
	}

}
