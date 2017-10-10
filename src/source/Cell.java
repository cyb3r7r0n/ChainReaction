package source;

import java.io.Serializable;

public class Cell implements Serializable {
    private String colour;
    private final int x;
    private final int y;
    private Grid grid;
    private int criticalMass;
    private int numberOfOrbs;
    private static final long serialVersionUID = 3L;

    public Cell(int x, int y, Grid grid) {
        this.x = x;
        this.y = y;
        this.grid = grid;
        this.numberOfOrbs = 0;
        this.colour = null;
        setCriticalMass();
    }

    public Cell(Cell cell) {
        this.x = cell.x;
        this.y = cell.y;
        this.grid = cell.grid;
        this.colour = cell.colour;
        this.criticalMass = cell.criticalMass;
        this.numberOfOrbs = cell.numberOfOrbs;
    }

    private void setCriticalMass() {
        int criticalMass = 4;
        if(x==0 || x==grid.getNumberOfRows()-1)
            criticalMass--;
        if(y==0 || y==grid.getNumberOfColumns()-1)
            criticalMass--;
        this.criticalMass = criticalMass;
    }

    public boolean addOrb(String colour){
        this.colour = colour;
        boolean burst = false;
        if(numberOfOrbs+1 == criticalMass)
            burst = true;
        this.numberOfOrbs = (this.numberOfOrbs+1)%criticalMass;
        if(burst)
            this.colour=null;
        return burst;
    }

    public String getColour() {
        return colour;
    }

    @Override
    public String toString() {
        if(colour==null)
            return "("+x+","+y+",  "+","+criticalMass+")";
        else
            return "("+x+","+y+","+this.numberOfOrbs+this.colour+","+criticalMass+")";
    }

    @Override
    public boolean equals(Object o) {
        if(o.getClass() == this.getClass()) {
            Cell cell2 = (Cell) o;
            if(cell2.colour == null) {
                return true;
            } else {
                return this.colour.equals(cell2.colour);
            }
        }
        return false;
    }
}
