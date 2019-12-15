package co.uk.silvania.rpgcore;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import co.uk.silvania.rpgcore.network.EquippedSkillsPacket;
import co.uk.silvania.rpgcore.network.LevelPacket;
import co.uk.silvania.rpgcore.skills.EquippedSkills;
import co.uk.silvania.rpgcore.skills.GlobalLevel;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class EventHandler {
	
	GuiConfig config = new GuiConfig();
	
	public static final ResourceLocation icons = new ResourceLocation(RPGCore.MODID, "textures/gui/icons.png");
	public static final ResourceLocation xpBars = new ResourceLocation(RPGCore.MODID, "textures/gui/xpbars.png");
	public static final ResourceLocation skillIcons = new ResourceLocation(RPGCore.MODID, "textures/gui/skillicons.png");
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent.Pre event) {
		Minecraft mc = Minecraft.getMinecraft();
		Gui gui = new Gui();

		//Global level XP bar
		GlobalLevel glevel = (GlobalLevel) GlobalLevel.get(mc.player);
		if (glevel != null) {
			mc.getTextureManager().bindTexture(xpBars);

			GL11.glEnable(GL11.GL_BLEND);
         	OpenGlHelper.glBlendFunc(770, 771, 1, 0);
         	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
         	GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
			
			Tessellator tess = Tessellator.instance;
			double px = 1.0 / 256.0; //One pixel of the sprite sheet. Cleaner than manually calculating and having big numbers!
			
			double cent = (mc.displayWidth/2)/100.0; //1% of the screen width
			float target;
			if (glevel.getLevel() <= 1) {
				target = glevel.getXpForLevel(glevel.getLevel()); //The point it levels up
			} else {
				target = glevel.getXpForLevel(glevel.getLevel()) - glevel.getXpForLevel(glevel.getLevel()-1); //The point it levels up
			}
			if (target == 0) {
				target = 1;
			}
			float current = glevel.getXPGlobal() - glevel.getXpForLevel(glevel.getLevel()-1); //Current amount of XP since last level-up
			//mc.fontRenderer.drawString("target: " + target + " (" + glevel.getXpForLevel(glevel.getLevel()), p_78276_2_, p_78276_3_, p_78276_4_)
			double progress = (current/target) * 100; //Current XP as percentage
			//Therefore progress*cent = percentage of screen to be filled
			
			int barPos = 0;
			
			if (!config.globalXpTop) { barPos = (mc.displayHeight/2)-3; }
			
			tess.startDrawingQuads();
			tess.addVertexWithUV(0, barPos+3, 1, 0, px*256);
			tess.addVertexWithUV(mc.displayWidth/2, barPos+3, 1, 1, px*256);
			tess.addVertexWithUV(mc.displayWidth/2, barPos, 1, 1, px*254);
			tess.addVertexWithUV(0, barPos, 1, 0, px*254);
			tess.draw();
			
			tess.startDrawingQuads();
			tess.addVertexWithUV(0, barPos+3, 1, 0, px*253);
			tess.addVertexWithUV(progress*cent, barPos+3, 1, (current/target), px*253);
			tess.addVertexWithUV(progress*cent, barPos, 1, (current/target), px*251);
			tess.addVertexWithUV(0, barPos, 1, 0, px*251);
			tess.draw();
			
			if (config.showGlobalLevel) {
				int posLeft = config.globalLevelLeft ? 5 : (mc.displayWidth/2) - 22;
				int posHeight = config.globalXpTop ? 3 : (mc.displayHeight/2) - 14;
				
				gui.drawTexturedModalRect(posLeft, posHeight, 0, 238, 17, 11);
				String lvl = "" + glevel.getLevel();
				mc.fontRenderer.drawString(lvl, posLeft+9-(mc.fontRenderer.getStringWidth(lvl)/2), posHeight+2, 16777215);
			}
		}
		//Skill XP bars
		for (int i = 0; i < RegisterSkill.skillList.size(); i++) {
			SkillLevelBase skillBase = RegisterSkill.skillList.get(i);
			SkillLevelBase skill = (SkillLevelBase) skillBase.get((EntityPlayer) mc.thePlayer, skillBase.skillId);
			EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get((EntityPlayer) Minecraft.getMinecraft().thePlayer);
			
			if (skill != null) {
				if (equippedSkills.isSkillEquipped(skill.skillId)) {
					int slot = equippedSkills.findSkillSlot(skill.skillId);
					
					if (config.getShowXp(slot)) {
						GL11.glEnable(GL11.GL_BLEND);
			         	OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			         	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			         	GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
						
						mc.getTextureManager().bindTexture(xpBars);
						int barStyle = config.getXPBarStyle(slot);
						int anchor = config.getXPBarPos(slot);
						int barWidth = config.getXPBarWidth(slot);
						int posX = 0;
						int posY = 0;
						
						int xOffset = config.getXPXOffset(slot);
						int yOffset = config.getXPYOffset(slot);
						
						if (anchor == 1 || anchor == 4) { posX = 0; }
						if (anchor == 2 || anchor == 5) { posX = (mc.displayWidth/4)-barWidth; }
						if (anchor == 3 || anchor == 6) { posX = (mc.displayWidth/2)-barWidth; }
						
						if (anchor == 1 || anchor == 2 || anchor == 3) { posY = 0; }
						if (anchor == 4 || anchor == 5 || anchor == 6) { posY = (mc.displayHeight/2)-12; }
						
						int barIconX = 0;
						int barIconY = 0;
						
						int iconBarX = 0;
						
						boolean rightAlign = false;
						int textOffset = 2;
						int iconOffset = 0;
						
						if (config.getShowIcon(slot)) { iconOffset = 8; }
						
						if (barStyle == 2 || barStyle == 4 || barStyle == 6 || barStyle == 8 || barStyle == 10 || barStyle == 12) { barIconX = 131; rightAlign = true; }
						
						if (barStyle == 3 || barStyle == 4)   { barIconY = 13; }
						if (barStyle == 5 || barStyle == 6)   { barIconY = 26; }
						if (barStyle == 7 || barStyle == 8)   { barIconY = 39; }
						if (barStyle == 9 || barStyle == 10)  { barIconY = 52; }
						if (barStyle == 11 || barStyle == 12) { barIconY = 65; }
						if (barStyle == 13)   				  { barIconY = 78; }
						
						if (barStyle == 1 || barStyle == 3 || barStyle == 5 || barStyle == 7) { iconBarX = posX - 8; }
						if (barStyle == 2 || barStyle == 4 || barStyle == 6 || barStyle == 8) { iconBarX = (posX) + (barWidth) - 8; }
						if (barStyle >= 9 && barStyle <= 13) { iconBarX = posX + (barWidth/2) - 8; }
						
						gui.drawTexturedModalRect(posX + xOffset,             posY + yOffset, barIconX,     barIconY, 11, 12); //Bar Left
						gui.drawTexturedModalRect(posX + xOffset+11,          posY + yOffset, barIconX+11,  barIconY, barWidth-22, 12); //Bar
						gui.drawTexturedModalRect(posX + xOffset+barWidth-11, posY + yOffset, barIconX+114, barIconY, 11, 12); //Bar Right
						
						int col = skill.xpBarColour();
						
						if ((col & -67108864) == 0) {
							col |= -16777216;
			            }
						
						float red = (float)(col >> 16 & 255) / 255.0F;
						float blue = (float)(col >> 8 & 255) / 255.0F;
						float green = (float)(col & 255) / 255.0F;
						float alpha = (float)(col >> 24 & 255) / 255.0F;
						
						float target = skill.getXpForLevel(skill.getLevel()) - skill.getXpForLevel(skill.getLevel()-1); //The point it levels up
						float current = skill.getXP() - skill.getXpForLevel(skill.getLevel()-1); //Current amount of XP since last level-up
						
						double progress = (current/target) * 100; //Current XP as percentage
						double cent = barWidth/100.0;
						
						int xpFill = (int) (progress*cent);

						GL11.glColor4f(red, blue, green, alpha);
						if (skill.canGainXP()) {
							if (xpFill < 11) {
								gui.drawTexturedModalRect(posX + xOffset,             posY + yOffset, barIconX,     barIconY+91, xpFill, 12); //Bar Left
							} else {
								gui.drawTexturedModalRect(posX + xOffset,             posY + yOffset, barIconX,     barIconY+91, 11, 12); //Bar Left
							}
							
							if (xpFill < barWidth-11) {
								gui.drawTexturedModalRect(posX + xOffset+11,          posY + yOffset, barIconX+11,  barIconY+91, xpFill-11, 12); //Bar
							} else {
								gui.drawTexturedModalRect(posX + xOffset+11,          posY + yOffset, barIconX+11,  barIconY+91, barWidth-22, 12); //Bar
								gui.drawTexturedModalRect(posX + xOffset+barWidth-11, posY + yOffset, barIconX+114, barIconY+91, xpFill-(barWidth-11), 12); //Bar Right
							}
						} else {
							gui.drawTexturedModalRect(posX + xOffset,             posY + yOffset, barIconX,     barIconY+91, 11, 12); //Bar Left
							gui.drawTexturedModalRect(posX + xOffset+11,          posY + yOffset, barIconX+11,  barIconY+91, barWidth-22, 12); //Bar
							gui.drawTexturedModalRect(posX + xOffset+barWidth-11, posY + yOffset, barIconX+114, barIconY+91, 11, 12); //Bar Right
						}
						
						int txtStyle = config.getXPTextType(slot);
						String text = "";
						if (txtStyle == 0) { text = "Lvl " + skill.getLevel(); }
						if (txtStyle == 1) { text = "Lvl " + skill.getLevel() + " (" + skill.getXPProgressForPrint() + ")"; }
						if (txtStyle == 2) { text = "Lvl " + skill.getLevel() + " (" + skill.getXPProgressAsPercentage() + ")"; }
						if (txtStyle == 3) { text = skill.skillName() + " - Lvl " + skill.getLevel(); }
						if (txtStyle == 4) { text = skill.skillName() + " - Lvl " + skill.getLevel() + " (" + skill.getXPProgressForPrint() + ")"; }
						if (txtStyle == 5) { text = skill.skillName() + " - Lvl " + skill.getLevel() + " (" + skill.getXPProgressAsPercentage() + ")"; }
						if (txtStyle == 6) { text = skill.shortName() + " - Lvl " + skill.getLevel(); }
						if (txtStyle == 7) { text = skill.shortName() + " - Lvl " + skill.getLevel() + " (" + skill.getXPProgressForPrint() + ")"; }
						if (txtStyle == 8) { text = skill.shortName() + " - Lvl " + skill.getLevel() + " (" + skill.getXPProgressAsPercentage() + ")"; }
						if (txtStyle == 9) { text = skill.skillName(); }
						if (txtStyle == 10) { text = skill.shortName(); }
						if (txtStyle == 11) { text = skill.getXPProgressForPrint(); }
						if (txtStyle == 12) { text = skill.getXPProgressAsPercentage(); }
						
						if (rightAlign) { textOffset = barWidth-mc.fontRenderer.getStringWidth(text)-2-iconOffset; iconOffset = 0; }
						
						mc.fontRenderer.drawString(text, posX + xOffset + textOffset + iconOffset, posY + yOffset + 2, 16777215);
						
						ResourceLocation icon;
						int iconX;
						int iconZ;
						
						if (skill.skillIcon() != null) {
							icon = skill.skillIcon();
							iconX = skill.iconX();
							iconZ = skill.iconZ()+128;
						} else {
							icon = skillIcons;
							iconX = 92;
							iconZ = 220;
						}
						if (config.getShowIcon(slot)) {
							mc.getTextureManager().bindTexture(icon);
							gui.drawTexturedModalRect(xOffset + iconBarX - 2, posY + yOffset - 2, skill.iconX(), skill.iconZ()+128, 16, 16);
						}
					}
				}
			}
		}
		mc.getTextureManager().bindTexture(new ResourceLocation("minecraft", "textures/gui/icons.png"));
	}
	
	public float addedXpForRender = 0;
	
	boolean rendering = false;
	int ticker = 0;
	
	/*@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderXPGain(RenderGameOverlayEvent.Post event) {
		Minecraft mc = Minecraft.getMinecraft();
		if (addedXpForRender > 0 && !rendering) {
			rendering = true;
			ticker = 0;
		}

		if (rendering) {
			if (ticker < 60) {
				mc.fontRenderer.drawString("+"+addedXpForRender, mc.displayWidth/4, mc.displayHeight/4, 16777215);
				//ticker++;
			} else {
				rendering = false;
			}
		}
	}*/
	
	public void setXpForRender(float xp) {
		addedXpForRender = xp;
	}
	
	//Save the player to disk, then load it to the new player
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) {
		NBTTagCompound nbt = new NBTTagCompound();
		for (int i = 0; i < RegisterSkill.skillList.size(); i++) {
			SkillLevelBase skillBase = RegisterSkill.skillList.get(i);
			
			skillBase.get(event.original, skillBase.skillId).saveNBTData(nbt);
			skillBase.get(event.entityPlayer, skillBase.skillId).loadNBTData(nbt);
		}
		GlobalLevel.get(event.original).saveNBTData(nbt);
		GlobalLevel.get(event.entityPlayer).loadNBTData(nbt);
	}
	
	@SubscribeEvent
	public void onPlayerJoinWorld(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			if (!player.worldObj.isRemote) {
				for (int i = 0; i < RegisterSkill.skillList.size(); i++) {
					SkillLevelBase skillBase = RegisterSkill.skillList.get(i);
					SkillLevelBase skill = (SkillLevelBase) skillBase.get(player, skillBase.skillId);
					if (skill != null) {
						RPGCore.network.sendTo(new LevelPacket(skill.getXP(), -1, skill.skillId), (EntityPlayerMP) player);
					}
				}
				EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get((EntityPlayer) player);
				
				GlobalLevel glevel = (GlobalLevel) GlobalLevel.get((EntityPlayer) player);
				RPGCore.network.sendTo(new LevelPacket((int)(glevel.getXPGlobal()), glevel.getSkillPoints(), glevel.skillId), (EntityPlayerMP) player);
				
				RPGCore.network.sendTo(new EquippedSkillsPacket(
						equippedSkills.getSkillInSlot(0), 
						equippedSkills.getSkillInSlot(1), 
						equippedSkills.getSkillInSlot(2), 
						equippedSkills.getSkillInSlot(3), 
						equippedSkills.getSkillInSlot(4), 
						equippedSkills.getSkillInSlot(5), 
						equippedSkills.getSkillInSlot(6),
						equippedSkills.getSkillInSlot(7),
						equippedSkills.getSkillInSlot(8),
						equippedSkills.getSkillInSlot(9),
						equippedSkills.getSkillInSlot(10),
						equippedSkills.getSkillInSlot(11)), (EntityPlayerMP) player);
			}
		}
	}
}
