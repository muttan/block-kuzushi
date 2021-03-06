package muttan;

import org.newdawn.slick.*;

import javax.sound.midi.SysexMessage;
import java.util.*;

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
        /*objects.stream().forEach(new Consumer<IGameObject>() {
            @Override
            public void accept(IGameObject object) {
                if (object.isAlive()) {
                    object.render(g);
                }
            }
        });*/
        objects.stream()
                .filter(object -> object.isAlive())
                .forEach(object -> object.render(g));


        /*for (IGameObject object : objects) {
            if (object.isAlive()) {
                object.render(g);
            }
        }*/

        Map<String,Integer> objectCountMap = new HashMap<>();
        for (IGameObject object:objects) {

            int count = objectCountMap.getOrDefault((object.getClass().getName()),0);

            objectCountMap.put(object.getClass().getName(),count+1);
        }

        String result = "";

        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String,Integer> objectCount:objectCountMap.entrySet()) {
            builder.append( String.format("%s: %d \n", objectCount.getKey(),objectCount.getValue()));

        }
        for (IGameObject object:pendingObjects ){
            builder.append(String.format("%s \n",object.getClass().getName()));
        }
        result = builder.toString();
        g.setColor(Color.black);
        g.drawString(result,11,31);
        g.drawString(result,9,29);
        g.drawString(result,11,29);
        g.drawString(result,9,31);
        g.setColor(Color.white);
        g.drawString(result,10,30);


    }

    @Override
    public void init(GameContainer container) throws SlickException {

        objects = new ArrayList<>();
        pendingObjects = new ArrayList<>();
        objects.add(new Wall());
        Bar bar = new Bar();
        objects.add(bar);
        for (int i = 0; i < 1; i++) {
            Ball ball = initBall(bar.getX() + bar.getWidth() / 2 - Ball.WIDTH / 2, bar.getY() + Ball.HEIGHT);
            objects.add(ball);
            ball.stick(bar);
        }
        for (int x = 0; x < 640; x += 64) {
            for (int y = 0; y < 100; y += 25) {
                objects.add(initBlock(x, y));
            }
        }

    }

    private Ball initBall(float x, float y) {
        float angle= (float)(Main.gameRandom.nextDouble() * 1 / 2 * Math.PI + 1f / 4 * Math.PI);
        float speed=  0.4f;
        switch (gameRandom.nextInt(1)) {
            case 0:
                return new Ball(x, y, angle, speed, gameRandom.nextInt(4));
            case 1:
                return new GravityBall(x, y, angle, speed, gameRandom.nextInt(4));
            default:
                return null;
        }
    }

    private IGameObject initBlock(int x, int y) {
        return new StaticBlock(x, y, 64, 25);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        List<IGameObject> deadObjects;
        deadObjects = new ArrayList<>();
        for (IGameObject object : objects) {
            object.update(container, delta);
            if (!object.isAlive()) {
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

    /*
    受け取ったobjectを追加リストに追加する
     */
    public void instantiate(IGameObject object) {
        pendingObjects.add(object);
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
            return;
        }
    }


}