package kwak0.queuestatus.mixin.client;

import kwak0.queuestatus.Config;
import kwak0.queuestatus.Context;
import kwak0.queuestatus.Post;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import java.io.IOException;
import java.net.*;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin(ClientPlayNetworkHandler.class)
abstract class ClientPlayNetworkHandlerMixin {
	@Inject(method = "onSubtitle", at = @At("HEAD"))
	private void onSubtitle(SubtitleS2CPacket packet, CallbackInfo ci) throws IOException, URISyntaxException {
		if ("connect.2b2t.org.".equals(Context.address)) {
			Post.message = packet.text().getString();
			Pattern pattern = Pattern.compile("\\d+");
			Matcher matcher = pattern.matcher(Post.message);
			if (matcher.find()) {
				String positionString = matcher.group();
				if (Integer.parseInt(positionString) != Context.position) {
					Context.position = Integer.parseInt(positionString);
					Post.PostRequest();
				}
			}
		}
	}
	@Inject(method = "onGameJoin", at = @At("RETURN"))
	private void onGameJoin(GameJoinS2CPacket packet, CallbackInfo ci) {
		Context.config = Config.loadConfig();
		if (Context.client.getNetworkHandler() != null && !Context.client.isInSingleplayer()) {
			InetSocketAddress inetSocketAddress = (InetSocketAddress) Context.client.getNetworkHandler().getConnection().getAddress();
			Context.address = inetSocketAddress.getHostName();
			Context.connected = true;
		}
	}
}
@Mixin(ClientConnection.class)
abstract class ClientConnectionMixin {
	@Inject(method = "disconnect", at = @At("HEAD"))
	public void onDisconnect(Text disconnectReason, CallbackInfo ci) throws IOException, URISyntaxException {
		if (Context.address != null) {
			Context.connected = false;
			Context.address = null;
			Post.PostRequest();
		}
	}
}

@Mixin(MinecraftClient.class)
abstract class KeyPressMixin {
	@Inject(method = "handleInputEvents", at = @At("HEAD"))
	private void onHandleInputEvents(CallbackInfo info) throws IOException, URISyntaxException {
		if (Context.config.debug && InputUtil.isKeyPressed(Context.client.getWindow().getHandle(), GLFW.GLFW_KEY_G)) {
			System.out.println("G key was pressed!");
			System.out.println(Post.PostRequest());
		}
	}
}
