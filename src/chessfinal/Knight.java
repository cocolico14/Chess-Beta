/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessfinal;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.image.Image;

/**
 *
 * @author soheil
 */
public class Knight extends Glaze {

    private ArrayList<Cord> ValidM = new ArrayList<Cord>();
    private ArrayList<Cord> ValidK = new ArrayList<Cord>();
    
    /**
     *
     * @param WorB
     * @param c
     */
    public Knight(int WorB, Cord c){
        super(WorB, c);
    }

    /**
     *
     * @param index
     * @param c
     * @param root
     * @param g
     */
    public Knight(int index, Cord c, Group root, Game g) {
        super(index, c, root, g);
        Image image;
        if (super.getWB() > 0) {
            image = new Image(getClass().getResourceAsStream("KnightB.png"));
        } else {
            image = new Image(getClass().getResourceAsStream("KnightW.png"));
        }
        super.getGlazeImg().setImage(image);
    }

    /**
     *
     */
    @Override
    public void run() {
        ValidM.clear();
        ValidM.trimToSize();
        ValidK.clear();
        ValidK.trimToSize();
        Cord v;
        if ((v = new Cord(super.getCord().getX() + 2, super.getCord().getY() + 1)).isValid()) {
            if (Game.getBase()[super.getCord().getX() + 2][super.getCord().getY() + 1].getBStatus() != super.getWB()) {
                ValidM.add(v);
                if(Game.getBase()[super.getCord().getX() + 2][super.getCord().getY() + 1].getBStatus() == (super.getWB()*(-1))){
                    ValidK.add(v);
                }
            }
        }
        if ((v = new Cord(super.getCord().getX() - 2, super.getCord().getY() + 1)).isValid()) {
            if (Game.getBase()[super.getCord().getX() - 2][super.getCord().getY() + 1].getBStatus() != super.getWB()) {
                ValidM.add(v);
                if(Game.getBase()[super.getCord().getX() - 2][super.getCord().getY() + 1].getBStatus() == (super.getWB()*(-1))){
                    ValidK.add(v);
                }
            }
        }
        if ((v = new Cord(super.getCord().getX() + 2, super.getCord().getY() - 1)).isValid()) {
            if (Game.getBase()[super.getCord().getX() + 2][super.getCord().getY() - 1].getBStatus() != super.getWB()) {
                ValidM.add(v);
                if(Game.getBase()[super.getCord().getX() + 2][super.getCord().getY() - 1].getBStatus() == (super.getWB()*(-1))){
                    ValidK.add(v);
                }
            }
        }
        if ((v = new Cord(super.getCord().getX() - 2, super.getCord().getY() - 1)).isValid()) {
            if (Game.getBase()[super.getCord().getX() - 2][super.getCord().getY() - 1].getBStatus() != super.getWB()) {
                ValidM.add(v);
                if(Game.getBase()[super.getCord().getX() - 2][super.getCord().getY() - 1].getBStatus() == (super.getWB()*(-1))){
                    ValidK.add(v);
                }
            }
        }
        if ((v = new Cord(super.getCord().getX() + 1, super.getCord().getY() + 2)).isValid()) {
            if (Game.getBase()[super.getCord().getX() + 1][super.getCord().getY() + 2].getBStatus() != super.getWB()) {
                ValidM.add(v);
                if(Game.getBase()[super.getCord().getX() + 1][super.getCord().getY() + 2].getBStatus() == (super.getWB()*(-1))){
                    ValidK.add(v);
                }
            }
        }
        if ((v = new Cord(super.getCord().getX() - 1, super.getCord().getY() + 2)).isValid()) {
            if (Game.getBase()[super.getCord().getX() - 1][super.getCord().getY() + 2].getBStatus() != super.getWB()) {
                ValidM.add(v);
                if(Game.getBase()[super.getCord().getX() - 1][super.getCord().getY() + 2].getBStatus() == (super.getWB()*(-1))){
                    ValidK.add(v);
                }
            }
        }
        if ((v = new Cord(super.getCord().getX() + 1, super.getCord().getY() - 2)).isValid()) {
            if (Game.getBase()[super.getCord().getX() + 1][super.getCord().getY() - 2].getBStatus() != super.getWB()) {
                ValidM.add(v);
                if(Game.getBase()[super.getCord().getX() + 1][super.getCord().getY() - 2].getBStatus() == (super.getWB()*(-1))){
                    ValidK.add(v);
                }
            }
        }
        if ((v = new Cord(super.getCord().getX() - 1, super.getCord().getY() - 2)).isValid()) {
            if (Game.getBase()[super.getCord().getX() - 1][super.getCord().getY() - 2].getBStatus() != super.getWB()) {
                ValidM.add(v);
                if(Game.getBase()[super.getCord().getX() - 1][super.getCord().getY() - 2].getBStatus() == (super.getWB()*(-1))){
                    ValidK.add(v);
                }
            }
        }
    }

    /**
     * 
     * @param to
     */
    @Override
    public void move(Cord to) {
        Cord prePl = new Cord(this.getCord().getX(), this.getCord().getY()); //backup pre cord whether the moves cause a check for friednly king
        boolean Checked = false;
        if (ValidM.contains(to) && to.getX() != -1 && Game.getBase()[to.getX()][to.getY()].getBStatus() != super.getWB()) {
            int dx = to.getX() - super.getCord().getX();
            int dy = to.getY() - super.getCord().getY();
            if (Game.getFocusedE() > 0) {
                Game.getGlazes()[Game.getFocusedE()].killGlaze();
            } else if (Game.getBase()[to.getX()][to.getY()].getGIndex() != -1) {
                Game.getGlazes()[Game.getBase()[to.getX()][to.getY()].getGIndex()].killGlaze();
            }
            Game.getBase()[super.getCord().getX()][super.getCord().getY()].setBStatus(0);
            Game.getBase()[to.getX()][to.getY()].setBStatus(this.getWB());
            Game.getBase()[super.getCord().getX()][super.getCord().getY()].setGIndex(-1);
            Game.getBase()[to.getX()][to.getY()].setGIndex(Game.getFocused());
            super.getCord().set(to.getX(), to.getY());
            
            Game.checkGen();
            if (this.getWB() == 1) {
                Checked = Game.getKingB().isChecked();
            } else if (this.getWB() == -1) {
                Checked = Game.getKingW().isChecked();
            }

            if (Checked) {
                Game.getBase()[super.getCord().getX()][super.getCord().getY()].setBStatus(0);
                Game.getBase()[prePl.getX()][prePl.getY()].setBStatus(this.getWB());
                Game.getBase()[to.getX()][to.getY()].setGIndex(-1);
                Game.getBase()[prePl.getX()][prePl.getY()].setGIndex(Game.getFocused());
                super.getCord().set(prePl.getX(), prePl.getY());
            } else {
                super.getGlazeImg().setX(super.getGlazeImg().getX() + (dx * Game.BLOCK_SIZE));
                super.getGlazeImg().setY(super.getGlazeImg().getY() + (dy * Game.BLOCK_SIZE));
                Game.setTurn(Game.getTurn()*(-1));
                Game.setMoveCount(Game.getMoveCount()+1);
            }
        }
    }

    /**
     * for getting Valid moves
     * 
     * @return ValidM
     */
    @Override
    public ArrayList<Cord> getValidM() {
        return this.ValidM;
    }
    
    /**
     * for getting Valid kills
     * 
     * @return ValidK
     */
    @Override
    public ArrayList<Cord> getValidK() {
        return this.ValidK;
    }

}
