package test.dc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.dc.entity.Answer;
import test.dc.entity.Data;
import test.dc.entity.TestInfo;
import test.dc.entity.Topic;
import test.dc.mapper.TestMapper;

@Service
public class TestService {
	
	@Autowired 
	private TestMapper testMapper; 
	
	public Topic getTestInfo(Integer id) {
		Topic tp=new Topic();//题
		
		List<TestInfo> tlist = testMapper.selectAllTesting(id);
		
		for(TestInfo ti:tlist) {
			List<Data> data =testMapper.getData(ti.getId());
			List<Answer> answer=testMapper.getAnswer(ti.getId());
			ti.setAnswer(answer);
			ti.setData(data);
		}
		
		tp.setTesting(tlist);
		return tp;
	}

	public List<Data> getData(Integer id) {
		// TODO Auto-generated method stub
		return testMapper.getData(id);
	}

	public List<Answer> getAnswer(Integer id) {
		// TODO Auto-generated method stub
		return testMapper.getAnswer(id);
	}

	public List<Topic> getAllTopic() {
		// TODO Auto-generated method stub
		return testMapper.getAllTopic();
	}

	public Topic getTopicInfo(Integer id) {
		// TODO Auto-generated method stub
		return testMapper.getTopicInfo(id);
	}
	
}
