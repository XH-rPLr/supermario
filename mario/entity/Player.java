package mario.entity;

import java.awt.Color;
import java.awt.Graphics;

import mario.Handler;
import mario.Id;
import mario.tile.Tile;
import mario.Game;


public class Player extends Entity {

	private int frame = 0;
	private int frameDelay = 0;

	private boolean animate = false;

	public Player(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
		
	}

	public void render(Graphics g) {
		g.drawImage(Game.player[frame].getBufferedImage(), x, y, width, height, null);
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if (y + height >= 771) y = 771 - height;
		if (velX != 0) animate = true;
		else animate = false;
		for (Tile t: handler.tile) {
			if (!t.solid) break;
			if (t.getId() == Id.wall) {
				if (getBoundsTop().intersects(t.getBounds())) {
					setVelY(0);
					if (jumping) {
						jumping = false;
						gravity = 0.1;
						falling = true;
					}
				
				}
				if (getBoundsBottom().intersects(t.getBounds())) {
					setVelY(0);
					if (falling) falling = false;
				} else {
					if (!falling && !jumping) {
						gravity = 0.2;
						falling = true;
					}
				}
				if (getBoundsLeft().intersects(t.getBounds())) {
					setVelX(0);
					x = t.getX() + t.width;
				}
				if (getBoundsRight().intersects(t.getBounds())) {
					setVelX(0);
					x = t.getX() - t.width;
				}
			}
		}
		if (jumping) {
			gravity -= 0.1;
			setVelY((int)-gravity);
			if (gravity <= 0.0) {
				jumping = false;
				falling = true;
			}
		}
		if (falling) {
			gravity += 0.1;
			setVelY((int)gravity);
		}

		if (animate) {
			frameDelay ++;
			if (frameDelay >= 50) {
				frame++;
				if (frame >= Game.player.length) {
					frame = 0;
				}
				frameDelay = 0;
		}
		}
		
	}

}
