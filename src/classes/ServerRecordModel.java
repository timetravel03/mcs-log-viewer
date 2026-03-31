package classes;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;

public class ServerRecordModel implements TableModel {
    private final ArrayList<String[]> registers = new ArrayList<String[]>();
    private final int ARR_SIZE = 3;

    @Override
    public int getRowCount() {
        return registers.size();
    }

    @Override
    public int getColumnCount() {
        return ARR_SIZE;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> "Server Time";
            case 1 -> "Event";
            case 2 -> "Description";
            default -> "";
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if ((rowIndex >= 0 && rowIndex < registers.size()) && (columnIndex >= 0 && columnIndex < ARR_SIZE)) {
            return registers.get(rowIndex)[columnIndex];
        } else {
            return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if ((rowIndex >= 0 && rowIndex < registers.size()) && (columnIndex >= 0 && columnIndex < ARR_SIZE)) {
            registers.get(rowIndex)[columnIndex] = aValue.toString();
        }
    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }
}
