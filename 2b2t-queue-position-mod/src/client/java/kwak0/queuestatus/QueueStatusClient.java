package kwak0.queuestatus;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;

public class QueueStatusClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		Post.client = MinecraftClient.getInstance();
		Post.session = Post.client.getSession();

		Post.connected = false;
		Post.username = Post.session.getUsername();
	}

}