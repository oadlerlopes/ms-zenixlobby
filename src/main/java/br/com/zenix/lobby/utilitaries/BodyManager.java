package br.com.zenix.lobby.utilitaries;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.zenix.core.spigot.player.hologram.Hologram;
import br.com.zenix.lobby.manager.Manager;
import br.com.zenix.lobby.manager.constructor.Management;

public class BodyManager extends Management {

	public BodyManager(Manager manager) {
		super(manager);
	}

	public boolean initialize() {
		if (!getManager().isSkyWarsLobby())
			return true;
		
		Hologram hologram = new Hologram("§eSkyWars", new Location(Bukkit.getWorld("world"), 0, 150, -7).add(0, 1.2, 0), true);
		Hologram line = hologram.addLine("§bJogadores: 0");
		
		new BukkitRunnable() {
			public void run() {

				line.update("§bJogadores: " + getManager().getServerManager().getServerPlayersPath("pvp"));

			}
		}.runTaskTimer(getManager().getPlugin(), 20L, 20L);

		// World world = Bukkit.getWorlds().get(0);
		// WorldServer craftWorld = ((CraftWorld) world).getHandle();
		//
		// CustomBody customBody = new CustomBody(craftWorld, "A" +
		// getManager().getRandom().nextInt(1000), new Location(world, 0, 150,
		// -7));
		// customBody.teleportTo(new Location(world, 0, 150, -7), false);
		//
		// for (Player players : world.getPlayers()) {
		// PlayerConnection connection = ((CraftPlayer)
		// players).getHandle().playerConnection;
		//
		// connection.sendPacket(customBody.getAddInfoPacket());
		// connection.sendPacket(customBody.getPacketPlayOutNamedEntitySpawn());
		// }
		//
		// new BukkitRunnable() {
		// public void run() {
		// for (Player players : world.getPlayers()) {
		// PlayerConnection connection = ((CraftPlayer)
		// players).getHandle().playerConnection;
		//
		// connection.sendPacket(customBody.getRemoveInfoPacket());
		// }
		// }
		// }.runTaskLater(getManager().getPlugin(), 10L);

		return true;
	}

}
