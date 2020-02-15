package hunting;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by rick on 24.04.17.
 */
public class HuntHandler extends JFrame
            implements ChangeListener, KeyListener {
        private ArrayList<Bullet> bullets;
        private ArrayList<Target> targets;
        private Hunter hunter;
        static final int width = 1200;
        static final int height = 700;


    public static void main(String[] args) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
            SwingUtilities.invokeLater(() -> {
                HuntHandler frame = new HuntHandler();
                frame.setVisible(true);
                frame.setResizable(false);
                frame.setSize(new Dimension(width, height));
                frame.setTitle("Hunting game");
            });
        }

        HuntHandler() {
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
            targets = new ArrayList<>();
            bullets = new ArrayList<>();

            for(int i = 0; i < 20; i++){
                int x = new Random().nextInt((width - 2*MainPanel.targetWidth));
                int y = new Random().nextInt((height - 2*MainPanel.targetHeight));
                targets.add(new Target(x, y));
            }

            hunter = new Hunter(width / 2, height - 150, bullets);
            MainPanel panel = new MainPanel(targets, hunter);
            add(panel);
            addKeyListener(this);

            for(Target t: targets){
                t.start();
            }

            pack();
        }



        @Override
        public void keyReleased(KeyEvent e) {

        }
        @Override
        public void keyTyped(KeyEvent e){

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int location = e.getKeyCode();

            if (location == KeyEvent.VK_LEFT) {
                hunter.left();
            } else if (location == KeyEvent.VK_RIGHT) {
                hunter.right();
            } else if(location == KeyEvent.VK_SPACE){
                hunter.shoot();
            }
        }

        public void stateChanged(ChangeEvent e) {
            Object source = e.getSource();
        }
}
