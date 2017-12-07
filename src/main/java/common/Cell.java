package common;

public class Cell {
    private byte numMineAround;
    private boolean open;
    private boolean mine;
    private boolean flag;
    private boolean boomed;
    private CellType cellType;

    public void reset() {
        numMineAround = 0;
        open = false;
        mine = false;
        flag = false;
        boomed = false;
        cellType = CellType.Close;
    }

    public int getNumMineAround() {
        return numMineAround;
    }

    public void setNumMineAround(int numMineAround) {
        this.numMineAround = (byte) numMineAround;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isBoomed() {
        return boomed;
    }

    public void setBoomed(boolean boomed) {
        this.boomed = boomed;
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }
}
