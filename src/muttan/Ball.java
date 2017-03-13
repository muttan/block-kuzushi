package muttan;

import org.newdawn.slick.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by muttan on 2017/03/01.
 */
public class Ball extends RectangleGameObject {
    private static SpriteSheet sheet;
    private Image sprite;
    protected float dx = 0.5f;
    protected float dy = 0.5f;

    public Ball(float initial_x, float initial_y, float initial_dx, float initial_dy, int sprite_number) {
        x = initial_x;
        y = initial_y;
        dx = initial_dx;
        dy = initial_dy;
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

    public void render(Graphics g) {
        g.drawImage(sprite, x, y);

    }

    public void update(GameContainer container, int delta) {
//Todo 跳ね返った時の処理をちゃんとする！！！！
        x += dx * delta;
        y += dy * delta;
        List<IGameObject> targets = Main.instance.hitTest(this);
        targets = targets.stream().filter(gameObject -> !(gameObject instanceof Ball)).collect(Collectors.toList());
        if (!targets.isEmpty()) {


            boolean leftUpper = targets.stream().anyMatch(gameObject -> gameObject.isHitPosition(x, y));
            boolean leftBottom = targets.stream().anyMatch(gameObject -> gameObject.isHitPosition(x, y + height));
            boolean rightUpper = targets.stream().anyMatch(gameObject -> gameObject.isHitPosition(x + width, y));
            boolean rightBottom = targets.stream().anyMatch(gameObject -> gameObject.isHitPosition(x + width, y + height));
            x -= dx * delta;
            y -= dy * delta;
            //上に跳ね返るとき
            if (leftBottom && !(leftUpper)) {
                dy *= -1;
            } else if (rightBottom && !(rightUpper)) {
                dy *= -1;
            }
            //下に跳ね返るとき
            if (leftUpper && !(leftBottom)) {
                dy *= -1;
            } else if (rightUpper && !(rightBottom)) {
                dy *= -1;
            }
            //右に跳ね返るとき
            if (leftUpper && !(rightUpper)) {
                dx *= -1;
            } else if (leftBottom && !(rightBottom)) {
                dx *= -1;
            }
            //左に跳ね返るとき
            if (rightUpper && !(leftUpper)) {
                dx *= -1;
            } else if (rightBottom && !(leftBottom)) {
                dx *= -1;
            }
            for (IGameObject target : targets) {
                if (target instanceof IBallHitListener) {
                    ((IBallHitListener) target).onBallHit();
                }
            }

        }


    }
}
