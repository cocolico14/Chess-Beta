/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessfinal;

/**
 *
 * @author soheil
 */
public class Cord {

    private int x, y;

    public Cord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isValid() {
        if (this.x < 1 || this.x > 8 || this.y < 1 || this.y > 8) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return x + "; " + y;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Cord){
            Cord c = (Cord) obj;
            if(this.x == c.x && this.y == c.y){
                return true;
            }
        }
        return false;
    }
    
    

}
