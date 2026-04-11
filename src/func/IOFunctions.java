package func;

import classes.AppConfig;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IOFunctions {
    private static final int SERVER_TIME = 0;
    private static final int EVENT = 1;
    private static final int EVENT_DESCRIPTION = 2;


    public static boolean createConfigFile() {
        try {

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean loadServerLog(String logPath) {
        Pattern recordStartPtt = Pattern.compile("\\[[^\\]]+\\]\\s\\[[^\\]]+\\]:");
        String serverTime = "";
        String event = "";
        StringBuilder eventDescription = new StringBuilder();
        boolean firstRecord = true;
        try {
            List<String> lines = Files.readAllLines(Path.of(logPath));
            GVar.SERVER_LOG_LATEST_STRING = lines.toString();
            Object[] record = new Object[3];

            for (String line : lines) {
                Matcher recordStartMatcher = recordStartPtt.matcher(line);
                if (recordStartMatcher.find()) {
                    if (!firstRecord) {
                        record[EVENT_DESCRIPTION] = eventDescription;
                        GVar.SERVER_LOG_LATEST_JTABLE_ARRAY.add(record);
                    }

                    record = new Object[3];
                    firstRecord = false;

                    // hora
                    int openTime = line.indexOf('[') + 1;
                    int closeTime = line.indexOf(']');
                    record[SERVER_TIME] = line.substring(openTime, closeTime);

                    // evento
                    int openEvent = line.indexOf('[', openTime + 1) + 1;
                    int closeEvent = line.indexOf(']', closeTime + 1);
                    record[EVENT] = line.substring(openEvent, closeEvent);

                    // limpiar mensaje
                    int startMessage = line.indexOf(':', closeEvent) + 1;
                    eventDescription = new StringBuilder(line.substring(startMessage));
                } else {
                    eventDescription.append(line);
                }
            }
            return true;
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            return false;
        }
    }

    public static String getLogsPath() {
        if (GVar.SERVER_FOLDER != null && !GVar.SERVER_FOLDER.isEmpty()) {
            return GVar.SERVER_FOLDER + "/logs/";
        } else {
            return "";
        }
    }

    public static void exitTasks() {
        try {
            AppConfig.getInstance().save();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error on config save");
        }
    }
}
