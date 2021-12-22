package mario;
import java.util.LinkedList;

import java.awt.Graphics;
import mario.tile.Tile;
import mario.entity.Entity;
import mario.tile.Wall;

public class Handler {
	public LinkedList<Entity> entity = new LinkedList<>();
    public LinkedList<Tile> tile = new LinkedList<>();
    public Handler() {
        createLevel();
    }

    public void render(Graphics g) {
        for (Entity en:entity) {
            en.render(g);
        }
        for (Tile ti:tile) {
            ti.render(g);
        }
    }
    public void tick() {
        for (Entity en:entity) {
            en.tick();
        }
        for (Tile ti:tile) {
            ti.tick();
        }
    }
    public void addEntity(Entity en) {
        entity.add(en);
    }
    public void removeEntity(Entity en) {
        entity.remove(en);
    }
    public void addTile(Tile ti) {
        tile.add(ti);
    }
    public void removeTile(Tile ti) {
        tile.remove(ti);
    }

    public void createLevel() {
        for (int i = 0; i < Game.WIDTH * Game.SCALE / 64 + 1; i++) {
            addTile(new Wall (i * 64, Game.HEIGHT * Game.SCALE - 128, 64, 64, true, Id.wall, this));
            if (i != 0 && i != 1 && i != 5 && i != 6 && i != 7 && i != 13 && i != 14 && i != 15&& i != 16 && i != 17) {
                addTile(new Wall (i * 64, 300, 64, 64, true, Id.wall, this));

            }
        }
    }

}
