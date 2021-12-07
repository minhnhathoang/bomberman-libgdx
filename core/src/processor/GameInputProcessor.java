package processor;

import com.badlogic.gdx.InputAdapter;

public class GameInputProcessor extends InputAdapter {
    private static final int NUM_KEYS = 255;

    public static boolean[] keys;

    static {
        keys = new boolean[NUM_KEYS];
        for (int i = 0; i < NUM_KEYS; ++i) {
            keys[i] = false;
        }
    }

    public static boolean isHold(int i) {
        return keys[i];
    }

    public static void setKeys(int i, boolean b) {
        keys[i] = b;
    }

    @Override
    public boolean keyDown(int keycode) {
        keys[keycode] = true;
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        keys[keycode] = false;
        return false;
    }
}
