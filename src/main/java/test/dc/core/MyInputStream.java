package test.dc.core;

import java.io.IOException;
import java.io.InputStream;

/**
 * 	自定义输入流 （从字符串中读取）
 * @author 硅谷探秘者(jia)
 *
 */
public class MyInputStream extends InputStream{
	private String s=null;
	private int location=0;
	private int length;
	public MyInputStream(final String s) {
		this.s=s;
		length=s.length();
	}
	@Override
	public int read() throws IOException {
		// TODO Auto-generated method stub
		if(location>=length) {
			return -1;
		}
		return s.charAt(location++);
	}
}