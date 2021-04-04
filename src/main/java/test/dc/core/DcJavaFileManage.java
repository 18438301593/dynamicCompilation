package test.dc.core;

import java.io.IOException;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
@SuppressWarnings("rawtypes")
public class DcJavaFileManage extends ForwardingJavaFileManager {
	
	private ByteJavaFileObjectBox byteJavaFileObjectBox;
	
	@SuppressWarnings("unchecked")
	DcJavaFileManage(JavaFileManager fileManager,ByteJavaFileObjectBox byteJavaFileObjectBox) {
        super(fileManager);
        this.byteJavaFileObjectBox=byteJavaFileObjectBox;
    }
    //获取输出的文件对象，它表示给定位置处指定类型的指定类。
    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        ByteJavaFileObject javaFileObject = new ByteJavaFileObject(className, kind);
        this.byteJavaFileObjectBox.setByteJavaFileObject(className,javaFileObject);
        return javaFileObject;
    }
}