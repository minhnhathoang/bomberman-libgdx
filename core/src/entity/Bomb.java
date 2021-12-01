package entity;

public class Bomb {

    public enum State {
        NORMAL,
        UP,
        DOWN,
        LEFT,
        RIGHT,
        EXPLODING
    }

    public State state;
    float timeCountDown;

    public Bomb() {
        this(2);
    }

    public Bomb(float timeCountDown) {
        this.timeCountDown = timeCountDown;
    }
}
