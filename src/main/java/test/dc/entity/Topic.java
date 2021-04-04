package test.dc.entity;

import java.util.List;

public class Topic {
	private Integer id;
	private String title;
	private String content;
	private List<TestInfo> testing;
	public List<TestInfo> getTesting() {
		return testing;
	}
	public void setTesting(List<TestInfo> testing) {
		this.testing = testing;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
