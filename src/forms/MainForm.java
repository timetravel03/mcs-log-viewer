package forms;

import func.GVar;
import func.IOFunctions;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JFrame {
    private JPanel contentPane;
    private JTable table1; // NOTE tiene que ir dentro de un JScroll para que muestre los nombres de las columnas
    private JTabbedPane tabbedPane1;
    private JScrollPane latestScrollPane;
    private JTextField txtServerFolder;
    private JButton openButton;
    private JTabbedPane tabbedPane2;
    private JPanel latestPane;
    private JPanel configPane;
    private JPanel rawLatestPane;
    private JTextPane txtLatestRaw;

    private void setFormConfig() {
        txtLatestRaw.setEditable(false);
    }

    public MainForm() {
        setContentPane(contentPane);

        tabbedPane1.setTitleAt(0, "latest.log");
        tabbedPane2.setTitleAt(0, "Raw");
        tabbedPane2.setTitleAt(1, "Table");
        tabbedPane1.setTitleAt(1, "Config");

        // datamodel para el jtable
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Server Time", "Event", "Description"}, 0
        ) {
            @Override
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
            txtLatestRaw.setText(GVar.SERVER_LOG_LATEST_STRING);
        }

        openButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jFileChooser.setDialogTitle("Select server folder");
                int result = jFileChooser.showDialog(openButton.getParent(), "Select");
                if (result == JFileChooser.APPROVE_OPTION) {
                    txtServerFolder.setText(jFileChooser.getSelectedFile().getPath());
                }
            }
        });
    }
}
