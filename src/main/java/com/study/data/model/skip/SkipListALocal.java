package com.study.data.model.skip;

import java.util.Comparator;
import java.util.Random;

/**
 * 
 * @param <T>
 * @Title: SkipListALocal
 * @Description:JAVA SkipList 跳表 的原理
 * @see http://blog.csdn.net/sun_ru/article/details/51917273
 * @Author: zhaotf
 * @Since:2017年10月19日 下午5:53:04
 * @Version:1.0
 */
public class SkipListALocal<K, V> {

	public static void main(String[] args) {
		SkipListALocal<String, Object> sla = new SkipListALocal<String, Object>();
		for (int i = 0; i < 40; i++) {
			sla.add(String.valueOf(i), "val" + i);
		}
		sla.pring();
		System.out.println("-------------------------------------------");
		String key = String.valueOf(sla.rand.nextInt(40));
		if (sla.find(key) != null) {
			System.out.println("\nOK");
		} else {// 找不到
			System.out.println("\nfalse");
		}
		System.out.println("-------------------------------------------");

	}

	private SkipNode<K, V> head;// 头节点
	private SkipNode<K, V> tail;// 尾结点
	private int level = 0;// 层数
	private int size = 0;// 元素个数
	private Random rand;// 每次的随机数用来确定需不需要增加层数
	private Comparator<? super K> comparator;

	public SkipListALocal() {
		this.head = new SkipNode(SkipNode.tou, SkipNode.tou);
		this.tail = new SkipNode(SkipNode.wei, SkipNode.wei);
		head.right = tail;
		tail.left = head;
		this.rand = new Random();
	}

	/**
	 * 添加元素
	 * 
	 * @param key
	 * @param val
	 * @return T
	 */
	public V add(K key, V val) {
		if (val == null) {
			throw new NullPointerException();
		}
		SkipNode<K, V> temp = findFull(key);// 基准节点
		// KEY相同时,覆盖旧值,并将旧值返回
		if (temp.key.equals(key)) {
			V v = temp.value;
			temp.value = val;// 覆盖旧值
			return v;
		}
		SkipNode<K, V> nNode = new SkipNode<K, V>(key, val);
		nNode.left = temp;
		nNode.right = temp.right;
		temp.right.left = nNode;
		temp.right = nNode;

		int lev = 0;// 从根层向上 设置用
		SkipNode<K, V> sn1, sn2;
		// 随机，是否将新节点，添加到上层
		while (rand.nextDouble() < 0.5) {
			// 若当前层数超出了高度，则需要另建一层，并进入上一层
			if (lev >= level) {
				level++;
				sn1 = new SkipNode(SkipNode.tou, null);// 头节点
				sn2 = new SkipNode(SkipNode.wei, null);// 尾节点
				sn1.right = sn2;
				sn1.down = head;
				sn2.left = sn1;
				sn2.down = tail;
				head.up = sn1;
				tail.up = sn2;
				head = sn1;
				tail = sn2;
			}
			// 换成上一层的基准节点
			while (temp.up == null) {
				temp = temp.left;
			}

			temp = temp.up;
			SkipNode<K, V> node = new SkipNode<K, V>(key, val);// 注意上下左右设置顺序
			node.left = temp;
			node.right = temp.right;
			node.down = nNode;
			temp.right.left = node;
			temp.right = node;
			nNode.up = node;

			nNode = node;
			lev++;
		}
		this.size++;
		return null;
	}

	private Comparable<? super K> comparable(Object key) throws ClassCastException {
		if (key == null)
			throw new NullPointerException();
		if (comparator != null)
			return new ComparableUsingComparator<K>((K) key, comparator);
		else
			return (Comparable<? super K>) key;
	}

	/**
	 * 快速节点查找
	 * 
	 * @param key
	 *            查找对象
	 * @return SkipNode<T>
	 */
	public SkipNode<K, V> find(String key) {
		System.out.println("查找路线:" + key);
		SkipNode<K, V> node = head;
		int count = 0;// 查找复杂度
		int index = 0;// 层次
		while (true) {
			index++;
			count++;
			System.out.print("第" + index + "层,");
			// 从左向右找
			while (node.right.key != SkipNode.wei && node.right.key.compareTo(key) <= 0) {
				count++;
				// 替换到右节点,直到尾节点
				node = node.right;
				System.out.print("--->" + node.key);
			}
			// 找到时
			if (node.key.equals(key)) {
				System.out.println("\n找到--[" + node.key + ":" + node.value + "],复杂度:" + count);
				return node;
			}

			// 未找到时
			if (node.down != null) {
				// 进入下层
				node = node.down;
				System.out.print("-->下层:" + node.key);
				System.out.println();
			} else {
				// 无下一层
				return null;
			}
		}
	}

	/**
	 * 找到需要插入位置的前一个节点，节点深层查找
	 * 
	 * @param key
	 * @return SkipNode 目标节点，或者，头节点
	 */
	public SkipNode<K, V> findFull(K key) {
		SkipNode<K, V> node = head;
		while (true) {
			// 从左向右找
			while (node.right.key != SkipNode.wei && node.right.key.compareTo(key) <= 0) {
				// 当链表最底层不为空的时候，从当前层向尾部方向开始查找，直到查找temp.getRight的下一个值大于
				// 当前k的值为止，此时temp小于或等于当前k的值 要插入的位置即为temp之后的位置了
				node = node.right;
			}
			if (node.down != null) {
				node = node.down;
			} else {
				break;
			}
		}
		return node;
	}

	/**
	 * 判断是否为空
	 * 
	 * @return boolean
	 */
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 节点删除,调用查找函数，删除最底层的某个节点，并把其节点的左右相连，和链表操作一样，只是其上方若有则都需要调整
	 * 
	 * @param key
	 * @return T
	 */
	public SkipNode<K, V> delNode(K key) {
		SkipNode<K, V> node = findFull(key);
		// TODO while->if 此处理问题
		while (node != null) {
			node.left.right = node.right;
			node.right.left = node.left;
			node = node.up;
		}
		return node;
	}

	/**
	 * 打印
	 * 
	 * void
	 */
	public void pring() {
		SkipNode<K, V> node;
		SkipNode<K, V> node1 = this.head;
		int lev = 0;// 层号
		while (node1 != null) {
			lev++;
			int count = 0;// 查找次数
			node = node1;
			System.out.print("第" + lev + "层,");
			// 从左向右查找
			while (node != null) {
				count++;
				System.out.print(node.key + "-");
				node = node.right;
			}
			System.out.print("(节点数量:" + count + ")");
			System.out.println();
			node1 = node1.down;// 进入下一层
		}

	}

	/**
	 * 
	 * @Title: SkipNode
	 * @Description:
	 * @Author: zhaotf
	 * @Since:2017年10月19日 下午5:58:18
	 * @Version:1.0
	 */
	@SuppressWarnings("hiding")
	class SkipNode<K, V> {
		private K key;// 位置
		private V value;// 内容
		private SkipNode<K, V> up, down, left, right;// 上/下/左/右
		public static final String tou = "【头】";// 头节点位置
		public static final String wei = "【尾】";// 尾节点位置

		public SkipNode() {
		}

		public SkipNode(K key, V val) {
			this.key = key;
			this.value = val;
		}

		// public static final int HEAD_KEY = Integer.MIN_VALUE; // 负无穷
		// public static final int TAIL_KEY = Integer.MAX_VALUE; // 正无穷
		// public String getKey() {
		// return key;
		// }
		//
		// public void setKey(String key) {
		// this.key = key;
		// }
		//
		// public T getValue() {
		// return value;
		// }
		//
		// public void setValue(T value) {
		// this.value = value;
		// }
		//
		// public SkipNode<T> getUp() {
		// return up;
		// }
		//
		// public void setUp(SkipNode<T> up) {
		// this.up = up;
		// }
		//
		// public SkipNode<T> getDown() {
		// return down;
		// }
		//
		// public void setDown(SkipNode<T> down) {
		// this.down = down;
		// }
		//
		// public SkipNode<T> getLeft() {
		// return left;
		// }
		//
		// public void setLeft(SkipNode<T> left) {
		// this.left = left;
		// }
		//
		// public SkipNode<T> getRight() {
		// return right;
		// }
		//
		// public void setRight(SkipNode<T> right) {
		// this.right = right;
		// }

	}
}
