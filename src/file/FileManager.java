package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 文件管理
 * 
 * @author <a href="https://github.com/MysticalDream" target=
 *         "_blank">MysticalDream</a>
 * @version 1.0 <br>
 *          <b>PackageName:</b> file <br>
 *          <b>ClassName:</b> FileManager <br>
 *          <b>Date:</b> 2021年6月16日 下午2:46:25
 */
public class FileManager {

	private final String CLIENTPARENTPATH = "./src/file/user/img";
	private final String SERVERPARENTPATH = "./src/file/server/users/img";
	public String usersaveLocation;
	public String serversaveLocation;

	public FileManager() {
		createFolder();
	}

	public void Changesave(File flie) {
		if (flie == null) {
			return;
		}
		try {
			ByteBuffer bb = java.nio.ByteBuffer.allocate(1024);
			FileInputStream is = new FileInputStream(flie);
			FileChannel ifc = is.getChannel();
			FileOutputStream os = new FileOutputStream((usersaveLocation = CLIENTPARENTPATH + "/" + flie.getName()));
			FileChannel ofc = os.getChannel();
			while ((ifc.read(bb)) != -1) {
				bb.flip();
				ofc.write(bb);
				bb.clear();
			}
			os.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createFolder() {
		File file = new File(CLIENTPARENTPATH);
		File file2 = new File(SERVERPARENTPATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		if (!file2.exists()) {
			file2.mkdirs();
		}
	}
}
