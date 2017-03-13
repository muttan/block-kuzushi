package muttan;

import org.newdawn.slick.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by muttan on 2017/02/28.
 */
public class Main extends BasicGame {
    private List<IGameObject> objects;
    private List<IGameObject> pendingObjects;
    public static Random gameRandom = new Random();
    public static Main instance;


    public Main(String title) {
        super(title);
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setBackground(Color.white);
        for (IGameObject object : objects) {
            if (object.isAlive()) {
                object.render(g);
            }
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        objects = new ArrayList<>();
        pendingObjects = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            objects.add(initBall(320, 110));
        }
        for (int x = 0; x < 640; x += 64) {
            for (int y = 0; y < 100; y += 25) {
                objects.add(initBlock(x, y));
            }
        }
        objects.add(new Wall());
        objects.add(new Bar());
    }

    private IGameObject initBall(float x, float y) {
        float dx;
        float dy;
        double theta;
        theta = gameRandom.nextDouble() * 2 * Math.PI;
        dx = (float) Math.cos(theta) * 0.4f;
        dy = (float) Math.sin(theta) * 0.4f;
        switch (gameRandom.nextInt(1)) {
            case 0:
                return new Ball(x, y, dx, dy, gameRandom.nextInt(4));
            case 1:
                return new GravityBall(x, y, dx, dy, gameRandom.nextInt(4));
            case 2:
                return new SquareBall(x, y, gameRandom.nextInt(4));
            default:
                return null;
        }
    }

    private IGameObject initBlock(int x, int y) {
        return new Block(x, y, 64, 25);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        List<IGameObject> deadObjects;
        deadObjects = new ArrayList<>();
        for (IGameObject object : objects) {
            object.update(container, delta);
            if (object.isAlive() == false) {
                deadObjects.add(object);
            }
        }
        for (IGameObject deadObject : deadObjects) {
            objects.remove(deadObject);
        }
        for (IGameObject pendingObject : pendingObjects) {
            objects.add(pendingObject);
        }
        pendingObjects.clear();
    }

    public List<IGameObject> hitTest(IGameObject gameObject) {
        List<IGameObject> targets = new ArrayList<>();
        for (IGameObject object : objects) {
            if (gameObject.isHit(object)) {
                targets.add(object);
            }

        }
        return targets;
    }

    public void newBall(float x, float y, float dx, float dy) {

        pendingObjects.add(new Ball(x, y, dx, dy, gameRandom.nextInt(4)));
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        instance = new Main("break out");
        AppGameContainer container;
        try {
            container = new AppGameContainer(instance);
            container.start();
        } catch (SlickException e) {
            // ignore
        }
    }


}