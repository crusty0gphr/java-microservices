package com.harut.resourceservice.utils;

import org.apache.tika.metadata.Metadata;

import java.util.HashMap;
import java.util.Map;

public class ResourceUtils {

	public static Map<String, String> extractMetadata(Metadata input) {
		Map<String, String> metadata = new HashMap<>();

		String[] names = input.names();
		for (String name : names) {
			String key = name.replaceFirst("^[^:]+:", "");

			metadata.put(key, input.get(name));
		}

		return metadata;
	}

	public static String formatDuration(String duration) {
		double totalSeconds = Double.parseDouble(duration);
		int minutes = (int) (totalSeconds / 60);
		int seconds = (int) (totalSeconds % 60);
		return String.format("%02d:%02d", minutes, seconds);
	}
}
