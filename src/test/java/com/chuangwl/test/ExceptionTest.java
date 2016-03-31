package com.chuangwl.test;

import org.junit.Test;

import com.chuangwl.poiext.message.RuntimeExceptionMessage;

public class ExceptionTest {
	
	
	@Test
	public void test1() throws RuntimeExceptionMessage{
		try{
			Integer.parseInt("aas");
		}catch(Exception e){
			throw new RuntimeExceptionMessage("字符串格式化错误 ");
		}
		
	}
	
	@Test
	public void test2(){
		ExceptionTest tt=new ExceptionTest();
		tt.test1();
	}
	
}
