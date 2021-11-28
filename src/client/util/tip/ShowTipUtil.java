package client.util.tip;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javafx.scene.control.Labeled;

public final class ShowTipUtil {

	private ShowTipUtil() {

	}

	public static ExecutorService pool = new ThreadPoolExecutor(0, 1, 0L, TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(1));// 创建线程池,单线池

	/**
	 * 提示
	 * 
	 * @param label
	 * @param millis
	 * @param tipContent <br/>
	 *                   <b>Date:</b> 2021年6月8日 上午10:54:33
	 */
	public static void showTip(Labeled label, long millis, String tipContent) {
		label.setText(tipContent);
		try {
			pool.execute(() -> {
				label.setVisible(true);
				try {
					Thread.sleep(millis);
				} catch (InterruptedException e) {
					System.out.println("睡眠中断");
					e.printStackTrace();
				}
				label.setVisible(false);
			});
		} catch (RejectedExecutionException e) {
			return;
		}
	}

	/**
	 * 关闭线程池
	 * 
	 * @return <br/>
	 *         <b>Date:</b> 2021年6月8日 上午11:00:18
	 */
	public boolean closePool() {
		pool.shutdownNow();
		return true;
	}
}
