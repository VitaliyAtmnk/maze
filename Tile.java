package gui.maze;

import java.util.Comparator;

public class Tile {
    Tile[] neighbours; // ve formátu { NORTH, EAST, SOUTH, WEST }
    boolean orb, target, trap;
    int index;

    public Tile(Tile[] possibleDirections, boolean orb, boolean target, boolean trap, int index) {
        this.neighbours = possibleDirections;
        this.orb = orb;
        this.target = target;
        this.trap = trap;
        this.index = index;
    }

    public static final Comparator<Tile> BY_INDEX = new Comparator<Tile>() {
        @Override
        public int compare(Tile o1, Tile o2) {
            return Integer.compare(o1.index, o2.index);
        }
    };
}
