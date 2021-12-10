package helper;

import java.util.Random;

public class Rand {
    public static Random random = new Random();

    public static int rand(int x) {
        return random.nextInt(x);
    }

    public static int rand(int x, int y) {
        return random.nextInt(y - x + 1) + x;
    }

    public static float rand(float x, float y) {
        return random.nextFloat() + rand((int) x, (int) y);
    }
}
