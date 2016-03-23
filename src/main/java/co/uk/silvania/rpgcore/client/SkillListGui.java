package co.uk.silvania.rpgcore.client;

import org.lwjgl.opengl.GL11;

import co.uk.silvania.rpgcore.RPGCore;
import co.uk.silvania.rpgcore.RegisterSkill;
import co.uk.silvania.rpgcore.skills.EquippedSkills;
import co.uk.silvania.rpgcore.skills.SkillLevelBase;
import cpw.mods.fml.client.GuiScrollingList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class SkillListGui extends GuiScreen {
	
	public static final ResourceLocation skillList  = new ResourceLocation(RPGCore.MODID, "textures/gui/skilllist.png");
	
	int xSize = 256;
	int ySize = 256;
	
	GuiScrollingList list;
	
	public GuiButton buttonCancel;
	public GuiButton buttonConfirm;
	public GuiButton buttonDetails;
	
	@Override
	public void drawScreen(int mouseX, int mouseZ, float partialTick) {	
		this.drawDefaultBackground();
		list.drawScreen(mouseX, mouseZ, partialTick);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_BLEND);
		this.mc.getTextureManager().bindTexture(skillList); //Main skill pane
		int left = (this.width - this.xSize) / 2;
		int top  = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(left, top, 0, 0, this.xSize, this.ySize);
		
		
		
		super.drawScreen(mouseX, mouseZ, partialTick);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void initGui() {
		System.out.println("initGui");		
		int left = (this.width - this.xSize) / 2;
		int top  = (this.height - this.ySize) / 2;

		buttonCancel = new GuiButton(1, left+181, top+14, 68, 20, "Cancel");
		buttonConfirm = new GuiButton(2, left+27, top+224, 92, 20, "Confirm Selection");
		buttonDetails = new GuiButton(3, left+137, top+224, 92, 20, "Skill Details");
		
		buttonList.add(buttonCancel);
		buttonList.add(buttonConfirm);
		buttonList.add(buttonDetails);
		
		this.list = new SkillListScrollable(this, this.width, this.height, 256, 256);
		this.list.registerScrollButtons(this.buttonList, 7, 8);
		//buttons n stuff
	}
	
	private int selected = -1;
	private SkillLevelBase selectedSkill;
	
	public void selectModIndex(int index) {
        this.selected = index;
        if (index >= 0 && index <= RegisterSkill.skillList.size()) {
            this.selectedSkill = RegisterSkill.skillList.get(selected);
        } else {
            this.selectedSkill = null;
        }
        System.out.println("Selected skill: " + selectedSkill.skillName);
        //cachedLogo = null;
    }

    public boolean modIndexSelected(int index) {
        return index == selected;
    }

}
