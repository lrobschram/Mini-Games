public class Square {
    private int xPos; 
    private int yPos;
    private int xVel;
    private int yVel;
    private int[] widXhi;
    private Hitbox hitbox;

    public Square( int xPos, int yPos, int xVel, int yVel, int[] widXhi) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xVel = xVel;
        this.yVel = yVel;
        this.widXhi = widXhi;

        hitbox = new Hitbox(xPos + (widXhi[0] / 2), yPos + (widXhi[1] / 2), widXhi[0], widXhi[1]);
    }

    public int getXPos() {
        return this.xPos;
    }
    public int getYPos() {
        return this.yPos;
    }
    public int getXVel() {
        return this.xVel;
    }
    public int getYVel() {
        return this.yVel;
    }

    public void setXPos(int x) {
        this.xPos = x;
    }
    public void setYPos(int y) {
        this.yPos = y;
    }
    public void setXVel(int x) {
        this.xVel = x;
    }
    public void setYVel(int y) {
        this.yVel = y;
    }

    public Hitbox getHitbox() {
		return hitbox;
	}

    public void updateHitbox(int x, int y) {
        this.hitbox.setPosition(x + (widXhi[0] / 2), y + (widXhi[1] / 2));
    }
    
}
