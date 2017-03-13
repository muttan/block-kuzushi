package muttan;

import org.newdawn.slick.*;

/**
 * Created by muttan on 2017/03/06.
 */
public class SquareBall extends RectangleGameObject {
    private static SpriteSheet sheet;
    private Image sprite;
    private float sw = 0;

    public SquareBall(float initial_x, float initial_y, int sprite_number) {
        x = initial_x;
        y = initial_y;
        width = 16;
        height = 16;

        try {

            Image img = new Image("resources/ball.png");
            sheet = new SpriteSheet(img, 16, 16);
            sprite = sheet.getSprite(sprite_number, 0);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(GameContainer container, int delta) {
        if ((int) sw / 100 == (int) (sw + delta) / 100) {
            updateReal((int) (sw + delta) / 100, delta);
        } else {
            int nextDirection = ((int) sw / 100 + 1) % 4;
            float nextDelta = ((int) sw / 100 + 1) * 100 - sw;
            updateReal((int) (sw + delta) / 100, nextDelta);
            updateReal(nextDirection, sw + delta - ((int) sw / 100 + 1) * 100);
        }
        sw += delta * 0.4;
        sw = sw % 400;
    }

    private void updateReal(int direction, float delta) {
        switch (direction) {
            case 0:
                x += delta * 0.4;
                break;
            case 1:
                y += delta * 0.4;
                break;
            case 2:
                x -= delta * 0.4;
                break;
            case 3:
                y -= delta * 0.4;
                break;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite, x, y);

    }
}
