package resources;

public class Utils {
    public static final int SCREEN_WIDTH = 1200;
    public static final int SCREEN_HEIGHT = 1000;

    public static final float PPM = 100;

    public static final float GAME_SCALE = 4f;

    public static final short BIT_PLAYER = 1 << 1;
    public static final short BIT_BOMB = 1 << 1;
    public static final short BIT_EXPLOSION = 1 << 1;

    public enum PlayerState {
        UP,
        DOWN,
        LEFT,
        RIGHT,
    }

}
