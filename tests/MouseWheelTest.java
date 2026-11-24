import SetUp.Input.Mouse;
import java.awt.Canvas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseWheelTest {
    public static void main(String[] args) {
        Mouse mouse = new Mouse();
        Canvas source = new Canvas();
        for (int rotation : new int[] {-4, -1, 0, 1, 3}) {
            mouse.mouseWheelMoved(new MouseWheelEvent(source, MouseEvent.MOUSE_WHEEL,
                    0, 0, 0, 0, 0, false, MouseWheelEvent.WHEEL_UNIT_SCROLL, 3, rotation));
            if (mouse.isScrollingUp() != (rotation < 0)
                    || mouse.isScrollingDown() != (rotation > 0)) {
                throw new AssertionError("Wrong direction for wheel rotation " + rotation);
            }
            mouse.resetScroll();
            if (mouse.isScrollingUp() || mouse.isScrollingDown()) {
                throw new AssertionError("Reset left a scroll direction active");
            }
        }
        System.out.println("Wheel directions and resets passed for five rotations.");
    }
}
