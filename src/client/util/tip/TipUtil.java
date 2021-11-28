package client.util.tip;

import models.Tip;

public final class TipUtil {
	private TipUtil() {

	}

	/**
	 * 超出给定长度就截掉多余的并返回截断后的，否则返回null
	 * 
	 * @param text
	 * @param limit
	 * @param tip
	 * @return <br/>
	 *         <b>Date:</b> 2021年6月8日 上午11:39:27
	 */
	public static String limitLength(String text, int limit, Tip tip) {
		if (text.length() > limit) {
			ShowTipUtil.showTip(tip.getLabel(), tip.getShowTime(), tip.getTipContent());
			String newText = text.substring(0, limit);
			return newText;
		}
		return null;
	}
}
