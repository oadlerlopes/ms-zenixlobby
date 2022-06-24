package br.com.zenix.lobby.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

import br.com.zenix.core.plugin.data.handler.type.DataType;
import br.com.zenix.core.spigot.player.account.Account;
import br.com.zenix.core.spigot.player.scoreboard.ScoreboardConstructor;
import br.com.zenix.core.spigot.player.scoreboard.ScoreboardScroller;
import br.com.zenix.lobby.gamer.Gamer;
import br.com.zenix.lobby.manager.Manager;
import br.com.zenix.lobby.manager.constructor.Management;

/**
 * Copyright (C) Zenix, all rights reserved unauthorized copying of this file,
 * via any medium is strictly prohibited proprietary and confidential
 */

public class ScoreboardManager extends Management {

	private String title = "§e§lZENIX";
	private ScoreboardScroller scoreboardScroller;

	public ScoreboardManager(Manager manager) {
		super(manager, "Scoreboard");
	}

	public boolean initialize() {
		scoreboardScroller = new ScoreboardScroller("     ZENIX     ", "§f§l", "§6§l", "§6§l", 8);

		return startScores();
	}

	public boolean startScores() {

		new BukkitRunnable() {
			public void run() {
				if (Bukkit.getOnlinePlayers().size() == 0)
					return;

				title = "§f§l" + scoreboardScroller.next();

				for (Player player : Bukkit.getOnlinePlayers()) {
					updateScoreboard(player);

					Gamer gamer = getManager().getGamerManager().getGamer(player);
					getManager().getGamerManager().updateTab(gamer);
				}
			}
		}.runTaskTimer(getManager().getPlugin(), 2, 2);
		return true;
	}

	public void createScoreboard(Player player) {
		ScoreboardConstructor scoreboardHandler = new ScoreboardConstructor(player);
		scoreboardHandler.initialize(title);

		scoreboardHandler.setScore("§b§c", "§2§l", "§f");

		if (!getManager().isSkyWarsLobby()) {
			scoreboardHandler.setScore("§fRank ", "§f", "§f");
			scoreboardHandler.setScore("§fClã atual ", "§f", "§f");
			scoreboardHandler.setScore("§3§c", "§5§l", "§f");
			scoreboardHandler.setScore("§fJogadores ", "§f", "0");
			scoreboardHandler.setScore("§7§c", "§5§l", "§f");
			scoreboardHandler.setScore("§fLiga ", "§f", "§f");
			scoreboardHandler.setScore("§fXP ", "§f", "§f");
			scoreboardHandler.setScore("§fColocação ", "§f", "§f");
		} else {
			scoreboardHandler.setScore("§fSolo Kills: ", "§f", "§f");
			scoreboardHandler.setScore("§fSolo Wins: ", "§f", "§e0");
			scoreboardHandler.setScore("§3§c", "§5§l", "§f");
			scoreboardHandler.setScore("§fELO: ", "§f", "0");
			scoreboardHandler.setScore("§fCoins: ", "§f", "§e0");
			scoreboardHandler.setScore("§7§c", "§5§l", "§f");
		}

		if (!getManager().isSkyWarsLobby()) {
		} else {
			scoreboardHandler.setScore("§fOnline: ", "§f", "0");
		}
		scoreboardHandler.setScore("§1§2", "§3§4", "§f");
		scoreboardHandler.setScore("www.zenix", "§e", "§e.cc");

		getManager().getCoreManager().getAccountManager().getAccount(player).setScoreboardHandler(scoreboardHandler);
	}

	public void updateScoreboard(Player player) {
		Account account = getManager().getCoreManager().getAccountManager().getAccount(player);
		Gamer gamer = getManager().getGamerManager().getGamer(player);
		if (gamer == null)
			return;

		if (account.isScoreboard()) {
			account.setScoreboardHandler(null);
			player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
			return;
		}

		if (account.getScoreboardHandler() == null) {
			createScoreboard(player);
		}

		ScoreboardConstructor scoreboardHandler = account.getScoreboardHandler();

		scoreboardHandler.setDisplayName(title);

		if (account.getRank().getName() == null || account.getLeague().getName() == null || account.getRank() == null) {
			player.kickPlayer("§cAlgo ocorreu e sua conta não foi carregada!");
			return;
		}

		scoreboardHandler.updateScore("§fJogadores ", "§f", "§7" + getManager().getServerManager().getTotalPlayers());
		scoreboardHandler.updateScore("§fXP ", "§f",
				"§a" + account.getDataHandler().getValue(DataType.GLOBAL_XP).getValue());
		scoreboardHandler.updateScore("§fLobby ", "§f",
				"§7#" + getManager().getCoreManager().getServerName().replace("LOBBY-", ""));
		scoreboardHandler.updateScore("§fLiga ", "§f", "" + account.getLeague().getColor()
				+ account.getLeague().getSymbol() + " " + account.getLeague().getName().toUpperCase());

		scoreboardHandler.updateScore("§fSolo Wins: ", "§f",
				"§a" + account.getDataHandler().getValue(DataType.SKYWARS_WINS).getValue());
		scoreboardHandler.updateScore("§fSolo Kills ", "§f",
				"§a" + account.getDataHandler().getValue(DataType.SKYWARS_KILLS).getValue());

		scoreboardHandler.updateScore("§fELO ", "§f",
				"§a" + account.getDataHandler().getValue(DataType.SKYWARS_ELO).getValue());
		scoreboardHandler.updateScore("§fRank ", "§f",
				"" + gamer.getAccount().getRank().getTag().getColor().replace("§l", "")
						+ getManager().getUtils().captalize(gamer.getAccount().getRank().getName()));
		scoreboardHandler.updateScore("§fClã atual ", "§f",
				"§e" + (account.isHaveClan() ? (getManager().getCoreManager().getClanAccountManager().getClanAccount(player).getClan().getName() == null
										? "NRE"
										: getManager().getCoreManager().getClanAccountManager().getClanAccount(player)
												.getClan().getTag())
						: "NRE"));
		
		scoreboardHandler.updateScore("§fColocação ", "§f",
				"§6" + (account.getDataHandler().getValue(DataType.GLOBAL_XP).getValue() >= 500 ? getManager().getGamerPositionManager().getPosition(player) + "º"  : "???"));

		scoreboardHandler.updateScore("§fCoins ", "§f",
				"§b" + account.getDataHandler().getValue(DataType.GLOBAL_COINS).getValue());
	}

}
