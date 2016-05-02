package co.uk.silvania.rpgcore.client.skillgui;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import co.uk.silvania.rpgcore.GuiConfig;
import co.uk.silvania.rpgcore.RPGCore;
import co.uk.silvania.rpgcore.RPGUtils;
import co.uk.silvania.rpgcore.SkillsContainer;
import co.uk.silvania.rpgcore.network.OpenGuiPacket;
import co.uk.silvania.rpgcore.network.SkillPointPacket;
import co.uk.silvania.rpgcore.skills.EquippedSkills;
import co.uk.silvania.rpgcore.skills.GlobalLevel;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import cpw.mods.fml.client.config.GuiSlider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import scala.actors.threadpool.Arrays;

public class PlayerConfig extends GuiScreen {

	public GuiButton addSTR;
	public GuiButton removeSTR;
	public GuiButton addAGI;
	public GuiButton removeAGI;
	public GuiButton saveChanges;
	public GuiButton cancelChanges;
	
	public GuiButton configSlot1;
	public GuiButton configSlot2;
	public GuiButton configSlot3;
	public GuiButton configSlot4;
	public GuiButton configSlot5;
	public GuiButton configSlot6;
	public GuiButton configSlot7;
	public GuiButton configSlot8;
	public GuiButton configSlot9;
	public GuiButton configSlot10;
	public GuiButton configSlot11;
	public GuiButton configSlot12;
	
	public GuiButton configGlobalLevel;
	
	int availablePoints;
	
	int addStrVal;
	int addAgiVal;

	public static final ResourceLocation skillConfig = new ResourceLocation(RPGCore.MODID, "textures/gui/blankgui.png");
	
	int xSize = 256;
	int ySize = 256;
	
	@Override
	public void initGui() {
		super.initGui();
		
		GlobalLevel glevel = (GlobalLevel) GlobalLevel.get((EntityPlayer) mc.thePlayer);
		availablePoints = glevel.skillPoints;
		//EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get((EntityPlayer) Minecraft.getMinecraft().thePlayer);
		//SkillLevelBase skill = SkillLevelBase.getSkillByID(equippedSkills.getSkillInSlot(slot), mc.thePlayer);
		
		int left = (this.width - this.xSize) / 2;
		int top  = (this.height - this.ySize) / 2;
		
		addSTR = new GuiButton(1, left+93, top+21, 20, 20, "+");
		removeSTR = new GuiButton(2, left+19, top+21, 20, 20, "-");
		addAGI = new GuiButton(3, left+216, top+21, 20, 20, "+");
		removeAGI = new GuiButton(4, left+144, top+21, 20, 20, "-");
		saveChanges = new GuiButton(5, left+7, top+47, 119, 20, "Save Changes");
		cancelChanges = new GuiButton(6, left+130, top+47, 119, 20, "Cancel Changes");
		
		configSlot1 = new GuiButton(7,   left+7,   top+109, 119, 20, "Configure Slot 1");
		configSlot2 = new GuiButton(8,   left+7,   top+133, 119, 20, "Configure Slot 2");
		configSlot3 = new GuiButton(9,   left+7,   top+157, 119, 20, "Configure Slot 3");
		configSlot4 = new GuiButton(10,  left+7,   top+181, 119, 20, "Configure Slot 4");
		configSlot5 = new GuiButton(11,  left+7,   top+205, 119, 20, "Configure Slot 5");
		configSlot6 = new GuiButton(12,  left+7,   top+229, 119, 20, "Configure Slot 6");
		configSlot7 = new GuiButton(13,  left+130, top+109, 119, 20, "Configure Slot 7");
		configSlot8 = new GuiButton(14,  left+130, top+133, 119, 20, "Configure Slot 8");
		configSlot9 = new GuiButton(15,  left+130, top+157, 119, 20, "Configure Slot 9");
		configSlot10 = new GuiButton(16, left+130, top+181, 119, 20, "Configure Slot 10");
		configSlot11 = new GuiButton(17, left+130, top+205, 119, 20, "Configure Slot 11");
		configSlot12 = new GuiButton(18, left+130, top+229, 119, 20, "Configure Slot 12");
		
		configGlobalLevel = new GuiButton(19, left+72, top+81, 120, 20, "Configure Global Level");


		buttonList.add(addSTR);
		buttonList.add(removeSTR);
		buttonList.add(addAGI);
		buttonList.add(removeAGI);
		buttonList.add(saveChanges);
		buttonList.add(cancelChanges);
		
		buttonList.add(configSlot1);
		buttonList.add(configSlot2);
		buttonList.add(configSlot3);
		buttonList.add(configSlot4);
		buttonList.add(configSlot5);
		buttonList.add(configSlot6);
		buttonList.add(configSlot7);
		buttonList.add(configSlot8);
		buttonList.add(configSlot9);
		buttonList.add(configSlot10);
		buttonList.add(configSlot11);
		buttonList.add(configSlot12);
		
		buttonList.add(configGlobalLevel);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseZ, float par3) {
		this.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_BLEND);
		this.mc.getTextureManager().bindTexture(skillConfig); //Main skill pane
		int left = (this.width - this.xSize) / 2;
		int top  = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
		
		if (addStrVal <= 0) { removeSTR.enabled = false; } else { removeSTR.enabled = true; }
		if (addAgiVal <= 0) { removeAGI.enabled = false; } else { removeAGI.enabled = true; }
		if (availablePoints <= 0) {
			addSTR.enabled = false;
			addAGI.enabled = false;
		} else {
			addSTR.enabled = true;
			addAGI.enabled = true;
		}

		String pnts = "Skill Points: " + availablePoints;
		
		mc.fontRenderer.drawString(pnts, left + 249 - mc.fontRenderer.getStringWidth(pnts), top + 7, 4210752);
		mc.fontRenderer.drawString("Player Configuration", left + 7, top + 7, 4210752);
		
		String str = "STR: " + SkillLevelBase.getSkillByID("skillStrength", mc.thePlayer).getLevel();
		String agi = "AGI: " + SkillLevelBase.getSkillByID("skillAgility", mc.thePlayer).getLevel();
		
		String strAdd = addStrVal > 0 ? "+" + addStrVal : "";
		String agiAdd = addAgiVal > 0 ? "+" + addAgiVal : "";
		
		mc.fontRenderer.drawString(str, left + 66-(mc.fontRenderer.getStringWidth(str+strAdd)/2), top + 27, 4210752);
		mc.fontRenderer.drawString(strAdd, left + 66+(mc.fontRenderer.getStringWidth(str)/2), top + 27, 25600);
		
		mc.fontRenderer.drawString(agi, left + 189-(mc.fontRenderer.getStringWidth(agi+agiAdd)/2), top + 27, 4210752);
		mc.fontRenderer.drawString(agiAdd, left + 189+(mc.fontRenderer.getStringWidth(agi)/2), top + 27, 25600);
		
		super.drawScreen(mouseX, mouseZ, par3);
		
		/*if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			if (showGlobalXP.func_146115_a()) {
				String[] str = {"\u00A7lShow XP Bar", "\u00A7oShows or hides the XP bar for this skill.", "\u00A7c\u00A7oIf hidden, the other settings here are ignored."};
				List temp = Arrays.asList(str);
				drawHoveringText(temp, mouseX, mouseZ, fontRendererObj);
			}
			
			if (xpTextType.func_146115_a()) {
				String[] str = {"\u00A7lXP Text Type", "\u00A7oToggle the text shown on the XP bar.", "\u00A7e0: Only level is shown", "\u00A7e1: XP is shown as XP since last level/XP for next level", "\u00A7e2: XP is shown as Overall Experience/XP for next level.", "\u00A7e3: XP is shown as a percentage (%)", "\u00A7aUse +/- or L/R arrow keys for fine-tuning."};
				List temp = Arrays.asList(str);
				drawHoveringText(temp, mouseX, mouseZ, fontRendererObj);
			}
			
			if (xpBarPos.func_146115_a()) {
				String[] str = {"\u00A7lXP Bar Position", "\u00A7oChange the position of the bar on the screen.", "\u00A7e1: Top-left corner", "\u00A7e2: Top-center", "\u00A7e3: Top-right corner", "\u00A7e4: Bottom-left corner", "\u00A7e5: Bottom-center", "\u00A7e6: Bottom-right corner", "\u00A7aUse +/- or L/R arrow keys for fine-tuning."};
				List temp = Arrays.asList(str);
				drawHoveringText(temp, mouseX, mouseZ, fontRendererObj);
			}
	
			if (xOffset.func_146115_a()) {
				String[] str = {"\u00A7lXP Bar X-Offset", "\u00A7oMove the bar from the position on the X-axis (left-right)", "\u00A7cNote this setting may hide the XP bar", "\u00A7coff-screen, or cause overlapping.", "\u00A7aUse +/- or L/R arrow keys for fine-tuning."};
				List temp = Arrays.asList(str);
				drawHoveringText(temp, mouseX, mouseZ, fontRendererObj);
			}
			
			if (yOffset.func_146115_a()) {
				String[] str = {"\u00A7lXP Bar Y-Offset", "\u00A7oMove the bar from the position on the Y-axis (up-down)", "\u00A7cNote this setting may hide the XP bar", "\u00A7coff-screen, or cause overlapping.", " ", "\u00A7cBars in the same corner will automatically offset", "\u00A7cto leave a 2-pixel gap from the last bar.", "\u00A7aUse +/- or L/R arrow keys for fine-tuning."};
				List temp = Arrays.asList(str);
				drawHoveringText(temp, mouseX, mouseZ, fontRendererObj);
			}
			
			if (xpBarStyle.func_146115_a()) {
				String[] str = {"\u00A7lXP Bar Style", "\u00A7oChange the look of the XP bar"};
				List temp = Arrays.asList(str);
				drawHoveringText(temp, mouseX, mouseZ, fontRendererObj);
			}
			
			if (xpBarWidth.func_146115_a()) {
				String[] str = {"\u00A7lXP Bar Width", "\u00A7oChange the width of the XP bar"};
				List temp = Arrays.asList(str);
				drawHoveringText(temp, mouseX, mouseZ, fontRendererObj);
			}
			
			if (configureSkill.func_146115_a() && configureSkill.enabled) {
				String[] str = {"\u00A7lConfigure Skill", "\u00A7oOpens the Skill's GUI for skill-specific options."};
				List temp = Arrays.asList(str);
				drawHoveringText(temp, mouseX, mouseZ, fontRendererObj);
			}
			
			if (saveChanges.func_146115_a()) {
				String[] str = {"\u00A7lSave Changes", "\u00A7oSave changes and return to Skill Overview."};
				List temp = Arrays.asList(str);
				drawHoveringText(temp, mouseX, mouseZ, fontRendererObj);
			}
			
			if (cancelChanges.func_146115_a()) {
				String[] str = {"\u00A7lCancel Changes", "\u00A7oReturn to Skill Overview without saving."};
				List temp = Arrays.asList(str);
				drawHoveringText(temp, mouseX, mouseZ, fontRendererObj);
			}
		}*/
	}
	
    @Override
    public void actionPerformed(GuiButton button) {
    	switch(button.id) {
    	case 1:
    		addStrVal += 1;
    		availablePoints -= 1;
    		break;
    	case 2:
    		addStrVal -= 1;
    		availablePoints += 1;
    		break;
    	case 3:
    		addAgiVal += 1;
    		availablePoints -= 1;
    		break;
    	case 4:
    		addAgiVal -= 1;
    		availablePoints += 1;
    		break;
    	case 5:
    		System.out.println("Syncing!");
    		RPGCore.network.sendToServer(new SkillPointPacket(addStrVal, addAgiVal));
    		Minecraft.getMinecraft().thePlayer.closeScreen();
    		RPGCore.network.sendToServer(new OpenGuiPacket(0));
    		break;
    	case 6:
    		Minecraft.getMinecraft().thePlayer.closeScreen();
    		RPGCore.network.sendToServer(new OpenGuiPacket(0));
    		break;
    	case 7:
    		configureSkill(1);
    		break;
    	case 8:
    		configureSkill(2);
    		break;
    	case 9:
    		configureSkill(3);
    		break;
    	case 10:
    		configureSkill(4);
    		break;
    	case 11:
    		configureSkill(5);
    		break;
    	case 12:
    		configureSkill(6);
    		break;
    	case 13:
    		configureSkill(7);
    		break;
    	case 14:
    		configureSkill(8);
    		break;
    	case 15:
    		configureSkill(9);
    		break;
    	case 16:
    		configureSkill(10);
    		break;
    	case 17:
    		configureSkill(11);
    		break;
    	case 18:
    		configureSkill(12);
    		break;
    	case 19:
    		configureSkill(999);
    		break;
    	}
    }
    
    public void configureSkill(int slot) {
    	EntityPlayer player = Minecraft.getMinecraft().thePlayer;
    	if (slot == 999) {
    		player.openGui(RPGCore.instance, 4, Minecraft.getMinecraft().theWorld, (int) player.posX, (int) player.posY, (int) player.posZ);
    	} else {
	    	SkillSelectGui.slotClicked = slot;
	    	player.openGui(RPGCore.instance, 1, Minecraft.getMinecraft().theWorld, (int) player.posX, (int) player.posY, (int) player.posZ);
    	}
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
