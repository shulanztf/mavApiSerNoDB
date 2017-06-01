package com.design.pattern.builder;

import java.util.ArrayList;
import java.util.List;

import com.design.pattern.abstractFactory.Sender;
import com.design.pattern.abstractFactory.impl.MailSender;
import com.design.pattern.abstractFactory.impl.SmsSender;

/**
 * 
 * @Title: Builder
 * @Description:创建型模式:建造者模式（Builder）
 * @Author: zhaotf
 * @Since:2017年6月1日 下午3:23:21
 * @Version:1.0
 */
public class Builder {
	private List<Sender> list = new ArrayList<Sender>();

	public void produceMailSender(int count) {
		for (int i = 0; i < count; i++) {
			list.add(new MailSender());
		}
	}

	public void produceSmsSender(int count) {
		for (int i = 0; i < count; i++) {
			list.add(new SmsSender());
		}
	}

	public static void main(String[] args) {
		Builder builder = new Builder();
		builder.produceMailSender(10);
	}

}
