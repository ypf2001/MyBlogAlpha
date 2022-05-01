package com.ypf.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class stringUtils {
    public  static Map jsonToMap  (String str) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
      return   objectMapper.readValue(str,Map.class);
    }
}
