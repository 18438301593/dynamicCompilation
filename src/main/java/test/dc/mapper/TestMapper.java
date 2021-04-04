package test.dc.mapper;

import java.util.List;

import test.dc.entity.Answer;
import test.dc.entity.Data;
import test.dc.entity.TestInfo;
import test.dc.entity.Topic;

public interface TestMapper {
	public TestInfo getTestInfo();

	public List<Data> getData(Integer id);

	public List<Answer> getAnswer(Integer id);

	public List<TestInfo> selectAllTesting(Integer id);

	public List<Topic> getAllTopic();

	public Topic getTopicInfo(Integer id);
}
