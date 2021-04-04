package test.dc.service;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 * 	文件下载 
 * @author Jiajiajia
 * @date 2019年11月10日
 * @todu TODO
 */
public class DownloadService {
	public void download(HttpServletResponse response,HttpServletRequest request,byte[] bytes,String filename) {
        BufferedOutputStream out = null;  
        try {  
            out = new BufferedOutputStream(response.getOutputStream());  
            response.setContentType("text/plain");// 设置response内容的类型  
            if (isMSBrowser(request)) {
                filename = URLEncoder.encode(filename, "UTF-8");
            } else {
                filename = new String(filename.getBytes("utf-8"), "ISO8859-1");
            }
            response.setHeader("Content-disposition", "attachment;filename=" + filename+".txt");// 设置头部信息  
            out.write(bytes);
            out.flush();  
        } catch (IOException e) {
            e.printStackTrace();
        } finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }
	}
	public static boolean isMSBrowser(HttpServletRequest request) {
        String[] IEBrowserSignals = {"MSIE", "Trident", "Edge"};  
        String userAgent = request.getHeader("User-Agent");  
        for (String signal : IEBrowserSignals) {              
            if (userAgent.contains(signal)){  
                return true;  
            }  
        }  
        return false;  
    }
}
