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
}
