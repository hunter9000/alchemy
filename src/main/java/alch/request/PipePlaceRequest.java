package alch.request;

import alch.model.DirectionType;

public class PipePlaceRequest {
    Integer row, col;
    DirectionType dir1, dir2;

    public Integer getRow() {
        return row;
    }
    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getCol() {
        return col;
    }
    public void setCol(Integer col) {
        this.col = col;
    }

    public DirectionType getDir1() {
        return dir1;
    }
    public void setDir1(DirectionType dir1) {
        this.dir1 = dir1;
    }

    public DirectionType getDir2() {
        return dir2;
    }
    public void setDir2(DirectionType dir2) {
        this.dir2 = dir2;
    }
}
