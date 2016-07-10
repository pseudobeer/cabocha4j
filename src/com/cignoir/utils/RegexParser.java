package com.cignoir.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author noire722
 *
 */
public class RegexParser {
	public static final String DOUBLE_QUATE = "\"";
	
	/**
	 * Find and get the inner string between the specified symbol in
	 * constructor's argument.If not found, return null.
	 * 
	 * @param symbol
	 * @return String
	 */
	public static String getInnerString(String searchFrom, String start, String end) {
		Matcher matcher = Pattern.compile(start + ".*" + end).matcher(searchFrom);
		String result = null;
		if (matcher.find()) {
			result = matcher.group();
			result = result.replaceFirst(start, "");
			result = result.replaceFirst(end, "");
		}
		return result;
	}
	
	public static String removeTags(String searchFrom) {
		Matcher matcher = Pattern.compile("<.*?>", Pattern.DOTALL).matcher(searchFrom);
		String result = "";
		if (matcher.find()) {
			result = matcher.replaceAll("");
		}
		return result;
	}

	public static String convertToOldXML(String input) {
		if (input.contains("sentence>")) return input;
		if (input.contains("<chunk")) return input;
		if (input.contains("</chunk>")) return input;

		Matcher matcher = Pattern.compile(
				"(feature=\\\"([^\"\\,]*),([^\"\\,]*),([^\"\\,]*),([^\"\\,]*),([^\"\\,]*),([^\"\\,]*),(([^\"\\,])*))(,([^\"\\,]*),([^\"\\,]*)\\\")",
				Pattern.DOTALL).matcher(input);
		String result = "";
		if (matcher.find()) {
			result = matcher.replaceAll("read=\"$11\" base=\"$8\" pos=\"$2-$3-$4\" ctype=\"\" cform=\"\" ne=\"0\"");
		} else {
			matcher = Pattern.compile(
					"(feature=\\\"([^\"\\,]*),([^\"\\,]*),([^\"\\,]*),([^\"\\,]*),([^\"\\,]*),([^\"\\,]*),([^\"\\,]*)\\\"\\>([^<]*))",
					Pattern.DOTALL).matcher(input);

			if (matcher.find()) {
				result = matcher.replaceAll("read=\"$9\" base=\"$8\" pos=\"$2-$3-$4\" ctype=\"\" cform=\"\" ne=\"0\">$9");
			}
		}

		matcher = Pattern.compile("(\\-\\*)|(\\*)", Pattern.DOTALL).matcher(result);
		if (matcher.find()) {
			result = matcher.replaceAll("");
		}

		return result;

	}
}
