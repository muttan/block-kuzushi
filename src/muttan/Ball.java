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
    protected Bar stickingTarget;
    protected float relative;
    public static final float WIDTH = 16;
    public static final float HEIGHT = 16;


    public Ball(float initial_x, float initial_y, float initial_dx, float initial_dy, int sprite_number) {
        x = initial_x;
        y = initial_y;
        dx = initial_dx;
        dy = initial_dy;
        width = WIDTH;
        height = HEIGHT;
        try {

            Image img = new Image("resources/ball.png");
            sheet = new SpriteSheet(img, (int)WIDTH, (int)HEIGHT);
            sprite = sheet.getSprite(sprite_number, 0);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g) {
        g.drawImage(sprite, x, y);

    }
    //barにballをくっつける
    public void stick(Bar bar){
        relative = x -(bar.getX());
        stickingTarget = bar;

    }

    public void update(GameContainer container, int delta) {
//Todo 跳ね返った時の処理をちゃんとする！！！！
        x += dx * delta;
        y += dy * delta;
        List<IGameObject> targets = Main.instance.hitTest(this);
        targets = targets.stream().filter(gameObject -> !(gameObject instanceof Ball)).collect(Collectors.toList());

            if (stickingTarget !=null) {
                x = stickingTarget.getX() + relative;
                y = stickingTarget.getY() - height;
                Input input = container.getInput();
            if (input.isKeyDown(Input.KEY_SPACE)) {
                stickingTarget = null;
                return;
            }
        }
        if (!targets.isEmpty()) {
            boolean leftUpper = targets.stream().anyMatch(gameObject -> gameObject.isHitPosition(x, y));
            boolean leftBottom = targets.stream().anyMatch(gameObject -> gameObject.isHitPosition(x, y + height));
            boolean rightUpper = targets.stream().anyMatch(gameObject -> gameObject.isHitPosition(x + width, y));
            boolean rightBottom = targets.stream().anyMatch(gameObject -> gameObject.isHitPosition(x + width, y + height));
            x -= dx * delta;
            y -= dy * delta;
            //上に跳ね返るとき
            if ((leftBottom && !(leftUpper))&&dy>0) {
                dy *= -1;
            } else if ((rightBottom && !(rightUpper))&&dy>0) {
                dy *= -1;
            }
            //下に跳ね返るとき
            if ((leftUpper && !(leftBottom))&&dy<0) {
                dy *= -1;
            } else if ((rightUpper && !(rightBottom))&&dy<0) {
                dy *= -1;
            }
            //右に跳ね返るとき
            if ((leftUpper && !(rightUpper))&&dx<0) {
                dx *= -1;
            } else if ((leftBottom && !(rightBottom))&&dx<0) {
                dx *= -1;
            }
            //左に跳ね返るとき
            if ((rightUpper && !(leftUpper))&&dx>0) {
                dx *= -1;
            } else if ((rightBottom && !(leftBottom))&&dx>0) {
                dx *= -1;
            }
            x +=dx*delta;
            y+=dy*delta;
            for (IGameObject target : targets) {
                if (target instanceof IBallHitListener) {
                    ((IBallHitListener) target).onBallHit();
                }
            }

        }


    }
}
