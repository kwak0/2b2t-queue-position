package kwak0.queuestatus;

import com.google.gson.Gson;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
    String flaskAddress;

    private Config() {
        this.flaskAddress =  "http://127.0.0.1:5000/";
    }

    public static Config loadConfig() {
        Config config;
        Path path = getConfigFilePath();
        Gson gson = new Gson();

        if (Files.exists(path)) {
            try (FileReader reader = new FileReader(path.toFile())) {
                config = gson.fromJson(reader, Config.class);
            } catch (IOException e) {
                throw new RuntimeException("Could not parse config", e);
            }
        } else {
            config = new Config();
        }
        try {
            Files.writeString(path, gson.toJson(config));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't update config file", e);
        }

        return config;
    }

    private static Path getConfigFilePath() {
        return FabricLoader.getInstance().getConfigDir().resolve("2b2t_position_mod_config.json");
    }

    public String getFlaskAddress() {
        return flaskAddress;
    }

}
