package MultiplyingSquaresGame;

public class Square {
    private int xPos; 
    private int yPos;
    private int xVel;
    private int yVel;

    public Square(int xVel, int yVel) {
        xPos = 350;
        yPos = 200;
        this.xVel = xVel;
        this.yVel = yVel;
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
    
}

