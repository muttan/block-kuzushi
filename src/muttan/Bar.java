package muttan;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 * Created by muttan on 2017/03/10.
 */
public class Bar extends RectangleGameObject {

    public Bar() {
        x = 290;
        y = 440;
        width = 60;
        height = 10;
    }

    @Override
    public void update(GameContainer container, int delta) {
        Input input = container.getInput();
        if (input.isKeyDown(Input.KEY_LEFT) || input.isControllerLeft(Input.ANY_CONTROLLER)) {
            x -= 0.4 * delta;
            if (x < 0) {
                x = 0;
            }
        }

        if (input.isKeyDown(Input.KEY_RIGHT) || input.isControllerRight(Input.ANY_CONTROLLER)) {
            x += 0.4 * delta;
            if (x + width > 640) {
                x = 640 - width;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(x, y, width, height);
    }
}
