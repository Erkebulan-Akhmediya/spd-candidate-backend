package kz.afm.candidate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {

    public static String toJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
