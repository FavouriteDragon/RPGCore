/*
 * Forge Mod Loader
 * Copyright (c) 2012-2013 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     cpw - implementation
 *     
 *     Class extended and modified by Flenix to allow tooltips when hovering over elements.
 *     Only private/required things are kept. Unfortunately the whole drawScreen method had to be cloned to avoid render issues.
 *     #IHatePrivateAccessLevelModifiers
 */

package co.uk.silvania.rpgcore.client.skillgui;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumChatFormatting;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.GuiScrollingList;

public abstract class GuiScrollingList_Mod extends GuiScrollingList {
	
    private final Minecraft client;
    protected final int listWidth;
    protected final int listHeight;
    protected final int top;
    protected final int bottom;
    private final int right;
    protected final int left;
    protected final int slotHeight;
    private int scrollUpActionId;
    private int scrollDownActionId;
    protected int mouseX;
    protected int mouseY;
    private float initialMouseClickY = -2.0F;
    private float scrollFactor;
    private float scrollDistance;
    private int selectedIndex = -1;
    private long lastClickTime = 0L;
    private boolean field_25123_p = true;
    private boolean field_27262_q;
    private int field_27261_r;
    protected FontRenderer fontRendererObj;
    
    public int hoverElement;

    public GuiScrollingList_Mod(Minecraft client, int width, int height, int top, int bottom, int left, int entryHeight) {
    	super(client, width, height, top, bottom, left, entryHeight);
        this.client = client;
        this.listWidth = width;
        this.listHeight = height;
        this.top = top;
        this.bottom = bottom;
        this.slotHeight = entryHeight;
        this.left = left;
        this.right = width + this.left;
    }

    private void applyScrollLimits()  {
        int var1 = this.getContentHeight() - (this.bottom - this.top - 4);
        if (var1 < 0) { var1 /= 2; }
        if (this.scrollDistance < 0.0F) { this.scrollDistance = 0.0F; }
        if (this.scrollDistance > (float)var1) { this.scrollDistance = (float)var1; }
    }

    public void drawScreen(int mouseX, int mouseY, float p_22243_3_) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.drawBackground();
        int listLength = this.getSize();
        int scrollBarXStart = this.left + this.listWidth - 6;
        int scrollBarXEnd = scrollBarXStart + 6;
        int boxLeft = this.left;
        int boxRight = scrollBarXStart-1;
        int var10;
        int var11;
        int var13;
        int var19;
        
        var10 = mouseY - this.top - this.field_27261_r + (int)this.scrollDistance - 4;
        var11 = var10 / this.slotHeight;

        //Tells the GUI which element we're hovering over, for tooltip rendering.
        if (mouseX >= boxLeft && mouseX <= boxRight && var11 >= 0 && var10 >= 0 && var11 < listLength) {
        	hoverElement = var11;
        }

        if (Mouse.isButtonDown(0)) {
            if (this.initialMouseClickY == -1.0F) {
                boolean var7 = true;

                if (mouseY >= this.top && mouseY <= this.bottom) {
                    var10 = mouseY - this.top - this.field_27261_r + (int)this.scrollDistance - 4;
                    var11 = var10 / this.slotHeight;

                    if (mouseX >= boxLeft && mouseX <= boxRight && var11 >= 0 && var10 >= 0 && var11 < listLength) {
                        boolean var12 = var11 == this.selectedIndex && System.currentTimeMillis() - this.lastClickTime < 250L;
                        this.elementClicked(var11, var12);
                        this.selectedIndex = var11;
                        this.lastClickTime = System.currentTimeMillis();
                    } else if (mouseX >= boxLeft && mouseX <= boxRight && var10 < 0) {
                        this.func_27255_a(mouseX - boxLeft, mouseY - this.top + (int)this.scrollDistance - 4);
                        var7 = false;
                    }

                    if (mouseX >= scrollBarXStart && mouseX <= scrollBarXEnd) {
                        this.scrollFactor = -1.0F;
                        var19 = this.getContentHeight() - (this.bottom - this.top - 4);

                        if (var19 < 1) {
                            var19 = 1;
                        }

                        var13 = (int)((float)((this.bottom - this.top) * (this.bottom - this.top)) / (float)this.getContentHeight());

                        if (var13 < 32) {
                            var13 = 32;
                        }

                        if (var13 > this.bottom - this.top - 8) {
                            var13 = this.bottom - this.top - 8;
                        }

                        this.scrollFactor /= (float)(this.bottom - this.top - var13) / (float)var19;
                    } else {
                        this.scrollFactor = 1.0F;
                    }

                    if (var7) {
                        this.initialMouseClickY = (float)mouseY;
                    } else {
                        this.initialMouseClickY = -2.0F;
                    }
                } else {
                    this.initialMouseClickY = -2.0F;
                }
            } else if (this.initialMouseClickY >= 0.0F) {
                this.scrollDistance -= ((float)mouseY - this.initialMouseClickY) * this.scrollFactor;
                this.initialMouseClickY = (float)mouseY;
            }
        } else {
            while (Mouse.next()) {
                int var16 = Mouse.getEventDWheel();

                if (var16 != 0) {
                    if (var16 > 0) {
                        var16 = -1;
                    } else if (var16 < 0) {
                        var16 = 1;
                    }

                    this.scrollDistance += (float)(var16 * this.slotHeight / 2);
                }
            }

            this.initialMouseClickY = -1.0F;
        }

        this.applyScrollLimits();
        Tessellator var18 = Tessellator.instance;
        if (this.client.theWorld != null) {
            this.drawGradientRect(this.left, this.top, this.right, this.bottom, -1072689136, -804253680);
        } else {
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_FOG);
            this.client.renderEngine.bindTexture(Gui.optionsBackground);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            float var17 = 32.0F;
            var18.startDrawingQuads();
            var18.setColorOpaque_I(2105376);
            var18.addVertexWithUV((double)this.left, (double)this.bottom, 0.0D, (double)((float)this.left / var17), (double)((float)(this.bottom + (int)this.scrollDistance) / var17));
            var18.addVertexWithUV((double)this.right, (double)this.bottom, 0.0D, (double)((float)this.right / var17), (double)((float)(this.bottom + (int)this.scrollDistance) / var17));
            var18.addVertexWithUV((double)this.right, (double)this.top, 0.0D, (double)((float)this.right / var17), (double)((float)(this.top + (int)this.scrollDistance) / var17));
            var18.addVertexWithUV((double)this.left, (double)this.top, 0.0D, (double)((float)this.left / var17), (double)((float)(this.top + (int)this.scrollDistance) / var17));
            var18.draw();
        }
        //boxRight = this.listWidth / 2 - 92 - 16;
        var10 = this.top + 4 - (int)this.scrollDistance;

        if (this.field_27262_q) {
            this.func_27260_a(boxRight, var10, var18);
        }

        int var14;

        for (var11 = 0; var11 < listLength; ++var11) {
            var19 = var10 + var11 * this.slotHeight + this.field_27261_r;
            var13 = this.slotHeight - 4;

            if (var19 <= this.bottom && var19 + var13 >= this.top) {
                if (this.field_25123_p && this.isSelected(var11)) {
                    var14 = boxLeft;
                    int var15 = boxRight;
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    var18.startDrawingQuads();
                    var18.setColorOpaque_I(8421504);
                    var18.addVertexWithUV((double)var14, (double)(var19 + var13 + 2), 0.0D, 0.0D, 1.0D);
                    var18.addVertexWithUV((double)var15, (double)(var19 + var13 + 2), 0.0D, 1.0D, 1.0D);
                    var18.addVertexWithUV((double)var15, (double)(var19 - 2), 0.0D, 1.0D, 0.0D);
                    var18.addVertexWithUV((double)var14, (double)(var19 - 2), 0.0D, 0.0D, 0.0D);
                    var18.setColorOpaque_I(0);
                    var18.addVertexWithUV((double)(var14 + 1), (double)(var19 + var13 + 1), 0.0D, 0.0D, 1.0D);
                    var18.addVertexWithUV((double)(var15 - 1), (double)(var19 + var13 + 1), 0.0D, 1.0D, 1.0D);
                    var18.addVertexWithUV((double)(var15 - 1), (double)(var19 - 1), 0.0D, 1.0D, 0.0D);
                    var18.addVertexWithUV((double)(var14 + 1), (double)(var19 - 1), 0.0D, 0.0D, 0.0D);
                    var18.draw();
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                }

                this.drawSlot(var11, boxRight, var19, var13, var18);
            }
        }

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        byte var20 = 4;
        
        if (this.client.theWorld == null) {
            this.overlayBackground(0, this.top, 255, 255);
            this.overlayBackground(this.bottom, this.listHeight, 255, 255);
        }
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        var18.startDrawingQuads();
        var18.setColorRGBA_I(0, 0);
        var18.addVertexWithUV((double)this.left, (double)(this.top + var20), 0.0D, 0.0D, 1.0D);
        var18.addVertexWithUV((double)this.right, (double)(this.top + var20), 0.0D, 1.0D, 1.0D);
        var18.setColorRGBA_I(0, 255);
        var18.addVertexWithUV((double)this.right, (double)this.top, 0.0D, 1.0D, 0.0D);
        var18.addVertexWithUV((double)this.left, (double)this.top, 0.0D, 0.0D, 0.0D);
        var18.draw();
        var18.startDrawingQuads();
        var18.setColorRGBA_I(0, 255);
        var18.addVertexWithUV((double)this.left, (double)this.bottom, 0.0D, 0.0D, 1.0D);
        var18.addVertexWithUV((double)this.right, (double)this.bottom, 0.0D, 1.0D, 1.0D);
        var18.setColorRGBA_I(0, 0);
        var18.addVertexWithUV((double)this.right, (double)(this.bottom - var20), 0.0D, 1.0D, 0.0D);
        var18.addVertexWithUV((double)this.left, (double)(this.bottom - var20), 0.0D, 0.0D, 0.0D);
        var18.draw();
        var19 = this.getContentHeight() - (this.bottom - this.top - 4);

        if (var19 > 0) {
            var13 = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();

            if (var13 < 32) {
                var13 = 32;
            }

            if (var13 > this.bottom - this.top - 8) {
                var13 = this.bottom - this.top - 8;
            }

            var14 = (int)this.scrollDistance * (this.bottom - this.top - var13) / var19 + this.top;

            if (var14 < this.top) {
                var14 = this.top;
            }

            var18.startDrawingQuads();
            var18.setColorRGBA_I(0, 255);
            var18.addVertexWithUV((double)scrollBarXStart, (double)this.bottom, 0.0D, 0.0D, 1.0D);
            var18.addVertexWithUV((double)scrollBarXEnd, (double)this.bottom, 0.0D, 1.0D, 1.0D);
            var18.addVertexWithUV((double)scrollBarXEnd, (double)this.top, 0.0D, 1.0D, 0.0D);
            var18.addVertexWithUV((double)scrollBarXStart, (double)this.top, 0.0D, 0.0D, 0.0D);
            var18.draw();
            var18.startDrawingQuads();
            var18.setColorRGBA_I(8421504, 255);
            var18.addVertexWithUV((double)scrollBarXStart, (double)(var14 + var13), 0.0D, 0.0D, 1.0D);
            var18.addVertexWithUV((double)scrollBarXEnd, (double)(var14 + var13), 0.0D, 1.0D, 1.0D);
            var18.addVertexWithUV((double)scrollBarXEnd, (double)var14, 0.0D, 1.0D, 0.0D);
            var18.addVertexWithUV((double)scrollBarXStart, (double)var14, 0.0D, 0.0D, 0.0D);
            var18.draw();
            var18.startDrawingQuads();
            var18.setColorRGBA_I(12632256, 255);
            var18.addVertexWithUV((double)scrollBarXStart, (double)(var14 + var13 - 1), 0.0D, 0.0D, 1.0D);
            var18.addVertexWithUV((double)(scrollBarXEnd - 1), (double)(var14 + var13 - 1), 0.0D, 1.0D, 1.0D);
            var18.addVertexWithUV((double)(scrollBarXEnd - 1), (double)var14, 0.0D, 1.0D, 0.0D);
            var18.addVertexWithUV((double)scrollBarXStart, (double)var14, 0.0D, 0.0D, 0.0D);
            var18.draw();
        }

        this.func_27257_b(mouseX, mouseY);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
    }

    private void overlayBackground(int p_22239_1_, int p_22239_2_, int p_22239_3_, int p_22239_4_) {
        Tessellator var5 = Tessellator.instance;
        this.client.renderEngine.bindTexture(Gui.optionsBackground);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float var6 = 32.0F;
        var5.startDrawingQuads();
        var5.setColorRGBA_I(4210752, p_22239_4_);
        var5.addVertexWithUV(0.0D, (double)p_22239_2_, 0.0D, 0.0D, (double)((float)p_22239_2_ / var6));
        var5.addVertexWithUV((double)this.listWidth + 30, (double)p_22239_2_, 0.0D, (double)((float)(this.listWidth + 30) / var6), (double)((float)p_22239_2_ / var6));
        var5.setColorRGBA_I(4210752, p_22239_3_);
        var5.addVertexWithUV((double)this.listWidth + 30, (double)p_22239_1_, 0.0D, (double)((float)(this.listWidth + 30) / var6), (double)((float)p_22239_1_ / var6));
        var5.addVertexWithUV(0.0D, (double)p_22239_1_, 0.0D, 0.0D, (double)((float)p_22239_1_ / var6));
        var5.draw();
    }
}