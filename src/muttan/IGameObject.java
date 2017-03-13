package muttan;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by muttan on 2017/03/02.
 */
public interface IGameObject {
    void update(GameContainer container, int delta);

    void render(Graphics g);

    // 自分とgameObjectが当たったかの判定
    boolean isHit(IGameObject gameObject);

    // 自分とxとyが当たったかの判定
    boolean isHitPosition(float x, float y);

    // 自分が生きてるかの判定
    boolean isAlive();
}
