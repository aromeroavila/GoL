package arao.gameoflife.model;

public class Cell {

    private int x;
    private int y;
    private boolean value;

    private Cell(Builder builder) {
        value = builder.value;
        x = builder.x;
        y = builder.y;
    }

    public boolean getValue() {
        return value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static final class Builder {
        private boolean value;
        private int x;
        private int y;

        public static Builder aCell() {
            return new Builder();
        }

        public Builder value(boolean value) {
            this.value = value;
            return this;
        }

        public Builder x(int x) {
            this.x = x;
            return this;
        }

        public Builder y(int y) {
            this.y = y;
            return this;
        }

        public Cell build() {
            return new Cell(this);
        }
    }
}
