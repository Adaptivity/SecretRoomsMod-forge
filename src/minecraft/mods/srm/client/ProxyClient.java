package mods.SecretRoomsMod.client;

import mods.SecretRoomsMod.SecretRooms;
import mods.SecretRoomsMod.common.ProxyCommon;
import mods.SecretRoomsMod.network.PacketSRM2Key;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(value = Side.CLIENT)
public class ProxyClient extends ProxyCommon
{
	public static KeyBinding	key_OneWayFace;
	private boolean				oneWayFaceAway	= true;

	public ProxyClient()
	{
		key_OneWayFace = new KeyBinding("Change OneWayBlock face", Keyboard.KEY_BACKSLASH);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void loadRenderStuff()
	{
		SecretRooms.camoRenderId = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new CamoRenderer());

		SecretRooms.torchRenderId = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new TorchRenderer());
	}

	@Override
	public void loadKeyStuff()
	{
		KeyBindingRegistry.registerKeyBinding(new SecretKey(key_OneWayFace));
	}

	@Override
	public void onServerStop(FMLServerStoppingEvent e)
	{
		super.onServerStop(e);
		oneWayFaceAway = true;
	}

	@Override
	public void onKeyPress(String username)
	{
		oneWayFaceAway = !oneWayFaceAway;
		PacketDispatcher.sendPacketToServer(new PacketSRM2Key().getPacket250());
	}

	@Override
	public boolean getFaceAway(String username)
	{
		return oneWayFaceAway;
	}

}