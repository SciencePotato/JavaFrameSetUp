package SetUp;

import SetUp.Input.Keyboard;
import SetUp.Input.Mouse;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serial;

public class Main extends Canvas implements Runnable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Thread thread;
    private JFrame frame;
    private static String title = "Title";
    private final static int WIDTH = 800;
    private final static int HEIGHT = 600;
    private volatile boolean running = false;

    private Mouse mouse;
    private Keyboard keyboard;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main display = new Main();
            display.frame.setTitle("Display");
            display.frame.add(display);
            display.frame.pack();
            display.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            display.frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent event) {
                    display.stop();
                }
            });
            display.frame.setLocationRelativeTo(null);
            display.frame.setResizable(false);
            display.frame.setVisible(true);

            display.start();
        });
    }

    public Main() {
        this.frame = new JFrame();

        Dimension size = new Dimension(WIDTH, HEIGHT);
        this.setPreferredSize(size);

        this.mouse = new Mouse();

        this.addMouseListener(this.mouse);
        this.addMouseMotionListener(this.mouse);
        this.addMouseWheelListener(this.mouse);

        this.keyboard = new Keyboard();
        this.addKeyListener(this.keyboard);
    }

    public synchronized void start(){
        if (running || (this.thread != null && this.thread.isAlive())) return;
        running = true;
        this.thread = new Thread(this, "Display");
        this.thread.setDaemon(true);
        this.thread.start();
    }

    public void stop(){
        Thread threadToJoin;
        synchronized (this) {
            running = false;
            threadToJoin = this.thread;
        }
        if (threadToJoin == null || threadToJoin == Thread.currentThread()) return;
        try {
            threadToJoin.join();
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    public void run() {
        /*
            Calculates the Delta time, so we won't base everything on FPS
            but rather time passed.
         */
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60;
        double delta = 0;
        int frames = 0;

        try {
            while(running) {
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;
                while(delta >= 1) {
                    update();
                    delta--;
                    render();
                    frames++;
                }

                if(System.currentTimeMillis() - timer > 1000) {
                    timer += 1000;
                    int framesPerSecond = frames;
                    SwingUtilities.invokeLater(() -> this.frame.setTitle(title + " | " + framesPerSecond + " fps"));
                    frames = 0;
                }
            }
        } finally {
            running = false;
        }
    }

    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.dispose();
        bs.show();
    }

    /*
        update Updates the simulations, it's not a fixed update but a loop update
     */
    public void update(){

    }
}
