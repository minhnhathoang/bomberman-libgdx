package manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;
import java.util.Map;

public class FontManager {
    public static final Map<Integer, BitmapFont> fonts = new HashMap<>();

    public FontManager() {

    }

    public static BitmapFont getFont(int sizeFont, Color color) {
        if (fonts.containsKey(sizeFont)) {
            BitmapFont font = fonts.get(sizeFont);
            font.setColor(color);
            return font;
        }

        //FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/LRpixel.ttf"));
        FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = sizeFont;

        BitmapFont newFont = freeTypeFontGenerator.generateFont(parameter);
        newFont.setColor(color);

        fonts.put(sizeFont, newFont);

        return newFont;
    }


    public static void drawText(SpriteBatch batch, String text, int sizeFont, Color color, int x, int y) {
        getFont(sizeFont, color).draw(batch, text, x, y);
    }

    public static void drawText(SpriteBatch batch, String text, int sizeFont, int x, int y) {
        getFont(sizeFont, Color.WHITE).draw(batch, text, x, y);
    }
}
