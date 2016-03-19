package co.uk.silvania.rpgcore;

import co.uk.silvania.rpgcore.client.SkillSelectGui;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	public GuiHandler() {}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (id) {
			case 0: {
				return new SkillsContainer(player);
			}
		}
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (id) {
			case 0: {
				return new SkillSelectGui(new SkillsContainer(player));
			}
		}
		return null;
	}
}
