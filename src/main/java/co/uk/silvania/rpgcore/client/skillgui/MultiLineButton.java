package co.uk.silvania.rpgcore.client.skillgui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;

public class MultiLineButton extends GuiButton {

	public MultiLineButton(int id, int posX, int posZ, int sizeX, int sizeZ, String text) {
		super(id, posX, posZ, sizeX, sizeZ, text);
	}
	
	
	//Cloned from GuiButton, I just needed to make the text multi-line.
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseZ) {
        if (this.visible) {
            FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(buttonTextures);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = mouseX >= this.xPosition && mouseZ >= this.yPosition && mouseX < this.xPosition + this.width && mouseZ < this.yPosition + this.height;
            int k = this.getHoverState(this.field_146123_n);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            
            int h = this.height - 2;
            int offset = 1;
            
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, 1);
        	this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, 1);
            while (h > 16) {
            	this.drawTexturedModalRect(this.xPosition, this.yPosition + offset, 0, 47 + k * 20, this.width / 2, 16);
            	this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition + offset, 200 - this.width / 2, 47 + k * 20, this.width / 2, 16);
            	h -= 16;
            	offset += 16;
            }
            this.drawTexturedModalRect(this.xPosition, this.yPosition + offset, 0, (47 + k * 20)+16-h, this.width / 2, h);
        	this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition + offset, 200 - this.width / 2, (47 + k * 20)+16-h, this.width / 2, h);
        	offset += h;
        	
        	this.drawTexturedModalRect(this.xPosition, this.yPosition + offset, 0, 63 + k * 20, this.width / 2, 3);
        	this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition + offset, 200 - this.width / 2, 63 + k * 20, this.width / 2, 3);
        	
            this.mouseDragged(mc, mouseX, mouseZ);
            int l = 14737632;

            if (packedFGColour != 0) {
                l = packedFGColour;
            } else if (!this.enabled) {
                l = 10526880;
            } else if (this.field_146123_n) {
                l = 16777120;
            }
            
            int nl = 1;
            
            for (int i = 0; i < this.displayString.length(); i++) {
            	if (this.displayString.charAt(i) == '#') {
            		nl++;
            	}
	            for (int j = 0; j < nl; j++) {
	            	String[] lines = this.displayString.split("#");
	            	this.drawCenteredString(fontrenderer, lines[j], this.xPosition + this.width / 2, this.yPosition + (this.height - 16) / 2 + (j * 9), l);
	            }
            }
        }
    }
	
	

}
