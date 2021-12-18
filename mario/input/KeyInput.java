package mario.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import mario.entity.Entity;
import mario.Game;

public class KeyInput implements KeyListener {


    public void keyTyped(KeyEvent e) {
        //Not Using
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        for (Entity en: Game.handler.entity) {
            switch(key) {
                case KeyEvent.VK_W:
                    if (!en.jumping) {
                        en.jumping = true;
                        en.gravity = 10.0;
                    }
                    break;
                case KeyEvent.VK_A:
                    en.setVelX(-1);
                    break;
                case KeyEvent.VK_D:
                    en.setVelX(1);
                    break;
            }
        }
        
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        for (Entity en: Game.handler.entity) {
            switch(key) {
                case KeyEvent.VK_W:
                    en.setVelY(0);
                    break;
                case KeyEvent.VK_S:
                    en.setVelY(0);
                    break;
                case KeyEvent.VK_A:
                    en.setVelX(0);
                    break;
                case KeyEvent.VK_D:
                    en.setVelX(0);
                    break;
            }
        }
    }

}
