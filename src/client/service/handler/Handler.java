package client.service.handler;

import java.io.IOException;
import java.net.Socket;
/**
 * 消息策略
 * @author <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
 * @version 1.0
 * <br><b>PackageName:</b> client.service.handler
 * <br><b>ClassName:</b> Handler
 * <br><b>Date:</b> 2021年6月16日 下午2:18:49
 */
public interface Handler {
	void handle(Socket socket) throws IOException ;
}
