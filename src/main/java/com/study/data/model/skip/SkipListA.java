package com.study.data.model.skip;

import java.util.Random;

/**
 * 
 * @Title: SkipListA
 * @Description:java 实现跳跃表
 * @see http://blog.csdn.net/sun_ru/article/details/51917273
 * @Author: zhaotf
 * @Since:2017年10月19日 上午10:41:37
 * @Version:1.0
 */
public class SkipListA {

	public static void main(String[] args) {
		SkipListA sla = new SkipListA();
		for (int i = 0; i < 30; i++) { // 随机数字进行测试
			sla.add(String.valueOf(i), i);
		}
		sla.print();

		System.out.println("-------------------------------------------");

		String serch = String.valueOf(sla.rand.nextInt(30));
		if (sla.find(serch) != null) { // 查找
			System.out.println("\nOK");
		} else {// 找不到
			System.out.println("\nfalse");
		}

		System.out.println("-------------------------------------------");

		sla.delNode(serch); // 删除
		sla.print();
	}

	public Node head; // 头节点
	public Node tail; // 尾结点
	public int level; // 层数
	public int size; // 元素个数
	public Random rand; // 每次的随机数用来确定需不需要增加层数

	public SkipListA() {
		head = new Node(Node.tou, 0);
		tail = new Node(Node.wei, 0);
		head.setRight(tail);
		tail.setLeft(head);
		level = 0;
		size = 0;
		rand = new Random();
	}

	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 添加元素
	 * 
	 * @param key
	 * @param val
	 * @return int
	 */
	public int add(String key, int val) {
		Node temp;
		temp = findFull(key);
		if (key.equals(temp.getKey())) {
			System.out.println("对象属性完全相同无法添加！");
			int a = temp.value;
			temp.value = val;
			return a;
		}
		Node temp1 = new Node(key, val);
		temp1.setLeft(temp);
		temp1.setRight(temp.getRight());
		temp.getRight().setLeft(temp1);
		temp.setRight(temp1);
		int lev = 0; // 当前层数

		Node p1, p2;
		while (rand.nextDouble() < 0.5) { // 进行随机，是否需要 在上层添加
			if (lev >= level) { // 若当前层数超出了高度，则需要另建一层
				level = level + 1;
				p1 = new Node(Node.tou, 0);
				p2 = new Node(Node.wei, 0);

				p1.setRight(p2);
				p1.setDown(head);

				p2.setLeft(p1);
				p2.setDown(tail);

				head.setUp(p1);
				tail.setUp(p2);

				head = p1;
				tail = p2;
			}
			while (temp.getUp() == null) {
				// TODO ?
				temp = temp.getLeft();
			}
			temp = temp.getUp();
			Node node = new Node(key, val);
			node.setLeft(temp);
			node.setRight(temp.getRight());
			node.setDown(temp1);

			temp.getRight().setLeft(node);
			temp.setRight(node);
			temp1.setUp(node);

			temp1 = node;
			lev = lev + 1;

		}

		size = size + 1;
		return 0;
	}

	/**
	 * 节点快速查找
	 */
	public Node find(String key) {
		System.out.println("查找路线:" + key); // 用于测试
		Node node = head;
		int ind = 0;// 层次
		while (true) {
			ind++;
			System.out.print("第" + ind + "层,");
			// 当前节点的右节点不是尾节点,并且,不比key大
			while (node.getRight().key != Node.wei && node.getRight().getKey().compareTo(key) <= 0) {// &&node.getRight().getValue()!=v
				// 替换到右节点,直到尾节点
				node = node.getRight();
				System.out.print("--->" + node.getKey());
			}

			// 找到时
			if (node.key.equals(key)) {
				System.out.println("---[" + node.getKey() + "]");
				System.out.print("==>" + node.getValue());
				return node;
			}

			// 未找到时
			if (node.getDown() != null) {
				// 进入下层
				node = node.getDown();
				System.out.print("---|" + node.getKey());
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
	 * @return Node
	 */
	public Node findFull(String key) {
		Node temp = head;
		while (true) {
			// 当前节点的右节点,不是尾节点,并且,不能比key大
			while (temp.getRight().key != Node.wei && temp.getRight().key.compareTo(key) <= 0) {
				/*
				 * 当链表最底层不为空的时候，从当前层向尾部方向开始查找，直到查找temp.getRight的下一个值大于
				 * 当前k的值为止，此时temp小于或等于当前k的值 要插入的位置即为temp之后的位置了
				 */
				temp = temp.getRight();
			}
			if (temp.getDown() != null) {
				// 进入下一层
				temp = temp.getDown();
			} else {
				break;
			}
		}
		return temp; // 找到节点并返回
	}

	/**
	 * 节点删除,调用查找函数，删除最底层的某个节点，并把其节点的左右相连，和链表操作一样，只是其上方若有则都需要调整
	 * 
	 * @param k
	 */
	public void delNode(String k) {
		Node temp = findFull(k);
		while (temp != null) {
			temp.getLeft().setRight(temp.getRight());
			temp.getRight().setLeft(temp.getLeft());
			temp = temp.getUp();
		}
	}

	public void print() {
		Node node;
		Node node1 = head;
		int level = 0;

		while (node1 != null) {
			level++;
			int k = 0;
			node = node1;
			System.out.print("第" + level + "层,");
			while (node != null) {
				System.out.print(node.getKey() + "-");
				k++;
				node = node.getRight();
			}

			System.out.print("(节点数量:" + k + ")");
			System.out.println();
			node1 = node1.getDown();// 指向下一个
		}
	}

}

/**
 * 
 * @Description:
 */
class Node {
	public String key;// 位置
	public int value;// 内容
	public Node up, down, left, right;// 上/下/左/右
	public static String tou = "【头】";
	public static String wei = "【尾】";

	public Node(String key, int val) {
		this.key = key;
		this.value = val;
		up = down = left = right = null;
	}

	public void setUp(Node up) {
		this.up = up;
	}

	public Node getUp() {
		return up;
	}

	public void setDown(Node down) {
		this.down = down;
	}

	public Node getDown() {
		return down;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getLeft() {
		return left;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public Node getRight() {
		return right;
	}

	public void setKey(String k) {
		this.key = k;
	}

	public String getKey() {
		return key;
	}

	public void setValue(int v) {
		this.value = v;
	}

	public int getValue() {
		return value;
	}

}
