package br.com.zenix.lobby.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import br.com.zenix.core.plugin.data.handler.type.DataType;
import br.com.zenix.core.spigot.commands.base.MessagesConstructor;
import br.com.zenix.core.spigot.player.events.ServerTimeEvent;
import br.com.zenix.core.spigot.player.item.ItemBuilder;
import br.com.zenix.lobby.custom.BukkitListener;
import br.com.zenix.lobby.gamer.Gamer;
import br.com.zenix.lobby.gamer.item.CacheItems;
import br.com.zenix.lobby.hologram.constructor.BasicConstructor;

public class GeneralEvents extends BukkitListener {

	public boolean spawned = false;
	int number;

	@EventHandler
	private void onPlayerJoin(PlayerJoinEvent event) {
		event.setJoinMessage(null);

		Player player = event.getPlayer();
		Gamer gamer = getManager().getGamerManager().getGamer(player);

		Location location = (getManager().isSkyWarsLobby() ? new Location(Bukkit.getWorld("world"), 1012, 102, 1007)
				: Bukkit.getWorld("world").getSpawnLocation());

		if (!getManager().isSkyWarsLobby()) {
			location.setYaw(120.0f);
		} else {
			location.setYaw(180.0f);
		}
		
		spawnRandomFirework(new Location(Bukkit.getWorld("world"), -1, 96, -31));
		spawnRandomFirework(new Location(Bukkit.getWorld("world"), 4, 103, -8));
		spawnRandomFirework(new Location(Bukkit.getWorld("world"), 0, 100, -19));

		player.teleport(location);

		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.setHealth(20.0D);
		player.setFoodLevel(20);
		player.setAllowFlight(false);
		player.setGameMode(GameMode.ADVENTURE);
		player.setLevel(gamer.getAccount().getDataHandler().getValue(DataType.GLOBAL_XP).getValue());

		if (gamer.getAccount().getRank() == null) {
			player.kickPlayer("");
		}

		if (!getManager().isSkyWarsLobby()) {
			CacheItems.JOIN.build(player);
		} else {
			CacheItems.JOINSW.build(player);
		}

		if (!getManager().isSkyWarsLobby()) {
			if (gamer.isShow()) {
				new ItemBuilder(Material.INK_SACK).setDurability(8).setName("§fJogadores [ON]")
						.setDescription("§eStatus> §aHabilitado").build(player, 7);
			} else {
				new ItemBuilder(Material.INK_SACK).setDurability(10).setName("§fJogadores [OFF]")
						.setDescription("§eStatus> §cDesabilitado").build(player, 7);
			}
		}

		if (player.hasPermission("commons.cmd.moderate")) {
			player.chat("/admin");
		}

		MessagesConstructor.sendTitleMessage(player, "§6Zenix Network", "§fSeja bem-vindo ao servidor.");

		new BasicConstructor(gamer).start();
	}

	public void spawnRandomFirework(Location location) {
		Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
		FireworkMeta fwm = firework.getFireworkMeta();

		int rt = getManager().getRandom().nextInt(4) + 1;

		FireworkEffect.Type type = FireworkEffect.Type.BALL;
		if (rt == 1) {
			type = FireworkEffect.Type.BALL;
		} else if (rt == 2) {
			type = FireworkEffect.Type.BALL_LARGE;
		} else if (rt == 3) {
			type = FireworkEffect.Type.BURST;
		} else if (rt == 4) {
			type = FireworkEffect.Type.STAR;
		}

		FireworkEffect effect = FireworkEffect.builder().flicker(getManager().getRandom().nextBoolean())
				.withColor(Color.WHITE).withColor(Color.ORANGE).withFade(Color.FUCHSIA).with(type)
				.trail(getManager().getRandom().nextBoolean()).build();
		fwm.addEffect(effect);
		fwm.setPower(getManager().getRandom().nextInt(2) + 1);

		firework.setFireworkMeta(fwm);
	}

	@EventHandler
	public void onServerAction(ServerTimeEvent event) {
		for (Player players : Bukkit.getOnlinePlayers()) {
			if (getManager().getAdminManager().isAdmin(players)) {
				MessagesConstructor.sendActionBarMessage(players, "§fVocê está no modo §cVANISH");
			}
		}

		if (getManager().isSkyWarsLobby()) {
			Bukkit.getWorld("world").setTime(12500L);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		event.setQuitMessage(null);
		getManager().getAdminManager().setPlayerQuit(event.getPlayer());
	}

	@EventHandler
	public void onPickup(PlayerPickupItemEvent event) {
		event.setCancelled(true);
	}

}
