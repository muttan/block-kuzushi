package muttan;

/**
 * Created by muttan on 2017/03/07.
 */
public abstract class RectangleGameObject extends BasicGameObject {
    protected float width;
    protected float height;

    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }

    @Override
    public boolean isHit(IGameObject gameObject) {
        if (!this.isAlive()) {
            return false;
        }
        boolean leftUpper = gameObject.isHitPosition(x, y);
        boolean leftBottom = gameObject.isHitPosition(x, y + height);
        boolean rightUpper = gameObject.isHitPosition(x + width, y);
        boolean rightBottom = gameObject.isHitPosition(x + width, y + height);
        return leftUpper || leftBottom || rightUpper || rightBottom;

    }

    @Override
    public boolean isHitPosition(float targetX, float targetY) {
        if (!this.isAlive()) {
            return false;
        }
        if (x <= targetX && x + width >= targetX && y <= targetY && y + height >= targetY) {
            return true;
        } else {
            return false;
        }
    }
}
