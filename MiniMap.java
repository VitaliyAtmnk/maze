package gui.maze;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MiniMap extends JFrame {
    ArrayList<TileImage> rooms;
    MiniMap(ArrayList<Tile> maze){
        this.setLayout(new GridLayout(8,8));
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);

        rooms = new ArrayList<>();

        for(Tile room : maze){
            TileImage toAdd = new TileImage(room);
            this.add(toAdd);
            rooms.add(toAdd);
        }
        pack();
    }
    //TODO vykreslovat hráče jako černou kouli a pak mu měnit barvu dle orbu
    void drawPlayer(Player player){
//        String path = "ikony//Orbs//purpleOrb.png";
//        rooms.get(player.currentLocation.index).setOpaque(true);
//        rooms.get(player.currentLocation.index).setIcon(new ImageIcon(path));
    }
    //TODO přidat png orbu na label
    void drawOrb(){

    }
    //TODO udělat icony jednotlivých roomek, akorát v červené barvě
    void drawTrap(){

    }
    //TODO přidat png targetu na label
    void drawTarget(){

    }
}
class TileImage extends JLabel{
    TileImage(Tile room){
        String imagePath = "";
        boolean north, east, south, west;

        north = room.pd[0] != null;
        east = room.pd[1] != null;
        south = room.pd[2] != null;
        west = room.pd[3] != null;

        if(north) imagePath += "N";
        if(east) imagePath += "E";
        if(south) imagePath += "S";
        if(west) imagePath += "W";

        imagePath = "ikony//"+ "Tiles//" +imagePath + ".png";

        this.setHorizontalAlignment(JLabel.CENTER);
        this.setIcon(new ImageIcon(imagePath));
        this.setOpaque(true);
    }
}

