package ca.jrvs.apps.twitter.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

public class JsonUtil {

    /**
     * Convert a java object to a JSON string
     *
     * @param object input object
     * @param prettyJson flag for if json should be "pretty" (i.e. indented)
     * @param includeNullValues flag for if null values should be included
     * @return JSON String
     * @throws JsonProcessingException
     */
    public static String toJson(Object object, boolean prettyJson, boolean includeNullValues) throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();
        if (includeNullValues == false) {
            m.setSerializationInclusion(Include.NON_NULL);
        }
        if (prettyJson) {
            m.enable(SerializationFeature.INDENT_OUTPUT);
        }

        return m.writeValueAsString(object);
    }

    /**
     * Parse JSON String to a Java object
     *
     * @param json JSON String
     * @param target class of the object to create
     * @return Object of type T
     * @param <T> Type of object to return
     * @throws IOException
     */
    public static <T> T toObjectFromJson(String json, Class target) throws IOException {
        ObjectMapper m = new ObjectMapper();
        return (T) m.readValue(json, target);
    }
}
