package co.uk.silvania.rpgcore.client;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import co.uk.silvania.rpgcore.RPGCore;
import co.uk.silvania.rpgcore.RegisterSkill;
import co.uk.silvania.rpgcore.network.EquipNewSkillPacket;
import co.uk.silvania.rpgcore.network.OpenGuiPacket;
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
	public GuiButton buttonClear;
	
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
		
		mc.fontRenderer.drawString("Selecting skill for slot " + SkillSelectGui.slotClicked, left+9, top+9, 4210752);
		
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
		buttonClear = new GuiButton(4, left+9, top+23, 92, 20, "Clear Slot");
		
		buttonList.add(buttonCancel);
		buttonList.add(buttonConfirm);
		buttonList.add(buttonDetails);
		buttonList.add(buttonClear);
		
		buttonDetails.enabled = false;
		
		this.list = new SkillListScrollable(this, this.width, this.height, 256, 256);
		this.list.registerScrollButtons(this.buttonList, 7, 8);
		//buttons n stuff
	}
	
	private int selected = -1;
	private SkillLevelBase selectedSkill;
	ArrayList<SkillLevelBase> skillLevelList = RegisterSkill.skillList;
	
	public void selectModIndex(int index) {
        this.selected = index;
        if (index >= 0 && index <= RegisterSkill.skillList.size()) {
            this.selectedSkill = RegisterSkill.skillList.get(selected);
        } else {
            this.selectedSkill = null;
        }
        
        buttonDetails.enabled = selectedSkill.hasGui;
        
        System.out.println("Selected skill: " + selectedSkill.skillName);
        //cachedLogo = null;
    }

    public boolean modIndexSelected(int index) {
        return index == selected;
    }
    
    @Override
    public void actionPerformed(GuiButton button) {
    	switch(button.id) {
    	case 1:
    		RPGCore.network.sendToServer(new OpenGuiPacket(0));
    		break;
    	case 2:
    		if (selected >= 0) {
	    		RPGCore.network.sendToServer(new EquipNewSkillPacket(SkillSelectGui.slotClicked, skillLevelList.get(selected).skillId));
				RPGCore.network.sendToServer(new OpenGuiPacket(0));
    		}
    		break;
    	case 3:
    		this.selectedSkill.openGui();
    		break;
    	case 4:
    		RPGCore.network.sendToServer(new EquipNewSkillPacket(SkillSelectGui.slotClicked, ""));
			RPGCore.network.sendToServer(new OpenGuiPacket(0));
			break;
    	}
    }

}
