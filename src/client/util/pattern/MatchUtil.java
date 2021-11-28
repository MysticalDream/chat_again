package client.util.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MatchUtil {
	private MatchUtil() {

	}

	/**
	 * 获得一个匹配器，将给定的输入与此模式匹配
	 * 
	 * @param regex
	 * @param input
	 * @return <br/>
	 *         <b>Date:</b> 2021年6月8日 上午11:12:37
	 */
	public static Matcher getMatcher(String regex, CharSequence input) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		return m;
	}
}
