package co.uk.silvania.rpgcore;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class SkillsContainer extends Container {
	
	public final IInventory inventory;
	
	public SkillsContainer(final EntityPlayer player) {
		inventory = player.inventory;
				
		for (int i = 0; i < 4; ++i) {
            final int k = i;
            this.addSlotToContainer(new Slot(inventory, inventory.getSizeInventory() - 1 - i, 53 + i * 18, 171) {
            	
                private static final String __OBFID = "CL_00001755";
                
                public int getSlotStackLimit() {
                    return 1;
                }
                
                public boolean isItemValid(ItemStack item) {
                    if (item == null) return false;
                    return item.getItem().isValidArmor(item, k, player);
                }
                
                @SideOnly(Side.CLIENT)
                public IIcon getBackgroundIconIndex() {
                    return ItemArmor.func_94602_b(k);
                }
            });
        }
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
		return null;
	}

}
