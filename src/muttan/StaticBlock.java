package muttan;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by muttan on 2017/03/15.
 */
public class StaticBlock extends Block {
    public StaticBlock(int x, int y, int width, int height) {
        super(x, y, width, height);

        sprite = sheet.getSprite(1, 0);
        sprite = sprite.getScaledCopy(width, height);

    }

    @Override
    public void onBallHit() {
        isAlive = false;
        Main.instance.instantiate(new Block((int) x, (int) y, (int) width, (int) height));
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite, x, y);
    }

}
