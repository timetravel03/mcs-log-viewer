package forms;

import func.GVar;
import func.IOFunctions;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class MainForm extends JFrame {
    private JPanel contentPane;
    private JTable table1; // NOTE tiene que ir dentro de un JScroll para que muestre los nombres de las columnas

    public MainForm() {
        // cosas de mierda q a nadie le importa
        setTitle("mcs-log-viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPane);

        // datamodel para el jtable
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Server Time", "Event", "Description"}, 0
        ) {@Override
            public Class<?> getColumnClass(int columnIndex) {
                return switch (columnIndex) {
                    case 0 -> String.class;
                    case 1 -> String.class;
                    case 2 -> String.class;
                    default -> String.class;
                };
            }
        };

        table1.setModel(model);

        //test
        if (IOFunctions.loadServerLog("X:\\Network Storage\\latest.log")) {
            Object[][] vector = GVar.SERVER_LOG_LATEST_JTABLE_ARRAY.toArray(new Object[3][GVar.SERVER_LOG_LATEST_JTABLE_ARRAY.size()]);
            model.setDataVector(vector, new Object[]{"Server Time", "Event", "Description"});
        }

        // duh
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
