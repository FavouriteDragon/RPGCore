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

public class GlobalLevelConfig extends GuiScreen {
	
	public MultiLineButton showGlobalXP;
	public MultiLineButton globalLevelTop;
	public MultiLineButton showGlobalLevel;
	public MultiLineButton globalLevelLeft;
	
	GuiConfig config = new GuiConfig();
	String saved = "";
	
	public MultiLineButton saveChanges;
	public MultiLineButton cancelChanges;
	
	boolean showGXP;
	boolean glTop;
	boolean showGL;
	boolean glLeft;

	public static final ResourceLocation blankGui = new ResourceLocation(RPGCore.MODID, "textures/gui/blankgui.png");
	
	int xSize = 256;
	int ySize = 120;
	
	@Override
	public void initGui() {
		super.initGui();
		
		saved = "";
		
		int left = (this.width - this.xSize) / 2;
		int top  = (this.height - this.ySize) / 2;
		
		showGXP = config.showGlobalXp;
		glTop = config.globalXpTop;
		showGL = config.showGlobalLevel;
		glLeft = config.globalLevelLeft;

		String showGXPStr = showGXP ? "Bar:#Shown" : "Bar:#Hidden";
		String glTopStr = glTop ? "Bar Position:#Top" : "Bar Position:#Bottom";
		String showGLStr = showGL ? "Level Text:#Shown" : "Level Text:#Hidden";
		String glLeftStr = glLeft ? "Level Position:#Left" : "Level Position:#Right";
		
		showGlobalXP    = new MultiLineButton(1, left+7,   top+21, 119, 24, showGXPStr);
		globalLevelTop  = new MultiLineButton(2, left+7,   top+49, 119, 24, glTopStr);
		showGlobalLevel = new MultiLineButton(3, left+130, top+21, 119, 24, showGLStr);
		globalLevelLeft = new MultiLineButton(4, left+130, top+49, 119, 24, glLeftStr);
		
		saveChanges =    new MultiLineButton(5, left+7,   top+81, 119, 24, "Save Changes");
		cancelChanges =  new MultiLineButton(6, left+130, top+81, 119, 24, "Close");
		
		buttonList.add(showGlobalXP);
		buttonList.add(globalLevelTop);
		buttonList.add(showGlobalLevel);
		buttonList.add(globalLevelLeft);

		buttonList.add(saveChanges);
		buttonList.add(cancelChanges);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseZ, float par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_BLEND);
		this.mc.getTextureManager().bindTexture(blankGui); //Main skill pane
		int left = (this.width - this.xSize) / 2;
		int top  = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(left, top, 0, 0, this.xSize, 3);
		this.drawTexturedModalRect(left, top+3, 0, 39, this.xSize, this.ySize-7);
		this.drawTexturedModalRect(left, top+ySize-4, 0, 252, this.xSize, 4);
		
		super.drawScreen(mouseX, mouseZ, par3);
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			if (showGlobalXP.func_146115_a()) {
				String[] str = {"\u00A7lShow XP Bar", "\u00A7oShows or hides the XP", "\u00A7obar for Global Level", " ", "\u00A7c\u00A7oIf hidden, the other settings", "\u00A7c\u00A7ohere are ignored."};
				List temp = Arrays.asList(str);
				drawHoveringText(temp, mouseX, mouseZ, fontRendererObj);
			}
			
			if (globalLevelTop.func_146115_a()) {
				String[] str = {"\u00A7lGlobal Level Position", "\u00A7oToggle the position of the bar.", " ", "\u00A7eIt spans the screen - either", "\u00A7ealong the top or bottom."};
				List temp = Arrays.asList(str);
				drawHoveringText(temp, mouseX, mouseZ, fontRendererObj);
			}
			
			if (showGlobalLevel.func_146115_a()) {
				String[] str = {"\u00A7lShow Level", "\u00A7oWhether or not the level is shown", "\u00A7oon the bar as text."};
				List temp = Arrays.asList(str);
				drawHoveringText(temp, mouseX, mouseZ, fontRendererObj);
			}
	
			if (globalLevelLeft.func_146115_a()) {
				String[] str = {"\u00A7lLevel Position", "\u00A7oToggle the Level text being on the ", "\u00A7oleft- or right-hand side of the bar."};
				List temp = Arrays.asList(str);
				drawHoveringText(temp, mouseX, mouseZ, fontRendererObj);
			}
			
			if (saveChanges.func_146115_a()) {
				String[] str = {"\u00A7lSave Changes", "\u00A7oSave changes to config and update screen."};
				List temp = Arrays.asList(str);
				drawHoveringText(temp, mouseX, mouseZ, fontRendererObj);
			}
			
			if (cancelChanges.func_146115_a()) {
				String[] str = {"\u00A7lCancel Changes", "\u00A7oReturn to Skill Overview.", "\u00A7cDoes not save - press save first if", "\u00A7cyou wish to keep your changes!"};
				List temp = Arrays.asList(str);
				drawHoveringText(temp, mouseX, mouseZ, fontRendererObj);
			}
		}
		String title = "Global Configuration";
		mc.fontRenderer.drawString(title, left+128-(mc.fontRenderer.getStringWidth(title)/2), top+7, 4210752);
		
		String tip = "Hold shift over buttons to see tips";
		mc.fontRenderer.drawString(tip, left+128-(mc.fontRenderer.getStringWidth(tip)/2), top+108, 4210752);
		mc.fontRenderer.drawString(saved, left+128-(mc.fontRenderer.getStringWidth(saved)/2), top+121, 4210752);
	}
	
    @Override
    public void actionPerformed(GuiButton button) {
    	if (button.id != 5) {
    		saved = "";
    	}
    	switch(button.id) {    	
    	case 1:
    		System.out.println("click!");
    		showGXP = !showGXP;
    		showGlobalXP.displayString = showGXP ? "Bar:#Shown" : "Bar:#Hidden";
    		break;
    	case 2:
    		System.out.println("click!");
    		glTop = !glTop;
    		globalLevelTop.displayString = glTop ? "Bar Position:#Top" : "Bar Position:#Bottom";
    		break;
    	case 3:
    		System.out.println("click!");
    		showGL = !showGL;
    		showGlobalLevel.displayString = showGL ? "Level Text:#Shown" : "Level Text:#Hidden";
    		break;
    	case 4:
    		System.out.println("click!");
    		glLeft = !glLeft;
    		globalLevelLeft.displayString = glLeft ? "Level Position:#Left" : "Level Position:#Right";
    		break;
    	case 5:
    		System.out.println("click! (save)");
    		config.setGlobalSettings(showGXP, glTop, showGL, glLeft);
    		saved = "Config file has been saved!";
    		break;
    	case 6:
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
