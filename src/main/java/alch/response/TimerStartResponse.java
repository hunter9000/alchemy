package alch.response;

import java.util.List;

public class TimerStartResponse {
    public List<CellError> cellErrors;

    public List<CellError> getCellErrors() {
        return cellErrors;
    }
    public void setCellErrors(List<CellError> cellErrors) {
        this.cellErrors = cellErrors;
    }
}
