package it.unimol.minesweeper.app;

public class Box {

    private boolean explored;
    private boolean mined;
    private boolean marked;
    private String appearance;

    // Constructor
    public Box(boolean mined) {
        this.mined = mined;
        this.explored = false;
        this.marked = false;
        this.appearance = null;
    }

    // Setter
    public void explore() {
        this.explored = true;
    }

    public void mark() {
        this.marked = true;
    }

    public void unmark() {
        this.marked = false;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    // Getter
    public boolean isMined() {
        return mined;
    }

    public boolean isMarked() {
        return marked;
    }

    public boolean isExplored() {
        if (this.isMined()) {
            this.appearance = null;
        }
        return explored;
    }

    public String getAppearance() {
        if (this.appearance != null) {
            return appearance;
        }

        if (this.explored) {
            return this.mined ? "X" : " ";
        } else if (this.marked) {
            return "#";
        }

        return "-";
    }
}
