package co.uk.silvania.rpgcore.client.skillgui;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import co.uk.silvania.rpgcore.GuiConfig;
import co.uk.silvania.rpgcore.RPGCore;
import co.uk.silvania.rpgcore.RPGUtils;
import co.uk.silvania.rpgcore.SkillsContainer;
import co.uk.silvania.rpgcore.network.OpenGuiPacket;
import co.uk.silvania.rpgcore.skills.EquippedSkills;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import cpw.mods.fml.client.config.GuiSlider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class SkillConfig extends GuiScreen {
	
	public MultiLineButton showXP;
	public MultiLineButton showIcon;
	public GuiSlider xpTextType;
	public GuiSlider xpBarPos;
	public GuiSlider xOffset;
	public GuiSlider yOffset;
	public GuiSlider xpBarStyle;
	public GuiSlider xpBarWidth;
	
	int slot = SkillSelectGui.slotClicked;
	
	String saved = "";
	
	GuiConfig config = new GuiConfig();
	
	public MultiLineButton configureSkill;
	public MultiLineButton saveChanges;
	public MultiLineButton cancelChanges;
	
	boolean showXPBar;
	boolean showIconBar;

	public static final ResourceLocation skillConfig = new ResourceLocation(RPGCore.MODID, "textures/gui/skillconfig.png");
	public static final ResourceLocation xpBars = new ResourceLocation(RPGCore.MODID, "textures/gui/xpbars.png");
	public static final ResourceLocation skillIcons = new ResourceLocation(RPGCore.MODID, "textures/gui/skillicons.png");
	
	int xSize = 256;
	int ySize = 248;
	
	@Override
	public void initGui() {
		super.initGui();
		saved = "";
		
		EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get((EntityPlayer) Minecraft.getMinecraft().thePlayer);
		SkillLevelBase skill = SkillLevelBase.getSkillByID(equippedSkills.getSkillInSlot(slot), mc.thePlayer);
		
		int left = (this.width - this.xSize) / 2;
		int top  = (this.height - this.ySize) / 2;
		
		showXPBar = config.getShowXp(slot);
		showIconBar = config.getShowIcon(slot);
		
		String showxp = showXPBar ? "XP Bar: Shown" : "XP Bar: Hidden";
		String showicon = showIconBar ? "Icon: Shown" : "Icon: Hidden";
		
		showXP = new MultiLineButton(1, left+161, top+6, 88, 15, showxp);
		showIcon = new MultiLineButton(2, left+161, top+24, 88, 15, showicon);
		xpTextType = new GuiSlider(3, left+7, top+44, 117, 20, "", "", 0, 13, 0, false, true);
		xpBarPos = new GuiSlider(4, left+132,  top+44, 117, 20, "", "", 1, 6, 1, false, true);
		
		xOffset = new GuiSlider(5, left+7,  top+68, 117, 20, "", "", -width/2, width/2, 0, false, true);
		yOffset = new GuiSlider(6, left+132, top+68, 117, 20, "", "", -height/2, height/2, 0, false, true);
		
		xpBarStyle = new GuiSlider(7, left+7,  top+92, 117, 20, "", "", 1, 13, 1, false, true);
		xpBarWidth = new GuiSlider(8, left+132,  top+92, 117, 20, "", "", 21, 125, 80, false, true);
		
		configureSkill = new MultiLineButton(9, left+23,  top+116, 60, 24, "Configure#Skill");
		saveChanges =    new MultiLineButton(10, left+98,  top+116, 60, 24, "Save#Changes");
		cancelChanges =  new MultiLineButton(11, left+173, top+116, 60, 24, "Close");
		
		xpTextType.displayString = "XP Text Type";
		xpBarPos.displayString = "XP Bar Position";
		xOffset.displayString = "X Offset";
		yOffset.displayString = "Y Offset";
		xpBarStyle.displayString = "XP Bar Style";
		xpBarWidth.displayString = "XP Bar Width";
		
		configureSkill.enabled = false;
		
		if (skill != null && skill.hasGui()) {
			configureSkill.enabled = true;
		}
		
		buttonList.add(showXP);
		buttonList.add(showIcon);
		buttonList.add(xpTextType);
		buttonList.add(xpBarPos);
		buttonList.add(xOffset);
		buttonList.add(yOffset);
		
		buttonList.add(xpBarStyle);
		buttonList.add(xpBarWidth);
		
		buttonList.add(configureSkill);
		buttonList.add(saveChanges);
		buttonList.add(cancelChanges);
		
		xpTextType.setValue(config.getXPTextType(slot));
		xpBarPos.setValue(config.getXPBarPos(slot));
		xOffset.setValue(config.getXPXOffset(slot));
		yOffset.setValue(config.getXPYOffset(slot));
		xpBarStyle.setValue(config.getXPBarStyle(slot));
		xpBarWidth.setValue(config.getXPBarWidth(slot));
	}
	
	@Override
	protected void keyTyped(char par1, int keyId) {
		if (keyId == Keyboard.KEY_ADD || keyId == Keyboard.KEY_RIGHT) {
			if (xpTextType.func_146115_a() && xpTextType.getValue() < xpTextType.maxValue) {
				xpTextType.setValue(xpTextType.getValue() + 1);
				xpTextType.displayString = xpTextType.getValueInt() + "";
			}
			if (xpBarPos.func_146115_a() && xpBarPos.getValue() < xpBarPos.maxValue)   {
				xpBarPos.setValue(xpBarPos.getValue() + 1);
				xpBarPos.displayString = xpBarPos.getValueInt() + "";
			}
			if (xOffset.func_146115_a() && xOffset.getValue() < xOffset.maxValue)    {
				xOffset.setValue(xOffset.getValue() + 1);
				xOffset.displayString = xOffset.getValueInt() + "";
			}
			if (yOffset.func_146115_a() && yOffset.getValue() < yOffset.maxValue)    {
				yOffset.setValue(yOffset.getValue() + 1);
				yOffset.displayString = yOffset.getValueInt() + "";
			}
			if (xpBarStyle.func_146115_a() && xpBarStyle.getValue() < xpBarStyle.maxValue)    {
				xpBarStyle.setValue(xpBarStyle.getValue() + 1);
				xpBarStyle.displayString = xpBarStyle.getValueInt() + "";
			}
			if (xpBarWidth.func_146115_a() && xpBarWidth.getValue() < xpBarWidth.maxValue)    {
				xpBarWidth.setValue(xpBarWidth.getValue() + 1);
				xpBarWidth.displayString = xpBarWidth.getValueInt() + "";
			}
		}
		if (keyId == Keyboard.KEY_SUBTRACT || keyId == Keyboard.KEY_LEFT) {
			if (xpTextType.func_146115_a() && xpTextType.getValue() > xpTextType.minValue) {
				xpTextType.setValue(xpTextType.getValue() - 1);
				xpTextType.displayString = xpTextType.getValueInt() + "";
			}
			if (xpBarPos.func_146115_a() && xpBarPos.getValue() > xpBarPos.minValue)   {
				xpBarPos.setValue(xpBarPos.getValue() - 1);
				xpBarPos.displayString = xpBarPos.getValueInt() + "";
			}
			if (xOffset.func_146115_a() && xOffset.getValue() > xOffset.minValue)    {
				xOffset.setValue(xOffset.getValue() - 1);
				xOffset.displayString = xOffset.getValueInt() + "";
			}
			if (yOffset.func_146115_a() && yOffset.getValue() > yOffset.minValue)    {
				yOffset.setValue(yOffset.getValue() - 1);
				yOffset.displayString = yOffset.getValueInt() + "";
			}
			if (xpBarStyle.func_146115_a() && xpBarStyle.getValue() > xpBarStyle.minValue)    {
				xpBarStyle.setValue(xpBarStyle.getValue() - 1);
				xpBarStyle.displayString = xpBarStyle.getValueInt() + "";
			}
			if (xpBarWidth.func_146115_a() && xpBarWidth.getValue() > xpBarWidth.minValue)    {
				xpBarWidth.setValue(xpBarWidth.getValue() - 1);
				xpBarWidth.displayString = xpBarWidth.getValueInt() + "";
			}			
		}
		super.keyTyped(par1, keyId);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseZ, float par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_BLEND);
		this.mc.getTextureManager().bindTexture(skillConfig); //Main skill pane
		int left = (this.width - this.xSize) / 2;
		int top  = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
		int xpX = 0;
		int xpY = 0;
		int pos = xpBarPos.getValueInt();
		//68
		if (pos <= 3) { xpY = 168; } else { xpY = 229; }
		
		if (pos == 1 || pos == 4) { xpX = 68; }
		if (pos == 2 || pos == 5) { xpX = 116; }
		if (pos == 3 || pos == 6) { xpX = 164; }
		
		xpX += ((int) (xOffset.getValue() / 7));
		xpY += ((int) (yOffset.getValue() / 7));
		
		if (showXPBar) {
			this.drawTexturedModalRect(left+xpX, top+xpY, 0, 248, 24, 4);
		}
		
		EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get((EntityPlayer) Minecraft.getMinecraft().thePlayer);
		

		SkillLevelBase skill = SkillLevelBase.getSkillByID(equippedSkills.getSkillInSlot(slot), mc.thePlayer);
		ResourceLocation icon;
		int iconX;
		int iconZ;
		int smallIconX;
		int smallIconZ;
		int iconOffset = 0;
		
		if (skill != null && skill.skillIcon() != null) {
			icon = skill.skillIcon();
			iconX = skill.iconX();
			iconZ = skill.iconZ();
			smallIconX = skill.iconX();
			smallIconZ = skill.iconZ() + 128;
		} else {
			icon = skillIcons;
			iconX = 0;
			iconZ = 220;
			smallIconX = 92;
			smallIconZ = 220;
		}
		if (skill != null) {
			mc.fontRenderer.drawString("Name: " + skill.skillName(), left + 43, top + 11, 4210752);
			mc.fontRenderer.drawString("Lvl: " + skill.getLevel(), left + 43, top + 21, 4210752);
			if (skill.canGainXP()) {
				mc.fontRenderer.drawString("XP: " + skill.getXPTotalForPrint(), left + 43, top + 30, 4210752);
			}
		}
		
		String tip = "Hold shift over buttons to see tips";
		mc.fontRenderer.drawString(tip, left+128-(RPGUtils.getStringLength(tip)/2), top+236, 4210752);
		mc.fontRenderer.drawString(saved, left+128-(RPGUtils.getStringLength(saved)/2), top+249, 16777215);
		
		if (showXPBar) {
			this.mc.getTextureManager().bindTexture(xpBars);
			int barStyle = xpBarStyle.getValueInt();
			int barWidth = xpBarWidth.getValueInt();
			int txtStyle = xpTextType.getValueInt();
			int leftPos = left+(xSize/2)-(barWidth/2);
			int barX = 0;
			int barY = 0;
			int iconBarX = 0;
			int iconBarY = 147;
			boolean rightAlign = false;
			int textOffset = 8;
			
			if (barStyle == 2 || barStyle == 4 || barStyle == 6 || barStyle == 8 || barStyle == 10 || barStyle == 12) { barX = 131; rightAlign = true;}
			
			if (barStyle == 3 || barStyle == 4)   { barY = 13; }
			if (barStyle == 5 || barStyle == 6)   { barY = 26; }
			if (barStyle == 7 || barStyle == 8)   { barY = 39; }
			if (barStyle == 9 || barStyle == 10)  { barY = 52; }
			if (barStyle == 11 || barStyle == 12) { barY = 65; }
			if (barStyle == 13)   				  { barY = 78; }
			
			if (barStyle == 1 || barStyle == 3 || barStyle == 5 || barStyle == 7) { iconBarX = leftPos - 8; }
			if (barStyle == 2 || barStyle == 4 || barStyle == 6 || barStyle == 8) { iconBarX = (leftPos) + (barWidth) - 8; }
			if (barStyle >= 9 && barStyle <= 13) { iconBarX = leftPos + (barWidth/2) - 8; }
			
			this.drawTexturedModalRect(leftPos,   top+149, barX, barY, 11, 12); //Bar Left
			this.drawTexturedModalRect(leftPos+11, top+149, barX+11, barY, barWidth-22, 12); //Bar
			this.drawTexturedModalRect(leftPos+barWidth-11, top+149, barX+114, barY, 11, 12); //Bar Right
			
			if (showIconBar) {
				this.mc.getTextureManager().bindTexture(icon);
				drawTexturedModalRect(iconBarX, top + iconBarY, smallIconX, smallIconZ, 16, 16); //small icon
				iconOffset = 8;
			}
			
			String text = "";
			if (skill != null) {
				if (txtStyle == 0) { text = skill.nameFormat() + "Lvl " + skill.getLevel(); }
				if (txtStyle == 1) { text = skill.nameFormat() + "Lvl " + skill.getLevel() + " (" + skill.getXPProgressForPrint() + ")"; }
				if (txtStyle == 2) { text = skill.nameFormat() + "Lvl " + skill.getLevel() + " (" + skill.getXPProgressAsPercentage() + ")"; }
				if (txtStyle == 3) { text = skill.nameFormat() + skill.skillName() + " - Lvl " + skill.getLevel(); }
				if (txtStyle == 4) { text = skill.nameFormat() + skill.skillName() + " - Lvl " + skill.getLevel() + " (" + skill.getXPProgressForPrint() + ")"; }
				if (txtStyle == 5) { text = skill.nameFormat() + skill.skillName() + " - Lvl " + skill.getLevel() + " (" + skill.getXPProgressAsPercentage() + ")"; }
				if (txtStyle == 6) { text = skill.nameFormat() + skill.shortName() + " - Lvl " + skill.getLevel(); }
				if (txtStyle == 7) { text = skill.nameFormat() + skill.shortName() + " - Lvl " + skill.getLevel() + " (" + skill.getXPProgressForPrint() + ")"; }
				if (txtStyle == 8) { text = skill.nameFormat() + skill.shortName() + " - Lvl " + skill.getLevel() + " (" + skill.getXPProgressAsPercentage() + ")"; }
				if (txtStyle == 9) { text = skill.nameFormat() + skill.skillName(); }
				if (txtStyle == 10) { text = skill.nameFormat() + skill.shortName(); }
				if (txtStyle == 11) { text = skill.nameFormat() + skill.getXPProgressForPrint(); }
				if (txtStyle == 12) { text = skill.nameFormat() + skill.getXPProgressAsPercentage(); }
			}
			if (rightAlign) { textOffset = barWidth-mc.fontRenderer.getStringWidth(text)-2-iconOffset; iconOffset = 0;; }
			mc.fontRenderer.drawString(text, left+128-(RPGUtils.getStringLength(text)/2), top + iconBarY + 4, 16777215);
		}

		this.mc.getTextureManager().bindTexture(icon);
		drawTexturedModalRect(left + 8, top + 8, iconX, iconZ, 30, 30);

		
		super.drawScreen(mouseX, mouseZ, par3);
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			if (showXP.func_146115_a()) {
				String[] str = {"\u00A7lShow XP Bar", "\u00A7oShows or hides the XP bar for this skill.", "\u00A7c\u00A7oIf hidden, the other settings here are ignored."};
				List temp = Arrays.asList(str);
				drawHoveringText(temp, mouseX, mouseZ, fontRendererObj);
			}
			
			if (xpTextType.func_146115_a()) {
				String[] str = {"\u00A7lXP Text Type", "\u00A7oToggle the text shown on the XP bar.", 
						"\u00A7e0: Only level is shown", 
						"\u00A7e1: Level plus CurrentXP/LevelXP", 
						"\u00A7e2: Level plus XP as a percentage (%)", 
						"\u00A7e3: Skill name plus 0", 
						"\u00A7e4: Skill name plus 1", 
						"\u00A7e5: Skill name plus 2", 
						"\u00A7e6: Skill shorthand plus 0", 
						"\u00A7e7: Skill shorthand plus 1", 
						"\u00A7e8: Skill shorthand plus 2", 
						"\u00A7e9: Just Skillname", 
						"\u00A7e10: Just shortname", 
						"\u00A7e11: Just CurrentXP/LevelXP",
						"\u00A7e12: Just XP as percentage",
						"\u00A7e13: No text at all.", 
						"\u00A7aUse +/- or L/R arrow keys for fine-tuning."};
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
				String[] str = {"\u00A7lSave Changes", "\u00A7oSaves changes to config and updates screen."};
				List temp = Arrays.asList(str);
				drawHoveringText(temp, mouseX, mouseZ, fontRendererObj);
			}
			
			if (cancelChanges.func_146115_a()) {
				String[] str = {"\u00A7lClose", "\u00A7oReturn to Skill Overview.", "\u00A7cDoes not save - press save first if", "\u00A7cyou wish to keep your changes!"};
				List temp = Arrays.asList(str);
				drawHoveringText(temp, mouseX, mouseZ, fontRendererObj);
			}
		}
	}
	
    @Override
    public void actionPerformed(GuiButton button) {
    	if (button.id != 10) {
    		saved = "";
    	}
    	switch(button.id) {
    	case 1:
    		System.out.println("click!");
    		showXPBar = !showXPBar;
    		showXP.displayString = showXPBar ? "XP Bar: Shown" : "XP Bar: Hidden";
    		break;
    	case 2:
    		System.out.println("click!");
    		showIconBar = !showIconBar;
    		showIcon.displayString = showIconBar ? "Icon: Shown" : "Icon: Hidden";
    		break;
    	case 9:
    		System.out.println("click! (config)");
    		EquippedSkills equippedSkills = (EquippedSkills) EquippedSkills.get((EntityPlayer) Minecraft.getMinecraft().thePlayer);
    		
    		if (equippedSkills != null) {
    			if (!equippedSkills.getSkillInSlot(slot).isEmpty()) {
    				SkillLevelBase skill = SkillLevelBase.getSkillByID(equippedSkills.getSkillInSlot(slot), mc.thePlayer);
    				
    				skill.openGui();
    			}
    		}
    		break;
    	case 10:
    		System.out.println("click! (save)");
    		config.setShowXp(slot, showXPBar);
    		config.setShowIcon(slot, showIconBar);
    		config.setXPTextType(slot, xpTextType.getValueInt());
    		config.setXPBarPos(slot, xpBarPos.getValueInt());
    		config.setXPXOffset(slot, xOffset.getValueInt());
    		config.setXPYOffset(slot, yOffset.getValueInt());
    		config.setXPBarStyle(slot, xpBarStyle.getValueInt());
    		config.setXPBarWidth(slot, xpBarWidth.getValueInt());
    		saved = "Config file has been saved!";
    		break;
    	case 11:
    		System.out.println("click! (cancel)");
    		Minecraft.getMinecraft().thePlayer.closeScreen();
    		RPGCore.network.sendToServer(new OpenGuiPacket(0));
    		break;
    	}
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
