package mario;

	import java.awt.Canvas;
	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.Graphics;
	import java.awt.image.BufferStrategy;
	import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.*;

	import javax.swing.JFrame;

import org.w3c.dom.css.RGBColor;

import mario.entity.Entity;
import mario.entity.Player;
	import mario.graphics.SpriteSheet;
	import mario.graphics.Sprite;
import mario.input.KeyInput;
import mario.tile.Wall;

	public class Game extends Canvas implements Runnable {
	    public static final int WIDTH = 270;
	    public static final int HEIGHT = WIDTH/14*10;
	    public static final int SCALE = 4;
	    public static final String TITLE = "Mario";

	    private Thread thread;
	    private boolean running = false;
		private BufferedImage image;

	    public static Handler handler;
		public static SpriteSheet sheet;
		public static Camera cam;

		public static Sprite grass;
		public static Sprite player[] = new Sprite[4];
	    
	    public Game() {
	        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
	        setPreferredSize(size);
	        setMaximumSize(size);
	        setMinimumSize(size);
	    }
	    
	    private void init() {
	    	handler = new Handler();
			sheet = new SpriteSheet("/resources/SpriteSheet.png");
			cam = new Camera();

			addKeyListener(new KeyInput());

			grass = new Sprite(sheet, 5, 1);

			for (int i = 0; i < player.length; i++) {
				player[i] = new Sprite(sheet, i + 1 , 1);
			}
			
	    	//handler.addEntity(new Player(300, 520, 64, 64, true, Id.player, handler));

			try {
				image = ImageIO.read(getClass().getResource("/resources/level.png"));
			} catch (IOException e) { 
				e.printStackTrace();
			}

			handler.createLevel(image);
	    	
	    }
	    
	    public synchronized void start() {
	         if (running) return;
	         running = true;
	         thread = new Thread(this, "Thread");
	         thread.start();
	    }

	    public synchronized void stop() {
	        if (!running) return;
	        running = false;
	        try {
	            thread.join();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

	    public void run() {
	    	init();
			requestFocus();
	        long lastTime = System.nanoTime();
	        long timer = System.currentTimeMillis();
	        double delta = 0.0;
	        double ns = 100000000.0/60.0;
	        int frames = 0;
	        int ticks = 0;
	        while(running) {
	            long now = System.nanoTime();
	            delta+=(now-lastTime)/ns;
	            lastTime = now;
	            while(delta >= 1) {
	                tick();
	                ticks++;
	                delta--;
	            }
	            render();
	            frames++;
	            if(System.currentTimeMillis()-timer > 1000) {
	                timer += 1000;
	                frames = 0;
	                ticks = 0;
	            }
	        }
	        stop();
	    }
	    
	    public void render() {
	        BufferStrategy bs = getBufferStrategy();
	        if (bs == null) {
	            createBufferStrategy(3);
	            return;
	        }
	        Graphics g = bs.getDrawGraphics();
	        g.setColor(new Color (135, 206, 235));
	        g.fillRect(0, 0, getWidth(), getHeight());
			g.translate(cam.getX(), cam.getY());
			handler.render(g);
	        
	        g.dispose();
	        bs.show();
	    }

	    public void tick() {
			handler.tick();
			
			for (Entity e: handler.entity) {
				if (e.getId() == Id.player) {
					cam.tick(e);
				}
			}
	    }

		public static int getFrameWidth() {
			return WIDTH * SCALE / 2;
		}

		public static int getFrameHeight() {
			return HEIGHT * SCALE / 2;
		}

	    public static void main(String[] args) {
	        Game game = new Game();
	        JFrame frame = new JFrame(TITLE);
	        frame.add(game);
	        frame.pack();
	        frame.setResizable(false);
	        frame.setLocationRelativeTo(null);
	        // put frame in the middle of the screen
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        //close window when you click x button
	        frame.setVisible(true);
	        game.start();
	    }
	}

