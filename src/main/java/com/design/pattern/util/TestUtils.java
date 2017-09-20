package com.design.pattern.util;

import com.design.pattern.abstractFactory.Provider;
import com.design.pattern.abstractFactory.Sender;
import com.design.pattern.abstractFactory.impl.SendMailFactory;
import com.design.pattern.adapter.Sourceable;
import com.design.pattern.adapter.Targetable;
import com.design.pattern.adapter.impl.Adapter;
import com.design.pattern.adapter.impl.SourceSub1;
import com.design.pattern.adapter.impl.SourceSub2;
import com.design.pattern.bridge.impl.Bridge;
import com.design.pattern.bridge.impl.MyBridge;
import com.design.pattern.builder.Builder;
import com.design.pattern.chain.responsibility.impl.MyHandler;
import com.design.pattern.command.Command;
import com.design.pattern.command.impl.Invoker;
import com.design.pattern.command.impl.MyCommand;
import com.design.pattern.command.impl.Receiver;
import com.design.pattern.decorator.impl.Source;
import com.design.pattern.facade.Computer;
import com.design.pattern.memento.Original;
import com.design.pattern.memento.Storage;
import com.design.pattern.proxy.impl.Proxy;
import com.design.pattern.state.Context;
import com.design.pattern.state.State;
import com.design.pattern.strategy.ICalculator;
import com.design.pattern.strategy.impl.Minus;
import com.design.pattern.strategy.impl.Multiply;
import com.design.pattern.strategy.impl.Plus;
import com.design.pattern.visitor.Subject;
import com.design.pattern.visitor.Visitor;
import com.design.pattern.visitor.impl.MySubject;
import com.design.pattern.visitor.impl.MyVisitor;
import com.design.pattern.visitor.impl.NodeA;
import com.design.pattern.visitor.impl.NodeB;
import com.design.pattern.visitor.impl.VisitorA;

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
		tu.checkMediator();
	}

	/**
	 * 解释器模式（Interpreter）
	 * 
	 * @see 解释器模式是我们暂时的最后一讲，一般主要应用在OOP开发中的编译器的开发中，所以适用面比较窄。
	 *      Context类是一个上下文环境类，Plus和Minus分别是用来计算的实现，代码如下：
	 * @see 基本就这样，解释器模式用来做各种各样的解释器，如正则表达式等的解释器等等
	 * 
	 *      void
	 */
	public void checkInterpreter() {
		// 计算9+2-8的值
		int result = new com.design.pattern.interpreter.impl.Minus()
				.interpret((new com.design.pattern.interpreter.impl.Context(
						new com.design.pattern.interpreter.impl.Plus()
								.interpret(new com.design.pattern.interpreter.impl.Context(
										9, 2)), 8)));
		System.out.println(result);
	}

	/**
	 * 中介者模式（Mediator）
	 * 
	 * @see 多个类呈网状结构时，改成星型结构。
	 * @see 中介者模式也是用来降低类类之间的耦合的，因为如果类类之间有依赖关系的话，不利于功能的拓展和维护，因为只要修改一个对象，
	 *      其它关联的对象都得进行修改。如果使用中介者模式，只需关心和Mediator类的关系，具体类类之间的关系及调度交给Mediator就行，
	 *      这有点像spring容器的作用。
	 * @see User类统一接口，User1和User2分别是不同的对象，二者之间有关联，如果不采用中介者模式，则需要二者相互持有引用，
	 *      这样二者的耦合度很高，为了解耦，引入了Mediator类，提供统一接口，MyMediator为其实现类，
	 *      里面持有User1和User2的实例，用来实现对User1和User2的控制。这样User1和User2两个对象相互独立，
	 *      他们只需要保持好和Mediator之间的关系就行，剩下的全由MyMediator类来维护！
	 * 
	 *      void
	 */
	public void checkMediator() {
//		Mediator mediator = new MyMediator();
//		mediator.createMediator();
//		mediator.workAll();
		
		Visitor va = new VisitorA();
		NodeA na = new NodeA();
		NodeB nb = new NodeB();
//		na.accept(va);
//		nb.accept(va);
		va.visit(na);
		va.visit(nb);
		
		
	}

	/**
	 * 访问者模式（Visitor）
	 * 
	 * @see 访问者模式就是一种分离对象数据结构与行为的方法，通过这种分离，可达到为一个被访问者动态添加新的操作而无需做其它的修改的效果。
	 * @see 该模式适用场景：如果我们想为一个现有的类增加新功能，不得不考虑几个事情：
	 * @see 1、新功能会不会与现有功能出现兼容性问题？
	 * @see 2、以后会不会再需要添加？
	 * @see 3、如果类不允许修改代码怎么办？ void
	 */
	public void checkVisitor() {
		// 一个Visitor类，存放要访问的对象(Subject)
		Visitor visitor = new MyVisitor();
		// Subject类，accept方法，接受将要访问它的对象，getSubject()获取将要被访问的属性，
		Subject subject = new MySubject();
		subject.accept(visitor);
	}

	/**
	 * 状态模式（State）
	 * 
	 * @see 核心思想就是：当对象的状态改变时，同时改变其行为，很好理解！就拿QQ来说，有几种状态，在线、隐身、忙碌等，每个状态对应不同的操作，
	 *      而且你的好友也能看到你的状态， 所以，状态模式就两点：1、可以通过改变状态来获得不同的行为。2、你的好友能同时看到你的变化。看图：
	 *      State类是个状态类，Context类可以实现切换，我们来看看代码： {@link} void
	 */
	public void checkState() {
		State state = new State();
		Context context = new Context(state);

		// 设置第一种状态
		state.setValue("state1");
		context.method();

		// 设置第二种状态
		state.setValue("state2");
		context.method();
	}

	/**
	 * 备忘录模式（Memento）
	 * 
	 * @see 主要目的是保存一个对象的某个状态，以便在适当的时候恢复对象，个人觉得叫备份模式更形象些，通俗的讲下：
	 *      假设有原始类A，A中有各种属性，A可以决定需要备份的属性，备忘录类B是用来存储A的一些内部状态，类C呢，就是一个用来存储备忘录的，
	 *      且只能存储，不能修改等操作。
	 * @see Original类是原始类，里面有需要保存的属性value及创建一个备忘录类，用来保存value值。
	 * @see Memento类是备忘录类，
	 * @see Storage类是存储备忘录的类，持有Memento类的实例，该模式很好理解。 void
	 */
	public void checkMemento() {
		// 创建原始类
		Original origi = new Original("egg");
		// 创建备忘录
		Storage storage = new Storage(origi.createMemento());

		// 修改原始类的状态
		System.out.println("初始化状态为：" + origi.getValue());
		origi.setValue("niu");
		System.out.println("修改后的状态为：" + origi.getValue());

		// 回复原始类的状态
		origi.restoreMemento(storage.getMemento());
		System.out.println("恢复后的状态为：" + origi.getValue());
	}

	/**
	 * 命令模式（Command）
	 * 
	 * void
	 */
	public void checkCommand() {
		Receiver receiver = new Receiver();
		Command cmd = new MyCommand(receiver);
		Invoker invoker = new Invoker(cmd);
		invoker.action("命令模式测试");
	}

	/**
	 * 责任链模式（Chain of Responsibility）
	 * 
	 * void
	 */
	public void checkChainResponsibility() {
		MyHandler h1 = new MyHandler("h1");
		MyHandler h2 = new MyHandler("h2");
		MyHandler h3 = new MyHandler("h3");
		h1.setHandler(h2);
		h2.setHandler(h3);
		h1.operator();
	}

	/**
	 * 迭代子模式（Iterator）
	 * 
	 * void
	 */
	public void checkIterator() {
		com.design.pattern.iterator.Collection collection = new com.design.pattern.iterator.impl.MyCollection();
		com.design.pattern.iterator.Iterator it = collection.iterator();

		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}

	/**
	 * 观察者模式（Observer）
	 * 
	 * @see 简单来讲就一句话：当一个对象变化时，其它依赖该对象的对象都会收到通知，并且随着变化！对象之间是一种一对多的关系。 void
	 */
	public void checkObserver() {
		com.design.pattern.observer.Subject sub = new com.design.pattern.observer.impl.MySubject();
		sub.add(new com.design.pattern.observer.impl.Observer1());
		sub.add(new com.design.pattern.observer.impl.Observer2());
		sub.operation();
	}

	/**
	 * 模板方法模式（Template Method）
	 * 
	 * @see 解释一下模板方法模式，就是指：一个抽象类中，有一个主方法，再定义1...n个方法，可以是抽象的，也可以是实际的方法，定义一个类，
	 *      继承该抽象类，重写抽象方法，通过调用抽象类，实现对子类的调用，先看个关系图 void
	 */
	public void checkTemplateMethod() {
		String exp = "8+8";
		com.design.pattern.template.method.AbstractCalculator cal = new com.design.pattern.template.method.Plus();
		int result = cal.calculate(exp, "\\+");
		System.out.println(result);
	}

	/**
	 * 策略模式（strategy）
	 * 
	 * @see 策略模式定义了一系列算法，并将每个算法封装起来，使他们可以相互替换，且算法的变化不会影响到使用算法的客户。需要设计一个接口，
	 *      为一系列实现类提供统一的方法，多个实现类实现该接口，设计一个抽象类（可有可无，属于辅助类），提供辅助函数，关系图如下：
	 *      策略模式的决定权在用户，系统本身提供不同算法的实现，新增或者删除算法，对各种算法做封装。因此，策略模式多用在算法决策系统中，
	 *      外部用户只需要决定用哪个算法即可。
	 * 
	 *      void
	 */
	public void checkStrategy() {
		String exp = "2+8";
		ICalculator calPlus = new Plus();
		System.out.println(calPlus.calculate(exp));

		String exp1 = "2-8";
		ICalculator calMinus = new Minus();
		System.out.println(calMinus.calculate(exp1));

		String exp2 = "2*8";
		ICalculator calMultiply = new Multiply();
		System.out.println(calMultiply.calculate(exp2));
	}

	/**
	 * 桥接模式（Bridge）
	 * 
	 * @see 桥接模式就是把事物和其具体实现分开，使他们可以各自独立的变化。桥接的用意是：将抽象化与实现化解耦，使得二者可以独立变化，
	 *      像我们常用的JDBC桥DriverManager一样，JDBC进行连接数据库的时候，在各个数据库之间进行切换，基本不需要动太多的代码，
	 *      甚至丝毫不用动， 原因就是JDBC提供统一接口，每个数据库提供各自的实现，用一个叫做数据库驱动的程序来桥接就行了。 void
	 */
	public void checkBridge() {
		Bridge bridge = new MyBridge();

		/* 调用第一个对象 */
		com.design.pattern.bridge.Sourceable source1 = new com.design.pattern.bridge.impl.SourceSub1();
		bridge.setSource(source1);
		bridge.method();

		/* 调用第二个对象 */
		com.design.pattern.bridge.Sourceable source2 = new com.design.pattern.bridge.impl.SourceSub2();
		bridge.setSource(source2);
		bridge.method();
	}

	/**
	 * 外观模式（Facade）
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
		com.design.pattern.decorator.Sourceable obj = new com.design.pattern.decorator.impl.Decorator(
				source);
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
