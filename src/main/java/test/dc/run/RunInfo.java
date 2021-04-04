package test.dc.run;
/**
 * 储存代码运行后的信息
 * @author 硅谷探秘者(jia)
 *
 */
public class RunInfo {
    private Boolean timeOut=true;

    private Long runTakeTime;
    private String runMessage;
    private Boolean runSuccess;
    
    private Integer testingId;
    
    private String result;
    
	public Boolean getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(Boolean timeOut) {
		this.timeOut = timeOut;
	}
	public Long getRunTakeTime() {
		return runTakeTime;
	}
	public void setRunTakeTime(Long runTakeTime) {
		this.runTakeTime = runTakeTime;
	}
	public String getRunMessage() {
		return runMessage;
	}
	public void setRunMessage(String runMessage) {
		this.runMessage = runMessage;
	}
	public Boolean getRunSuccess() {
		return runSuccess;
	}
	public void setRunSuccess(Boolean runSuccess) {
		this.runSuccess = runSuccess;
	}
	@Override
	public String toString() {
		return "RunInfo [timeOut=" + timeOut + ", runTakeTime=" + runTakeTime + ", runMessage=" + runMessage
				+ ", runSuccess=" + runSuccess + "]";
	}
    //省略get和set方法
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Integer getTestingId() {
		return testingId;
	}
	public void setTestingId(Integer testingId) {
		this.testingId = testingId;
	}
}