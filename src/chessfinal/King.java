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
public class King extends Glaze {

    private ArrayList<Cord> ValidM = new ArrayList<Cord>();
    private ArrayList<Cord> unSafe = new ArrayList<Cord>();
    private ArrayList<Cord> ValidK = new ArrayList<Cord>();
    private boolean Checked = false;
    private boolean Mated = false;
    private boolean CheckMate = false;
    private boolean castlble = false;
    private CheckMate CM;

    /**
     *
     * @param WorB
     * @param c
     */
    public King(int WorB, Cord c) {
        super(WorB, c);
    }

    /**
     *
     * @param index
     * @param c
     * @param root
     * @param g
     */
    public King(int index, Cord c, Group root, Game g) {
        super(index, c, root, g);
        Image image;
        if (super.getWB() > 0) {
            image = new Image(getClass().getResourceAsStream("KingB.png"));
        } else {
            image = new Image(getClass().getResourceAsStream("KingW.png"));
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
        if ((v = new Cord(super.getCord().getX() + 1, super.getCord().getY() + 1)).isValid()) {
            if (Game.getBase()[super.getCord().getX() + 1][super.getCord().getY() + 1].getBStatus() != this.getWB()) {
                ValidM.add(v);
                if (Game.getBase()[super.getCord().getX() + 1][super.getCord().getY() + 1].getBStatus() == (this.getWB() * (-1))) {
                    ValidK.add(v);
                }
            }
        }

        if ((v = new Cord(super.getCord().getX() - 1, super.getCord().getY() + 1)).isValid()) {
            if (Game.getBase()[super.getCord().getX() - 1][super.getCord().getY() + 1].getBStatus() != this.getWB()) {
                ValidM.add(v);
                if (Game.getBase()[super.getCord().getX() - 1][super.getCord().getY() + 1].getBStatus() == (this.getWB() * (-1))) {
                    ValidK.add(v);
                }
            }
        }

        if ((v = new Cord(super.getCord().getX() + 1, super.getCord().getY() - 1)).isValid()) {
            if (Game.getBase()[super.getCord().getX() + 1][super.getCord().getY() - 1].getBStatus() != this.getWB()) {
                ValidM.add(v);
                if (Game.getBase()[super.getCord().getX() + 1][super.getCord().getY() - 1].getBStatus() == (this.getWB() * (-1))) {
                    ValidK.add(v);
                }
            }
        }

        if ((v = new Cord(super.getCord().getX() - 1, super.getCord().getY() - 1)).isValid()) {
            if (Game.getBase()[super.getCord().getX() - 1][super.getCord().getY() - 1].getBStatus() != this.getWB()) {
                ValidM.add(v);
                if (Game.getBase()[super.getCord().getX() - 1][super.getCord().getY() - 1].getBStatus() == (this.getWB() * (-1))) {
                    ValidK.add(v);
                }
            }
        }

        if ((v = new Cord(super.getCord().getX(), super.getCord().getY() - 1)).isValid()) {
            if (Game.getBase()[super.getCord().getX()][super.getCord().getY() - 1].getBStatus() != this.getWB()) {
                ValidM.add(v);
                if (Game.getBase()[super.getCord().getX()][super.getCord().getY() - 1].getBStatus() == (this.getWB() * (-1))) {
                    ValidK.add(v);
                }
            }
        }

        if ((v = new Cord(super.getCord().getX(), super.getCord().getY() + 1)).isValid()) {
            if (Game.getBase()[super.getCord().getX()][super.getCord().getY() + 1].getBStatus() != this.getWB()) {
                ValidM.add(v);
                if (Game.getBase()[super.getCord().getX()][super.getCord().getY() + 1].getBStatus() == (this.getWB() * (-1))) {
                    ValidK.add(v);
                }
            }
        }

        if ((v = new Cord(super.getCord().getX() - 1, super.getCord().getY())).isValid()) {
            if (Game.getBase()[super.getCord().getX() - 1][super.getCord().getY()].getBStatus() != this.getWB()) {
                ValidM.add(v);
                if (Game.getBase()[super.getCord().getX() - 1][super.getCord().getY()].getBStatus() == (this.getWB() * (-1))) {
                    ValidK.add(v);
                }
            }
        }

        if ((v = new Cord(super.getCord().getX() + 1, super.getCord().getY())).isValid()) {
            if (Game.getBase()[super.getCord().getX() + 1][super.getCord().getY()].getBStatus() != this.getWB()) {
                ValidM.add(v);
                if (Game.getBase()[super.getCord().getX() + 1][super.getCord().getY()].getBStatus() == (this.getWB() * (-1))) {
                    ValidK.add(v);
                }
            }
        }
        
        //these 2 moves are for castle move for king and rook
        if ((v = new Cord(super.getCord().getX() + 2, super.getCord().getY())).isValid()) {
            if (Game.getBase()[super.getCord().getX() + 2][super.getCord().getY()].getBStatus() == 0 && isCastlble(1)) {
                ValidM.add(v);
            }
        }

        if ((v = new Cord(super.getCord().getX() - 2, super.getCord().getY())).isValid()) {
            if (Game.getBase()[super.getCord().getX() - 2][super.getCord().getY()].getBStatus() == 0 && isCastlble(-1)) {
                ValidM.add(v);
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
        if (ValidM.contains(to) && (to.getX() != -1 || Game.getBase()[to.getX()][to.getY()].getBStatus() != super.getWB())) {
            int dx = to.getX() - super.getCord().getX();
            int dy = to.getY() - super.getCord().getY();
            if (dx == 2 || dx == -2) {
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
                    Game.setTurn(Game.getTurn() * (-1));
                    Game.setMoveCount(Game.getMoveCount() + 1);
                    this.castlble = false;
                }

                if (this.getWB() == 1 && !Checked) {
                    if (dx > 0) {
                        Game.getBase()[Game.getGlazes()[1].getCord().getX()][Game.getGlazes()[1].getCord().getY()].setBStatus(0);
                        Game.getBase()[this.getCord().getX() - 1][this.getCord().getY()].setBStatus(Game.getGlazes()[1].getWB());
                        Game.getBase()[Game.getGlazes()[1].getCord().getX()][Game.getGlazes()[1].getCord().getY()].setGIndex(-1);
                        Game.getBase()[this.getCord().getX() - 1][this.getCord().getY()].setGIndex(1);
                        Game.getGlazes()[1].getGlazeImg().setX(Game.getGlazes()[1].getGlazeImg().getX() - (dx * Game.BLOCK_SIZE));
                        Game.getGlazes()[1].getCord().set(this.getCord().getX() - 1, this.getCord().getY());
                    } else if (dx < 0) {
                        Game.getBase()[Game.getGlazes()[0].getCord().getX()][Game.getGlazes()[0].getCord().getY()].setBStatus(0);
                        Game.getBase()[this.getCord().getX() + 1][this.getCord().getY()].setBStatus(Game.getGlazes()[0].getWB());
                        Game.getBase()[Game.getGlazes()[0].getCord().getX()][Game.getGlazes()[0].getCord().getY()].setGIndex(-1);
                        Game.getBase()[this.getCord().getX() + 1][this.getCord().getY()].setGIndex(0);
                        Game.getGlazes()[0].getGlazeImg().setX(Game.getGlazes()[0].getGlazeImg().getX() + (3 * Game.BLOCK_SIZE));
                        Game.getGlazes()[0].getCord().set(this.getCord().getX() + 1, this.getCord().getY());
                    }
                } else if (this.getWB() == -1 && !Checked) {
                    if (dx > 0) {
                        Game.getBase()[Game.getGlazes()[3].getCord().getX()][Game.getGlazes()[3].getCord().getY()].setBStatus(0);
                        Game.getBase()[this.getCord().getX() - 1][this.getCord().getY()].setBStatus(Game.getGlazes()[3].getWB());
                        Game.getBase()[Game.getGlazes()[3].getCord().getX()][Game.getGlazes()[3].getCord().getY()].setGIndex(-1);
                        Game.getBase()[this.getCord().getX() - 1][this.getCord().getY()].setGIndex(3);
                        Game.getGlazes()[3].getGlazeImg().setX(Game.getGlazes()[3].getGlazeImg().getX() - (dx * Game.BLOCK_SIZE));
                        Game.getGlazes()[3].getCord().set(this.getCord().getX() - 1, this.getCord().getY());
                    } else if (dx < 0) {
                        Game.getBase()[Game.getGlazes()[2].getCord().getX()][Game.getGlazes()[2].getCord().getY()].setBStatus(0);
                        Game.getBase()[this.getCord().getX() + 1][this.getCord().getY()].setBStatus(Game.getGlazes()[2].getWB());
                        Game.getBase()[Game.getGlazes()[2].getCord().getX()][Game.getGlazes()[2].getCord().getY()].setGIndex(-1);
                        Game.getBase()[this.getCord().getX() + 1][this.getCord().getY()].setGIndex(2);
                        Game.getGlazes()[2].getGlazeImg().setX(Game.getGlazes()[2].getGlazeImg().getX() + (3 * Game.BLOCK_SIZE));
                        Game.getGlazes()[2].getCord().set(this.getCord().getX() + 1, this.getCord().getY());
                    }
                }
            } else if (dx != 2 && dx != -2) {
                if (Game.getFocusedE() > 0) {
                    Game.getGlazes()[Game.getFocusedE()].killGlaze();
                } else if (Game.getBase()[to.getX()][to.getY()].getGIndex() != -1) {
                    Game.getGlazes()[Game.getBase()[to.getX()][to.getY()].getGIndex()].killGlaze();
                }
                Game.getBase()[super.getCord().getX()][super.getCord().getY()].setBStatus(0);
                Game.getBase()[to.getX()][to.getY()].setBStatus(this.getWB());
                Game.getBase()[super.getCord().getX()][super.getCord().getY()].setGIndex(-1);
                Game.getBase()[to.getX()][to.getY()].setGIndex(Game.getFocused());
                super.getGlazeImg().setX(super.getGlazeImg().getX() + (dx * Game.BLOCK_SIZE));
                super.getGlazeImg().setY(super.getGlazeImg().getY() + (dy * Game.BLOCK_SIZE));
                super.getCord().set(to.getX(), to.getY());
                Game.setTurn(Game.getTurn() * (-1));
                Game.setMoveCount(Game.getMoveCount() + 1);
                this.castlble = false;
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
     * getting valid kills
     * 
     * @return ValidK
     */
    @Override
    public ArrayList<Cord> getValidK() {
        return this.ValidK;
    }

    /**
     * check if friendly glazes can help king to get out of checkMate status
     * 
     * @return
     */
    public boolean checkMate() {
        ArrayList<Cord> temp = new ArrayList<Cord>();
        Cord v;
        Boolean NW = false, NE = false, SW = false, SE = false, N = false, W = false, E = false, S = false;
        for (int i = 1; i <= 8; i++) {
            if ((v = new Cord(super.getCord().getX() + i, super.getCord().getY() + i)).isValid() && !SE) {
                temp.add(v);
                if (Game.getBase()[super.getCord().getX() + i][super.getCord().getY() + i].getBStatus() != 0) {
                    SE = true;
                    if(Game.getBase()[super.getCord().getX() + i][super.getCord().getY() + i].getBStatus() == this.getWB()){
                        temp.trimToSize();
                        temp.remove(temp.size()-1);
                    }
                }
            }

            if ((v = new Cord(super.getCord().getX() - i, super.getCord().getY() + i)).isValid() && !SW) {
                temp.add(v);
                if (Game.getBase()[super.getCord().getX() - i][super.getCord().getY() + i].getBStatus() != 0) {
                    SW = true;
                    if(Game.getBase()[super.getCord().getX() - i][super.getCord().getY() + i].getBStatus() == this.getWB()){
                        temp.trimToSize();
                        temp.remove(temp.size()-1);
                    }
                }
            }

            if ((v = new Cord(super.getCord().getX() + i, super.getCord().getY() - i)).isValid() && !NE) {
                temp.add(v);
                if (Game.getBase()[super.getCord().getX() + i][super.getCord().getY() - i].getBStatus() != 0) {
                    NE = true;
                    if(Game.getBase()[super.getCord().getX() + i][super.getCord().getY() - i].getBStatus() == this.getWB()){
                        temp.trimToSize();
                        temp.remove(temp.size()-1);
                    }
                }
            }

            if ((v = new Cord(super.getCord().getX() - i, super.getCord().getY() - i)).isValid() && !NW) {
                temp.add(v);
                if (Game.getBase()[super.getCord().getX() - i][super.getCord().getY() - i].getBStatus() != 0) {
                    NW = true;
                    if(Game.getBase()[super.getCord().getX() - i][super.getCord().getY() - i].getBStatus() == this.getWB()){
                        temp.trimToSize();
                        temp.remove(temp.size()-1);
                    }
                }
            }

            if ((v = new Cord(super.getCord().getX(), super.getCord().getY() - i)).isValid() && !N) {
                temp.add(v);
                if (Game.getBase()[super.getCord().getX()][super.getCord().getY() - i].getBStatus() != 0) {
                    N = true;
                    if(Game.getBase()[super.getCord().getX()][super.getCord().getY() - i].getBStatus() == this.getWB()){
                        temp.trimToSize();
                        temp.remove(temp.size()-1);
                    }
                }
            }

            if ((v = new Cord(super.getCord().getX(), super.getCord().getY() + i)).isValid() && !S) {
                temp.add(v);
                if (Game.getBase()[super.getCord().getX()][super.getCord().getY() + i].getBStatus() != 0) {
                    S = true;
                    if(Game.getBase()[super.getCord().getX()][super.getCord().getY() + i].getBStatus() == this.getWB()){
                        temp.trimToSize();
                        temp.remove(temp.size()-1);
                    }
                }
            }

            if ((v = new Cord(super.getCord().getX() - i, super.getCord().getY())).isValid() && !W) {
                temp.add(v);
                if (Game.getBase()[super.getCord().getX() - i][super.getCord().getY()].getBStatus() != 0) {
                    W = true;
                    if(Game.getBase()[super.getCord().getX() - i][super.getCord().getY()].getBStatus() == this.getWB()){
                        temp.trimToSize();
                        temp.remove(temp.size()-1);
                    }
                }
            }

            if ((v = new Cord(super.getCord().getX() + i, super.getCord().getY())).isValid() && !E) {
                temp.add(v);
                if (Game.getBase()[super.getCord().getX() + i][super.getCord().getY()].getBStatus() != 0) {
                    E = true;
                    if(Game.getBase()[super.getCord().getX() + i][super.getCord().getY()].getBStatus() == this.getWB()){
                        temp.trimToSize();
                        temp.remove(temp.size()-1);
                    }
                }
            }
        }

        int c = 1;
        int inx = 0;
        while (c <= 16) {
            if (Game.getGlazes()[inx].getWB() == super.getWB()) {
                if (Game.getGlazes()[inx].isIsAlive()) {
                    for (int i = 0; i < temp.size(); i++) {
                        if (Game.getGlazes()[inx].getValidM().contains(temp.get(i))) {
                            System.out.println("this is why ! " + Game.getGlazes()[i] + "   " + temp.get(i));
                            return false;
                        }
                    }
                }
                c++;
            }
            inx++;
        }
        return true;
    }

    /**
     *
     * @return CheckMate
     */
    public boolean isCheckMate() {
        return CheckMate;
    }

    /**
     * for checking if a castle move is Valid
     * 
     * @param arrow
     * @return
     */
    public boolean isCastlble(int arrow) {
        if (arrow == 1) { //right
            Cord king = null;
            Cord rook = null;
            if (this.getWB() == 1) {
                king = new Cord(5, 1);
                rook = new Cord(8, 1);
                if (this.getCord().equals(king) && Game.getGlazes()[1].getCord().equals(rook)) {
                    if (ValidM.contains(new Cord(king.getX() + 1, king.getY()))) {
                        if (!this.Checked) {
                            return true;
                        }
                    }
                }
            } else if (this.getWB() == -1) {
                king = new Cord(5, 8);
                rook = new Cord(8, 8);
                if (this.getCord().equals(king) && Game.getGlazes()[3].getCord().equals(rook)) {
                    if (ValidM.contains(new Cord(king.getX() + 1, king.getY()))) {
                        if (!this.Checked) {
                            return true;
                        }
                    }
                }
            }
        } else if (arrow == -1) { //left
            Cord king = null;
            Cord rook = null;
            if (this.getWB() == 1) {
                king = new Cord(5, 1);
                rook = new Cord(1, 1);
                if (this.getCord().equals(king) && Game.getGlazes()[0].getCord().equals(rook)) {
                    if (ValidM.contains(new Cord(king.getX() - 1, king.getY()))) {
                        if (!this.Checked) {
                            return true;
                        }
                    }
                }
            } else if (this.getWB() == -1) {
                king = new Cord(5, 8);
                rook = new Cord(1, 8);
                if (this.getCord().equals(king) && Game.getGlazes()[2].getCord().equals(rook)) {
                    if (ValidM.contains(new Cord(king.getX() - 1, king.getY()))) {
                        if (!this.Checked) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
