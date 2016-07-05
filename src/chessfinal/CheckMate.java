/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessfinal;

import java.util.ArrayList;
import javafx.scene.Group;

/**
 *
 * @author soheil
 */
public class CheckMate implements Runnable {

    private int enamy;
    private Cord cord;
    private boolean checked = false;
    private boolean mated = false;
    private ArrayList<Cord> unsafe = new ArrayList<Cord>();
    private Group root;

    /**
     *
     * @param enamy
     * @param cord
     */
    public CheckMate(int enamy, Cord cord) {
        this.enamy = enamy;
        this.cord = cord;
    }

    /**
     *
     */
    @Override
    public void run() {
        int x = cord.getX(), y = cord.getY();
        unsafe.clear();
        unsafe.trimToSize();
        if (knightD(cord) || queenD(cord) || rookD(cord) || bishopD(cord) || kingD(cord) || pawnD(cord)) {
            this.checked = true;
        }
        if (knightD(new Cord(x + 1, y)) || queenD(new Cord(x + 1, y)) || rookD(new Cord(x + 1, y))
                || bishopD(new Cord(x + 1, y)) || kingD(new Cord(x + 1, y)) || pawnD(new Cord(x + 1, y))) {
            unsafe.add(new Cord(x + 1, y));
        }
        if (knightD(new Cord(x - 1, y)) || queenD(new Cord(x - 1, y)) || rookD(new Cord(x - 1, y))
                || bishopD(new Cord(x - 1, y)) || kingD(new Cord(x - 1, y)) || pawnD(new Cord(x - 1, y))) {
            unsafe.add(new Cord(x - 1, y));
        }
        if (knightD(new Cord(x, y + 1)) || queenD(new Cord(x, y + 1)) || rookD(new Cord(x, y + 1))
                || bishopD(new Cord(x, y + 1)) || kingD(new Cord(x, y + 1)) || pawnD(new Cord(x, y + 1))) {
            unsafe.add(new Cord(x, y + 1));
        }
        if (knightD(new Cord(x, y - 1)) || queenD(new Cord(x, y - 1)) || rookD(new Cord(x, y - 1))
                || bishopD(new Cord(x, y - 1)) || kingD(new Cord(x, y - 1)) || pawnD(new Cord(x, y - 1))) {
            unsafe.add(new Cord(x, y - 1));
        }
        if (knightD(new Cord(x + 1, y + 1)) || queenD(new Cord(x + 1, y + 1)) || rookD(new Cord(x + 1, y + 1))
                || bishopD(new Cord(x + 1, y + 1)) || kingD(new Cord(x + 1, y + 1)) || pawnD(new Cord(x + 1, y + 1))) {
            unsafe.add(new Cord(x + 1, y + 1));
        }
        if (knightD(new Cord(x + 1, y - 1)) || queenD(new Cord(x + 1, y - 1)) || rookD(new Cord(x + 1, y - 1))
                || bishopD(new Cord(x + 1, y - 1)) || kingD(new Cord(x + 1, y - 1)) || pawnD(new Cord(x + 1, y - 1))) {
            unsafe.add(new Cord(x + 1, y - 1));
        }
        if (knightD(new Cord(x - 1, y + 1)) || queenD(new Cord(x - 1, y + 1)) || rookD(new Cord(x - 1, y + 1))
                || bishopD(new Cord(x - 1, y + 1)) || kingD(new Cord(x - 1, y + 1)) || pawnD(new Cord(x - 1, y + 1))) {
            unsafe.add(new Cord(x - 1, y + 1));
        }
        if (knightD(new Cord(x - 1, y - 1)) || queenD(new Cord(x - 1, y - 1)) || rookD(new Cord(x - 1, y - 1))
                || bishopD(new Cord(x - 1, y - 1)) || kingD(new Cord(x - 1, y - 1)) || pawnD(new Cord(x - 1, y - 1))) {
            unsafe.add(new Cord(x - 1, y - 1));
        }
        mateGen();
    }

    /**
     * for calculate possibility of being Checked by Knight
     * 
     * @param c
     * @return
     */
    public boolean knightD(Cord c) {
        Knight test = new Knight(this.enamy * (-1), c);
        ArrayList<Cord> list = new ArrayList<Cord>();
        Thread t = new Thread(test);
        t.start();
        try {
            t.join();
            list = test.getValidM();
        } catch (Exception e) {

        }
        list.trimToSize();
        for (int i = 0; i < list.size(); i++) {
            if (this.enamy == 1) {
                if (Game.getBase()[list.get(i).getX()][list.get(i).getY()].getGIndex() == 4
                        || Game.getBase()[list.get(i).getX()][list.get(i).getY()].getGIndex() == 5) {
                    return true;
                }
            } else if (this.enamy == -1) {
                if (Game.getBase()[list.get(i).getX()][list.get(i).getY()].getGIndex() == 6
                        || Game.getBase()[list.get(i).getX()][list.get(i).getY()].getGIndex() == 7) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * for calculate possibility of being Checked by Queen
     *
     * @param c
     * @return
     */
    public boolean queenD(Cord c) {
        Queen test = new Queen(this.enamy, c);
        ArrayList<Cord> list = new ArrayList<Cord>();
        Thread t = new Thread(test);
        t.start();
        try {
            t.join();
            list = test.getValidM();
        } catch (Exception e) {

        }
        list.trimToSize();
        for (int i = 0; i < list.size(); i++) {
            if (this.enamy == 1) {
                if (Game.getBase()[list.get(i).getX()][list.get(i).getY()].getGIndex() == 14) {
                    return true;
                } else if (Game.getBase()[list.get(i).getX()][list.get(i).getY()].getGIndex() >= 16
                        && Game.getBase()[list.get(i).getX()][list.get(i).getY()].getGIndex() < 24) {
                    if (Game.getGlazes()[Game.getBase()[list.get(i).getX()][list.get(i).getY()].getGIndex()] instanceof Queen) {
                        return true;
                    }
                }
            } else if (this.enamy == -1) {
                if (Game.getBase()[list.get(i).getX()][list.get(i).getY()].getGIndex() == 15) {
                    return true;
                } else if (Game.getBase()[list.get(i).getX()][list.get(i).getY()].getGIndex() >= 24
                        && Game.getBase()[list.get(i).getX()][list.get(i).getY()].getGIndex() < 32) {
                    if (Game.getGlazes()[Game.getBase()[list.get(i).getX()][list.get(i).getY()].getGIndex()] instanceof Queen) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * for calculate possibility of being Checked by Rook
     *
     * @param c
     * @return
     */
    public boolean rookD(Cord c) {
        Rook test = new Rook(this.enamy, c);
        ArrayList<Cord> list = new ArrayList<Cord>();
        Thread t = new Thread(test);
        t.start();
        try {
            t.join();
            list = test.getValidM();
        } catch (Exception e) {

        }
        list.trimToSize();
        for (int i = 0; i < list.size(); i++) {
            if (this.enamy == 1) {
                if (Game.getBase()[list.get(i).getX()][list.get(i).getY()].getGIndex() == 0
                        || Game.getBase()[list.get(i).getX()][list.get(i).getY()].getGIndex() == 1) {
                    return true;
                }
            } else if (this.enamy == -1) {
                if (Game.getBase()[list.get(i).getX()][list.get(i).getY()].getGIndex() == 2
                        || Game.getBase()[list.get(i).getX()][list.get(i).getY()].getGIndex() == 3) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * for calculate possibility of being Checked by Pawn
     *
     * @param c
     * @return
     */
    public boolean pawnD(Cord c) {
        if (this.enamy == 1) { //white king
            if ((new Cord(c.getX() - 1, c.getY() - 1)).isValid()) {
                if (Game.getBase()[c.getX() - 1][c.getY() - 1].getBStatus() == this.enamy) {
                    return true;
                }
            }
            if ((new Cord(c.getX() + 1, c.getY() - 1)).isValid()) {
                if (Game.getBase()[c.getX() + 1][c.getY() - 1].getBStatus() == this.enamy) {
                    return true;
                }
            }
        } else if (this.enamy == -1) { //black king
            if ((new Cord(c.getX() - 1, c.getY() + 1)).isValid()) {
                if (Game.getBase()[c.getX() - 1][c.getY() + 1].getBStatus() == this.enamy) {
                    return true;
                }
            }
            if ((new Cord(c.getX() + 1, c.getY() + 1)).isValid()) {
                if (Game.getBase()[c.getX() + 1][c.getY() + 1].getBStatus() == this.enamy) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * for calculate possibility of being Checked by Bishop
     *
     * @param c
     * @return
     */
    public boolean bishopD(Cord c) {
        Bishop test = new Bishop(this.enamy, c);
        ArrayList<Cord> list = new ArrayList<Cord>();
        Thread t = new Thread(test);
        t.start();
        try {
            t.join();
            list = test.getValidM();
        } catch (Exception e) {

        }
        list.trimToSize();
        for (int i = 0; i < list.size(); i++) {
            if (this.enamy == 1) {
                if (Game.getBase()[list.get(i).getX()][list.get(i).getY()].getGIndex() == 8
                        || Game.getBase()[list.get(i).getX()][list.get(i).getY()].getGIndex() == 9) {
                    return true;
                }
            } else if (this.enamy == -1) {
                if (Game.getBase()[list.get(i).getX()][list.get(i).getY()].getGIndex() == 10
                        || Game.getBase()[list.get(i).getX()][list.get(i).getY()].getGIndex() == 11) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * for calculate possibility of being Checked by King
     *
     * @param c
     * @return
     */
    public boolean kingD(Cord c) {
        King test = new King(this.enamy, c);
        ArrayList<Cord> list = new ArrayList<Cord>();
        Thread t = new Thread(test);
        t.start();
        try {
            t.join();
            list = test.getValidM();
        } catch (Exception e) {

        }
        list.trimToSize();
        for (int i = 0; i < list.size(); i++) {
            if (this.enamy == 1) {
                if (Game.getBase()[list.get(i).getX()][list.get(i).getY()].getGIndex() == 12) {
                    return true;
                }
            } else if (this.enamy == -1) {
                if (Game.getBase()[list.get(i).getX()][list.get(i).getY()].getGIndex() == 13) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * for generating information whether if player is checkMated or not
     *
     */
    public void mateGen() {
        if (enamy == -1) {
            if(checked){
                ArrayList<Cord> temp = Game.getGlazes()[12].getValidM();
                temp.removeAll(unsafe);
                King t = (King)Game.getGlazes()[12];
                if(temp.isEmpty() && t.checkMate()){
                    mated = true;
                }
            }
        } else if (enamy == 1) {
            if(checked){
                ArrayList<Cord> temp = Game.getGlazes()[13].getValidM();
                temp.removeAll(unsafe);
                King t = (King)Game.getGlazes()[13];
                if(temp.isEmpty() && t.checkMate()){
                    mated = true;
                }
            }
        }
    }

    /**
     * getting unSafe bases for king
     * 
     * @return unSafe
     */
    public ArrayList<Cord> getUnsafe() {
        return unsafe;
    }

    /**
     *
     * @return checked
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     *
     * @return
     */
    public boolean isMated() {
        return mated;
    }

    
}
