package com.knu.ynortman;

public class Bullet extends Thread {
    private int x;
    private int y;
    private boolean running = true;

    Bullet(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() {
        super.run();
        while(running && y > 0 ) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            y -= 30;
        }
    }

    void stopThread(){
        running = false;
    }

    boolean getRunning() {
        return running;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }
}
