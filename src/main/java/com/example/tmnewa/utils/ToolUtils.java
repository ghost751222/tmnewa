package com.example.tmnewa.utils;

import com.example.tmnewa.vo.cti.Identify;

import java.util.ArrayList;
import java.util.List;



public class ToolUtils {
    public static String[] IdentifyPrefix(String id) {
        List letterArray = initLetterArray();
        int sCheck;
        int idTranscate;
        String[] outStream = null;

        char[] ofc = id.toCharArray();
        if (!id.isEmpty() && isNumeric(id)) {
            int j = 0;
            int iSum = 0;
            for (char c : ofc) {
                int i = Character.getNumericValue(c);
                if (Math.abs(8 - j) != 0)
                    iSum = iSum + i * Math.abs(8 - j);
                else
                    iSum = iSum + i;
                j++;
            }
            idTranscate = 10 * (iSum / 10 + (iSum % 10 != 0 ? 1 : 0));
            outStream = AppendixCode(idTranscate - iSum);
        } else {
            outStream = new String[]{};
        }
        return outStream;
    }

    private static String[] AppendixCode(final int iMod) {
        String[] outPara = null;
        if (iMod == 0) outPara = new String[]{"B", "N", "Z"};
        if (iMod == 1) outPara = new String[]{"A", "M", "W"};
        if (iMod == 2) outPara = new String[]{"K", "L", "Y"};
        if (iMod == 3) outPara = new String[]{"J", "V", "X"};
        if (iMod == 4) outPara = new String[]{"H", "U"};
        if (iMod == 5) outPara = new String[]{"G", "T"};
        if (iMod == 6) outPara = new String[]{"F", "S"};
        if (iMod == 7) outPara = new String[]{"E", "R"};
        if (iMod == 8) outPara = new String[]{"D", "O", "Q"};
        if (iMod == 9) outPara = new String[]{"C", "I", "P"};
        return outPara;
    }

    private static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
//            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static List<Identify> initLetterArray() {
        //Initial Array Letter
        List<Identify> letterArray = new ArrayList<>();
        //  var letterArray=['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
        //    var intArray=[10,   11, 12,13, 14, 15,  16, 17, 34, 18, 19, 20,21,22,35,23,24,25,26,27,28,29,32,30,31,33];
        //  A 臺北市10
        letterArray.add(new Identify(0, "A"));
        //  B 臺中市11
        letterArray.add(new Identify(1, "B"));
        //　C 基隆市12
        letterArray.add(new Identify(2, "C"));
        //　D 臺南市13
        letterArray.add(new Identify(3, "D"));
        //  E 高雄市14
        letterArray.add(new Identify(4, "E"));
        //  F 新北市15
        letterArray.add(new Identify(5, "F"));
        //  G 宜蘭縣16
        letterArray.add(new Identify(6, "G"));
        //  H 桃園市17
        letterArray.add(new Identify(7, "H"));
        //　I 嘉義市34
        letterArray.add(new Identify(24, "I"));
        //　J 新竹縣18
        letterArray.add(new Identify(8, "J"));
        //　K 苗栗縣19
        letterArray.add(new Identify(9, "K"));
        //已停发字母 L 台中縣20
        letterArray.add(new Identify(10, "L"));
        //　M 南投縣 21
        letterArray.add(new Identify(11, "M"));
        //  N 彰化縣 22
        letterArray.add(new Identify(12, "N"));
        //　O 新竹市35
        letterArray.add(new Identify(25, "O"));
        //  P 雲林縣23
        letterArray.add(new Identify(13, "P"));
        //　Q 嘉義縣24
        letterArray.add(new Identify(14, "Q"));
        //　R 台南縣 25
        letterArray.add(new Identify(15, "R"));
        //　S 高雄縣26
        letterArray.add(new Identify(16, "S"));
        //　T 屏東縣27
        letterArray.add(new Identify(17, "T"));
        //　U 花蓮縣 28
        letterArray.add(new Identify(18, "U"));
        // V 臺東縣 29
        letterArray.add(new Identify(19, "V"));
        // 　W 金門縣32
        letterArray.add(new Identify(22, "W"));
        //　X 澎湖縣30 　
        letterArray.add(new Identify(20, "X"));
        //  Y阳明山31
        letterArray.add(new Identify(21, "Y"));
        //Z 連江縣33
        letterArray.add(new Identify(23, "Z"));
        return letterArray;
    }

}

