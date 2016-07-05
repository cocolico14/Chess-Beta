/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessfinal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

/**
 *
 * @author soheil
 */
public class Game {

    public static final int GRID_LENGTH = 8;
    public static final int GRID_HEIGHT = 8;
    public static final int BLOCK_SIZE = 75;
    public static final int BASE = 20;

    private static int Focused = -1;
    private static int FocusedE = -1;
    private static Cord goTo = new Cord(-1, -1);
    private static Glaze[] glazes = new Glaze[32];
    private static Base[][] base = new Base[9][9];

    private AnimationTimer timer;
    private int temp = 0; // for control func in animition timer
    private String p1Name, p2Name;
    private int newload;
    private int PVPoE;
    private int computer; // for take computer turn

    private static int chosenG;
    private static Cord chosenB;
    private static int moveCount = 0;
    private static int Turn = -1; // -1 white + 1 Black
    private static CheckMate kingW;
    private static CheckMate kingB;
    private static File gameFile;

    /**
     *
     * @return moveCount
     */
    public static int getMoveCount() {
        return moveCount;
    }

    /**
     *
     * @param moveCount
     */
    public static void setMoveCount(int moveCount) {
        Game.moveCount = moveCount;
    }

    /**
     *
     * @return Turn
     */
    public static int getTurn() {
        return Turn;
    }

    /**
     *
     * @param Turn
     */
    public static void setTurn(int Turn) {
        Game.Turn = Turn;
    }

    /**
     * getting information about white King state 
     * 
     * @return kingW
     */
    public static CheckMate getKingW() {
        return kingW;
    }

    /**
     * getting information about black King state 
     * 
     * @return kingB
     */
    public static CheckMate getKingB() {
        return kingB;
    }

    /**
     * getting glazes information such as their Alliance and cord and their state
     * 
     * @return glazes
     */
    public static Glaze[] getGlazes() {
        return glazes;
    }

    /**
     *
     * @param aGlazes
     */
    public static void setGlazes(Glaze[] aGlazes) {
        glazes = aGlazes;
    }

    /**
     * for getting Node that mouse is Focused on
     * 
     * @return Focused
     */
    public static int getFocused() {
        return Focused;
    }

    /**
     * for setting Node that mouse is Focused on
     * 
     * @param aFocused
     */
    public static void setFocused(int aFocused) {
        Focused = aFocused;
    }

    /**
     * for getting second Node that mouse is Focused on
     * 
     * @return FocusedE
     */
    public static int getFocusedE() {
        return FocusedE;
    }

    /**
     * for setting second Node that mouse is Focused on
     * 
     * @param aFocusedE
     */
    public static void setFocusedE(int aFocusedE) {
        FocusedE = aFocusedE;
    }

    /**
     * for getting Cord that glaze is moving on to it
     * 
     * @return cord
     */
    public static Cord getGoTo() {
        return goTo;
    }

    /**
     * for setting Cord that glaze is moving on to it
     *
     * @param aGoTo
     */
    public static void setGoTo(Cord aGoTo) {
        goTo = aGoTo;
    }

    /**
     * for getting base Node information
     * 
     * @return base
     */
    public static Base[][] getBase() {
        return base;
    }

    /**
     * for setting base Node information
     * 
     * @param aBase
     */
    public static void setBase(Base[][] aBase) {
        base = aBase;
    }

    /**
     * for launching the game and connecting Game with Main Thread
     *
     * @param root
     * @param mode
     * @param newload
     * @param p1
     * @param p2
     * @return Scene
     * @throws IOException
     */
    public Scene launchGame(Group root, int mode, int newload, String p1, String p2, File file) throws IOException {
        Scene scene = new Scene(root, GRID_LENGTH * BLOCK_SIZE + BASE + (BLOCK_SIZE + 10), GRID_HEIGHT * BLOCK_SIZE + BASE + (45), Color.BLACK);
        this.newload = newload;
        this.PVPoE = mode;
        this.gameFile = file;
        if (newload == 1) {
            this.p1Name = p1;
            this.p2Name = p2;
            if (PVPoE == -1) {
                if (p1Name.equals("Computer")) {
                    computer = 1;
                } else {
                    computer = -1;
                }
            }
        }

        loadGame(root);
        checkGen();
        onUpdate();

        Rectangle box1 = new Rectangle((BLOCK_SIZE + 10), (GRID_HEIGHT * BLOCK_SIZE + (2 * BASE) + 10), Color.BLACK);
        box1.setX(GRID_LENGTH * BLOCK_SIZE + 15);
        box1.setY(6);
        box1.setStroke(Color.DARKCYAN);
        box1.setStrokeWidth(1.2);
        box1.toBack();

        Rectangle box2 = new Rectangle(GRID_LENGTH * BLOCK_SIZE, (BASE * 2) + 5, Color.BLACK);
        box2.setX(8);
        box2.setY(GRID_HEIGHT * BLOCK_SIZE + 15);
        box2.setStroke(Color.DARKCYAN);
        box2.setStrokeWidth(1.2);
        box2.toBack();

        Button btn = new Button();
        btn.setText("Save Game");
        btn.setLayoutX(GRID_LENGTH * BLOCK_SIZE + BASE);
        btn.setLayoutY(10);
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    saveGame();
                } catch (IOException ex) {
                    System.out.println("Problem with Saving: " + ex);
                }
            }
        });

        Text whoTurn = new Text();
        whoTurn.setX(12);
        whoTurn.setY(GRID_HEIGHT * BLOCK_SIZE + 50);
        whoTurn.setFill(Color.LIGHTSTEELBLUE);
        StringProperty s1 = new SimpleStringProperty(p1Name + "'s Turn");
        StringProperty s2 = new SimpleStringProperty(p2Name + "'s Turn");
        if (PVPoE == 1) {
            if (Turn == -1) {
                whoTurn.textProperty().bind(s1);
            } else {
                whoTurn.textProperty().bind(s2);
            }
        } else if (Turn == -1) {
            whoTurn.textProperty().bind(s2);
        } else {
            whoTurn.textProperty().bind(s1);
        }

        Text moves = new Text();
        moves.setX(12);
        moves.setY(GRID_HEIGHT * BLOCK_SIZE + 30);
        StringProperty s3 = new SimpleStringProperty("Move Counter: " + moveCount);
        moves.textProperty().bind(s3);
        moves.setFill(Color.LIGHTSTEELBLUE);

        Text checkedMate = new Text();
        checkedMate.setX(4 * BLOCK_SIZE + 10);
        checkedMate.setY(GRID_HEIGHT * BLOCK_SIZE + 30);
        checkedMate.setFill(Color.RED);
        checkedMate.setScaleX(1.3);
        checkedMate.setScaleY(1.3);
        StringProperty s4 = new SimpleStringProperty("");
        checkedMate.textProperty().bind(s4);

        root.getChildren().addAll(box1, box2, btn, whoTurn, moves, checkedMate);

        timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 200000000) {
                    if (PVPoE == 1) {
                        PVP();
                    } else if (PVPoE == -1) {
                        PVE();
                    }
                    if (temp != moveCount) {
                        if (PVPoE == 1) {
                            if (Turn == -1) {
                                whoTurn.textProperty().bind(s1);
                            } else {
                                whoTurn.textProperty().bind(s2);
                            }
                        } else if (Turn == -1) {
                            whoTurn.textProperty().bind(s2);
                        } else {
                            whoTurn.textProperty().bind(s1);
                        }
                        StringProperty s3 = new SimpleStringProperty("Move Counter: " + moveCount);
                        moves.textProperty().bind(s3);

                        onUpdate();

                        checkGen();
                        if (kingB.isChecked()) {
                            StringProperty s4 = new SimpleStringProperty(p2Name + " is checked !");
                            if (PVPoE == -1) {
                                s4 = new SimpleStringProperty(p1Name + " is checked !");
                            }
                            if (kingB.isMated()) {
                                s4 = new SimpleStringProperty(p2Name + " is checked mate ! " + p1Name + " WINS !!");
                                if (PVPoE == -1) {
                                    s4 = new SimpleStringProperty(p1Name + " is checked mate ! " + p2Name + " WINS !!");
                                }
                            }
                            checkedMate.textProperty().bind(s4);
                        } else if (kingW.isChecked()) {
                            StringProperty s4 = new SimpleStringProperty(p1Name + " is checked !");
                            if (PVPoE == -1) {
                                s4 = new SimpleStringProperty(p2Name + " is checked !");
                            }
                            if (kingW.isMated()) {
                                s4 = new SimpleStringProperty(p1Name + " is checked mate ! " + p2Name + " WINS !!");
                                if (PVPoE == -1) {
                                    s4 = new SimpleStringProperty(p1Name + " is checked mate ! " + p2Name + " WINS !!");
                                }
                            }
                            checkedMate.textProperty().bind(s4);
                        } else if (!kingB.isChecked() || !kingW.isChecked()) {
                            StringProperty s4 = new SimpleStringProperty("");
                            checkedMate.textProperty().bind(s4);
                        }

                        lastUpdate = now;
                        temp = moveCount;
                    }
                }
            }

        };
        timer.start();

        return scene;
    }

    /**
     * for loading the game from files and add Glazes from those file
     *
     * @param root
     * @throws IOException
     */
    private void loadGame(Group root) throws IOException {

        int index = 0;

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        Cord[] tcord = new Cord[32];
        ArrayList<Integer> promPawn = new ArrayList<Integer>();
        InputStreamReader isReader = new InputStreamReader(this.getClass().getResourceAsStream("default.txt"));
        BufferedReader fr = new BufferedReader(isReader);

        if (newload == 1) {
            isReader = new InputStreamReader(this.getClass().getResourceAsStream("newgame.txt"));
            fr = new BufferedReader(isReader);
        } else if (newload == -1 && PVPoE == 1) {
            fr = new BufferedReader(new FileReader(gameFile.getPath()));
        } else if (newload == -1 && PVPoE == -1) {
            fr = new BufferedReader(new FileReader(gameFile.getPath()));
        }

        int n = 0; // counter for tCode
        while ((line = fr.readLine()) != null) {
            String x = "", y = "";
            boolean flag = false;
            boolean infoR = false;
            boolean prom = false;
            if (line.equals("*")) {
                infoR = true;
            }
            if (line.equals("#")) {
                prom = true;
            }
            if (!infoR && !prom) {
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) != ' ' && !flag) {
                        x += "" + line.charAt(i);
                    }
                    if (flag) {
                        y += "" + line.charAt(i);
                    }
                    if (line.charAt(i) == ' ') {
                        flag = true;
                    }
                }
                tcord[n] = new Cord(Integer.parseInt(x), Integer.parseInt(y));
                n++;
            } else if (infoR && !prom) {
                p1Name = fr.readLine();
                p2Name = fr.readLine();
                if (PVPoE == -1) {
                    if (p1Name.equals("Computer")) {
                        computer = 1;
                    } else {
                        computer = -1;
                    }
                }
                Turn = Integer.parseInt(fr.readLine());
                moveCount = Integer.parseInt(fr.readLine());
            } else if (infoR && prom) {
                promPawn.add(Integer.parseInt(fr.readLine()));
            }
        }

        fr.close();

        for (int i = 1; i < getBase().length; i++) {
            for (int j = 1; j < getBase()[i].length; j++) {
                if ((i + j) % 2 != 0) {
                    getBase()[i][j] = new Base(new Cord(i, j), root, Color.BLACK);
                } else {
                    getBase()[i][j] = new Base(new Cord(i, j), root, Color.LIGHTCYAN);
                }
            }
        }
        int m = 0;
        for (int i = 0; i < 4; i++) {
            if (tcord[m].getX() != -1) {
                getGlazes()[m] = new Rook(m, tcord[m], root, this);
                getBase()[tcord[m].getX()][tcord[m].getY()].setGIndex(m);
                if (i == 0 || i == 1) {
                    getBase()[tcord[m].getX()][tcord[m].getY()].setBStatus(1);
                } else {
                    getBase()[tcord[m].getX()][tcord[m].getY()].setBStatus(-1);
                }
            } else {
                getGlazes()[m] = new Rook(m, tcord[m], root, this);
                getGlazes()[m].killGlaze();
            }
            m++;
        }
        for (int i = 0; i < 4; i++) {
            if (tcord[m].getX() != -1) {
                getGlazes()[m] = new Knight(m, tcord[m], root, this);
                getBase()[tcord[m].getX()][tcord[m].getY()].setGIndex(m);
                if (i == 0 || i == 1) {
                    getBase()[tcord[m].getX()][tcord[m].getY()].setBStatus(1);
                } else {
                    getBase()[tcord[m].getX()][tcord[m].getY()].setBStatus(-1);
                }
            } else {
                getGlazes()[m] = new Knight(m, tcord[m], root, this);
                getGlazes()[m].killGlaze();
                getGlazes()[m].getGlazeImg().toFront();
            }
            m++;
        }
        for (int i = 0; i < 4; i++) {
            if (tcord[m].getX() != -1) {
                getGlazes()[m] = new Bishop(m, tcord[m], root, this);
                getBase()[tcord[m].getX()][tcord[m].getY()].setGIndex(m);
                if (i == 0 || i == 1) {
                    getBase()[tcord[m].getX()][tcord[m].getY()].setBStatus(1);
                } else {
                    getBase()[tcord[m].getX()][tcord[m].getY()].setBStatus(-1);
                }
            } else {
                getGlazes()[m] = new Bishop(m, tcord[m], root, this);
                getGlazes()[m].killGlaze();
                getGlazes()[m].getGlazeImg().toFront();
            }
            m++;
        }
        for (int i = 0; i < 2; i++) {
            if (tcord[m].getX() != -1) {
                getGlazes()[m] = new King(m, tcord[m], root, this);
                getBase()[tcord[m].getX()][tcord[m].getY()].setGIndex(m);
                if (i == 0) {
                    getBase()[tcord[m].getX()][tcord[m].getY()].setBStatus(1);
                } else {
                    getBase()[tcord[m].getX()][tcord[m].getY()].setBStatus(-1);
                }
            } else {
                getGlazes()[m] = new King(m, tcord[m], root, this);
                getGlazes()[m].killGlaze();
                getGlazes()[m].getGlazeImg().toFront();
            }
            m++;
        }
        for (int i = 0; i < 2; i++) {
            if (tcord[m].getX() != -1) {
                getGlazes()[m] = new Queen(m, tcord[m], root, this);
                getBase()[tcord[m].getX()][tcord[m].getY()].setGIndex(m);
                if (i == 0) {
                    getBase()[tcord[m].getX()][tcord[m].getY()].setBStatus(1);
                } else {
                    getBase()[tcord[m].getX()][tcord[m].getY()].setBStatus(-1);
                }
            } else {
                getGlazes()[m] = new Rook(m, tcord[m], root, this);
                getGlazes()[m].killGlaze();
                getGlazes()[m].getGlazeImg().toFront();
            }
            m++;
        }
        for (int i = 0; i < 16; i++) {
            if (tcord[m].getX() != -1) {
                getGlazes()[m] = new Pawn(m, tcord[m], root, this);
                getBase()[tcord[m].getX()][tcord[m].getY()].setGIndex(m);
                if (i < 8) {
                    getBase()[tcord[m].getX()][tcord[m].getY()].setBStatus(1);
                } else {
                    getBase()[tcord[m].getX()][tcord[m].getY()].setBStatus(-1);
                }
            } else {
                getGlazes()[m] = new Pawn(m, tcord[m], root, this);
                getGlazes()[m].killGlaze();
                getGlazes()[m].getGlazeImg().toFront();
            }
            m++;
        }
        for (int i = 0; i < promPawn.size(); i++) {
            Cord cPawn = getGlazes()[promPawn.get(i)].getCord();
            getGlazes()[promPawn.get(i)].setIsAlive(false);
            getGlazes()[promPawn.get(i)].getGlazeImg().setVisible(false);
            getGlazes()[promPawn.get(i)] = new Queen(promPawn.get(i), cPawn, root, this);
        }

    }

    /**
     * for saving the game when player press save game button in game
     *
     * @throws IOException
     */
    private void saveGame() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        if (PVPoE == 1) {
            fileChooser.setInitialFileName("loadPVP.txt");
        } else {
            fileChooser.setInitialFileName("loadPVEtxt");
        }
        File savedFile = fileChooser.showSaveDialog(ChessFinal.primaryStage);

        if (savedFile != null) {
            try {
                FileWriter w = new FileWriter(savedFile.getAbsoluteFile());
                BufferedWriter fw = new BufferedWriter(w);
                for (int i = 0; i < 32; i++) {
                    if (getGlazes()[i].isIsAlive()) {
                        fw.write(getGlazes()[i].getCord().getX() + " " + getGlazes()[i].getCord().getY());
                        fw.newLine();
                    } else {
                        fw.write("-1 -1");
                        fw.newLine();
                    }
                }
                fw.write("*");
                fw.newLine();
                fw.write(p1Name);
                fw.newLine();
                fw.write(p2Name);
                fw.newLine();
                fw.write(String.valueOf(Turn));  
                fw.newLine();
                fw.write(String.valueOf(moveCount));  
                fw.newLine();
                fw.write("#");
                fw.newLine();
                for (int i = 16; i < 32; i++) {
                    if (getGlazes()[i] instanceof Queen) {
                        fw.write(i);
                        fw.newLine();
                    }
                }

                fw.close();
            } catch (IOException e) {
            }
        }

    }

    /**
     * for calculate valid move with starting Threads
     * 
     */
    private void onUpdate() {
        Thread[] t = new Thread[32];
        for (int i = 0; i < 32; i++) {
            t[i] = new Thread(getGlazes()[i]);
            t[i].start();
        }
        for (int i = 0; i < 32; i++) {
            try {
                t[i].join();
            } catch (Exception e) {

            }
        }
    }

    /**
     * for Player vs Player mode which only works with mouse Focused and EFocused
     * 
     */
    private void PVP() {
        if (getFocused() > -1 && (getGoTo().getX() != -1 || getFocusedE() > -1)) {
            if (getGlazes()[getFocused()].getWB() == Turn) {
                getGlazes()[getFocused()].move(getGoTo());
                setFocused(-1);
                setFocusedE(-1);
                getGoTo().set(-1, -1);
            }
        }
    }

    /**
     * for Player vs Computer mode which works with mouse Focused and EFoucused and moveGen()
     * 
     */
    private void PVE() {
        if (Turn != computer) {
            if (getFocused() > -1 && (getGoTo().getX() != -1 || getFocusedE() > -1)) {
                if (getGlazes()[getFocused()].getWB() == Turn) {
                    getGlazes()[getFocused()].move(getGoTo());
                    setFocused(-1);
                    setFocusedE(-1);
                    getGoTo().set(-1, -1);
                } else if (getGlazes()[getFocused()].getWB() == Turn) {
                    getGlazes()[getFocused()].move(getGoTo());
                    setFocused(-1);
                    setFocusedE(-1);
                    getGoTo().set(-1, -1);
                } else {
                    setFocused(-1);
                    setFocusedE(-1);
                    getGoTo().set(-1, -1);
                }
            }
        } else {
            moveGen();
        }
    }

    /**
     * for generating a Thread to check if any of players are Check/CheckMate or not
     * 
     */
    public static void checkGen() {
        kingB = new CheckMate(-1, getGlazes()[12].getCord());
        kingW = new CheckMate(1, getGlazes()[13].getCord());
        Thread t = new Thread(kingB);
        Thread tt = new Thread(kingW);
        t.start();
        tt.start();
        try {
            t.join();
            tt.join();
        } catch (Exception e) {
        }
    }

    /**
     * for generating moves for Computer with an AI that runs with separated Thread
     * 
     */
    public void moveGen() {
        ArrayList<Integer> possGlazeAct = new ArrayList<Integer>();
        ArrayList<Integer> piorGlazeAct = new ArrayList<Integer>();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < getGlazes().length; i++) {
                    if (getGlazes()[i].getWB() == computer) {
                        if (!glazes[i].getValidK().isEmpty()) {
                            piorGlazeAct.add(i);
                        }
                        if (!glazes[i].getValidM().isEmpty()) {
                            possGlazeAct.add(i);
                        }
                    }
                }
                if (Math.random() < 0.8) {
                    for (int i = 0; i < piorGlazeAct.size(); i++) {
                        for (int j = 0; j < getGlazes()[piorGlazeAct.get(i)].getValidK().size(); j++) {
                            chosenG = piorGlazeAct.get(i);
                            chosenB = getGlazes()[piorGlazeAct.get(i)].getValidK().get(j);
                        }
                    }
                } else {
                    chosenG = possGlazeAct.get((int) (possGlazeAct.size() * Math.random()));
                    chosenB = getGlazes()[chosenG].getValidM().get((int) (getGlazes()[chosenG].getValidM().size() * Math.random()));
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
        try {
            t.join();
        } catch (Exception e) {
        }
        getGlazes()[chosenG].move(chosenB);
        System.out.println(chosenG + " : " + chosenB);
    }

    /**
     * returns computers Turn
     * 
     * @return computer
     */
    public int getComputer() {
        return computer;
    }

}
