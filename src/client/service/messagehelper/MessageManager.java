package client.service.messagehelper;

import client.view.chat.ChatInterfaceController;
import javafx.application.Platform;
import models.message.entity.TextMessage;

/**
 * 消息管理
 * 
 * @author <a href="https://github.com/MysticalDream" target=
 *         "_blank">MysticalDream</a>
 * @version 1.0 <br>
 *          <b>PackageName:</b> client.service.messagehelper <br>
 *          <b>ClassName:</b> MessageManager <br>
 *          <b>Date:</b> 2021年6月16日 下午2:17:46
 */
public class MessageManager {

	public static ChatInterfaceController chatController = null;// 粗糙做法

	/**
	 * 接受一个文本消息,并添加到{@code messageMap}
	 * 
	 * @param textMessage <br/>
	 *                    <b>Date:</b> 2021年6月11日 下午4:17:15
	 */
	public static void addMessageToList(TextMessage textMessage, boolean isMy) {
		if (isMy) {
			Platform.runLater(() -> {
				MessageListButler.messageMap.get(textMessage.getReceiver().getAccount()).add(textMessage);
			});
		} else {
			Platform.runLater(() -> {
				MessageListButler.messageMap.get(textMessage.getSender().getAccount()).add(textMessage);
			});
		}
		chatController.scrollToBottom();
	}

}
