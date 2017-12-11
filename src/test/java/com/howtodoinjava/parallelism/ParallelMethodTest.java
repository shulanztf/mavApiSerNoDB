package com.howtodoinjava.parallelism;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @Title: ParallelMethodTest
 * @Description:TestNG系列教程：并行执行测试
 * @Author: zhaotf
 * @Since:2017年12月4日 下午1:31:12
 * @see {@link http://www.importnew.com/14508.html}
 */
public class ParallelMethodTest {
	@BeforeMethod
	public void beforeMethod() {
		long id = Thread.currentThread().getId();
		System.out.println("Before test-method. Thread id is: " + id);
	}

	@Test
	public void testMethodsOne() {
		long id = Thread.currentThread().getId();
		System.out.println("Simple test-method One. Thread id is: " + id);
	}

	@Test
	public void testMethodsTwo() {
		long id = Thread.currentThread().getId();
		System.out.println("Simple test-method Two. Thread id is: " + id);
	}

	@AfterMethod
	public void afterMethod() {
		long id = Thread.currentThread().getId();
		System.out.println("After test-method. Thread id is: " + id);
	}
}
