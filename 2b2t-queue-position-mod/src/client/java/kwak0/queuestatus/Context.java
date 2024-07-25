package kwak0.queuestatus;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.Session;

public class Context {
    public static MinecraftClient client;
    public static Session session;
    public static String address;
    public static boolean connected;
    public static String username;

    public static Config config;

    public static int position;
}
