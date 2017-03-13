package muttan;

import org.newdawn.slick.*;

/**
 * Created by muttan on 2017/03/07.
 */
public class Block extends RectangleGameObject implements IBallHitListener {
    private static SpriteSheet sheet;
    private Image sprite;

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
        //TODO　壊したブロックからボールを作るときは下に落ちるようにする！！！
        isAlive = false;
        double theta = Main.gameRandom.nextDouble() * 1 / 2 * Math.PI + 5f / 4f * Math.PI;
        float dx = (float) Math.cos(theta) * 0.4f;
        float dy = -(float) Math.sin(theta) * 0.4f;
        System.out.printf("%f", dy);
        Main.instance.newBall(x + width / 2, y + height / 2, dx, dy);
    }

}
