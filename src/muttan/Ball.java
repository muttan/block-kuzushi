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
    /**
     * 右基準で左回り
     */
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

    //移動処理
    private void move(int delta) {
        x += (float) Math.cos(angle) * speed * delta;
        y += (float) -Math.sin(angle) * speed * delta;
    }

    //barについていくやつ
    private void moveWithBar(GameContainer container) {
        x = stickingTarget.getX() + relative;
        y = stickingTarget.getY() - height;
        Input input = container.getInput();
        if (input.isKeyDown(Input.KEY_SPACE)) {
            stickingTarget = null;
            return;
        }
    }

    //当たり判定
    private List hitTest() {
        List<IGameObject> targets = Main.instance.hitTest(this);
        targets = targets.stream().filter(gameObject -> !(gameObject instanceof Ball)).collect(Collectors.toList());
        return targets;
    }

    //上下にぶつかってたら跳ね返る処理
    private void reboundVertical(HitTestResult hitTestResult) {
        boolean c1 = (angle >= Math.PI && angle < Math.PI * 2);
        //上に跳ね返るとき
        if (c1 && hitTestResult.bottom()) {
            angle = (float) Math.PI * 2 - angle;
        }

        //下に跳ね返るとき
        if (!c1 && hitTestResult.upper()) {
            angle = (float) Math.PI * 2 - angle;
        }
    }

    //左右にぶつかってたら跳ね返る処理
    private void reboundHorizontal(HitTestResult hitTestResult) {
        boolean c2 = (angle >= Math.PI / 2 && angle < Math.PI * 3 / 2);
        //右に跳ね返るとき
        if (c2 && hitTestResult.left()) {
            angle = (float) Math.PI - angle;
        }
        //左に跳ね返るとき
        if (!c2 && hitTestResult.right()) {
            angle = (float) Math.PI - angle;
        }
    }

    //跳ね返り処理
    private void rebound(List<IGameObject> targets) {
        boolean leftUpper = targets.stream().anyMatch(gameObject -> gameObject.isHitPosition(x, y));
        boolean leftBottom = targets.stream().anyMatch(gameObject -> gameObject.isHitPosition(x, y + height));
        boolean rightUpper = targets.stream().anyMatch(gameObject -> gameObject.isHitPosition(x + width, y));
        boolean rightBottom = targets.stream().anyMatch(gameObject -> gameObject.isHitPosition(x + width, y + height));

        HitTestResult hitTestResult = new HitTestResult(leftUpper, leftBottom, rightUpper, rightBottom);

        reboundVertical(hitTestResult);
        reboundHorizontal(hitTestResult);

        if (angle < 0) {
            angle += 2 * Math.PI;
        }

        for (IGameObject target : targets) {
            if (target instanceof Block) {
                ((IBallHitListener) target).onBallHit();
            }
        }
    }

    public void update(GameContainer container, int delta) {
        if (stickingTarget == null) {
            move(delta);

            List<IGameObject> targets = hitTest();

            //何かにあたっていた時
            if (!targets.isEmpty()) {
                //当たる前の状態に戻す
                move(-delta);
                rebound(targets);
                move(delta);
            }
        } else {
            moveWithBar(container);
        }
    }
}
