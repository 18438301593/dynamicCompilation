package test.dc.run;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import test.dc.core.DcJavaCompiler;
import test.dc.core.MyInputStream;
import test.dc.entity.Data;
import test.dc.entity.RunMessage;
import test.dc.entity.TestInfo;
import test.dc.entity.Topic;
/**
 * 执行编译和运行代码
 * @author 硅谷探秘者(jia)
 *
 */
public class CustomCallable implements Callable<RunMessage> {
    private String sourceCode;
    
    private Topic topic;

    public CustomCallable(String sourceCode,Topic topic) {
        this.sourceCode = sourceCode;
        this.topic=topic;
    }
    @Override
    public RunMessage call() throws Exception {
    	RunMessage rm=new RunMessage();
    	DcJavaCompiler compiler = new DcJavaCompiler(sourceCode);
    	//执行代码编译
    	if (compiler.compiler()) {
    		//编译成功
    		rm.setCompilerSuccess(true);
    		rm.setCompilerMessage(compiler.getCompilerMessage());
    	}else {
    		//编译失败
    		rm.setCompilerSuccess(false);
    		rm.setCompilerMessage(compiler.getCompilerMessage());
    		return rm;
    	}
    	
    	/**
    	 * 	保存编译信息
    	 */
    	rm.setCompilerTakeTime(compiler.getCompilerTakeTime());
        rm.setCompilerMessage(compiler.getCompilerMessage());
    	
    	List<RunInfo> listruninfo=new ArrayList<RunInfo>();
    	System.out.println(topic.getTesting());
    	/**
    	 * 	遍历所有的  测试信息
    	 */
    	for(TestInfo testing:topic.getTesting()) {
    		/*** 创建测试环境/数据 ***/
    		String testMsg=createTestMsg(testing);
    		
    		RunInfo runInfo = new RunInfo();
    		
    		runInfo.setTestingId(testing.getId());
    		/**
    		 * 保存先前的输入输出状态
    		 */
    		InputStream in = System.in;
        	PrintStream out = System.out;
        	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            
            /**
    		 * 	输入输出重定向（重定向一个string字符串，由数据库提供测试数据）
    		 */
            MyInputStream fis=new MyInputStream(testMsg);
            System.setIn(fis);
            System.setOut(printStream);
            /**
             * 	开启线程执行代码
             */
            Thread t1 = new Thread(() -> realCall(runInfo,outputStream,printStream,compiler));
            t1.start();
            try {
            	//等待1秒
                t1.join(1000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //停止线程
            t1.stop();
            
            //恢复输入输出
            System.setIn(in);
            System.setOut(out);
            
            outputStream.close();
            printStream.close();
            fis.close();
    		listruninfo.add(runInfo);
    	}
    	rm.setList(listruninfo);
        //不管有没有正常执行完成，强制停止t1
        return rm;
    }

    private void realCall(RunInfo runInfo,ByteArrayOutputStream outputStream,PrintStream printStream,DcJavaCompiler compiler) {
        try {
            compiler.runMainMethod(outputStream,printStream);
            runInfo.setRunSuccess(true);
            runInfo.setRunTakeTime(compiler.getRunTakeTime());
            runInfo.setRunMessage(compiler.getRunResult()); //获取运行的时候输出内容
        } catch (InvocationTargetException e) {
            //反射调用异常了,是因为超时的线程被强制stop了
            if ("java.lang.ThreadDeath".equalsIgnoreCase(e.getCause().toString())) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            runInfo.setRunSuccess(false);
            runInfo.setRunMessage(e.getMessage());
        }
        runInfo.setTimeOut(false); //走到这一步代表没有超时
    }
    
    /**
     * 	创建测试数据文件
     * @param tf
     * @param file
     * @throws IOException
     */
    public static void createTestFile(TestInfo tf,File file) throws IOException {
    	StringBuilder b=new StringBuilder();
    	//拼接测试数据
    	for(Data s:tf.getData()) {
			b.append(s.getData()).append("\r\n");
		}
    	saveDataToFile(file,b.toString());
    }
    
    /**
     * 	创建测试数据数据（输入重定向）
     * @param tf
     * @param file
     * @throws IOException
     */
    public static String createTestMsg(TestInfo tf) throws IOException {
    	StringBuilder b=new StringBuilder();
    	//拼接测试数据
    	for(Data s:tf.getData()) {
			b.append(s.getData()).append("\r\n");
		}
    	return b.toString();
    }
    
    
    /**
     * 	输入文件
     * @param file
     * @param data
     * @throws IOException
     */
    public static void saveDataToFile(File file,String data) throws IOException{
        // 创建文件
        file.createNewFile();
        FileOutputStream writer = new FileOutputStream(file);
        // 向文件写入内容
        writer.write(data.getBytes());
        writer.flush();
        writer.close();
	 }
}