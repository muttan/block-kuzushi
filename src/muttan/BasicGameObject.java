package muttan;

/**
 * Created by muttan on 2017/03/07.
 */
public abstract class BasicGameObject implements IGameObject {
    protected float x;
    protected float y;
    protected boolean isAlive = true;

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }


    @Override
    public boolean isAlive() {
        return isAlive;
    }

}
