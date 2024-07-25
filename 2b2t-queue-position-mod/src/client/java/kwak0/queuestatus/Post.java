package kwak0.queuestatus;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.Session;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;


public class Post {
    public static String flaskAddress;

    public static boolean connected;
    public static int position;
    public static String username;

    public static MinecraftClient client;
    public static Session session;
    public static String address;

    public static String message;

    public static String PostRequest() throws IOException, URISyntaxException {
        flaskAddress = Config.loadConfig().flaskAddress;
        String data = "{\"username\": \"%s\", \"position\": \"%s\", \"connected\": \"%s\"}".formatted(Post.username, Post.position, Post.connected);
        try {
            URL url = new URI(flaskAddress).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            try (DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {
                dos.writeBytes(data);
                try (BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    StringBuilder response = null;
                    while ((line = bf.readLine()) != null) {
                        response = new StringBuilder().append(line);
                    }
                    return response == null ? null : response.toString();
                }
            }
            catch (ConnectException e) {
                System.out.println("2b2t queue server not online");
                return null;
            }
            catch (IOException e) {
                System.out.println("Response: " + connection.getResponseMessage());
                return null;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Flask address: " + flaskAddress);
            return null;
        }
        catch (UnknownHostException e) {
            System.out.println("Flask address does not exist: " + flaskAddress);
            return null;
        }
    }
}