package com.t0g3pii.purplechat.twitch;

import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.chat.events.channel.ClearChatEvent;
import com.t0g3pii.purplechat.config.PurpleChatConfig;
import com.t0g3pii.purplechat.ui.MessageFormatter;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public final class TwitchService {
	private static final Logger LOGGER = LoggerFactory.getLogger("PurpleChat:Twitch");

	private TwitchClient client;
	private final Set<String> joined = new HashSet<>();
	private final ArrayDeque<String> recentQueue = new ArrayDeque<>();
	private final Set<String> recentSet = new HashSet<>();

	public void start(PurpleChatConfig cfg) {
		if (client != null) return;
		client = TwitchClientBuilder.builder()
				.withEnableChat(true)
				.withChatAccount(null)
				.build();

		client.getEventManager().onEvent(ChannelMessageEvent.class, event -> {
			if (event.getMessage() == null) return;
			String channel = event.getChannel().getName();
			String user = event.getUser().getName();
			String color = null;
			if (event.getMessageEvent() != null && event.getMessageEvent().getTags() != null) {
				color = event.getMessageEvent().getTags().get("color");
				if (color != null && color.isEmpty()) color = null;
			}
			// De-Dupe via message id or fallback key
			String msgId = event.getMessageEvent() != null && event.getMessageEvent().getTags() != null
					? event.getMessageEvent().getTags().get("id")
					: null;
			String key = msgId != null ? msgId : (channel + "|" + user + "|" + event.getMessage());
			if (isRecent(key)) return;

			net.minecraft.text.Text formatted = MessageFormatter.formatText(cfg, channel, user, event.getMessage(), color, event.getMessageEvent().getTags());
			MinecraftClient mc = MinecraftClient.getInstance();
			mc.execute(() -> {
				if (mc.player != null) {
					mc.inGameHud.getChatHud().addMessage(formatted);
				}
			});
		});

		// Kanalbereinigung / Timeout (CLEARCHAT)
		client.getEventManager().onEvent(ClearChatEvent.class, event -> {
			String channel = event.getChannel().getName();
			MinecraftClient mc = MinecraftClient.getInstance();
			mc.execute(() -> {
				if (mc.player != null) {
					net.minecraft.text.Text notice = net.minecraft.text.Text.literal("[")
							.styled(s -> s.withColor(net.minecraft.text.TextColor.fromRgb(0xAA00AA)))
							.append(net.minecraft.text.Text.literal("Twitch | ")
									.styled(s -> s.withColor(net.minecraft.text.TextColor.fromRgb(0xAA00AA))))
							.append(net.minecraft.text.Text.literal(channel)
									.styled(s -> s.withColor(net.minecraft.text.TextColor.fromRgb(0xAA00AA))))
							.append(net.minecraft.text.Text.literal("] "))
							.append(net.minecraft.text.Text.literal("Chat bereinigt"))
							.styled(s -> s.withItalic(true).withColor(net.minecraft.text.TextColor.fromRgb(0xAAAAAA)));
					mc.player.sendMessage(notice);
				}
			});
		});

		updateChannels(cfg);

		LOGGER.info("Twitch chat gestartet. Channels={}", cfg.channels);
	}

	public void stop() {
		if (client == null) return;
		try {
			client.close();
		} catch (Exception ignored) {
		}
		client = null;
		joined.clear();
		recentQueue.clear();
		recentSet.clear();
	}

	public void updateChannels(PurpleChatConfig cfg) {
		if (client == null) return;
		// Compute desired set
		Set<String> desired = new HashSet<>();
		for (String ch : cfg.channels) {
			if (ch == null || ch.isBlank()) continue;
			String normalized = ch.startsWith("#") ? ch.substring(1) : ch;
			desired.add(normalized.toLowerCase(Locale.ROOT));
		}

		// Leave removed
		for (String was : new HashSet<>(joined)) {
			if (!desired.contains(was)) {
				client.getChat().leaveChannel(was);
				joined.remove(was);
			}
		}

		// Join new
		for (String want : desired) {
			if (!joined.contains(want)) {
				client.getChat().joinChannel(want);
				joined.add(want);
			}
		}
	}

	private boolean isRecent(String key) {
		if (key == null) return false;
		if (recentSet.contains(key)) return true;
		recentQueue.addLast(key);
		recentSet.add(key);
		// cap to last 512 messages
		while (recentQueue.size() > 512) {
			String old = recentQueue.removeFirst();
			recentSet.remove(old);
		}
		return false;
	}
}


