package test.dc.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import test.dc.entity.AjaxResult;
import test.dc.entity.Answer;
import test.dc.entity.Data;
import test.dc.entity.RunMessage;
import test.dc.entity.TestInfo;
import test.dc.entity.Topic;
import test.dc.run.CompilerUtil;
import test.dc.run.RunInfo;
import test.dc.service.DownloadService;
import test.dc.service.TestService;

@Controller
@Scope("singleton")
public class TestController {
	
	@Autowired
	private TestService testService;

	@RequestMapping("/")
	public ModelAndView index(Integer id) {
		return page(id);
	}
	
	@RequestMapping("page")
	public ModelAndView page(Integer id) {
		if(id==null) {
			id=1;
		}
		ModelAndView mv=new ModelAndView("pages");
		List<Topic> ts=testService.getAllTopic();
		Topic t = testService.getTopicInfo(id);
		mv.addObject("ts",ts);
		mv.addObject("t", t);
		return mv;
	}
	
	@RequestMapping("/download/testing/{id}")
	public synchronized void downloadt(HttpServletResponse response,HttpServletRequest request,@PathVariable Integer id) {
		if(id==null) {
			return;
		}
		List<Data> data=testService.getData(id);
		String d=createTestData(data);
		DownloadService down=new DownloadService();
		down.download(response, request, d.getBytes(),"test");
	}
	
	@RequestMapping("/download/answer/{id}")
	public synchronized void downloada(HttpServletResponse response,HttpServletRequest request,@PathVariable Integer id) {
		if(id==null) {
			return;
		}
		List<Answer> data=testService.getAnswer(id);
		String d=createTestAnswer(data);
		DownloadService down=new DownloadService();
		down.download(response, request, d.getBytes(),"result");
	}
	
	@ResponseBody
	@RequestMapping("execute")
	public synchronized AjaxResult execute(String code,Integer topicId) {
		if(topicId==null||code==null||"".equals(code)) {
			return AjaxResult.fail("数据异常");
		}
		RunMessage im = null;
		try {
			Topic t=testService.getTestInfo(topicId);
			im = CompilerUtil.getRunInfo(code,t);
			//正确编译
			if(im.isCompilerSuccess()) {
				List<TestInfo> in=t.getTesting();
				List<RunInfo> run=im.getList();
				if(in!=null&&run!=null&&in.size()==run.size()) {
					int s=in.size();
					for(int i=0;i<s;i++) {
						RunInfo ri=run.get(i);
						String msg=ri.getRunMessage();
						String[] msgs = null;
						if(msg!=null) {
							msgs=msg.split("\r\n");
						TestInfo ti=in.get(i);
						List<Answer> ans=ti.getAnswer();
						if(ans!=null&&ans.size()==msgs.length) {
							for(int j=0;j<msgs.length;j++) {
								if(msgs[j].trim().equals(ans.get(j).getAnswer())) {
									ri.setResult("答案正确");
								}else {
										ri.setResult("答案错误");
									}
									ri.setRunMessage(null);
								}
							}else {
								ri.setResult("答案错误");
							}
						}else {
							ri.setResult("答案错误");
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return AjaxResult.fail("系统异常");
		}
		return AjaxResult.success(im);
	}
	
	public static String createTestData(List<Data> data){
    	StringBuilder b=new StringBuilder();
    	//拼接测试数据
    	for(Data s:data) {
			b.append(s.getData()).append("\r\n");
		}
    	return b.toString();
    }
	
	public static String createTestAnswer(List<Answer> data){
    	StringBuilder b=new StringBuilder();
    	//拼接测试数据
    	for(Answer s:data) {
			b.append(s.getAnswer()).append("\r\n");
		}
    	return b.toString();
    }
}
