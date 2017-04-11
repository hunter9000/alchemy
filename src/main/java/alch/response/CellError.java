package alch.response;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CellError {
    private final static Logger logger = LoggerFactory.getLogger(CellError.class);

    public int row;
    public int col;
    public String errorMessage;

    public CellError(int row, int col, String errorMessage) {
        this.row = row;
        this.col = col;
        this.errorMessage = errorMessage;

        if (StringUtils.isEmpty(errorMessage)) {
            logger.error("message is empty for {}, {}", row, col);
        }
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
