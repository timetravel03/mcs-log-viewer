package forms;

import classes.AppConfig;
import classes.ServerRouter;
import func.GVar;
import func.IOFunctions;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

import org.apache.commons.lang.StringEscapeUtils;

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
    private JTextField txtSMBPath;
    private JButton connectButton;
    private JTextField txtSMBUser;
    private JTextField txtSMBPwd;
    private JButton btnSaveConfig;

    private AppConfig config;
    private ServerRouter router;

    private void setFormConfig() {
        txtLatestRaw.setEditable(false);
    }

    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Server Time", "Event", "Description"}, 0
    ) {
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }
    };

    private void initialize(){
        config = AppConfig.getInstance();
        GVar.SERVER_FOLDER = config.get(AppConfig.CONFIG_KEYS.SERVER_PATH, "");
        router = new ServerRouter();
    }

    private void loadWindowContent() {
        initialize();

        // tab zone
        tabbedPane1.setTitleAt(0, "latest.log");
        tabbedPane2.setTitleAt(0, "Raw");
        tabbedPane2.setTitleAt(1, "Table");
        tabbedPane1.setTitleAt(1, "Config");

        // JTable zone
        table1.setModel(model);
        if (!GVar.SERVER_FOLDER.isEmpty()) {
            txtServerFolder.setText(GVar.SERVER_FOLDER);
            if (IOFunctions.loadServerLog(GVar.SERVER_FOLDER)){
                Object[][] vector = GVar.SERVER_LOG_LATEST_JTABLE_ARRAY.toArray(new Object[3][GVar.SERVER_LOG_LATEST_JTABLE_ARRAY.size()]);
                model.setDataVector(vector, new Object[]{"Server Time", "Event", "Description"});
                txtLatestRaw.setText(GVar.SERVER_LOG_LATEST_STRING);
            }
        }
    }

    public MainForm() {
        setContentPane(contentPane);

        loadWindowContent();

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
                    GVar.SERVER_FOLDER = StringEscapeUtils.escapeSql(txtServerFolder.getText().trim());
                    AppConfig.getInstance().set(AppConfig.CONFIG_KEYS.SERVER_PATH, GVar.SERVER_FOLDER);
                    IOFunctions.loadServerLog(IOFunctions.getLogsPath());
                }
            }
        });
        txtServerFolder.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (JOptionPane.showConfirmDialog(rootPane,
                        "Confirm exit",
                        "Good-bye",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    IOFunctions.exitTasks();
                    System.exit(0);
                }
            }
        });
        btnSaveConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtServerFolder.getText().isEmpty()) {
                    AppConfig.getInstance().set(AppConfig.CONFIG_KEYS.SERVER_PATH, txtServerFolder.getText());
                } else {
                    AppConfig.getInstance().set(AppConfig.CONFIG_KEYS.SERVER_PATH, txtSMBPath.getText());
                }
            }
        });
    }
}
