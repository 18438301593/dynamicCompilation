package test.dc.run;
import test.dc.entity.RunMessage;
import test.dc.entity.Topic;
/**
 * @author 硅谷探秘者(jia)
 */
public class CompilerUtil {
    //这里用一个线程是因为防止System.out输出内容错乱

    public static RunMessage getRunInfo(String javaSourceCode,Topic t) {
    	RunMessage runInfo = null;
        CustomCallable compilerAndRun = new CustomCallable(javaSourceCode,t);
        try {
			runInfo = compilerAndRun.call();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return runInfo;
    }
}