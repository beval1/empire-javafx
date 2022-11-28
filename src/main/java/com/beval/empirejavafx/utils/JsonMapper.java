package com.beval.empirejavafx.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {
    private JsonMapper(){}
    private static final ObjectMapper mapper = new ObjectMapper();
    public static ObjectMapper getMapper(){
        return mapper.findAndRegisterModules();
    }
}
