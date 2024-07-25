package kwak0.queuestatus;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;

public class QueueStatusClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		Context.client = MinecraftClient.getInstance();
		Context.session = Context.client.getSession();

		Context.connected = false;
		Context.username = Context.session.getUsername();

		Context.config = Config.loadConfig();
	}

}