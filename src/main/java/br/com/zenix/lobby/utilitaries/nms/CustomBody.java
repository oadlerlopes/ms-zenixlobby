package br.com.zenix.lobby.utilitaries.nms;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import br.com.zenix.core.plugin.utilitaries.Utils;
import net.minecraft.server.v1_7_R4.DamageSource;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutAnimation;
import net.minecraft.server.v1_7_R4.PacketPlayOutBed;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_7_R4.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_7_R4.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_7_R4.PlayerInteractManager;
import net.minecraft.server.v1_7_R4.WorldServer;
import net.minecraft.util.com.mojang.authlib.GameProfile;

@SuppressWarnings("static-access")
public class CustomBody extends EntityPlayer {

	public CustomBody(WorldServer world, String name, Location location) {
		super(((CraftServer) Bukkit.getServer()).getServer(), world, new GameProfile(UUID.randomUUID(), name), new PlayerInteractManager(world));

		move(location.getX(), location.getY(), location.getZ());
	}

	public boolean isSneaking() {
		return true;
	}

	public boolean isDeeplySleeping() {
		return true;
	}

	public boolean isSleeping() {
		return true;
	}

	public boolean isAlive() {
		return false;
	}

	public PacketPlayOutAnimation getPacketPlayOutAnimation() {
		return new PacketPlayOutAnimation(this, 0);
	}

	public PacketPlayOutBed getPacketPlayOutBed() {
		PacketPlayOutBed packet = new PacketPlayOutBed();
		Utils.setValue("a", packet, getBukkitEntity().getEntityId());
		Utils.setValue("b", packet, new Location(getBukkitEntity().getWorld(), locX, 1, locZ));
		return packet;
	}

	public PacketPlayOutPlayerInfo getRemoveInfoPacket() {
		return new PacketPlayOutPlayerInfo().removePlayer(this);
	}

	public PacketPlayOutNamedEntitySpawn getPacketPlayOutNamedEntitySpawn() {
		return new PacketPlayOutNamedEntitySpawn(this);
	}

	public PacketPlayOutPlayerInfo getAddInfoPacket() {
		return new PacketPlayOutPlayerInfo().addPlayer(this);
	}

	public boolean damageEntity(DamageSource damagesource, float f) {
		return false;
	}

	public void collide(Entity arg0) {

	}

	public void lookAt(Location location) {
		float yam = getLocalAngle(new Vector(locX, 0, locZ), location.toVector());
		this.yaw = yam;
		this.aP = 0F;
	}

	private final float getLocalAngle(Vector p1, Vector p2) {
		double dx = p2.getX() - p1.getX(), dz = p2.getZ() - p1.getZ();
		float angle = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90;

		if (angle < 0)
			angle += 360.0F;

		return angle;
	}

	private final void broadcastLocalPacket(Packet packet) {
		for (Player player : getBukkitEntity().getWorld().getPlayers()) {
			if (getBukkitEntity().getLocation().distanceSquared(player.getLocation()) <= Bukkit.getViewDistance() * 16 * Bukkit.getViewDistance() * 16) {
				((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
			}
		}
	}

	public void setEquipment(int slot, ItemStack item) {
		broadcastLocalPacket(new PacketPlayOutEntityEquipment(getId(), slot, CraftItemStack.asNMSCopy(item)));
	}

	@Override
	public void h() {
		super.h();
		this.C();

		if (world.getType(MathHelper.floor(locX), MathHelper.floor(locY), MathHelper.floor(locZ)).getMaterial() == net.minecraft.server.v1_7_R4.Material.FIRE)
			setOnFire(15);

		this.motY = onGround ? Math.max(0.0, motY) : motY;

		move(motX, motY, motZ);

		this.motX *= 0.800000011920929;
		this.motY *= 0.800000011920929;
		this.motZ *= 0.800000011920929;

		if (!this.onGround)
			this.motY -= 0.1;
	}

}