package manager;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

    private static String currentMusic = "";

    public static void playSound(String name) {
        Sound sound = ResourceManager.INSTANCE.assetManager.get("sound/" + name + ".ogg", Sound.class);
        sound.play();
    }

    public static void playMusic(String name) {
        Music music = ResourceManager.INSTANCE.assetManager.get("sound/" + name + ".mp3", Music.class);
        if (name.equals(currentMusic)) {
            if (music.isPlaying()) {
                return;
            }
        }
        pauseMusic();
        music.play();
        music.setLooping(true);
        currentMusic = name;
    }

    public static void pauseMusic() {
        if (currentMusic.equals("")) {
            return;
        }
        Music music = ResourceManager.INSTANCE.assetManager.get("sound/" + currentMusic + ".mp3", Music.class);
        if (music.isPlaying()) {
            music.stop();
        }
    }
}
