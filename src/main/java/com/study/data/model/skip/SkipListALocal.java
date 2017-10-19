package com.study.data.model.skip;

/**
 * 
 * @Title: SkipListALocal
 * @Description:JAVA SkipList 跳表 的原理
 * @see http://blog.csdn.net/sun_ru/article/details/51917273
 * @Author: zhaotf
 * @Since:2017年10月19日 下午5:53:04
 * @Version:1.0
 */
public class SkipListALocal {

	/**
	 * 
	 * @Title: SkipNode
	 * @Description:
	 * @Author: zhaotf
	 * @Since:2017年10月19日 下午5:58:18
	 * @Version:1.0
	 */
	class SkipNode<T> {
		public String key;// 位置
		public T value;// 内容
		public SkipNode<T> up, down, left, right;// 上/下/左/右
		public static final String tou = "【头】";// 头节点位置
		public static final String wei = "【尾】";// 尾节点位置

		// public static final int HEAD_KEY = Integer.MIN_VALUE; // 负无穷
		// public static final int TAIL_KEY = Integer.MAX_VALUE; // 正无穷
		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public T getValue() {
			return value;
		}

		public void setValue(T value) {
			this.value = value;
		}

		public SkipNode<T> getUp() {
			return up;
		}

		public void setUp(SkipNode<T> up) {
			this.up = up;
		}

		public SkipNode<T> getDown() {
			return down;
		}

		public void setDown(SkipNode<T> down) {
			this.down = down;
		}

		public SkipNode<T> getLeft() {
			return left;
		}

		public void setLeft(SkipNode<T> left) {
			this.left = left;
		}

		public SkipNode<T> getRight() {
			return right;
		}

		public void setRight(SkipNode<T> right) {
			this.right = right;
		}

	}
}
