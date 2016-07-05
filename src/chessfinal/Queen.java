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
public class Queen extends Glaze {

    private ArrayList<Cord> ValidM = new ArrayList<Cord>();
    private ArrayList<Cord> ValidK = new ArrayList<Cord>();

    /**
     *
     * @param WorB
     * @param c
     */
    public Queen(int WorB, Cord c) {
        super(WorB, c);
    }

    /**
     *
     * @param index
     * @param c
     * @param root
     * @param g
     */
    public Queen(int index, Cord c, Group root, Game g) {
        super(index, c, root, g);
        Image image;
        if (super.getWB() > 0) {
            image = new Image(getClass().getResourceAsStream("QueenB.png"));
        } else {
            image = new Image(getClass().getResourceAsStream("QueenW.png"));
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
        Boolean NW = false, NE = false, SW = false, SE = false, N = false, W = false, E = false, S = false;
        for (int i = 1; i <= 8; i++) {
            if ((v = new Cord(super.getCord().getX() + i, super.getCord().getY() + i)).isValid() && !SE) {
                ValidM.add(v);
                if (Game.getBase()[super.getCord().getX() + i][super.getCord().getY() + i].getBStatus() != 0) {
                    if(Game.getBase()[super.getCord().getX() + i][super.getCord().getY() + i].getBStatus() == (this.getWB()*(-1))){
                        ValidK.add(v);
                    }
                    SE = true;
                }
            }

            if ((v = new Cord(super.getCord().getX() - i, super.getCord().getY() + i)).isValid() && !SW) {
                ValidM.add(v);
                if (Game.getBase()[super.getCord().getX() - i][super.getCord().getY() + i].getBStatus() != 0) {
                    if(Game.getBase()[super.getCord().getX() - i][super.getCord().getY() + i].getBStatus() == (this.getWB()*(-1))){
                        ValidK.add(v);
                    }
                    SW = true;
                }
            }

            if ((v = new Cord(super.getCord().getX() + i, super.getCord().getY() - i)).isValid() && !NE) {
                ValidM.add(v);
                if (Game.getBase()[super.getCord().getX() + i][super.getCord().getY() - i].getBStatus() != 0) {
                    if(Game.getBase()[super.getCord().getX() + i][super.getCord().getY() - i].getBStatus() == (this.getWB()*(-1))){
                        ValidK.add(v);
                    }
                    NE = true;
                }
            }

            if ((v = new Cord(super.getCord().getX() - i, super.getCord().getY() - i)).isValid() && !NW) {
                ValidM.add(v);
                if (Game.getBase()[super.getCord().getX() - i][super.getCord().getY() - i].getBStatus() != 0) {
                    if(Game.getBase()[super.getCord().getX() - i][super.getCord().getY() - i].getBStatus() == (this.getWB()*(-1))){
                        ValidK.add(v);
                    }
                    NW = true;
                }
            }

            if ((v = new Cord(super.getCord().getX(), super.getCord().getY() - i)).isValid() && !N) {
                ValidM.add(v);
                if (Game.getBase()[super.getCord().getX()][super.getCord().getY() - i].getBStatus() != 0) {
                    if(Game.getBase()[super.getCord().getX()][super.getCord().getY() - i].getBStatus() == (this.getWB()*(-1))){
                        ValidK.add(v);
                    }
                    N = true;
                }
            }

            if ((v = new Cord(super.getCord().getX(), super.getCord().getY() + i)).isValid() && !S) {
                ValidM.add(v);
                if (Game.getBase()[super.getCord().getX()][super.getCord().getY() + i].getBStatus() != 0) {
                    if(Game.getBase()[super.getCord().getX()][super.getCord().getY() + i].getBStatus() == (this.getWB()*(-1))){
                        ValidK.add(v);
                    }
                    S = true;
                }
            }

            if ((v = new Cord(super.getCord().getX() - i, super.getCord().getY())).isValid() && !W) {
                ValidM.add(v);
                if (Game.getBase()[super.getCord().getX() - i][super.getCord().getY()].getBStatus() != 0) {
                    if(Game.getBase()[super.getCord().getX() - i][super.getCord().getY()].getBStatus() == (this.getWB()*(-1))){
                        ValidK.add(v);
                    }
                    W = true;
                }
            }

            if ((v = new Cord(super.getCord().getX() + i, super.getCord().getY())).isValid() && !E) {
                ValidM.add(v);
                if (Game.getBase()[super.getCord().getX() + i][super.getCord().getY()].getBStatus() != 0) {
                    if(Game.getBase()[super.getCord().getX() + i][super.getCord().getY()].getBStatus() == (this.getWB()*(-1))){
                        ValidK.add(v);
                    }
                    E = true;
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
                System.out.println("cant move it");
            } else {
                super.getGlazeImg().setX(super.getGlazeImg().getX() + (dx * Game.BLOCK_SIZE));
                super.getGlazeImg().setY(super.getGlazeImg().getY() + (dy * Game.BLOCK_SIZE));
                Game.setTurn(Game.getTurn()*(-1));
                Game.setMoveCount(Game.getMoveCount()+1);
            }

        }
    }

    /**
     * getting Valid moves
     *
     * @return ValidM
     */
    @Override
    public ArrayList<Cord> getValidM() {
        return this.ValidM;
    }
    
    /**
     * getting Valid kills
     *
     * @return ValidK
     */
    @Override
    public ArrayList<Cord> getValidK() {
        return this.ValidK;
    }
    
}
