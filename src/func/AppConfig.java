package func;

import java.io.*;
import java.nio.file.*;
import java.util.Properties;

public class AppConfig {
    public enum CONFIG_KEYS{
        SERVER_PATH
    }

    private static AppConfig instance;
    private static final String APP_NAME = "mcs-log-viewer";
    private final Path configDir;
    private final Path configFile;
    private final Properties props = new Properties();

    public AppConfig() {
        this.configDir = resolveConfigDir();
        this.configFile = configDir.resolve("config.properties");
        load();
    }

    private Path resolveConfigDir() {
        String os = System.getProperty("os.name").toLowerCase();
        String home = System.getProperty("user.home");

        if (os.contains("win")) {
            String appData = System.getenv("APPDATA");
            return Paths.get(appData != null ? appData : home, APP_NAME);
        } else if (os.contains("mac")) {
            return Paths.get(home, "Library", "Application Support", APP_NAME);
        } else {
            // Linux / Unix
            String xdgConfig = System.getenv("XDG_CONFIG_HOME");
            return Paths.get(xdgConfig != null ? xdgConfig : home + "/.config", APP_NAME.toLowerCase());
        }
    }

    public void load() {
        if (Files.exists(configFile)) {
            try (InputStream in = Files.newInputStream(configFile)) {
                props.load(in);
            } catch (IOException e) {
                // fall back to defaults
            }
        }
    }

    public void save() throws IOException {
        Files.createDirectories(configDir); // creates the folder if it doesn't exist
        try (OutputStream out = Files.newOutputStream(configFile)) {
            props.store(out, APP_NAME + " configuration");
        }
    }

    public String get(CONFIG_KEYS key, String defaultValue) {
        return props.getProperty(key.name(), defaultValue);
    }

    public void set(CONFIG_KEYS key, String value) {
        props.setProperty(key.name(), value);
    }

    public static AppConfig getInstance(){
        if (instance == null){
            instance = new AppConfig();
        }
        return instance;
    }
}