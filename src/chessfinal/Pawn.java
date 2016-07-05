/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessfinal;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;

/**
 *
 * @author soheil
 */
public class Pawn extends Glaze {

    private ArrayList<Cord> ValidM = new ArrayList<Cord>();
    private ArrayList<Cord> ValidK = new ArrayList<Cord>();
    private boolean firstMove = true;
    private boolean enPassDeath = false;
    private static int checkEnPassant = 0;
    private Group root;

    /**
     *
     * @param WorB
     * @param c
     */
    public Pawn(int WorB, Cord c) {
        super(WorB, c);
    }

    /**
     *
     * @param index
     * @param c
     * @param root
     * @param g
     */
    public Pawn(int index, Cord c, Group root, Game g) {
        super(index, c, root, g);
        this.root = root;
        Image image;
        if (super.getWB() > 0) {
            image = new Image(getClass().getResourceAsStream("PawnB.png"));
        } else {
            image = new Image(getClass().getResourceAsStream("PawnW.png"));
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
        Boolean NW = false, NE = false, SW = false, SE = false, N = false, S = false;

        if (Game.getMoveCount() - 2 == checkEnPassant && checkEnPassant > 0) {
            checkEnPassant = 0;
            for (int i = 16; i < 32; i++) {
                if (Game.getGlazes()[i] instanceof Pawn) {
                    Pawn t = (Pawn) Game.getGlazes()[i];
                    t.enPassDeath = false;
                    if (t.isIsAlive()) {
                        if (i < 24) {
                            Game.getBase()[t.getCord().getX()][t.getCord().getY() - 1].EnPassant(false);
                        } else {
                            Game.getBase()[t.getCord().getX()][t.getCord().getY() + 1].EnPassant(false);
                        }
                    }
                }
            }
        }

        if ((v = new Cord(super.getCord().getX() + 1, super.getCord().getY() + 1)).isValid() && !SE
                && Game.getBase()[super.getCord().getX()][super.getCord().getY()].getBStatus() == 1
                && (Game.getBase()[super.getCord().getX() + 1][super.getCord().getY() + 1].getBStatus() == -1
                || Game.getBase()[super.getCord().getX() + 1][super.getCord().getY() + 1].isEnPassant())) {
            ValidM.add(v);
            if (!Game.getBase()[super.getCord().getX() + 1][super.getCord().getY() + 1].isEnPassant()) {
                ValidK.add(v);
            }
            if (Game.getBase()[super.getCord().getX() + 1][super.getCord().getY() + 1].getBStatus() == this.getWB()) {
                SE = true;
            }
        }

        if ((v = new Cord(super.getCord().getX() - 1, super.getCord().getY() + 1)).isValid() && !SW
                && Game.getBase()[super.getCord().getX()][super.getCord().getY()].getBStatus() == 1
                && (Game.getBase()[super.getCord().getX() - 1][super.getCord().getY() + 1].getBStatus() == -1
                || Game.getBase()[super.getCord().getX() - 1][super.getCord().getY() + 1].isEnPassant())) {
            ValidM.add(v);
            if (!Game.getBase()[super.getCord().getX() - 1][super.getCord().getY() + 1].isEnPassant()) {
                ValidK.add(v);
            }
            if (Game.getBase()[super.getCord().getX() - 1][super.getCord().getY() + 1].getBStatus() == this.getWB()) {
                SW = true;
            }
        }

        if ((v = new Cord(super.getCord().getX() + 1, super.getCord().getY() - 1)).isValid() && !NE
                && Game.getBase()[super.getCord().getX()][super.getCord().getY()].getBStatus() == -1
                && (Game.getBase()[super.getCord().getX() + 1][super.getCord().getY() - 1].getBStatus() == 1
                || Game.getBase()[super.getCord().getX() + 1][super.getCord().getY() - 1].isEnPassant())) {
            ValidM.add(v);
            if (!Game.getBase()[super.getCord().getX() + 1][super.getCord().getY() - 1].isEnPassant()) {
                ValidK.add(v);
            }
            if (Game.getBase()[super.getCord().getX() + 1][super.getCord().getY() - 1].getBStatus() == this.getWB()) {
                NE = true;
            }
        }

        if ((v = new Cord(super.getCord().getX() - 1, super.getCord().getY() - 1)).isValid() && !NW
                && Game.getBase()[super.getCord().getX()][super.getCord().getY()].getBStatus() == -1
                && (Game.getBase()[super.getCord().getX() - 1][super.getCord().getY() - 1].getBStatus() == 1
                || Game.getBase()[super.getCord().getX() - 1][super.getCord().getY() - 1].isEnPassant())) {
            ValidM.add(v);
            if (!Game.getBase()[super.getCord().getX() - 1][super.getCord().getY() - 1].isEnPassant()) {
                ValidK.add(v);
            }
            if (Game.getBase()[super.getCord().getX() - 1][super.getCord().getY() - 1].getBStatus() == this.getWB()) {
                NW = true;
            }
        }

        if ((v = new Cord(super.getCord().getX(), super.getCord().getY() - 1)).isValid() && !N
                && Game.getBase()[super.getCord().getX()][super.getCord().getY()].getBStatus() == -1) {
            ValidM.add(v);
            if (Game.getBase()[super.getCord().getX()][super.getCord().getY() - 1].getBStatus() == this.getWB()) {
                N = true;
            }
        }

        if ((v = new Cord(super.getCord().getX(), super.getCord().getY() + 1)).isValid() && !S
                && Game.getBase()[super.getCord().getX()][super.getCord().getY()].getBStatus() == 1) {
            ValidM.add(v);
            if (Game.getBase()[super.getCord().getX()][super.getCord().getY() + 1].getBStatus() == this.getWB()) {
                S = true;
            }
        }

        if ((v = new Cord(super.getCord().getX(), super.getCord().getY() - 2)).isValid() && !N
                && Game.getBase()[super.getCord().getX()][super.getCord().getY()].getBStatus() == -1
                && this.firstMove) {
            ValidM.add(v);
            if (Game.getBase()[super.getCord().getX()][super.getCord().getY() - 2].getBStatus() == this.getWB()) {
                N = true;
            }
        }

        if ((v = new Cord(super.getCord().getX(), super.getCord().getY() + 2)).isValid() && !S
                && Game.getBase()[super.getCord().getX()][super.getCord().getY()].getBStatus() == 1
                && this.firstMove) {
            ValidM.add(v);
            if (Game.getBase()[super.getCord().getX()][super.getCord().getY() + 1].getBStatus() == this.getWB()) {
                S = true;
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
        if (ValidM.contains(to) && to.getX() != -1) {
            int dx = to.getX() - super.getCord().getX();
            int dy = to.getY() - super.getCord().getY();
            if (dx != 0 || Game.getBase()[to.getX()][to.getY()].getBStatus() == 0) {
                if (Game.getFocusedE() > 0 && dx != 0) {
                    Game.getGlazes()[Game.getFocusedE()].killGlaze();
                    if (this.enPassDeath) {
                        Game.getBase()[Game.getGlazes()[Game.getFocusedE()].getCord().getX()][Game.getGlazes()[Game.getFocusedE()].getCord().getY() + dy].EnPassant(false);
                        Game.getBase()[Game.getGlazes()[Game.getFocusedE()].getCord().getX() - dx][Game.getGlazes()[Game.getFocusedE()].getCord().getY() - (2 * dy)].EnPassant(false);
                    }
                } else if (Game.getBase()[to.getX()][to.getY()].getGIndex() != -1 && dx != 0) {
                    Game.getGlazes()[Game.getBase()[to.getX()][to.getY()].getGIndex()].killGlaze();
                    if (this.enPassDeath) {
                        Game.getBase()[to.getX()][to.getY() + dy].EnPassant(false);
                    }
                    Game.getBase()[to.getX() - dx][to.getY() - (2 * dy)].EnPassant(false);
                } else if (Game.getBase()[to.getX()][to.getY()].isEnPassant() && super.getWB() == 1
                        && Game.getBase()[to.getX()][to.getY() - 1].getBStatus() == (this.getWB() * (-1))) {
                    Game.getGlazes()[Game.getBase()[to.getX()][to.getY() - 1].getGIndex()].killGlaze();
                    Game.getBase()[to.getX()][to.getY()].EnPassant(false);
                } else if (Game.getBase()[to.getX()][to.getY()].isEnPassant() && super.getWB() == -1
                        && Game.getBase()[to.getX()][to.getY() + 1].getBStatus() == (this.getWB() * (-1))) {
                    Game.getGlazes()[Game.getBase()[to.getX()][to.getY() + 1].getGIndex()].killGlaze();
                    Game.getBase()[to.getX()][to.getY()].EnPassant(false);
                }
                Game.getBase()[super.getCord().getX()][super.getCord().getY()].setBStatus(0);
                Game.getBase()[to.getX()][to.getY()].setBStatus(this.getWB());
                Game.getBase()[super.getCord().getX()][super.getCord().getY()].setGIndex(-1);
                Game.getBase()[to.getX()][to.getY()].setGIndex(Game.getFocused());
                if (dy == 2 || dy == -2) {
                    Game.getBase()[super.getCord().getX()][super.getCord().getY() + (dy / 2)].EnPassant(true);
                    this.enPassDeath = true;
                    checkEnPassant = -1;
                } else if (Game.getBase()[super.getCord().getX()][super.getCord().getY() - dy].isEnPassant() || this.enPassDeath) {
                    this.enPassDeath = false;
                    Game.getBase()[super.getCord().getX()][super.getCord().getY() - dy].EnPassant(false);
                }
                super.getCord().set(to.getX(), to.getY());
                if (super.getCord().getY() == 1 || super.getCord().getY() == 8) {
                    proGlaze();
                }
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
                    checkEnPassant = 0;
                } else {
                    super.getGlazeImg().setX(super.getGlazeImg().getX() + (dx * Game.BLOCK_SIZE));
                    super.getGlazeImg().setY(super.getGlazeImg().getY() + (dy * Game.BLOCK_SIZE));
                    Game.setTurn(Game.getTurn() * (-1));
                    Game.setMoveCount(Game.getMoveCount() + 1);
                    if (checkEnPassant == -1) {
                        checkEnPassant = Game.getMoveCount();
                    }
                    if (this.firstMove) {
                        this.firstMove = false;
                    }
                }
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
        return ValidM;
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

    /**
     *
     * @return enPassDeath
     */
    public boolean isEnPaDeath() {
        return this.enPassDeath;
    }

    /**
     * for promoting glaze when reach the end
     *
     */
    public void proGlaze() {

        Game.getGlazes()[super.getIndex()].setIsAlive(false);
        Game.getGlazes()[super.getIndex()].getGlazeImg().setVisible(false);
        Game.getGlazes()[super.getIndex()] = new Queen(super.getIndex(), super.getCord(), root, super.getG());
/*
        ToggleGroup group = new ToggleGroup();
        RadioButton button1 = new RadioButton("Queen");
        button1.setToggleGroup(group);
        button1.setTranslateX(4 * Game.BLOCK_SIZE);
        button1.setTranslateY(Game.GRID_HEIGHT * Game.BLOCK_SIZE + 40);
        button1.setUserData("Q");
        RadioButton button2 = new RadioButton("Bishop");
        button2.setToggleGroup(group);
        button2.setTranslateX(4 * Game.BLOCK_SIZE + 6);
        button2.setTranslateY(Game.GRID_HEIGHT * Game.BLOCK_SIZE + 40);
        button1.setUserData("B");
        RadioButton button3 = new RadioButton("Rook");
        button3.setToggleGroup(group);
        button3.setTranslateX(4 * Game.BLOCK_SIZE + 12);
        button3.setTranslateY(Game.GRID_HEIGHT * Game.BLOCK_SIZE + 40);
        button1.setUserData("R");
        RadioButton button4 = new RadioButton("Knight");
        button4.setToggleGroup(group);
        button4.setTranslateX(4 * Game.BLOCK_SIZE + 18);
        button4.setTranslateY(Game.GRID_HEIGHT * Game.BLOCK_SIZE + 40);
        button1.setUserData("K");

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {
                    if (group.getSelectedToggle().getUserData().toString().equals("Q")) {

                    }
                }
            }
        });

        root.getChildren().addAll(button1, button2, button3, button4);
*/
    }

}
