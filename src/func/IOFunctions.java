package func;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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
            Object[] record = new Object[3];

            for (String line : lines) {
                Matcher recordStartMatcher = recordStartPtt.matcher(line);
                if (recordStartMatcher.find()) {
                    if (!firstRecord){
                        record[EVENT_DESCRIPTION] = eventDescription;
                        GVar.SERVER_LOG_LATEST_JTABLE_ARRAY.add(record);
                    }

                    record = new Object[3];
                    firstRecord = false;

                    // hora
                    int openTime = line.indexOf('[');
                    int closeTime = line.indexOf(']');
                    record[SERVER_TIME] = line.substring(openTime, closeTime);

                    // evento
                    int openEvent = line.indexOf('[', openTime+1);
                    int closeEvent = line.indexOf(']', closeTime+1);
                    record[EVENT] = line.substring(openEvent, closeEvent);

                    // limpiar mensaje
                    int startMessage = line.indexOf(':', closeEvent);
                    eventDescription = new StringBuilder(line.substring(startMessage));
                } else {
                    eventDescription.append(line);
                }
            }
            return true;
        } catch (IOException ioe) {
            return false;
        }
    }
}
