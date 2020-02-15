package hunting;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by rick on 24.04.17.
 */

public class Target extends Thread {

    private int x;
    private int y;
    private int direction;
    private boolean alive = true;

    Target(int x, int y){
        this.x = x;
        this.y = y;
    }

    void update(ArrayList<Bullet> bullets){
        for (Bullet bullet : bullets) {
            if (new Rectangle(x, y, MainPanel.targetWidth, MainPanel.targetHeight).contains(bullet.getX(), bullet.getY())) {
                alive = false;
                bullet.stopThread();
            }
        }
    }

    @Override
    public void run() {
        super.run();
        Random r = new Random();
        int speed = 2 + r.nextInt(5);
        direction = (r.nextInt()%2 == 0)? -1: 1;

        while(alive) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(x < HuntHandler.width-MainPanel.targetWidth && x >= 0)
                x += speed*direction;
            else {
                if(direction == 1)
                    x = 0;
                else
                    x = HuntHandler.width-(MainPanel.targetWidth+1);

                speed = 2 + r.nextInt(5);
            }
        }
    }

    public void setAlive(boolean alive){
        this.alive = alive;
    }

    public boolean getAlive(){
        return alive;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
