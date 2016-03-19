package co.uk.silvania.rpgcore.client;

import org.lwjgl.opengl.GL11;

import co.uk.silvania.rpgcore.RPGCore;
import co.uk.silvania.rpgcore.SkillsContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class SkillSelectGui extends GuiContainer {
	
	public static final ResourceLocation skillEquip = new ResourceLocation(RPGCore.MODID, "textures/gui/skillequip.png");
	public static final ResourceLocation skillIcons = new ResourceLocation(RPGCore.MODID, "textures/gui/skillicons.png");
	
	int xSize;
	int ySize;
	
	public SkillSelectGui(SkillsContainer containerSkills) {
		super(containerSkills);
		
		xSize = 256;
		ySize = 256;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_BLEND);
		this.mc.getTextureManager().bindTexture(skillEquip);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);		
	}
	

}
