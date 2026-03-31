package classes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import classes.AppConfig.CONFIG_KEYS;

public class ServerRouter {
    private String serverRoute;
    private boolean validRoute;

    public ServerRouter() {
        this.serverRoute = AppConfig.getInstance().get(CONFIG_KEYS.SERVER_PATH, "");
        this.validRoute = isServerPathValid();
    }

    public String getLatestPath() {
        if (validRoute && Files.exists(Path.of(serverRoute + "/logs/latest.log"))) {
            return serverRoute + "/logs/latest.log";
        } else {
            return null;
        }
    }

    public String getLatestString() {
        try {
            if (validRoute)
                return Files.readString(Path.of(getLatestPath()));
            else
                return "";
        } catch (IOException e) {
            return null;
        }
    }

    private boolean isServerPathValid() {
        return serverRoute != null
                && !serverRoute.isEmpty()
                && Files.exists(Path.of(serverRoute + "/logs/")) // carpeta logs existe
                && Files.exists(Path.of(serverRoute + "/server.properties"))
                && Files.exists(Path.of(serverRoute + "/whitelist.json"))
                && Files.exists(Path.of(serverRoute + "/server.jar")); // a partir de aqui no es necesario
    }

}
