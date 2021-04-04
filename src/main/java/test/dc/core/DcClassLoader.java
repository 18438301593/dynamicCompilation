package test.dc.core;
/**
 * 自定义类加载器  加载  编译成功后的class
 * @author 硅谷探秘者(jia)
 *
 */
public class DcClassLoader extends ClassLoader {
	ByteJavaFileObjectBox b = null;
	public DcClassLoader(ByteJavaFileObjectBox b) {
		this.b=b;
	}
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
    	ByteJavaFileObject fileObject = b.get(name);
        if (fileObject != null) {
            byte[] bytes = fileObject.getCompiledBytes();
            return defineClass(name, bytes, 0, bytes.length);
        }
        try {
            return ClassLoader.getSystemClassLoader().loadClass(name);
        } catch (Exception e) {
            return super.findClass(name);
        }
    }
}