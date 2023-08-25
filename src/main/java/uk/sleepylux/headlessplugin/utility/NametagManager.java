package uk.sleepylux.headlessplugin.utility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import uk.sleepylux.headlessplugin.HeadlessPlugin;

public class NametagManager {

    public enum TeamAction {
        CREATE, DESTROY, UPDATE
    }

    public static void updateVisibleLifeCount(HeadlessPlugin plugin, Player player, TeamAction action) {
        if (player == null || action == null)
            return;

        Scoreboard scoreboard = player.getScoreboard();

        Team team = scoreboard.getTeam(player.getName());

        if (team == null)
            team = scoreboard.registerNewTeam(player.getName());

        String lifeCount = (PlayersManager.getPlayerJSON(plugin, player.getUniqueId())).get("lives").toString();

        team.prefix(Component.text("[").color(TextColor.fromHexString("#FF55FF"))
                .append(Component.text(lifeCount).color(TextColor.fromHexString("#FFAA00")))
                .append(Component.text("] ").color(TextColor.fromHexString("#FF55FF")))
        );
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);

        switch (action) {
            case CREATE -> team.addPlayer(player);
            case DESTROY -> team.unregister();
            case UPDATE -> {
                team.unregister();
                team = scoreboard.registerNewTeam(player.getName());
                team.prefix(Component.text("[").color(TextColor.fromHexString("#FF55FF"))
                                .append(Component.text(lifeCount).color(TextColor.fromHexString("#FFAA00")))
                                .append(Component.text("] ").color(TextColor.fromHexString("#FF55FF")))
                );
                team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
                team.addPlayer(player);
            }
        }
    }
}
