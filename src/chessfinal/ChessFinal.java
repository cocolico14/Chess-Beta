/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessfinal;

import com.jfoenix.controls.JFXToggleButton;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author soheil
 */
public class ChessFinal extends Application {

    private static int mode = 0; // 1=pvp -1=pve
    private static int newload = 0; // 1=new -1=load

    private static Stage window;
    private static Scene menu1;
    private static Scene menu2;
    private static Scene input;
    private static Scene input2;
    
    public static Stage primaryStage;

    private Game game = new Game();
    private Group root = new Group();
    private static boolean isBlack = false;

    @Override
    public void start(Stage primaryStage) throws IOException {
        window = primaryStage;
        menu1 = new Scene(makeMenu1(), 500, 500);
        menu2 = new Scene(makeMenu2(), 500, 500);
        input = new Scene(InputScreen1(), 500, 500);
        input2 = new Scene(InputScreen2(), 500, 500);

        primaryStage.setTitle("Chess 0.1b");
        primaryStage.setScene(menu1);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void Swtch(int a, int b) {
        mode = a;
        newload = b;
    }

    public Group makeMenu1() {
        Group result = new Group();

        Image image = new Image(getClass().getResourceAsStream("234.jpg"));
        ImageView imv = new ImageView(image);
        imv.setX(-600);
        imv.setY(-300);
        imv.setScaleX(0.5);
        imv.setScaleY(0.5);
        imv.toBack();

        Text txt = new Text("Chess Game");
        txt.setScaleX(3);
        txt.setScaleY(3);
        txt.setX(220);
        txt.setY(100);
        txt.setStroke(Color.THISTLE);
        txt.setStrokeWidth(0.5);
        txt.setFill(Color.CADETBLUE);

        HBox h = new HBox(100);
        h.setAlignment(Pos.CENTER);

        Button PVP = new Button("Player vs Player");
        PVP.setOnAction((event -> {
            Swtch(1, newload);
            window.setScene(menu2);
        }));

        Button PVE = new Button("Player vs Envo");
        PVE.setOnAction(event -> {
            Swtch(-1, newload);
            window.setScene(menu2);
        });

        PVP.setScaleX(2);
        PVP.setScaleY(2);
        PVE.setScaleX(2);
        PVE.setScaleY(2);
        h.getChildren().addAll(PVP, PVE);
        h.setTranslateX(110);
        h.setTranslateY(240);

        result.getChildren().addAll(imv, h, txt);
        return result;
    }

    public HBox makeMenu2() {
        HBox h = new HBox(100);
        h.setAlignment(Pos.CENTER);

        Button newg = new Button("New Game");
        newg.setOnAction(event -> {
            Swtch(mode, 1);
            if (mode == 1) {
                window.setScene(input);
            } else {
                window.setScene(input2);
            }
        });

        Button load = new Button("Load Game");
        load.setOnAction(event -> {
            Swtch(mode, -1);
            try {
                FileChooser fileChooser = new FileChooser();
                File selectedFile = fileChooser.showOpenDialog(null);

                if (selectedFile != null) {
                    System.out.println("chose");
                } else {
                }
                window.setScene(game.launchGame(root, mode, newload, "", "", selectedFile));
            } catch (IOException ex) {
            }
        });

        newg.setScaleX(2);
        newg.setScaleY(2);
        load.setScaleX(2);
        load.setScaleY(2);
        h.getChildren().addAll(newg, load);
        return h;
    }

    public GridPane InputScreen1() {
        GridPane g = new GridPane();
        g.setPadding(new Insets(75, 30, 30, 50));
        g.setVgap(10);
        g.setHgap(10);

        Label player1 = new Label("White Player Name :");
        GridPane.setConstraints(player1, 0, 0);
        TextField p1 = new TextField("Player 1");
        p1.setMaxWidth(80);
        p1.setAlignment(Pos.CENTER);
        GridPane.setConstraints(p1, 1, 0);

        Label player2 = new Label("Black Player Name :");
        GridPane.setConstraints(player2, 0, 1);
        TextField p2 = new TextField("Player 2");
        p2.setAlignment(Pos.CENTER);
        p2.setMaxWidth(80);
        GridPane.setConstraints(p2, 1, 1);

        Button add = new Button("Play!");
        add.setOnAction(event -> {
            try {
                InputStreamReader isReader = new InputStreamReader(this.getClass().getResourceAsStream("newgame.txt"));
                File file = new File("newgame.txt");
                window.setScene(game.launchGame(root, mode, newload, p1.getText(), p2.getText(), file));
            } catch (IOException ex) {
            }
        });
        GridPane.setConstraints(add, 1, 2);

        g.getChildren().addAll(player1, p1, player2, p2, add);
        return g;
    }

    public GridPane InputScreen2() {
        GridPane g = new GridPane();
        g.setPadding(new Insets(75, 30, 30, 50));
        g.setVgap(10);
        g.setHgap(10);

        Label chose = new Label("Black or White ?");
        GridPane.setConstraints(chose, 0, 0);
        JFXToggleButton BorW = new JFXToggleButton();
        BorW.setOnMousePressed(event -> {
            isBlack = !isBlack;
        });
        BorW.setToggleColor(Color.BLACK);
        BorW.setAlignment(Pos.CENTER);
        GridPane.setConstraints(BorW, 1, 0);

        Label player1 = new Label("Player Name :");
        GridPane.setConstraints(player1, 0, 1);
        TextField p1 = new TextField("Player 1");
        p1.setMaxWidth(80);
        p1.setAlignment(Pos.CENTER);
        GridPane.setConstraints(p1, 1, 1);

        Button add = new Button("Play!");
        add.setOnAction(event -> {
            try {
                if (isBlack) {
                    File file = new File("newgame.txt");
                    window.setScene(game.launchGame(root, mode, newload, p1.getText(), "Computer", file));
                } else {
                    File file = new File("newgame.txt");
                    window.setScene(game.launchGame(root, mode, newload, "Computer", p1.getText(), file));
                }
            } catch (IOException ex) {
            }
        });
        GridPane.setConstraints(add, 1, 2);

        g.getChildren().addAll(player1, p1, chose, BorW, add);
        return g;
    }

}
