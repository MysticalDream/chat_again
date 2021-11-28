package models.message;

/**
 * 消息类型
 * 
 * @author 18177
 */
public interface MessageType {
	/**
	 * 文本
	 */
	int TEXT = 0x00;
	/**
	 * 图片
	 */
	int IMAGE = 0x01;
	/**
	 * 视频
	 */
	int VIDEO = 0x02;
	/**
	 * 文件
	 */
	int FILE = 0x03;
	/**
	 * 未知
	 */
	int UNKNOW = -1;
}
