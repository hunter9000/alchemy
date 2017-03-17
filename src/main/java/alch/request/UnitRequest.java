package alch.request;

public class UnitRequest {

    Long id;
    Integer row, col;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

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
}
