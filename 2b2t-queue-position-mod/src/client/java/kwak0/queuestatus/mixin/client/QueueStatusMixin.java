package kwak0.queuestatus.mixin.client;

import io.netty.channel.local.LocalAddress;
import kwak0.queuestatus.Post;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.text.Text;
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
		if ("connect.2b2t.org.".equals(Post.address) && Post.client.world.getPlayers().size() == 1) {
			Post.message = packet.text().getString();
			Pattern pattern = Pattern.compile("\\d+");
			Matcher matcher = pattern.matcher(Post.message);
			if (matcher.find()) {
				String positionString = matcher.group();
				Post.position = Integer.parseInt(positionString);
				Post.PostRequest();
			}
		}
	}
	@Inject(method = "onGameJoin", at = @At("RETURN"))
	private void onGameJoin(GameJoinS2CPacket packet, CallbackInfo ci) {
		if (Post.client.getNetworkHandler().getConnection().getAddress().getClass() != LocalAddress.class) {
			InetSocketAddress inetSocketAddress = (InetSocketAddress) Post.client.getNetworkHandler().getConnection().getAddress();
			Post.address = inetSocketAddress.getHostName();
			Post.connected = true;
		}
	}
}
@Mixin(ClientConnection.class)
abstract class ClientConnectionMixin {
	@Inject(method = "disconnect", at = @At("HEAD"))
	public void onDisconnect(Text disconnectReason, CallbackInfo ci) throws IOException, URISyntaxException {
		if (Post.address != null) {
			Post.connected = false;
			Post.address = null;
			Post.PostRequest();
		}
	}
}