package com.amii.plus.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.amii.plus.api.util.ArrayToolkit;
import com.amii.plus.api.util.LogToolkit;
import com.amii.plus.api.util.StringToolkit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.amii.plus.api.AmiiPlusApiApplicationTests.class)
public class AmiiPlusApiApplicationTests
{
    @Test
    public void contextLoads ()
    {
        LogToolkit.devLog("test loading...");

        testTrim();
    }

    public void testTrim ()
    {
        String msg = "  abcabcd123abcdabc  ";

        String msg1 = msg;
        LogToolkit.devLog("msg1=" + msg1);

        String msg2 = StringToolkit.trim(msg, Character.valueOf(' '));
        LogToolkit.devLog("msg2=" + msg2);

        String msg3 = StringToolkit.trim(msg);
        LogToolkit.devLog("msg3=" + msg3);

        String msg4 = StringToolkit.trim(msg, Character.valueOf('a'));
        LogToolkit.devLog("msg4=" + msg4);

        Character[] tA = {' ', 'a', 'b', 'c'};
        String msg5 = msg;
        for (int i = 0; i < tA.length; i++) {
            msg5 = StringToolkit.trim(msg5, tA[i]);
        }
        LogToolkit.devLog("msg5=" + msg5);

        String msg6 = StringToolkit.trim(msg, tA);
        LogToolkit.devLog("msg6=" + msg6);
    }

    public void testArrayContains ()
    {
        Boolean result = false;

        Character[] tA = {' ', 'a', 'b', 'c'};
        Character[] tb = {'a', 'b'};
        Character[] tc = {'a', 'b', 'd'};
        result = ArrayToolkit.isArrayContainsValue(tA, Character.valueOf('c'));
        LogToolkit.devLog("result_char1=" + result);
        result = ArrayToolkit.isArrayContainsValue(tA, Character.valueOf('d'));
        LogToolkit.devLog("result_char2=" + result);
        result = ArrayToolkit.isArrayContainsArray(tA, tb);
        LogToolkit.devLog("result_char3=" + result);
        result = ArrayToolkit.isArrayContainsArray(tA, tc);
        LogToolkit.devLog("result_char4=" + result);

        Integer[] iA = {11, 12, 13, 14};
        Integer[] ib = {11, 13};
        Integer[] ic = {11, 15};
        result = ArrayToolkit.isArrayContainsValue(iA, Integer.valueOf(13));
        LogToolkit.devLog("result_int1=" + result);
        result = ArrayToolkit.isArrayContainsValue(iA, Integer.valueOf(15));
        LogToolkit.devLog("result_int2=" + result);
        result = ArrayToolkit.isArrayContainsArray(iA, ib);
        LogToolkit.devLog("result_int3=" + result);
        result = ArrayToolkit.isArrayContainsArray(iA, ic);
        LogToolkit.devLog("result_int4=" + result);

        String[] sA = {"aa", "bb", "cc", "dd"};
        String[] sb = {"bb", "dd"};
        String[] sc = {"bb", "dd", "ee"};
        result = ArrayToolkit.isArrayContainsValue(sA, "cc");
        LogToolkit.devLog("result_string1=" + result);
        result = ArrayToolkit.isArrayContainsValue(sA, "ee");
        LogToolkit.devLog("result_string2=" + result);
        result = ArrayToolkit.isArrayContainsArray(sA, sb);
        LogToolkit.devLog("result_string3=" + result);
        result = ArrayToolkit.isArrayContainsArray(sA, sc);
        LogToolkit.devLog("result_string4=" + result);
    }
}
