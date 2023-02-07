package gui.maze;

public class Tile {
    Tile[] pd; // ve formátu { NORTH, EAST, SOUTH, WEST }
    boolean orb, target, trap;
    int index;

    public Tile(Tile[] possibleDirections, boolean orb, boolean target, boolean trap, int index) {
        this.pd = possibleDirections;
        this.orb = orb;
        this.target = target;
        this.trap = trap;
        this.index = index;
    }

}
