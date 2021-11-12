package mario.entity;

import java.awt.Color;
import java.awt.Graphics;

import mario.Handler;
import mario.Id;

public class Player extends Entity {

	public Player(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
	}

	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, width, height);
	}

	public void tick() {
		
	}

}
