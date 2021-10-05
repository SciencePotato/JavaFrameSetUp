package SetUp.Input;

import java.awt.event.*;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

    private int mouseX = -1;
    private int mouseY = -1;
    private int mouseB = -1;
    private int scroll = 0;

    public int getX() {
        return this.mouseX;
    }
    public int getY() {
        return this.mouseY;
    }
    public ClickType getButton() {
        return switch (this.mouseB) {
            case 1 -> ClickType.LeftClick;
            case 2 -> ClickType.ScrollClick;
            case 3 -> ClickType.RightClick;
            case 4 -> ClickType.ForwardPage;
            case 5 -> ClickType.BackwardPage;
            default -> ClickType.Unknown;
        };
    }

    public void resetButton() {
        this.mouseB = -1;
    }

    public boolean isScrollingUp() {
        return this.scroll == -1;
    }

    public boolean isScrollingDown() {
        return this.scroll == 1;
    }

    public void resetScroll() {
        this.scroll = 0;
    }

    public void mouseWheelMoved(MouseWheelEvent event) {
        // TODO Auto-generated method stub
        this.scroll = event.getWheelRotation();
    }

    public void mouseDragged(MouseEvent event) {
        // TODO Auto-generated method stub
        this.mouseX = event.getX();
        this.mouseY = event.getY();

    }

    public void mouseMoved(MouseEvent event) {
        // TODO Auto-generated method stub
        this.mouseX = event.getX();
        this.mouseY = event.getY();
    }

    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void mousePressed(MouseEvent event) {
        // TODO Auto-generated method stub
        this.mouseB = event.getButton();
    }

    public void mouseReleased(MouseEvent event) {
        // TODO Auto-generated method stub
        this.mouseB = -1;
    }

}
