package com.study.data.model.skip;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * @Title: SkipList
 * @Description:
 * @see http://www.cnblogs.com/acfox/p/3688607.html
 * @Author: zhaotf
 * @Since:2017年10月18日 下午5:13:56
 * @Version:1.0
 */
public class SkipList<T> {

	public static void main(String[] args) {
		SkipList<String> list = new SkipList<String>();
		ExecutorService es = Executors.newCachedThreadPool();
		AtomicLong al = new AtomicLong(System.currentTimeMillis());
		for (int i = 0; i < 5; i++) {
			es.execute(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < 4; i++) {
						int ind = Integer.valueOf(Thread.currentThread().getId() + "000" + i);
						String text = Thread.currentThread().getId() + ":aaaa:" + al.getAndIncrement();
						list.put(ind, text);
						System.out.println("添加:" + text);
					}
				}
			});
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(list.size() + "," + list);
		es.shutdown();
	}

	private SkipListNode<T> head;
	private SkipListNode<T> tail;
	private int nodes;// 节点总数
	private int listLevel;// 层数
	private Random random;// 用于投掷硬币
	private static final double PROBABILITY = 0.5;// 向上提升一个的概率

	public SkipList() {
		random = new Random();
		clear();
	}

	/**
	 * 清空跳跃表
	 */
	public void clear() {
		head = new SkipListNode<T>(SkipListNode.HEAD_KEY, null);
		tail = new SkipListNode<T>(SkipListNode.TAIL_KEY, null);
		horizontalLink(head, tail);
		listLevel = 0;
		nodes = 0;
	}

	public boolean isEmpty() {
		return nodes == 0;
	}

	public int size() {
		return nodes;
	}

	/**
	 * 在最下面一层，找到要插入的位置前面的那个key
	 */
	private SkipListNode<T> findNode(int key) {
		SkipListNode<T> p = head;
		while (true) {
			while (p.right.key != SkipListNode.TAIL_KEY && p.right.key <= key) {
				p = p.right;
			}
			if (p.down != null) {
				p = p.down;
			} else {
				break;
			}

		}
		return p;
	}

	/**
	 * 查找是否存储key，存在则返回该节点，否则返回null
	 */
	public SkipListNode<T> search(int key) {
		SkipListNode<T> p = findNode(key);
		if (key == p.getKey()) {
			return p;
		} else {
			return null;
		}
	}

	/**
	 * 向跳跃表中添加key-value
	 * 
	 */
	public void put(int key, T val) {
		SkipListNode<T> p = findNode(key);
		// 如果key值相同，替换原来的vaule即可结束
		if (key == p.getKey()) {
			p.value = val;
			return;
		}
		SkipListNode<T> q = new SkipListNode<T>(key, val);
		backLink(p, q);
		int currentLevel = 0;// 当前所在的层级是0
		// 抛硬币
		while (random.nextDouble() < PROBABILITY) {
			// 如果超出了高度，需要重新建一个顶层
			if (currentLevel >= listLevel) {
				listLevel++;
				SkipListNode<T> p1 = new SkipListNode<T>(SkipListNode.HEAD_KEY, null);
				SkipListNode<T> p2 = new SkipListNode<T>(SkipListNode.TAIL_KEY, null);
				horizontalLink(p1, p2);
				vertiacallLink(p1, head);
				vertiacallLink(p2, tail);
				head = p1;
				tail = p2;
			}
			// 将p移动到上一层
			while (p.up == null) {
				p = p.left;
			}
			p = p.up;

			SkipListNode<T> e = new SkipListNode<T>(key, null);// 只保存key就ok
			backLink(p, e);// 将e插入到p的后面
			vertiacallLink(e, q);// 将e和q上下连接
			q = e;
			currentLevel++;
		}
		nodes++;// 层数递增
	}

	// node1后面插入node2
	private void backLink(SkipListNode<T> node1, SkipListNode<T> node2) {
		node2.left = node1;
		node2.right = node1.right;
		node1.right.left = node2;
		node1.right = node2;
	}

	/**
	 * 水平双向连接
	 */
	private void horizontalLink(SkipListNode<T> node1, SkipListNode<T> node2) {
		node1.right = node2;
		node2.left = node1;
	}

	/**
	 * 垂直双向连接
	 */
	private void vertiacallLink(SkipListNode<T> node1, SkipListNode<T> node2) {
		node1.down = node2;
		node2.up = node1;
	}

	/**
	 * 打印出原始数据
	 */
	@Override
	public String toString() {
		if (isEmpty()) {
			return "跳跃表为空！";
		}
		StringBuilder builder = new StringBuilder();
		SkipListNode<T> p = head;
		while (p.down != null) {
			p = p.down;
		}

		while (p.left != null) {
			p = p.left;
		}
		if (p.right != null) {
			p = p.right;
		}
		while (p.right != null) {
			builder.append(p);
			builder.append("\n");
			p = p.right;
		}

		return builder.toString();
	}

	/**
	 * 跳跃表的节点,包括key-value和上下左右4个指针 created by 曹艳丰，2016-08-14
	 * 参考：http://www.acmerblog.com/skip-list-impl-java-5773.html
	 */
	public class SkipListNode<T> {
		public int key;
		public T value;
		public SkipListNode<T> up, down, left, right; // 上下左右 四个指针

		public static final int HEAD_KEY = Integer.MIN_VALUE; // 负无穷
		public static final int TAIL_KEY = Integer.MAX_VALUE; // 正无穷

		public SkipListNode(int k, T v) {
			key = k;
			value = v;
		}

		public int getKey() {
			return key;
		}

		public void setKey(int key) {
			this.key = key;
		}

		public T getValue() {
			return value;
		}

		public void setValue(T value) {
			this.value = value;
		}

		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null) {
				return false;
			}
			if (!(o instanceof SkipListNode)) {
				return false;
			}
			SkipListNode<T> ent;
			try {
				ent = (SkipListNode<T>) o; // 检测类型
			} catch (ClassCastException ex) {
				return false;
			}
			return (ent.getKey() == key) && (ent.getValue() == value);
		}

		@Override
		public String toString() {
			return "key-value:" + key + "-" + value;
		}
	}

}
