package test.dc.core;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
/**
 * java编译器 和 代码执行方法（反射调用）
 * @author 硅谷探秘者(jia)
 *
 */
public class DcJavaCompiler {
    //类全名
    private String fullClassName;
    private String sourceCode;
    //获取java的编译器
    private static final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    //存放编译过程中输出的信息
    private DiagnosticCollector<JavaFileObject> diagnosticsCollector = new DiagnosticCollector<>();
    
    ByteJavaFileObjectBox byteJavaFileObjectBox=new ByteJavaFileObjectBox();
    
    //执行结果（控制台输出的内容）
    private String runResult;
    //编译耗时(单位ms)
    private long compilerTakeTime;
    //运行耗时(单位ms)
    private long runTakeTime;
    
    public DcJavaCompiler(String sourceCode) {
        this.sourceCode = sourceCode;
        this.fullClassName = getFullClassName(sourceCode);
    }
    
    /**
     * @return 控制台打印的信息
     */
    public String getRunResult() {
        return runResult;
    }

    public long getCompilerTakeTime() {
        return compilerTakeTime;
    }

    public long getRunTakeTime() {
        return runTakeTime;
    }
    
    /**
     * @return 编译信息(错误 警告)
     */
    public String getCompilerMessage() {
        StringBuilder sb = new StringBuilder();
        List<Diagnostic<? extends JavaFileObject>> diagnostics = diagnosticsCollector.getDiagnostics();
        for (Diagnostic<?> diagnostic : diagnostics) {
            sb.append(diagnostic.toString()).append("\r\n");
        }
        return sb.toString();
    }
    
    /**
     * 获取类的全名称
     *
     * @param sourceCode 源码
     * @return 类的全名称
     */
    public static String getFullClassName(String sourceCode) {
        String className = "";
        Pattern pattern = Pattern.compile("package\\s+\\S+\\s*;");
        Matcher matcher = pattern.matcher(sourceCode);
        if (matcher.find()) {
            className = matcher.group().replaceFirst("package", "").replace(";", "").trim() + ".";
        }

        pattern = Pattern.compile("class\\s+\\S+\\s+\\{");
        matcher = pattern.matcher(sourceCode);
        if (matcher.find()) {
            className += matcher.group().replaceFirst("class", "").replace("{", "").trim();
        }
        return className;
    }
    

    
    public boolean compiler() {
        long startTime = System.currentTimeMillis();
        //标准的内容管理器,更换成自己的实现，覆盖部分方法
        StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(diagnosticsCollector, null, null);
        
        
        JavaFileManager javaFileManager = new DcJavaFileManage(standardFileManager,byteJavaFileObjectBox);
        //构造源代码对象
        JavaFileObject javaFileObject = new DcJavaFileObject(fullClassName, sourceCode);
        //获取一个编译任务
        JavaCompiler.CompilationTask task = compiler.getTask(null, javaFileManager, diagnosticsCollector, null, null, Arrays.asList(javaFileObject));
        //设置编译耗时
        compilerTakeTime = System.currentTimeMillis() - startTime;
        return task.call();
    }
    
    
    /**
     * 执行main方法，重定向System.out.print
     */
    public void runMainMethod(ByteArrayOutputStream outputStream,PrintStream printStream) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, UnsupportedEncodingException {
        try {
            long startTime = System.currentTimeMillis();

            DcClassLoader scl = new DcClassLoader(byteJavaFileObjectBox);
            Class<?> aClass = scl.findClass(fullClassName);
            Method main = aClass.getMethod("main", String[].class);
            Object[] pars = new Object[]{1};
            pars[0] = new String[]{};
            main.invoke(null, pars); //调用main方法
            //设置运行耗时
            runTakeTime = System.currentTimeMillis() - startTime;
            //设置打印输出的内容
            runResult = new String(outputStream.toByteArray(), "utf-8");
        } finally {
            //还原默认打印的对象
        }
    }
}
