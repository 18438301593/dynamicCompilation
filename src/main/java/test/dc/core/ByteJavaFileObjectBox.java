package test.dc.core;

import java.util.HashMap;
import java.util.Map;

public class ByteJavaFileObjectBox {
	private Map<String,ByteJavaFileObject> byteJavaFileObjectMap = new HashMap<String, ByteJavaFileObject>();

	public Map<String,ByteJavaFileObject> getByteJavaFileObject() {
		return byteJavaFileObjectMap;
	}
	
	public void setByteJavaFileObject(String name,ByteJavaFileObject byteJavaFileObject) {
		byteJavaFileObjectMap.put(name, byteJavaFileObject);
	}
	
	public ByteJavaFileObject get(String className) {
		return byteJavaFileObjectMap.get(className);
	}
}
