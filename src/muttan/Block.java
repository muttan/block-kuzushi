package muttan;

import org.newdawn.slick.*;

/**
 * Created by muttan on 2017/03/07.
 */
public class Block extends RectangleGameObject implements IBallHitListener {
    protected static SpriteSheet sheet;
    protected Image sprite;

    public Block(int x, int y, int width, int height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        try {

            Image img = new Image("resources/block.png");
            sheet = new SpriteSheet(img, 80, 30);
            sprite = sheet.getSprite(0, 0);
            sprite = sprite.getScaledCopy(width, height);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(GameContainer container, int delta) {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite, x, y);
    }

    @Override
    public void onBallHit() {
        isAlive = false;
        float angle = (float)(Main.gameRandom.nextDouble() * 1 / 2 * Math.PI + 5f / 4 * Math.PI);;
        float speed = 0.4f;
        Main.instance.newBall(x + width / 2, y + height / 2, angle, speed);
    }

}
