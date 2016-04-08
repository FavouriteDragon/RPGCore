package co.uk.silvania.rpgcore.client.skillgui;

import org.lwjgl.opengl.GL11;

import co.uk.silvania.rpgcore.RPGCore;
import co.uk.silvania.rpgcore.SkillsContainer;
import co.uk.silvania.rpgcore.skills.EquippedSkills;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class SkillConfig extends SkillSelectGui {
	
	public SkillConfig(SkillsContainer containerSkills) {
		super(containerSkills);
	}

	public static final ResourceLocation skillConfig  = new ResourceLocation(RPGCore.MODID, "textures/gui/skillconfig.png");
	public static final ResourceLocation skillIcons  = new ResourceLocation(RPGCore.MODID, "textures/gui/skillicons.png");
	
	int xSize = 256;
	int ySize = 256;
	
	@Override
	public void initGui() {
		super.initGui();
		int left = (this.width - this.xSize) / 2;
		int top  = (this.height - this.ySize) / 2;

		buttonConfig = new MultiLineButton(1, left+257, top+206, 72, 22, "Back");
		
		buttonList.add(buttonConfig);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int mouseX, int mouseZ) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_BLEND);
		this.mc.getTextureManager().bindTexture(skillConfig); //Main skill pane
		int left = (this.width - this.xSize) / 2;
		int top  = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
		
		this.mc.getTextureManager().bindTexture(skillEquip2); //Item inventory & party/guild panel
		this.drawTexturedModalRect(left+this.xSize, top+11, 0, 0, 81, 226);
		this.drawTexturedModalRect(left-106, top+11, 150, 0, 106, 226);
		
		int skillSlot = getSkillSlotHover(mouseX, mouseZ);
		int slotX = 0;
		int slotZ = 0;
		
		EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get((EntityPlayer) Minecraft.getMinecraft().thePlayer);
		
		if (equippedSkills != null) {
			
			for (int i = 0; i < equippedSkills.skillSlots; i++) {
				if (!equippedSkills.getSkillInSlot(i).isEmpty()) {
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
							mc.getTextureManager().bindTexture(skillIcons);
							iconPosZ = 88;
						}
					} else {
						mc.getTextureManager().bindTexture(skillIcons);
						iconPosX = 226;
						iconPosZ = 226;
					}
					
					int xPos = 0;
					int zPos = 0;
					
					if (i == 0)  { xPos = 8;   zPos = 16;  }
					if (i == 1)  { xPos = 8;   zPos = 56;  }
					if (i == 2)  { xPos = 8;   zPos = 96;  }
					if (i == 3)  { xPos = 8;   zPos = 136; }
					if (i == 4)  { xPos = 8;   zPos = 176; }
					if (i == 5)  { xPos = 8;   zPos = 216; }
					if (i == 6)  { xPos = 218; zPos = 16;  }
					if (i == 7)  { xPos = 218; zPos = 56;  }
					if (i == 8)  { xPos = 218; zPos = 96;  }
					if (i == 9)  { xPos = 218; zPos = 136; }
					if (i == 10) { xPos = 218; zPos = 176; }
					if (i == 11) { xPos = 218; zPos = 216; }
					
					drawTexturedModalRect(((this.width - xSize) / 2) + xPos, ((this.height - ySize) / 2) + zPos, iconPosX, iconPosZ, 30, 30);
				}
			}
		}
		mc.fontRenderer.drawString("Skill configs are work-in-progress.", left + 50, top + 8, 4210752);
		mc.fontRenderer.drawString("They will mainly just let", left + 50, top + 18, 4210752);
		mc.fontRenderer.drawString("you move the XP bar around,", left + 50, top + 28, 4210752);
		mc.fontRenderer.drawString("and show/hide it - but also", left + 50, top + 38, 4210752);
		mc.fontRenderer.drawString("give access to the detailed", left + 50, top + 48, 4210752);
		mc.fontRenderer.drawString("Skill Gui, where things like", left + 50, top + 58, 4210752);
		mc.fontRenderer.drawString("required items can be equipped,", left + 50, top + 68, 4210752);
		mc.fontRenderer.drawString("or any skill-relevant settings", left + 50, top + 78, 4210752);
		mc.fontRenderer.drawString("can be changed (eg dodge range)", left + 50, top + 88, 4210752);
	}
}
