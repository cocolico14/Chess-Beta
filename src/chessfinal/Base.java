/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessfinal;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author soheil
 */
public class Base {

    private Cord c;
    private Rectangle base;
    private Color color;
    private int BaseStatus; // -1 While + 1 Black + 0 Empty 
    private int glazeIndex; // -1 empty + 0-31 glaze on it
    private boolean enPassant = false;

    /**
     *
     * @param c
     * @param root
     * @param color
     */
    public Base(Cord c, Group root, Color color) {
        this.c = c;
        this.color = color;
        this.BaseStatus = 0;
        base = new Rectangle();
        this.glazeIndex = -1;
        base.setX(c.getX() * Game.BLOCK_SIZE - (Game.BASE * 3.3));
        base.setY(c.getY() * Game.BLOCK_SIZE - (Game.BASE * 3.3));
        base.setWidth(Game.BLOCK_SIZE);
        base.setHeight(Game.BLOCK_SIZE);
        base.setFill(color);
        base.setSmooth(true);
        base.setStroke(Color.CADETBLUE);
        base.setStrokeWidth(2);
        root.getChildren().add(base);
        base.setOnMouseClicked(event -> {
            if (Game.getFocused() > -1 && Game.getFocusedE() == -1) {
                System.out.println(c);
                Game.getGoTo().set(c.getX(), c.getY());
                Game.getGlazes()[Game.getFocused()].getGlazeImg().setEffect(null);
            }else if(Game.getFocused() > -1 && Game.getFocusedE() > -1){
                System.out.println(c);
                Game.setFocusedE(-1);
                Game.getGoTo().set(c.getX(), c.getY());
                Game.getGlazes()[Game.getFocused()].getGlazeImg().setEffect(null);
            }
        });

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
     * @return base
     */
    public Rectangle getBase() {
        return base;
    }

    /**
     * getting status of base if (-1 While) or (1 Black) or (0 Empty)
     *
     * @return BaseStatus
     */
    public int getBStatus() {
        return this.BaseStatus;
    }

    /**
     * setting status of base if (-1 While) or (1 Black) or (0 Empty)
     *
     * @param bs
     */
    public void setBStatus(int bs) {
        this.BaseStatus = bs;
    }

    /**
     * setting glaze index if (-1 empty) or (0-31 glaze on it) 
     * 0-3 Rook + 4-7 Knight + 8-11 Bishop + 12-13 King + 14-15 Queen + 16-31 Pawn (First Half Black Second Half White)
     *
     * @param glazeIndex
     */
    public void setGIndex(int glazeIndex) {
        this.glazeIndex = glazeIndex;
    }

    /**
     * getting glaze index if (-1 empty) or (0-31 glaze on it) 
     * 0-3 Rook + 4-7 Knight + 8-11 Bishop + 12-13 King + 14-15 Queen + 16-31 Pawn (First Half Black Second Half White)
     *
     * @return glazeIndex
     */
    public int getGIndex() {
        return this.glazeIndex;
    }
    
    /**
     * changing enPassant status of a base
     * 
     * @param t
     */
    public void EnPassant(boolean t){
        this.enPassant = t;
    }
    
    /**
     *
     * @return enPassant
     */
    public boolean isEnPassant(){
        return this.enPassant;
    }
}
