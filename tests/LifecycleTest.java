import SetUp.Main;
import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import sun.misc.Unsafe;

public class LifecycleTest {
    static class LoopCanvas extends Main {
        CountDownLatch updated;
        @Override public void update() { updated.countDown(); }
        @Override public void render() {}
    }
    static class SelfStoppingCanvas extends Main {
        CountDownLatch stopped;
        @Override public void update() { stop(); stopped.countDown(); }
        @Override public void render() {}
    }

    public static void main(String[] args) throws Exception {
        // Avoid JFrame construction only; start/run/stop are the real implementation.
        Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        LoopCanvas canvas = (LoopCanvas) unsafe.allocateInstance(LoopCanvas.class);
        canvas.updated = new CountDownLatch(1);
        canvas.start();
        if (!canvas.updated.await(1, TimeUnit.SECONDS)) throw new AssertionError("Loop did not update");
        Thread stopper = new Thread(canvas::stop);
        stopper.setDaemon(true);
        stopper.start();
        stopper.join(1000);
        if (stopper.isAlive()) throw new AssertionError("stop() deadlocked");
        canvas.updated = new CountDownLatch(1);
        canvas.start();
        if (!canvas.updated.await(1, TimeUnit.SECONDS)) throw new AssertionError("Loop did not restart");
        canvas.stop();
        SelfStoppingCanvas self = (SelfStoppingCanvas) unsafe.allocateInstance(SelfStoppingCanvas.class);
        self.stopped = new CountDownLatch(1);
        self.start();
        if (!self.stopped.await(1, TimeUnit.SECONDS)) throw new AssertionError("Loop joined itself");
        self.stop();
        System.out.println("Headless start/stop/restart passed");
    }
}
