package gui.maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Game extends JFrame implements ActionListener, KeyListener {
    JButton northButton, southButton, westButton, eastButton;
    JButton[] buttons = new JButton[4];
    ArrayList<Tile> maze;
    Player player;
    MiniMap miniMap;
    final int DIMENSION;

    CenterWindow center;

    int orbsLeft;

    /***
     * A window with "generated maze"
     * It puts player into a starting position, which is one of the Tiles from @maze list
     * Initializes all buttons, panel and styles them
     */
    Game(int dimension) {
        this.setLayout(new BorderLayout());
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        northButton = createSimpleButton("^");
        southButton = createSimpleButton("v");
        westButton = createSimpleButton("<");
        eastButton = createSimpleButton(">");

        northButton.setFocusable(false);
        southButton.setFocusable(false);
        westButton.setFocusable(false);
        eastButton.setFocusable(false);


        this.add(northButton, BorderLayout.NORTH);
        this.add(southButton, BorderLayout.SOUTH);
        this.add(eastButton, BorderLayout.EAST);
        this.add(westButton, BorderLayout.WEST);

//        maze = ogMaze();
        DIMENSION = dimension;
        maze = generateMaze(DIMENSION, 0);
        miniMap = new MiniMap(maze, DIMENSION);
        orbsLeft = 5;

        center = new CenterWindow(DIMENSION * DIMENSION);

        this.add(center, BorderLayout.CENTER);

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
        player.currentLocation = maze.get(randomNumberGenerator(DIMENSION * DIMENSION));
        center.setRoomInfo(player.currentLocation);

        configButtons(player.currentLocation);
    }

    /***
     * Pre-made maze from Lucid Nightmare.jpg
     * Creates 64 instances of Tile class
     * @return ArrayList of Tiles
     */
    ArrayList<Tile> ogMaze() {
        ArrayList<Tile> maze = new ArrayList<>();
        // Generates 64 Tiles
        for (int i = 0; i < 64; i++) {
            maze.add(new Tile(new Tile[]{null, null, null, null}, false, false, false, i));
        }
        // Boháčová noční můra
        maze.get(0).neighbours = new Tile[]{maze.get(60), maze.get(1), null, maze.get(39)};
        maze.get(1).neighbours = new Tile[]{null, null, maze.get(9), maze.get(0)};
        maze.get(2).neighbours = new Tile[]{maze.get(62), maze.get(3), maze.get(10), null};
        maze.get(3).neighbours = new Tile[]{maze.get(63), maze.get(4), maze.get(11), maze.get(2)};
        maze.get(4).neighbours = new Tile[]{maze.get(56), null, null, maze.get(3)};
        maze.get(5).neighbours = new Tile[]{null, null, maze.get(13), null};
        maze.get(6).neighbours = new Tile[]{maze.get(58), maze.get(7), maze.get(14), null};
        maze.get(7).neighbours = new Tile[]{null, null, maze.get(15), maze.get(6)};
        maze.get(8).neighbours = new Tile[]{null, maze.get(9), null, maze.get(47)};
        maze.get(9).neighbours = new Tile[]{maze.get(1), maze.get(10), null, maze.get(8)};
        maze.get(10).neighbours = new Tile[]{maze.get(2), maze.get(11), null, maze.get(9)};
        maze.get(11).neighbours = new Tile[]{maze.get(3), maze.get(12), maze.get(19), maze.get(10)};
        maze.get(12).neighbours = new Tile[]{null, null, maze.get(20), maze.get(11)};
        maze.get(13).neighbours = new Tile[]{maze.get(5), maze.get(14), maze.get(21), null};
        maze.get(14).neighbours = new Tile[]{maze.get(6), maze.get(15), maze.get(22), maze.get(13)};
        maze.get(15).neighbours = new Tile[]{maze.get(7), null, maze.get(23), maze.get(14)};
        maze.get(16).neighbours = new Tile[]{null, maze.get(17), maze.get(24), null};
        maze.get(17).neighbours = new Tile[]{null, null, maze.get(25), maze.get(16)};
        maze.get(18).neighbours = new Tile[]{null, maze.get(19), maze.get(26), null};
        maze.get(19).neighbours = new Tile[]{maze.get(11), maze.get(20), null, maze.get(18)};
        maze.get(20).neighbours = new Tile[]{maze.get(12), maze.get(21), maze.get(28), maze.get(19)};
        maze.get(21).neighbours = new Tile[]{maze.get(13), maze.get(22), null, maze.get(20)};
        maze.get(22).neighbours = new Tile[]{maze.get(14), null, maze.get(30), maze.get(21)};
        maze.get(23).neighbours = new Tile[]{maze.get(15), maze.get(48), null, null};
        maze.get(24).neighbours = new Tile[]{maze.get(16), maze.get(25), null, maze.get(63)};
        maze.get(25).neighbours = new Tile[]{maze.get(17), maze.get(26), maze.get(33), maze.get(24)};
        maze.get(26).neighbours = new Tile[]{maze.get(18), maze.get(27), maze.get(34), maze.get(25)};
        maze.get(27).neighbours = new Tile[]{null, maze.get(28), null, maze.get(26)};
        maze.get(28).neighbours = new Tile[]{maze.get(20), maze.get(29), null, maze.get(27)};
        maze.get(29).neighbours = new Tile[]{null, maze.get(30), null, maze.get(28)};
        maze.get(30).neighbours = new Tile[]{maze.get(22), maze.get(31), maze.get(38), maze.get(29)};
        maze.get(31).neighbours = new Tile[]{null, null, maze.get(39), maze.get(30)};
        maze.get(32).neighbours = new Tile[]{null, maze.get(33), maze.get(40), null};
        maze.get(33).neighbours = new Tile[]{maze.get(25), null, maze.get(41), maze.get(32)};
        maze.get(34).neighbours = new Tile[]{maze.get(26), maze.get(35), maze.get(42), null};
        maze.get(35).neighbours = new Tile[]{null, maze.get(36), null, maze.get(34)};
        maze.get(36).neighbours = new Tile[]{null, maze.get(37), null, maze.get(35)};
        maze.get(37).neighbours = new Tile[]{null, maze.get(38), maze.get(45), maze.get(36)};
        maze.get(38).neighbours = new Tile[]{maze.get(30), null, maze.get(46), maze.get(37)};
        maze.get(39).neighbours = new Tile[]{maze.get(31), maze.get(0), null, null};
        maze.get(40).neighbours = new Tile[]{maze.get(32), maze.get(41), null, null};
        maze.get(41).neighbours = new Tile[]{maze.get(33), null, maze.get(49), maze.get(40)};
        maze.get(42).neighbours = new Tile[]{maze.get(34), null, maze.get(50), null};//trap
        maze.get(43).neighbours = new Tile[]{null, maze.get(44), maze.get(51), null};
        maze.get(44).neighbours = new Tile[]{null, maze.get(45), maze.get(52), maze.get(43)};
        maze.get(45).neighbours = new Tile[]{maze.get(37), null, maze.get(53), maze.get(44)};
        maze.get(46).neighbours = new Tile[]{maze.get(38), maze.get(47), maze.get(54), null};
        maze.get(47).neighbours = new Tile[]{null, maze.get(8), maze.get(55), maze.get(46)};
        maze.get(48).neighbours = new Tile[]{null, null, maze.get(56), maze.get(23)};
        maze.get(49).neighbours = new Tile[]{maze.get(41), maze.get(50), maze.get(57), null};
        maze.get(50).neighbours = new Tile[]{maze.get(42), maze.get(51), null, maze.get(49)};
        maze.get(51).neighbours = new Tile[]{maze.get(43), maze.get(52), maze.get(59), maze.get(50)};
        maze.get(52).neighbours = new Tile[]{maze.get(44), maze.get(53), maze.get(60), maze.get(51)};
        maze.get(53).neighbours = new Tile[]{maze.get(45), null, maze.get(61), maze.get(52)};
        maze.get(54).neighbours = new Tile[]{maze.get(46), maze.get(55), maze.get(62), null};
        maze.get(55).neighbours = new Tile[]{maze.get(47), null, null, maze.get(54)};
        maze.get(56).neighbours = new Tile[]{maze.get(48), null, maze.get(4), null};
        maze.get(57).neighbours = new Tile[]{maze.get(49), maze.get(58), null, null};
        maze.get(58).neighbours = new Tile[]{null, null, maze.get(6), maze.get(57)};
        maze.get(59).neighbours = new Tile[]{maze.get(51), null, null, null};
        maze.get(60).neighbours = new Tile[]{maze.get(52), maze.get(61), maze.get(0), null};
        maze.get(61).neighbours = new Tile[]{maze.get(53), null, null, maze.get(60)};
        maze.get(62).neighbours = new Tile[]{maze.get(54), null, maze.get(2), null};
        maze.get(63).neighbours = new Tile[]{null, maze.get(24), maze.get(3), null};

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

        //Sets a Tile to a Trap
        maze.get(42).trap = true;

        return maze;
    }
    /**
     * A maze generated with either a DFS or BFS algorithm
     * @param dimension expects an even integer as an input and generates maze of a size dimension * dimension, if an odd integer is inputted
     *                  it may end up with an error. This problem will totally be fixed in the future :).
     * @param algorithm 0 - Depth first search
     *                  1 - bread first search
     *                  any other int will call ogMaze
     * @return ArrayList of Tiles
     */
    ArrayList<Tile> generateMaze(int dimension, int algorithm) {
        ArrayList<Tile> maze = new ArrayList<>();

        for (int i = 0; i < dimension * dimension; i++) {
            maze.add(new Tile(new Tile[]{null, null, null, null}, false, false, false, i));
        }

        int randDirection;
        Tile neighbour;
        Stack<Tile> stack = new Stack<>();
        Set<Tile> visited = new HashSet<>();
        Tile start = maze.get(randomNumberGenerator(maze.size()));

        if (algorithm == 0) {
            stack.push(start);
            visited.add(start);

            while (!stack.empty()) {
                Tile current = stack.pop();

                ArrayList<Tile> neighbours = new ArrayList<>();
                ArrayList<Integer> directions = new ArrayList<>();

                for (int i = 0; i < 4; i++) {
                    neighbour = maze.get(neighbourIndex(current.index, i));
                    if (visited.contains(neighbour)) continue;
                    neighbours.add(neighbour);
                    directions.add(i);

                }

                if (!neighbours.isEmpty()) {
                    randDirection = randomNumberGenerator(neighbours.size());
                    neighbour = neighbours.get(randDirection);
                    current.neighbours[directions.get(randDirection)] = neighbour;
                    neighbour.neighbours[(directions.get(randDirection) + 2) % 4] = current;
                    stack.push(current);
                    stack.push(neighbour);
                    visited.add(neighbour);
                }
            }
        }
        if (algorithm == 1) {
            Queue<Tile> queue = new LinkedList<>();
            queue.add(start);
            visited.add(start);

            while (!queue.isEmpty()) {
                Tile current = queue.poll();

                ArrayList<Tile> neighbours = new ArrayList<>();
                ArrayList<Integer> directions = new ArrayList<>();

                for (int i = 0; i < 4; i++) {
                    neighbour = maze.get(neighbourIndex(current.index, i));
                    if (visited.contains(neighbour)) continue;
                    neighbours.add(neighbour);
                    directions.add(i);
                    visited.add(neighbour); // Mark neighbour as visited when it is discovered
                }

                if (!neighbours.isEmpty()) {
                    for (int i = 0; i < neighbours.size(); i++) {
                        neighbour = neighbours.get(i);
                        int direction = directions.get(i);
                        current.neighbours[direction] = neighbour;
                        neighbour.neighbours[(direction + 2) % 4] = current;
                        queue.add(neighbour); // Add unexplored neighbours to the queue
                    }
                }
            }
        }
        else return ogMaze();
        ArrayList<Integer> toChoose = generateUniqueNumberSet(11, (maze.size()) - 1);

        // sets all orbs and targets (5 of each)
        for (int i = 0; i < 9; i += 2) {
            maze.get(toChoose.get(i)).orb = true;
            maze.get(toChoose.get(i + 1)).target = true;
        }

        // sets up a trap
        maze.get(toChoose.get(10)).trap = true;

        return maze;
    }

    public static void main(String[] args) {
        Game g = new Game(8);
        g.setVisible(true);
    }
    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (player.currentLocation.trap) {
            player.currentLocation = maze.get(randomNumberGenerator(DIMENSION * DIMENSION));
            configButtons(player.currentLocation);

        } else if (e.getSource() == northButton) {
            player.currentLocation = player.currentLocation.neighbours[0];
            if (configButtons(player.currentLocation)) westEastDisabler();

        } else if (e.getSource() == eastButton) {
            player.currentLocation = player.currentLocation.neighbours[1];
            if (configButtons(player.currentLocation)) northSouthDisabler();

        } else if (e.getSource() == southButton) {
            player.currentLocation = player.currentLocation.neighbours[2];
            if (configButtons(player.currentLocation)) westEastDisabler();

        } else if (e.getSource() == westButton) {
            player.currentLocation = player.currentLocation.neighbours[3];
            if (configButtons(player.currentLocation)) northSouthDisabler();

        }
        center.setRoomInfo(player.currentLocation);
        center.updateScore();
        center.repaint();
    }

    /***
     * enables/disables all 4 buttons depending on currPos neighbours.
     * if we are able to get from current room to another one, then the button is enabled, otherwise not
     * @param currPos instance of Tile Class, in which is Player currently in
     * @return if a Tile is a crossRoom or not
     */
    private boolean configButtons(Tile currPos) {
        boolean isCrossRoom = true;
        for (int i = 0; i < 4; i++) {
            if (currPos.neighbours[i] == null) {
                isCrossRoom = false;
                buttons[i].setEnabled(false);
            } else buttons[i].setEnabled(true);
        }
        return isCrossRoom;
    }

    /***
     * "random" number generator
     * @return integer from 0 to upperBound - (upperBound excluded)
     */
    public int randomNumberGenerator(int upperBound) {
        Random r = new Random();
        return r.nextInt(upperBound);
    }

    /**
     * generates an Array of unique integers from 0 to upperBound
     *
     * @param amount     number of integers to generate
     * @param upperBound the greatest integer that can be generated
     * @return ArrayList of integers
     */
    private ArrayList<Integer> generateUniqueNumberSet(int amount, int upperBound) {
        Set<Integer> unique = new HashSet<>();

        while (unique.size() != amount) {
            int randInt = ThreadLocalRandom.current().nextInt(0, upperBound);
            unique.add(randInt);
        }

        return new ArrayList<>(unique.stream().toList());
    }

    /**
     * returns an index of a neighbour in a chosen direction
     *
     * @param index index of a current tile
     * @param dir   0 - North
     *              1 - East
     *              2 - South
     *              3 - West
     *              any other input will return -1
     * @return index of a neighbour in a chosen direction; -1 if chosen wrongly
     */
    private int neighbourIndex(int index, int dir) {
        int dim_half = DIMENSION / 2;

        switch (dir) {
            case 0: // NORTH
                if (index / DIMENSION != 0) return index - DIMENSION;
                return DIMENSION * (DIMENSION - 1) + ((dim_half + index) % DIMENSION);         // - is an edge
            case 1: // EAST
                if ((index + 1) % DIMENSION != 0) return index + 1;
                return ((index + (DIMENSION * (dim_half - 1))) + 1) % (DIMENSION * DIMENSION); // - is an edge
            case 2: // SOUTH
                if (index / DIMENSION != (DIMENSION - 1)) return index + DIMENSION;
                return (index - (DIMENSION * (DIMENSION - 1)) + dim_half) % DIMENSION;         // - is an edge
            case 3: // WEST
                if (index % DIMENSION != 0) return index - 1;
                return ((index + (DIMENSION * (dim_half + 1))) - 1) % (DIMENSION * DIMENSION); // - is an edge
        }
        return -1;
    }

    /***
     * disables westButton and eastButton
     */
    private void westEastDisabler() {
        eastButton.setEnabled(false);
        westButton.setEnabled(false);
    }

    /***
     * disables northButton and southButton
     */
    private void northSouthDisabler() {
        northButton.setEnabled(false);
        southButton.setEnabled(false);
    }

    /**
     * returns button with style B->
     *
     * @param text text that is displayed with button
     * @return stylized instance of JButton
     */
    private static JButton createSimpleButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(41, 41, 41));
        button.setForeground(Color.white);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        Font buttonFont = new Font("Arial", Font.BOLD, 26);
        button.setFont(buttonFont);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // opens up a minimap
        if (e.getKeyChar() == 'm') {
            miniMap.setVisible(true);
            return;
        }
        // picks up orb
        if (e.getKeyChar() == 'e' && player.currentLocation.orb && !player.holdingOrb) {
            player.holdingOrb = true;
            player.currentLocation.orb = false;
        }
        // drops orb
        if (e.getKeyChar() == 'g' && player.holdingOrb && !player.currentLocation.orb) {
            player.holdingOrb = false;
            player.currentLocation.orb = !player.currentLocation.target;
            if (player.currentLocation.target) {
                orbsLeft--;
                if (orbsLeft == 0) {
                    JOptionPane.showMessageDialog(new JFrame(), "You won, your score is: " + center.score);
                    System.exit(0);
                }
                player.currentLocation.target = false;
            }
        }
        // při použití kláves místo tlačítek, můžete kompletně ignorovat trapku, tohle je featura ne bug.
        if (e.getKeyCode() == KeyEvent.VK_UP && northButton.isEnabled()) {
            center.updateScore();
            player.currentLocation = player.currentLocation.neighbours[0];
            if (configButtons(player.currentLocation)) westEastDisabler();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && eastButton.isEnabled()) {
            center.updateScore();
            player.currentLocation = player.currentLocation.neighbours[1];
            if (configButtons(player.currentLocation)) northSouthDisabler();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && southButton.isEnabled()) {
            center.updateScore();
            player.currentLocation = player.currentLocation.neighbours[2];
            if (configButtons(player.currentLocation)) westEastDisabler();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && westButton.isEnabled()) {
            center.updateScore();
            player.currentLocation = player.currentLocation.neighbours[3];
            if (configButtons(player.currentLocation)) northSouthDisabler();
        }

        center.setRoomInfo(player.currentLocation);
        center.repaint();

    }
}

