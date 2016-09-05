package dist.purify.air.utils;

import java.util.Random;

/**
 * Created by sunshine on 4/18/16.
 */
public class IDGenerator {
    private static final Random seed = new Random();
    private static final char[] code = {'z', 'h', 'a', 'n', 'g', 'x', 'u', 'f', 'a', 'n', 'w', 'i', 'l', 'l', 'l', 'o', 'v', 'e', 'f', 'a', 'n', 'y', 'u', 'y', 'a', 'n', 'f', 'o', 'e', 'v', 'e', 'r'};

    private static int num(int min, int max) {
        return min + seed.nextInt(max - min);
    }

    private static char generate() {
        return code[num(0, code.length)];
    }

    public static String generate(String prefix) {
        char[] temp = new char[8];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = generate();
        }
        StringBuffer result = new StringBuffer();
        Random random = new Random();
        result.append(prefix.toUpperCase());
        result.append(new String(temp));
        result.append(random.nextInt(99));
        return result.toString();
    }
}
