package gui.maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JFrame implements ActionListener, KeyListener {
    JButton northButton, southButton, westButton, eastButton;
    JButton[] buttons = new JButton[4];
    JLabel help;
    ArrayList<Tile> maze;
    Player player;

    MiniMap miniMap;

    /***
     * A window with "generated maze"
     * It puts player into a starting position, which is one of the Tiles from @maze list
     * Initializes all buttons, panel and styles them
     */
    Game() {
        this.setLayout(new BorderLayout());
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        northButton = new JButton("^");
        southButton = new JButton("v");
        westButton = new JButton("<");
        eastButton = new JButton(">");

        northButton.setFont(new Font("MV Boli", Font.BOLD, 26));
        southButton.setFont(new Font("MV Boli", Font.BOLD, 26));
        westButton.setFont(new Font("MV Boli", Font.BOLD, 26));
        eastButton.setFont(new Font("MV Boli", Font.BOLD, 26));

        northButton.setFocusable(false);
        southButton.setFocusable(false);
        westButton.setFocusable(false);
        eastButton.setFocusable(false);

        help = new JLabel("TMP");
        help.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        help.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(northButton, BorderLayout.NORTH);
        this.add(southButton, BorderLayout.SOUTH);
        this.add(eastButton, BorderLayout.EAST);
        this.add(westButton, BorderLayout.WEST);
        this.add(help, BorderLayout.CENTER);

        maze = initiateMaze();
        miniMap = new MiniMap(maze);

        northButton.addActionListener(this);
        southButton.addActionListener(this);
        westButton.addActionListener(this);
        eastButton.addActionListener(this);
        addKeyListener(this);

        buttons[0] = northButton;
        buttons[1] = eastButton;
        buttons[2] = southButton;
        buttons[3] = westButton;

        player = new Player();
        player.currentLocation = maze.get(0);

        help.setText(String.valueOf(player.currentLocation.index + 1)); // sets text of a center label to current room's number
        configButtons(player.currentLocation);

    }

    //TODO generovat random bludište pomoci nějakého algoritmu, pak naházet do roomek orby/target a trapku
    /***
     * Pre-made maze from Lucid Nightmare.jpg
     * Creates 64 instances of Tile class
     * @return ArrayList of Tiles
     */
    ArrayList<Tile> initiateMaze() {
        ArrayList<Tile> maze = new ArrayList<>();
        //Vygeneruje 64 místností
        for (int i = 0; i < 64; i++) {
            maze.add(new Tile(new Tile[]{null, null, null, null}, false, false, false,i));
        }
        //Nastaví všechny sousedicí místonstí k jednotlivým místnostem
        maze.get(0).pd = new Tile[]{maze.get(60), maze.get(1), null, maze.get(39)};
        maze.get(1).pd = new Tile[]{null, null, maze.get(9), maze.get(0)};
        maze.get(2).pd = new Tile[]{maze.get(62), maze.get(3), maze.get(10), null};
        maze.get(3).pd = new Tile[]{maze.get(63), maze.get(4), maze.get(11), maze.get(2)};
        maze.get(4).pd = new Tile[]{maze.get(56), null, null, maze.get(3)};
        maze.get(5).pd = new Tile[]{null, null, maze.get(13), null};
        maze.get(6).pd = new Tile[]{maze.get(58), maze.get(7), maze.get(14), null};
        maze.get(7).pd = new Tile[]{null, null, maze.get(15), maze.get(6)};
        maze.get(8).pd = new Tile[]{null, maze.get(9), null, maze.get(47)};
        maze.get(9).pd = new Tile[]{maze.get(1), maze.get(10), null, maze.get(8)};
        maze.get(10).pd = new Tile[]{maze.get(2), maze.get(11), null, maze.get(9)};
        maze.get(11).pd = new Tile[]{maze.get(3), maze.get(12), maze.get(19), maze.get(10)};
        maze.get(12).pd = new Tile[]{null, null, maze.get(20), maze.get(11)};
        maze.get(13).pd = new Tile[]{maze.get(5), maze.get(14), maze.get(21), null};
        maze.get(14).pd = new Tile[]{maze.get(6), maze.get(15), maze.get(22), maze.get(13)};
        maze.get(15).pd = new Tile[]{maze.get(7), null, maze.get(23), maze.get(14)};
        maze.get(16).pd = new Tile[]{null, maze.get(17), maze.get(24), null};
        maze.get(17).pd = new Tile[]{null, null, maze.get(25), maze.get(16)};
        maze.get(18).pd = new Tile[]{null, maze.get(19), maze.get(26), null};
        maze.get(19).pd = new Tile[]{maze.get(11), maze.get(20), null, maze.get(18)};
        maze.get(20).pd = new Tile[]{maze.get(12), maze.get(21), maze.get(28), maze.get(19)};
        maze.get(21).pd = new Tile[]{maze.get(13), maze.get(22), null, maze.get(20)};
        maze.get(22).pd = new Tile[]{maze.get(14), null, maze.get(30), maze.get(21)};
        maze.get(23).pd = new Tile[]{maze.get(15), maze.get(48), null, null};
        maze.get(24).pd = new Tile[]{maze.get(16), maze.get(25), null, maze.get(63)};
        maze.get(25).pd = new Tile[]{maze.get(17), maze.get(26), maze.get(33), maze.get(24)};
        maze.get(26).pd = new Tile[]{maze.get(18), maze.get(27), maze.get(34), maze.get(25)};
        maze.get(27).pd = new Tile[]{null, maze.get(28), null, maze.get(26)};
        maze.get(28).pd = new Tile[]{maze.get(20), maze.get(29), null, maze.get(27)};
        maze.get(29).pd = new Tile[]{null, maze.get(30), null, maze.get(28)};
        maze.get(30).pd = new Tile[]{maze.get(22), maze.get(31), maze.get(38), maze.get(29)};
        maze.get(31).pd = new Tile[]{null, null, maze.get(39), maze.get(30)};
        maze.get(32).pd = new Tile[]{null, maze.get(33), maze.get(40), null};
        maze.get(33).pd = new Tile[]{maze.get(25), null, maze.get(41), maze.get(32)};
        maze.get(34).pd = new Tile[]{maze.get(26), maze.get(35), maze.get(42), null};
        maze.get(35).pd = new Tile[]{null, maze.get(36), null, maze.get(34)};
        maze.get(36).pd = new Tile[]{null, maze.get(37), null, maze.get(35)};
        maze.get(37).pd = new Tile[]{null, maze.get(38), maze.get(45), maze.get(36)};
        maze.get(38).pd = new Tile[]{maze.get(30), null, maze.get(46), maze.get(37)};
        maze.get(39).pd = new Tile[]{maze.get(31), maze.get(0), null, null};
        maze.get(40).pd = new Tile[]{maze.get(32), maze.get(41), null, null};
        maze.get(41).pd = new Tile[]{maze.get(33), null, maze.get(49), maze.get(40)};
        maze.get(42).pd = new Tile[]{maze.get(34), null, maze.get(50), null};//trap
        maze.get(43).pd = new Tile[]{null, maze.get(44), maze.get(51), null};
        maze.get(44).pd = new Tile[]{null, maze.get(45), maze.get(52), maze.get(43)};
        maze.get(45).pd = new Tile[]{maze.get(37), null, maze.get(53), maze.get(44)};
        maze.get(46).pd = new Tile[]{maze.get(38), maze.get(47), maze.get(54), null};
        maze.get(47).pd = new Tile[]{null, maze.get(8), maze.get(55), maze.get(46)};
        maze.get(48).pd = new Tile[]{null, null, maze.get(56), maze.get(23)};
        maze.get(49).pd = new Tile[]{maze.get(41), maze.get(50), maze.get(57), null};
        maze.get(50).pd = new Tile[]{maze.get(42), maze.get(51), null, maze.get(49)};
        maze.get(51).pd = new Tile[]{maze.get(43), maze.get(52), maze.get(59), maze.get(50)};
        maze.get(52).pd = new Tile[]{maze.get(44), maze.get(53), maze.get(60), maze.get(51)};
        maze.get(53).pd = new Tile[]{maze.get(45), null, maze.get(61), maze.get(52)};
        maze.get(54).pd = new Tile[]{maze.get(46), maze.get(55), maze.get(62), null};
        maze.get(55).pd = new Tile[]{maze.get(47), null, null, maze.get(54)};
        maze.get(56).pd = new Tile[]{maze.get(48), null, maze.get(4), null};
        maze.get(57).pd = new Tile[]{maze.get(49), maze.get(58), null, null};
        maze.get(58).pd = new Tile[]{null, null, maze.get(6), maze.get(57)};
        maze.get(59).pd = new Tile[]{maze.get(51), null, null, null};
        maze.get(60).pd = new Tile[]{maze.get(52), maze.get(61), maze.get(0), null};
        maze.get(61).pd = new Tile[]{maze.get(53), null, null, maze.get(60)};
        maze.get(62).pd = new Tile[]{maze.get(54), null, maze.get(2), null};
        maze.get(63).pd = new Tile[]{null, maze.get(24), maze.get(3), null};

        //Puts orbs in Tiles
        maze.get(0).orb = true;     // purple
        maze.get(13).orb = true;    // yellow
        maze.get(29).orb = true;    // green
        maze.get(34).orb = true;    // red
        maze.get(54).orb = true;    // blue

        //Puts target in Tiles
        maze.get(43).target = true;   // purple
        maze.get(18).target = true;   // yellow
        maze.get(36).target = true;   // green
        maze.get(23).target = true;   // red
        maze.get(49).target = true;   // blue

        //Sets Tile to a Trap
        maze.get(42).trap = true;

        return maze;
    }

    public static void main(String[] args) {
        Game g = new Game();
        g.setVisible(true);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO fixnout trapu a přidat metody na vykreslování chujovin
        if (player.currentLocation.trap) {
            player.currentLocation = maze.get(randomTile());
            configButtons(player.currentLocation);
        } else {
            if (e.getSource() == northButton) {
                player.currentLocation = player.currentLocation.pd[0];
                if (configButtons(player.currentLocation)) {
                    eastButton.setEnabled(false);
                    westButton.setEnabled(false);
                }
            }
            if (e.getSource() == eastButton) {
                player.currentLocation = player.currentLocation.pd[1];
                if (configButtons(player.currentLocation)) {
                    northButton.setEnabled(false);
                    southButton.setEnabled(false);
                }
            }
            if (e.getSource() == southButton) {
                player.currentLocation = player.currentLocation.pd[2];
                if (configButtons(player.currentLocation)) {
                    eastButton.setEnabled(false);
                    westButton.setEnabled(false);
                }
            }
            if (e.getSource() == westButton) {
                player.currentLocation = player.currentLocation.pd[3];
                if (configButtons(player.currentLocation)) {
                    northButton.setEnabled(false);
                    southButton.setEnabled(false);
                }
            }
        }
        help.setText(String.valueOf(player.currentLocation.index + 1));
        miniMap.drawPlayer(player);
    }

    /***
     * enables/disables all 4 buttons depending on currPos neighbours.
     * if we are able to get from current room to another one, then the button is enabled, otherwise not
     * @param currPos istance of Tile Class, in which is Player currently in
     * @return if a Tile is a crossRoom or not
     */
    boolean configButtons(Tile currPos) {
        boolean isCrossRoom = true;
        for (int i = 0; i < 4; i++) {
            if (currPos.pd[i] == null) {
                isCrossRoom = false;
                buttons[i].setEnabled(false);
            } else buttons[i].setEnabled(true);
        }
        return isCrossRoom;
    }

    /***
     * "random" number generator
     * @return integer from 0 to 64 - (64 excluded)
     */
    int randomTile() {
        Random r = new Random();
        return r.nextInt(64);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    //TODO přidat bind na sebrání/dropnutí orbu
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'm') miniMap.setVisible(true);
    }
}

