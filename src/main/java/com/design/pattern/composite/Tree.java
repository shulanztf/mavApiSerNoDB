package com.design.pattern.composite;

/**
 * 
 * @Title: Tree
 * @Description:结构型模式:组合模式（Composite）
 * @Author: zhaotf
 * @Since:2017年6月2日 上午8:59:49
 * @Version:1.0
 */
public class Tree {
	TreeNode root = null;

	public Tree(String name) {
		root = new TreeNode(name);
	}

	/**
	 * @see 组合模式有时又叫部分-整体模式在处理类似树形结构的问题时比较方便，看看关系图：
	 *      使用场景：将多个对象组合在一起进行操作，常用于表示树形结构中，例如二叉树，数等。
	 * @param args
	 *            void
	 */
	public static void main(String[] args) {
		Tree tree = new Tree("A");
		TreeNode nodeB = new TreeNode("B");
		TreeNode nodeC = new TreeNode("C");

		nodeB.add(nodeC);
		tree.root.add(nodeB);
		System.out.println("build the tree finished!结构型模式:组合模式（Composite） 树节点");
	}
}
