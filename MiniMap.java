package gui.maze;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MiniMap extends JFrame {
    ArrayList<Tile> rooms;
    private int x, y;

    MiniMap(ArrayList<Tile> maze, int dimension) {
        this.setLayout(new GridLayout(dimension, dimension));
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);

        maze.sort(Tile.BY_INDEX);
        rooms = new ArrayList<>(maze);

        for (Tile room : maze) {
            this.add(new TileImage(room));
        }
        pack();
    }

}

class TileImage extends JLabel {
    TileImage(Tile room) {
        String imagePath = "";
        boolean north, east, south, west;

        north = room.neighbours[0] != null;
        east = room.neighbours[1] != null;
        south = room.neighbours[2] != null;
        west = room.neighbours[3] != null;

        if (north) imagePath += "N";
        if (east) imagePath += "E";
        if (south) imagePath += "S";
        if (west) imagePath += "W";

        imagePath = "ikony//" + "Tiles//" + imagePath + ".png";

        this.setHorizontalAlignment(JLabel.CENTER);
        this.setIcon(new ImageIcon(imagePath));
        this.setOpaque(false);
    }
}

