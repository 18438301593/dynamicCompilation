package test.dc.core;

import java.io.IOException;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/**
 * 自定义一个字符串的源码对象
 */
public class DcJavaFileObject extends SimpleJavaFileObject {
    //等待编译的源码字段
    private String contents;

    //java源代码 => DcFileObject对象 的时候使用
    public DcJavaFileObject(String className, String contents) {
        super(URI.create("string:///" + className.replaceAll("\\.", "/") + Kind.SOURCE.extension), Kind.SOURCE);
        this.contents = contents;
    }

    //字符串源码会调用该方法
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return contents;
    }

}