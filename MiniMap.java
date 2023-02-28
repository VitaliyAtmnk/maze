package gui.maze;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MiniMap extends JFrame {
    
    MiniMap(ArrayList<Tile> maze, int dimension) {
        this.setLayout(new GridLayout(dimension, dimension));
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);

        maze.sort(Tile.BY_INDEX);

        for (Tile room : maze) {
            this.add(new TileImage(room));
        }
        pack();
    }

}

class TileImage extends JLabel {
    /**
     * Drawing pictures as an overlay is *really* inefficient in scope of this task.
     * It is however a good approach coming for 3D textures and resources, so I guess I can call it a good practice.
     * Also, all possible variations are quite easily calculable (V(4,2) = 4^2 - 1 all closed scenario), therefore
     * drawing it should not be such a pain.
     * @param room room to be drawn
     */
    TileImage(Tile room) {
        String imagePath = "";
        boolean north, east, south, west;

        north = room.neighbours[0] != null;
        east = room.neighbours[1] != null;
        south = room.neighbours[2] != null;
        west = room.neighbours[3] != null;

        //This is certainly...unique approach
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

