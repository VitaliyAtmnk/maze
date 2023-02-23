package gui.maze;

import javax.swing.*;
import java.awt.*;


public class CenterWindow extends JPanel {

    int score;
    boolean orb, target, trap;
    int numOfRooms;

    String roomInfo;
    Image background;
    Image playerIcon;
    Image orbImage, targetImage, scoreHolder;

    CenterWindow(int numberOfRooms) {
        setLayout(new BorderLayout());
        setSize(450, 450);

        playerIcon = new ImageIcon("ikony/player.png").getImage();
        orbImage = new ImageIcon("ikony//Orbs//purpleOrb.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        targetImage = new ImageIcon("ikony//Target//purpleTarget.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        scoreHolder = new ImageIcon("ikony//pepeHolder.png").getImage();

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

        background = new ImageIcon(path + ".png").getImage().getScaledInstance(500, 500, Image.SCALE_FAST);

        roomInfo = String.format("%d / %d", (current.index + 1), numOfRooms);

        orb = current.orb;
        target = current.target;
        trap = current.trap;
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        do {
            g2.drawImage(background, 0, 0, null);
        } while (!g2.drawImage(background, 0, 0, null));
        g2.drawString(roomInfo, 360, 20);

        g2.drawImage(playerIcon, (getWidth() / 2) - (playerIcon.getWidth(null) / 2), (getHeight() / 2) - (playerIcon.getHeight(null) / 2), null);
        g2.drawImage(scoreHolder, 30, 0, null);
        g2.setColor(Color.BLACK);
        g2.drawString(String.valueOf(score), 50, 25);

        g2.setColor(Color.decode("#CA4262"));
        g2.setFont(new Font("Arial", Font.BOLD, 30));

        if(orb) g2.drawString("Orb",370,60);
        if(target) g2.drawString("Target",355,60);
        if(trap) g2.drawString("Trap",365,60);

    }
}
