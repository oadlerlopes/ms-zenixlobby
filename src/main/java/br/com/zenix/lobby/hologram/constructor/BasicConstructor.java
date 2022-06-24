package br.com.zenix.lobby.hologram.constructor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import br.com.zenix.core.plugin.data.handler.type.DataType;
import br.com.zenix.core.spigot.player.bossbar.BarAPI;
import br.com.zenix.core.spigot.player.hologram.Hologram;
import br.com.zenix.lobby.Lobby;
import br.com.zenix.lobby.gamer.Gamer;
import br.com.zenix.lobby.manager.Manager;

public class BasicConstructor {

	private Gamer gamer;

	private Hologram status;

	public BasicConstructor(Gamer gamer) {
		this.gamer = gamer;
	}

	public void start() {

		Manager manager = Lobby.getManager();
		boolean isSky = manager.isSkyWarsLobby();
		
		manager.getVanish().updateVanished();

		for (Player players : Bukkit.getOnlinePlayers()) {
			Gamer gamers = manager.getGamerManager().getGamer(players);
			if (gamers.isShow()) {
				players.hidePlayer(gamer.getAccount().getPlayer());
			}
		}

		if (isSky) {
			status = new Hologram("§6§lSEUS STATUS", new Location(Bukkit.getWorld("world"), 1008, 103.5, 989), false);
			status.addLine("");
			status.addLine("§fSeu ELO: §7" + gamer.getAccount().getDataHandler().getValue(DataType.SKYWARS_ELO).getValue());
			status.addLine("§fPartidas ganhas: §7" + gamer.getAccount().getDataHandler().getValue(DataType.SKYWARS_WINS).getValue());
			status.addLine("");
			status.addLine("§fPartidas perdidas: §7" + gamer.getAccount().getDataHandler().getValue(DataType.SKYWARS_DEATHS).getValue());
			status.addLine("§fJogadores abatidos: §7" + gamer.getAccount().getDataHandler().getValue(DataType.SKYWARS_KILLS).getValue());
			status.addLine("");

			status.show(gamer.getAccount().getPlayer());
		} else {
			status = new Hologram("  §6§lSEUS STATUS  ", new Location(Bukkit.getWorld("world"), 20.5, 64, -40.4), false);
			status.addLine("");
			status.addLine("§fRank: §7" + gamer.getAccount().getRank().getTag().getColor().replace("§l", "") + manager.getUtils().captalize(gamer.getAccount().getRank().getName()));
			status.addLine("");
			status.addLine("§fXP League: §7" + gamer.getAccount().getDataHandler().getValue(DataType.GLOBAL_XP).getValue());
			status.addLine("§fLiga: §7" + gamer.getAccount().getLeague().getColor() + gamer.getAccount().getLeague().getSymbol() + " " + gamer.getAccount().getLeague().getName());
			status.addLine("");
			status.addLine("§fKills 1v1: §7" + gamer.getAccount().getDataHandler().getValue(DataType.PVP_KILL).getValue());
			status.addLine("§fMost KillStreak: §7" + gamer.getAccount().getDataHandler().getValue(DataType.PVP_MOST_KILLSTREAK).getValue());
			status.addLine("");
			status.addLine("§fWins HG: §7" + gamer.getAccount().getDataHandler().getValue(DataType.HG_WINS).getValue());
			status.addLine("§fKills HG: §7" + gamer.getAccount().getDataHandler().getValue(DataType.HG_KILL).getValue());
			status.addLine("§fMost KillStreak: §7" + gamer.getAccount().getDataHandler().getValue(DataType.HG_MOST_KILLSTREAK).getValue());
			status.addLine("");

			status.show(gamer.getAccount().getPlayer());
		}
		
		BarAPI.setMessage(gamer.getAccount().getPlayer(), "§b§LBETA VIP - PRACTICE §c§l>> §e§lLOJA.ZENIX.CC");
	}

}
