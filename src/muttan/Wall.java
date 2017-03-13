package muttan;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by muttan on 2017/03/09.
 */
public class Wall implements IGameObject {

    @Override
    public void update(GameContainer container, int delta) {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public boolean isHit(IGameObject gameObject) {
        return gameObject.isHit(this);
    }

    @Override
    public boolean isHitPosition(float x, float y) {
        if (640 < x || 0 > x || 0 > y) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean isAlive() {
        return true;
    }
}
