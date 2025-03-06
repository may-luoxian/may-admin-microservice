package com.may.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class UtilsApplicationTests {

    Map<Character, Integer> map = new HashMap<Character, Integer>() {{
        put('I', 1);
        put('V', 5);
        put('X', 10);
        put('L', 50);
        put('C', 100);
        put('D', 500);
        put('M', 1000);
    }};

    @Test
    public int contextLoads(String s) {
        int result = 0;
        int length = s.length();
        for (int i = 0; i < length; i++) {

            Integer value = map.get(s.charAt(i));

            if (i != 0 && map.get(s.charAt(i - 1)) < value) {
                Integer preValue = map.get(s.charAt(i - 1));
                result -= preValue;
                result += (value - preValue);
            } else {
                result = result + value;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String s = "IV";

        UtilsApplicationTests utilsApplicationTests = new UtilsApplicationTests();
        int i = utilsApplicationTests.contextLoads(s);
        System.out.println(i);
    }

}
