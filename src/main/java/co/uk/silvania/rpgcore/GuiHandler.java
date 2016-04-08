package co.uk.silvania.rpgcore;

import co.uk.silvania.rpgcore.client.skillgui.SkillListGui;
import co.uk.silvania.rpgcore.client.skillgui.SkillSelectGui;
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
			case 1: {
				//AGI/STR stuff
				return null;
			}
			case 2: {
				return new SkillListGui();
			}
		}
		return null;
	}
}
