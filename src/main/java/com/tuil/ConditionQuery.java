package com.tuil;

import java.util.Scanner;

/**
 * @Title: ConditionQuery
 * @Description:
 * @author zhaotf
 * @date 2016年8月25日 下午1:22:30
 * @version V1.0
 *
 */
public class ConditionQuery {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("行数：" + sc);

		int row = sc.nextInt();
		for (int i = 0; i < row; i++) {
			int A = 65;
			for (int j = row - 1; j > 0; j--) {
				System.out.print(" ");
			}
			for (int k = 0; k < 2 * i + 1; k++) {
				if (k < (2 * i + 1) / 2) {
					System.out.print(A++);
				} else {
					System.out.print(A--);
				}
			}
			System.out.println("");
		}

	}


}
