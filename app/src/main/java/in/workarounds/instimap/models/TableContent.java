package in.workarounds.instimap.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by manidesto on 02/02/15.
 */
public class TableContent {
    @SerializedName("columns")
    int columns;

    @SerializedName("rows")
    int rows;

    @SerializedName("column_layout")
    boolean columnLayout;

    @SerializedName("row_layout")
    boolean rowLayout;

    public ArrayList<String>[] getColumnData() {
        return columnData;
    }

    public void setColumnData(ArrayList<String>[] columnData) {
        this.columnData = columnData;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public boolean isColumnLayout() {
        return columnLayout;
    }

    public void setColumnLayout(boolean columnLayout) {
        this.columnLayout = columnLayout;
    }

    public boolean isRowLayout() {
        return rowLayout;
    }

    public void setRowLayout(boolean rowLayout) {
        this.rowLayout = rowLayout;
    }

    @SerializedName("table")

    ArrayList<String> columnData[];
}
