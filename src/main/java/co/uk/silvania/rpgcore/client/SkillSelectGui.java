package co.uk.silvania.rpgcore.client;

import org.lwjgl.opengl.GL11;

import co.uk.silvania.rpgcore.RPGCore;
import co.uk.silvania.rpgcore.SkillsContainer;
import co.uk.silvania.rpgcore.network.OpenGuiPacket;
import co.uk.silvania.rpgcore.skills.EquippedSkills;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class SkillSelectGui extends GuiContainer {
	
	public static final ResourceLocation skillEquip  = new ResourceLocation(RPGCore.MODID, "textures/gui/skillequip.png");
	public static final ResourceLocation skillEquip2 = new ResourceLocation(RPGCore.MODID, "textures/gui/skillequip2.png");
	public static final ResourceLocation skillIcons  = new ResourceLocation(RPGCore.MODID, "textures/gui/skillicons.png");
	
	int xSize;
	int ySize;
	
	public static int slotClicked = -1;
	
	public SkillSelectGui(SkillsContainer containerSkills) {
		super(containerSkills);
		
		xSize = 256;
		ySize = 256;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseZ, float par3) {
		super.drawScreen(mouseX, mouseZ, par3);
		
		int skillSlot = getSkillSlotHover(mouseX, mouseZ);
		int slotX = 0;
		int slotZ = 0;
		
		int left = (this.width - this.xSize) / 2;
		int top  = (this.height - this.ySize) / 2;
		
		EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get((EntityPlayer) Minecraft.getMinecraft().thePlayer);
		
		if (equippedSkills != null) {
			
			for (int i = 0; i < 7; i++) {
				if (!equippedSkills.getSkillInSlot(i).isEmpty()) {
					//System.out.println("Skill found in slot 0!");
					SkillLevelBase skill = SkillLevelBase.getSkillByID(equippedSkills.getSkillInSlot(i), mc.thePlayer);
					ResourceLocation icon;
					int iconPosX = 0;
					int iconPosZ = 0;
					
					GL11.glDisable(GL11.GL_LIGHTING);
		            GL11.glDisable(GL11.GL_DEPTH_TEST);
		            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		    		GL11.glEnable(GL11.GL_BLEND);
					
					if (skill != null) {
						if (skill.skillIcon != null) {
							mc.getTextureManager().bindTexture(skill.skillIcon);
							iconPosX = skill.iconX;
							iconPosZ = skill.iconZ;
						} else {
							mc.getTextureManager().bindTexture(new ResourceLocation(RPGCore.MODID, "textures/gui/skillicons.png"));
							iconPosZ = 88;
						}
					}
					
					int xPos = 0;
					int zPos = 0;
					
					if (i == 0) { xPos = 45;  zPos = 181; }
					if (i == 1) { xPos = 17;  zPos = 113; }
					if (i == 2) { xPos = 45;  zPos = 45;  }
					if (i == 3) { xPos = 113; zPos = 17;  }
					if (i == 4) { xPos = 181; zPos = 45;  }
					if (i == 5) { xPos = 208; zPos = 113; }
					if (i == 6) { xPos = 181; zPos = 181; }
					
					drawTexturedModalRect(((this.width - xSize) / 2) + xPos, ((this.height - ySize) / 2) + zPos, iconPosX, iconPosZ, 30, 30);
				}
			}
		}
		
		if (skillSlot == 0) { slotX = 45;  slotZ = 181; }
		if (skillSlot == 1) { slotX = 17;  slotZ = 113; }
		if (skillSlot == 2) { slotX = 45;  slotZ = 45;  }
		if (skillSlot == 3) { slotX = 113; slotZ = 17;  }
		if (skillSlot == 4) { slotX = 181; slotZ = 45;  }
		if (skillSlot == 5) { slotX = 208; slotZ = 113; }
		if (skillSlot == 6) { slotX = 181; slotZ = 181; }
		
		if (skillSlot >= 0) {
			GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glColorMask(true, true, true, false);
            
            for (int i = 0; i < 30; i++) {
            	int j = 0; //Adjust the width. Gives the render "roundness" for the icon slots.
            	if (i == 0 || i == 29) { j = 11; }
            	if (i == 1 || i == 28) { j = 8; }
            	if (i == 2 || i == 27) { j = 6; }
            	if (i == 3 || i == 26) { j = 5; }
            	if (i == 4 || i == 25) { j = 4; }
            	if (i == 5 || i == 24) { j = 3; }
            	
            	if (i == 6 || i == 7 || i == 22 || i == 23)      { j = 2; }
            	if ((i >= 8 && i <= 10) || (i >= 19 && i <= 21)) { j = 1; }
            	
            	this.drawGradientRect(left+slotX+j, top+slotZ+i, left+slotX+30-j, top+slotZ+i+1, -2130706433, -2130706433);
            }
            
            GL11.glColorMask(true, true, true, true);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
		}
	}
	
	protected void mouseClicked(int mouseX, int mouseZ, int buttonId) {
        super.mouseClicked(mouseX, mouseZ, buttonId);
        int skillSlot = getSkillSlotHover(mouseX, mouseZ);
        if (skillSlot >= 0) {
        	if (skillSlot == 4) {
        		System.out.println("Clicked AGI/STR Slot");
        		//RPGCore.network.sendToServer(new OpenGuiPacket(1));
        	} else
        	System.out.println("Clicked Skill Slot " + skillSlot);
        	slotClicked = skillSlot;
			openGui(2);
        }
	}
	
	public void openGui(int id) {
		mc.thePlayer.openGui(RPGCore.instance, id, mc.thePlayer.worldObj, (int) mc.thePlayer.posX, (int) mc.thePlayer.posY, (int) mc.thePlayer.posZ);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int z) {
		
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_BLEND);
		this.mc.getTextureManager().bindTexture(skillEquip); //Main skill pane
		int left = (this.width - this.xSize) / 2;
		int top  = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
		
		this.mc.getTextureManager().bindTexture(skillEquip2); //Item inventory on the side (Skill window is a full 256x texture)
		this.drawTexturedModalRect(left+this.xSize, top+11, 0, 0, 81, 176);
	}
	
	public int getSkillSlotHover(int mouseX, int mouseZ) {
		int left = (this.width - this.xSize) / 2;
		int top  = (this.height - this.ySize) / 2;
		
		if (isMouseWithinCircle(mouseX, mouseZ, left + 45,  top + 181)) { return 0; }
		if (isMouseWithinCircle(mouseX, mouseZ, left + 17,  top + 113)) { return 1; }
		if (isMouseWithinCircle(mouseX, mouseZ, left + 45,  top + 45))  { return 2; }		
		if (isMouseWithinCircle(mouseX, mouseZ, left + 113, top + 17))  { return 3; }
		if (isMouseWithinCircle(mouseX, mouseZ, left + 181, top + 45))  { return 4; }
		if (isMouseWithinCircle(mouseX, mouseZ, left + 208, top + 113)) { return 5; }
		if (isMouseWithinCircle(mouseX, mouseZ, left + 181, top + 181)) { return 6; }
		
		return -1;
	}
	
	//Assumes your circle is 30x30. Can't generalize it unfortunately.
	public boolean isMouseWithinCircle(int mouseX, int mouseZ, int circleLeft, int circleTop) {
		for (int i = 0; i < 30; i++) {
        	int j = 0;
        	if (i == 0 || i == 29) { j = 11; }
        	if (i == 1 || i == 28) { j = 8; }
        	if (i == 2 || i == 27) { j = 6; }
        	if (i == 3 || i == 26) { j = 5; }
        	if (i == 4 || i == 25) { j = 4; }
        	if (i == 5 || i == 24) { j = 3; }
        	
        	if (i == 6 || i == 7 || i == 22 || i == 23)      { j = 2; }
        	if ((i >= 8 && i <= 10) || (i >= 19 && i <= 21)) { j = 1; }
        	
        	if (mouseX >= circleLeft+j && mouseZ >= circleTop+i && mouseX <= circleLeft+30-j && mouseZ <= circleTop+i+1) {
        		return true;
        	}
        }
		return false;
	}
}
