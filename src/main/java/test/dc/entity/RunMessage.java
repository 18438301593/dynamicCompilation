package test.dc.entity;

import java.util.List;

import test.dc.run.RunInfo;

public class RunMessage {
	private boolean compilerSuccess;
	private List<RunInfo> list;
    private Long compilerTakeTime;
    private String compilerMessage;
	public boolean isCompilerSuccess() {
		return compilerSuccess;
	}
	public void setCompilerSuccess(boolean compilerSuccess) {
		this.compilerSuccess = compilerSuccess;
	}
	public List<RunInfo> getList() {
		return list;
	}
	public void setList(List<RunInfo> list) {
		this.list = list;
	}
	public Long getCompilerTakeTime() {
		return compilerTakeTime;
	}
	public void setCompilerTakeTime(Long compilerTakeTime) {
		this.compilerTakeTime = compilerTakeTime;
	}
	public String getCompilerMessage() {
		return compilerMessage;
	}
	public void setCompilerMessage(String compilerMessage) {
		this.compilerMessage = compilerMessage;
	}
}
