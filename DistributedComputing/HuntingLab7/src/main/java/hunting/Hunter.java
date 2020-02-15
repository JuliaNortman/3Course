package hunting;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by rick on 24.04.17.
 */
public class Hunter {
    private int x;
    private int y;
    ArrayList<Bullet> bullets;

    Hunter(int x, int y, ArrayList<Bullet> bullets){
        this.x = x;
        this.y = y;
        this.bullets = bullets;
    }

    public void removeBullet(int b){
        synchronized (bullets){
            bullets.remove(b);
        }
    }

    public void shoot(){
        Bullet b = new Bullet(x+50, y+40);
        bullets.add(b);
        b.start();
    }

    public void left(){
        if(x >= 5)
            x-=10;
    }
    public void right(){
        if(x < HuntHandler.width - 110)
            x+=10;
    }
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public ArrayList<Bullet> getBullets(){
        return bullets;
    }
}
