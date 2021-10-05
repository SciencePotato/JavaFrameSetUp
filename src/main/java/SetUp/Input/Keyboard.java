package SetUp.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    private int keyBut = -1;

    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        if(e.getKeyCode() == KeyEvent.VK_C)
            keyBut = KeyEvent.VK_C;
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            keyBut = KeyEvent.VK_RIGHT;
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
            keyBut = KeyEvent.VK_LEFT;
        if(e.getKeyCode() == KeyEvent.VK_UP)
            keyBut = KeyEvent.VK_UP;
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            keyBut = KeyEvent.VK_DOWN;
    }

    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    public int getKey() {
        return this.keyBut;
    }

    public void keyRelease() {
        this.keyBut = -1;
    }

}