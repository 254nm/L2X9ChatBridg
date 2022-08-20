package me.l2x9.chatbridge.paper.listeners;

import lombok.RequiredArgsConstructor;
import me.l2x9.chatbridge.L2X9ChatBridge;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Arrays;

@RequiredArgsConstructor
public class JoinLeaveListener implements Listener {
    private final L2X9ChatBridge plugin;

    @EventHandler(priority = EventPriority.LOWEST) //Allow other plugins to change the message
    public void onJoin(PlayerJoinEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        if (!event.getPlayer().hasPlayedBefore()) {
            embedBuilder.setTitle(String.format(":tada: %s Joined for the first time", event.getPlayer().getName()));
        } else embedBuilder.setTitle(String.format("%s joined", event.getPlayer().getName()));
        embedBuilder.setColor(0x6eff3b);
        plugin.getBot().getBridgeChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }

    @EventHandler(priority = EventPriority.LOWEST) //Allow other plugins to change the message
    public void onLeave(PlayerQuitEvent event) {
        if (Arrays.stream(Thread.currentThread().getStackTrace()).anyMatch(e -> e.toString().contains("PlayerConnection.disconnect")))
            return;
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(String.format("%s left", event.getPlayer().getName()));
        embedBuilder.setColor(0xaa0000);
        plugin.getBot().getBridgeChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }

    @EventHandler(priority = EventPriority.LOWEST) //Allow other plugins to change the message
    public void onKick(PlayerKickEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(String.format("%s was kicked for \"%s\"", event.getPlayer().getName(), ChatColor.stripColor(event.getReason())));
        embedBuilder.setColor(0xaa0000);
        plugin.getBot().getBridgeChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }
}