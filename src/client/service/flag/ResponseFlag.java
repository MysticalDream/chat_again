package client.service.flag;
/**
 * 响应标志
 * @author <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
 * @version 1.0
 * <br><b>PackageName:</b> client.service.flag
 * <br><b>ClassName:</b> ResponseFlag
 * <br><b>Date:</b> 2021年6月16日 下午2:31:30
 */
public interface ResponseFlag {
	/**
	 * 成功或通过
	 */
	int SUCCESS = 0x0A;
	/**
	 * 重复
	 */
	int DUPLICATE = 0x0B;
	/**
	 * 密码错误
	 */
	int WRONGPASSWORD = 0x0C;
	/**
	 * 账号不存在
	 */
	int INEXISTENCE = 0x0D;
}
