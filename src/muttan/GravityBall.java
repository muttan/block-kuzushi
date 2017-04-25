package muttan;

import org.newdawn.slick.GameContainer;

/**
 * Created by muttan on 2017/03/02.
 */
public class GravityBall extends Ball {
    public GravityBall(float initial_x, float initial_y, float initial_dx, float initial_dy, int sprite_number) {
        super(initial_x, initial_y, initial_dx, initial_dy, sprite_number);
    }

    @Override

    public void update(GameContainer container, int delta) {
      /*
        dy += 0.001;
        x += dx * delta;
        y += dy * delta;
        if (x >= 626) {
            dx *= -1;
            x = 626 - (x - 626);
        } else if (x <= 0) {
            dx *= -1;
            x = -x;
        }
        if (y >= 460) {
            dy *= -1;
            y = 460 - (y - 460);
        } else if (y <= 0) {
            dy *= -1;
            y = -y;
        }
        */
    }
}
