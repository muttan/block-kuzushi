package muttan;

import org.newdawn.slick.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by muttan on 2017/03/01.
 */
public class Ball extends RectangleGameObject {
    public static final float WIDTH = 16;
    public static final float HEIGHT = 16;
    private static SpriteSheet sheet;
    /** 右基準で左回り*/
    protected float angle;
    protected float speed;
    //自分がくっついている先
    protected Bar stickingTarget;
    //x座標の差
    protected float relative;
    private Image sprite;


    public Ball(float initial_x, float initial_y, float angle, float speed, int sprite_number) {
        x = initial_x;
        y = initial_y;
        this.angle = angle;
        this.speed = speed;
        width = WIDTH;
        height = HEIGHT;

        try {
            Image img = new Image("resources/ball.png");
            sheet = new SpriteSheet(img, (int) WIDTH, (int) HEIGHT);
            sprite = sheet.getSprite(sprite_number, 0);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g) {
        g.drawImage(sprite, x, y);

    }

    //barにballをくっつける
    public void stick(Bar bar) {
        relative = x - (bar.getX());
        stickingTarget = bar;

    }

    public void update(GameContainer container, int delta) {
//Todo 跳ね返るメソッドを作る！！！！

        x += (float) Math.cos(angle) * speed * delta;
        y += (float) -Math.sin(angle) * speed * delta;
        List<IGameObject> targets = Main.instance.hitTest(this);
        targets = targets.stream().filter(gameObject -> !(gameObject instanceof Ball)).collect(Collectors.toList());

        if (stickingTarget != null) {
            x = stickingTarget.getX() + relative;
            y = stickingTarget.getY() - height;
            Input input = container.getInput();
            if (input.isKeyDown(Input.KEY_SPACE)) {
                stickingTarget = null;
                return;
            }
        }
        //何かにあたっていた時
        if (!targets.isEmpty()) {
            boolean leftUpper = targets.stream().anyMatch(gameObject -> gameObject.isHitPosition(x, y));
            boolean leftBottom = targets.stream().anyMatch(gameObject -> gameObject.isHitPosition(x, y + height));
            boolean rightUpper = targets.stream().anyMatch(gameObject -> gameObject.isHitPosition(x + width, y));
            boolean rightBottom = targets.stream().anyMatch(gameObject -> gameObject.isHitPosition(x + width, y + height));
            //当たる前の状態に戻す
            x -= (float) Math.cos(angle) * speed * delta;
            y -= (float) -Math.sin(angle) * speed * delta;
            //上に跳ね返るとき
            if ((leftBottom && !(leftUpper)) && (angle >= Math.PI  && angle < Math.PI * 2)) {
                angle = (float) Math.PI *2- angle;

            } else if ((rightBottom && !(rightUpper)) && (angle >= Math.PI  && angle < Math.PI * 2)) {
                angle = (float) Math.PI*2 - angle;

            }
            //下に跳ね返るとき
            if ((leftUpper && !(leftBottom)) && !(angle >= Math.PI  && angle < Math.PI * 2)) {
                angle = (float) Math.PI*2 - angle;
            } else if ((rightUpper && !(rightBottom)) && !(angle >= Math.PI  && angle < Math.PI * 2)) {
                angle = (float) Math.PI*2 - angle;

            }
            //右に跳ね返るとき
            if ((leftUpper && !(rightUpper)) && (angle >= Math.PI/2 && angle < Math.PI * 3/2)) {
                angle = (float) Math.PI  - angle;
            } else if ((leftBottom && !(rightBottom)) && (angle >= Math.PI/2 && angle < Math.PI * 3/2)) {
                angle = (float) Math.PI  - angle;
            }
            //左に跳ね返るとき
            if ((rightUpper && !(leftUpper)) && !(angle >= Math.PI/2 && angle < Math.PI * 3/2)) {
                angle = (float) Math.PI  - angle;
            } else if ((rightBottom && !(leftBottom)) && !(angle >= Math.PI/2 && angle < Math.PI * 3/2)) {
                angle = (float) Math.PI  - angle;
            }
            if (angle < 0) {
                angle += 2 * Math.PI;
            }

            x += (float) Math.cos(angle) * speed * delta;
            y += (float) -Math.sin(angle) * speed * delta;
            for (IGameObject target : targets) {
                if (target instanceof Block) {
                    ((IBallHitListener) target).onBallHit();
                }

            }
        }


    }
}
