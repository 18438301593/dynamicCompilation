package test.dc.entity;
/**
 *	封装返回消息
 * @author 硅谷探秘者(jia)
 */
public class AjaxResult {
	private boolean success;
	private String msg;
	private Object data;
	private int code;
	private int count;
	public AjaxResult(){
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public AjaxResult(boolean success,int code, String msg, Object data){
		this.success = success;
		this.code=code;
		this.msg = msg;
		this.data = data;
	}
	public AjaxResult(boolean success,int code, String msg, Object data,int count){
		this.success = success;
		this.code=code;
		this.msg = msg;
		this.data = data;
		this.count=count;
	}
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static AjaxResult success(Object data){
		return new AjaxResult(true,200, null, data);
	}

	public static AjaxResult success(Object data, String msg){
		return new AjaxResult(true,200, msg, data);
	}

	public static AjaxResult success(String msg){
		return new AjaxResult(true,200, msg, null);
	}
	
	public static AjaxResult success(int code,String msg){
		return new AjaxResult(true,code, msg, null);
	}

    public static AjaxResult fail(String msg){
        return new AjaxResult(false,300, msg, null);
    }
    public static AjaxResult fail(String msg,int code){
        return new AjaxResult(false,code, msg, null);
    }
    
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
}
