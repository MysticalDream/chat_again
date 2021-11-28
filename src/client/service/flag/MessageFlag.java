package client.service.flag;

/**
 * 消息标志
 * 
 * @author <a href="https://github.com/MysticalDream" target=
 *         "_blank">MysticalDream</a>
 * @version 1.0 <br>
 *          <b>PackageName:</b> client.service.flag <br>
 *          <b>ClassName:</b> MessageFlag <br>
 *          <b>Date:</b> 2021年6月16日 下午2:24:12
 */
public interface MessageFlag {
	/**
	 * 心跳
	 */
	int HEARTBEAT = 0x00;
	/**
	 * 登录
	 */
	int LOGIN = 0x1;
	/**
	 * 注册
	 */
	int REGISTER = 0x2;
	/**
	 * 私聊
	 */
	int PRIVATEMESSAGE = 0x3;
	/**
	 * 添加好友
	 */
	int ADDFRIEND = 0x4;
	/**
	 * 删除好友
	 */
	int DELETEFRIEND = 0x5;
	/**
	 * 群聊
	 */
	int GROUPMESSAGE = 0x6;
	/**
	 * 下线
	 */
	int OFFLINE = 0x7;
	/**
	 * 获取用户列表
	 */
	int GETUSERLIST = 0x8;
}
