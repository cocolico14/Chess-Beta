/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessfinal;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;

/**
 *
 * @author soheil
 */
public class Glaze implements Runnable {

    private Cord c;
    private Game g;
    private boolean isAlive = true;
    private int Danger;
    private ImageView Glaze;
    private int index; // 0-3 Rook + 4-7 Knight + 8-11 Bishop + 12-13 King + 14-15 Queen + 16-31 Pawn (First Half Black Second Half White)
    private int WorB; // 1 = Black + -1 = White
    private static int Wkilled;
    private static int Bkilled;

    /**
     *
     * @param WorB
     * @param c
     */
    public Glaze(int WorB, Cord c) {
        this.WorB = WorB;
        this.c = c;
    }

    /**
     *
     * @param index
     * @param c
     * @param root
     * @param g
     */
    public Glaze(int index, Cord c, Group root, Game g) {
        this.c = c;
        this.index = index;
        this.g = g;
        if (index < 2 || (index >= 4 && index < 6) || (index >= 8 && index < 10) || (index == 12) || (index == 14) || (index >= 16 && index < 24)) {
            WorB = 1;
        } else {
            WorB = -1;
        }
        Glaze = new ImageView();
        final Effect glow = new Glow(2.0);
        Glaze.setX(c.getX() * Game.BLOCK_SIZE - (3.3 * Game.BASE));
        Glaze.setY(c.getY() * Game.BLOCK_SIZE - (3.3 * Game.BASE));
        Glaze.setPreserveRatio(true);
        Glaze.setCache(true);
        root.getChildren().add(Glaze);

        Glaze.setOnMouseClicked(event -> {
            if (this.isAlive) {
                if (Game.getFocused() == -1 && this.getWB() == Game.getTurn()) {
                    //    System.out.println("Focused on == " + this.index);
                    Game.setFocused(this.index);
                    Glaze.setEffect(glow);
                } else if (Game.getFocused() > -1 && Game.getFocusedE() == -1 && Game.getGlazes()[Game.getFocused()].getWB() != this.WorB
                        && (Game.getGlazes()[Game.getFocused()].getWB() == Game.getTurn())) {
                    //    System.out.println("Enamy Glaze == " + this.index);
                    Game.setGoTo(this.c);
                    Game.setFocusedE(this.index);
                    Game.getGlazes()[Game.getFocused()].getGlazeImg().setEffect(null);
                    Glaze.setEffect(null);
                } else if (Game.getFocused() > -1 && Game.getGlazes()[Game.getFocused()].getWB() == this.WorB && this.getWB() == Game.getTurn()) {
                    //    System.out.println("Focused on == " + this.index);
                    Game.getGlazes()[Game.getFocused()].getGlazeImg().setEffect(null);
                    Game.setFocused(this.index);
                    Game.setFocusedE(-1);
                    Glaze.setEffect(glow);
                }
            }
        });

        Glaze.setFocusTraversable(true);

    }

    /**
     *
     * @return c
     */
    public Cord getCord() {
        return this.c;
    }

    /**
     *
     * @param m
     */
    public void setCord(Cord m) {
        this.c = m;
    }

    /**
     *
     * @return isAlive
     */
    public boolean isIsAlive() {
        return isAlive;
    }

    /**
     *
     * @param isAlive
     */
    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    /**
     * index of glaze represent 0-3 Rook + 4-7 Knight + 8-11 Bishop + 12-13 King + 14-15 Queen + 16-31 Pawn (First Half Black Second Half White)
     * 
     * @return index
     */
    public int getIndex() {
        return index;
    }

    /**
     *
     * @return Glaze
     */
    public ImageView getGlazeImg() {
        return this.Glaze;
    }

    /**
     * White Black of glaze represent the alliance (1 for black) and (-1 for white)
     * 
     * @return WorB
     */
    public int getWB() {
        return this.WorB;
    }

    /**
     * return the Game that relate to the glaze set
     * 
     * @return g
     */
    public Game getG() {
        return g;
    }

    /**
     * kill that Glaze and move the pic to up right corner
     * 
     */
    public void killGlaze() {
        if (this.isAlive) {
            this.isAlive = !isAlive;
            if (this.WorB == 1) {
                this.Glaze.setScaleX(0.5);
                this.Glaze.setScaleY(0.5);
                this.Glaze.setX((Game.GRID_LENGTH * Game.BLOCK_SIZE));
                this.Glaze.setY((Bkilled * (Game.BLOCK_SIZE / 2)) + (Game.BASE));
                this.Glaze.toFront();
                Bkilled++;
            } else {
                this.Glaze.setScaleX(0.5);
                this.Glaze.setScaleY(0.5);
                this.Glaze.setX((Game.GRID_LENGTH * Game.BLOCK_SIZE + (Game.BLOCK_SIZE / 2) + 5));
                this.Glaze.setY((Wkilled * (Game.BLOCK_SIZE / 2)) + (Game.BASE));
                this.Glaze.toFront();
                Wkilled++;
            }
        }
    }

    /**
     *
     */
    public void run() {
    }

    /**
     *
     * @param to
     */
    public void move(Cord to) {
    }

    /**
     *
     * @return
     */
    public ArrayList<Cord> getValidM() {
        return null;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Cord> getValidK() {
        return null;
    }
    
}
