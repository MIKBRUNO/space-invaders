package swing_view;

import game.ArrayListScene;
import game.Bounds;

import java.awt.*;

public class BlackScreenScene extends ArrayListScene implements BgColorScene {
    public BlackScreenScene(Bounds bounds) {
        super(bounds);
    }

    @Override
    public Color getBgColor() {
        return Color.BLACK;
    }
}
