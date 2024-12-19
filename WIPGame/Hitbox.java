public class Hitbox {
    /** the 2D coordinates of the center of this hitbox [x,y] */
    private float[] coordinates;
    /** the width of the box */
    private float width;
    /** the height of the box */
    private float height;

    /**
     * Creates a new Hitbox object based on the given parameters.
     * 
     * @param x,      the x-coordinate of the center of the hitbox
     * @param y,      the y-coordinate of the center of the hitbox
     * @param width,  the width of the hitbox
     * @param height, the height of the hitbox
     * @throws IllegalStateException if processing is null
     * @author Michelle
     */
    public Hitbox(float x, float y, float width, float height) {
        this.coordinates = new float[] { x, y };
        this.width = width;
        this.height = height;
    }

    /**
     * Changes the coordinates of the center of this Hitbox
     * 
     * @param x, the new x-coordinate of the Hitbox's center
     * @param y, the new y-coordinate of the Hitbox's center
     * @author Michelle
     */
    public void setPosition(float x, float y) {
        this.coordinates[0] = x;
        this.coordinates[1] = y;
    }

    /**
     * Detects if this Hitbox and another collide by overlapping.
     * 
     * @param other, the Hitbox to check for if it collides with this one
     * @return true if the 2 Hitboxes overlap, false otherwise
     * @author Luke Schram
     */
    public boolean doesCollide(Hitbox other) {
        // if right side of other object is passed left side of this object
        // AND if left side of other object is not passed right side of this object
        if ((other.coordinates[0] + other.width / 2) > (coordinates[0] - width / 2)
                && (other.coordinates[0] - other.width / 2) < (coordinates[0] + width / 2)) {
            // if bottom of other object is passed top of this object
            // AND top of other object is not passed bottom of this object
            if ((other.coordinates[1] + other.height / 2) > (coordinates[1] - height / 2)
                    && (other.coordinates[1] - other.height / 2) < (coordinates[1] + height / 2)) {
                return true; // objects are colliding
            }
        }
        return false; // objects not colliding
    }

    /**
     * Change both the width and height of this Hitbox
     * 
     * @param width,  the new width for the Hitbox
     * @param height, the new height for the Hitbox
     * @author Michelle
     */
    public void changeDimensions(float width, float height) {
        this.height = height;
        this.width = width;
    }

    /**
     * Draws the Hitbox to the screen. Provided solely for visualization and
     * debugging purposes.
     * 
     * @author Michelle
     */
    public int[] visualizeHitbox() {
        int[] hitboxToDraw = {(int)(coordinates[0] - (width / 2)), (int)(coordinates[1] - (height / 2))};
        return hitboxToDraw;
    }
}
