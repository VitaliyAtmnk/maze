package gui.maze;

import javax.swing.*;
import java.awt.*;


public class CenterWindow extends JPanel {

    int score;
    boolean orb, target;
    int numOfRooms;

    String roomInfo;
    Image background;
    Image playerIcon;
    Image orbImage, targetImage;

    CenterWindow(int numberOfRooms) {
        setLayout(new BorderLayout());
        JPanel infoPanel = new JPanel();
        setSize(450, 450);
        infoPanel.setLayout(new GridLayout(1, 2));
        playerIcon = new ImageIcon("ikony/player.png").getImage();

        orbImage = new ImageIcon("ikony//Orbs//purpleOrb.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        targetImage = new ImageIcon("ikony//Target//purpleTarget.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        add(infoPanel, BorderLayout.NORTH);

        numOfRooms = numberOfRooms;
        score = 0;
    }

    void updateScore() {
        score += 10;
    }

    void setRoomInfo(Tile current) {
        String path = "ikony//Tiles//";
        if (current.neighbours[0] != null) path += "N";
        if (current.neighbours[1] != null) path += "E";
        if (current.neighbours[2] != null) path += "S";
        if (current.neighbours[3] != null) path += "W";

        Image room = new ImageIcon(path + ".png").getImage();
        background = room.getScaledInstance(500, 500, Image.SCALE_FAST);
        String type = "Normal";
        if (current.orb) type = "Orb";
        if (current.target) type = "Target";
        if (current.trap) type = "Trap";
        orb = current.orb;
        target = current.target;
        String roomNumber = String.format("%d / %d", (current.index + 1), numOfRooms);
        roomInfo = roomNumber + " Room : " + type;
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 12));

        do {
            g2.drawImage(background, 0, 0, null);
        } while(!g2.drawImage(background, 0, 0, null));

        g2.drawImage(playerIcon, (getWidth() / 2) - (playerIcon.getWidth(null) / 2), (getHeight() / 2) - (playerIcon.getHeight(null) / 2), null);
        g2.drawString(roomInfo, 340,20);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        g2.drawString(String.valueOf(score),30,25);
        //  TODO souřadky
        if(orb) g2.drawImage(orbImage, 70, 400, null);
        if(target) g2.drawImage(targetImage, 70, 400, null);
    }
}
