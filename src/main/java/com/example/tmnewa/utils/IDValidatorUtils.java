package com.example.tmnewa.utils;

public class IDValidatorUtils {
    // 字母對應的數字值 (A=10, B=11, ..., Z=35)
    private static final int[] LETTER_VALUES = {
            10, 11, 12, 13, 14, 15, 16, 17, 34, 18, 19, 20, 21,
            22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 35
    };

    // 統一編號的加權係數
    private static final int[] UNIFORM_WEIGHTS = {1, 2, 1, 2, 1, 2, 4, 1};

    // 驗證身份證號碼
    public static boolean isValidTWID(String id) {
        if (id == null || !id.matches("[A-Z][12]\\d{8}")) {
            return false;
        }

        int letterValue = LETTER_VALUES[id.charAt(0) - 'A'];
        int sum = (letterValue / 10) + (letterValue % 10) * 9;

        for (int i = 1; i < 9; i++) {
            sum += (id.charAt(i) - '0') * (9 - i);
        }
        sum += (id.charAt(9) - '0');

        return sum % 10 == 0;
    }

    // 驗證外國人居留證號碼 (ARC)
    public static boolean isValidARC(String arc) {
        if (arc == null || !arc.matches("[A-Z][A-Z]\\d{8}")) {
            return false;
        }

        int firstLetterValue = LETTER_VALUES[arc.charAt(0) - 'A'];
        int secondLetterValue = LETTER_VALUES[arc.charAt(1) - 'A'];
        int sum = (firstLetterValue / 10) + (firstLetterValue % 10) * 9 +
                (secondLetterValue % 10) * 8;

        for (int i = 2; i < 9; i++) {
            sum += (arc.charAt(i) - '0') * (9 - i);
        }
        sum += (arc.charAt(9) - '0');

        return sum % 10 == 0;
    }

    // 驗證統一編號
    public static boolean isValidUniformNumber(String number) {
        if (number == null || !number.matches("\\d{8}")) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 8; i++) {
            int digit = number.charAt(i) - '0';
            int product = digit * UNIFORM_WEIGHTS[i];
            sum += (product / 10) + (product % 10);
        }

        if (sum % 10 == 0) {
            return true;
        }
        return number.charAt(6) == '7' && (sum + 1) % 10 == 0;
    }


}
