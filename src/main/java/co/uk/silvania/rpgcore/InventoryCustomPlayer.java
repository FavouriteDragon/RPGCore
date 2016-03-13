package co.uk.silvania.rpgcore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryCustomPlayer implements IInventory {
	
	private final String name = "Skills";
	private final String tagName = "RPGCore_Skills";
	
	public static final int INV_SIZE = 3; //TODO decide how big it'll be.
	private ItemStack[] inventory = new ItemStack[INV_SIZE];
	
	public InventoryCustomPlayer(){}
	
	public void copy(InventoryCustomPlayer inv) {
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack item = inv.getStackInSlot(i);
			inventory[i] = (item == null ? null : item.copy());
		}
		markDirty();
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize > amount) {
				stack = stack.splitStack(amount);
				markDirty();
			} else {
				setInventorySlotContents(slot, null);
			}
		}

		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack item = getStackInSlot(slot);
		setInventorySlotContents(slot, null);
		return item;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack item) {
		inventory[slot] = item;
		if (item != null && item.stackSize > getInventoryStackLimit()) {
			item.stackSize = getInventoryStackLimit();
		}
		markDirty();
	}


	@Override
	public void markDirty() {
		for (int i = 0; i < getSizeInventory(); ++i) {
			if (getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0) {
				inventory[i] = null;
			}
		}
	}
	
	//All one-line returns, this is tidier for me. Sue me.
	@Override public int getSizeInventory() { 				return inventory.length; }
	@Override public ItemStack getStackInSlot(int slot) { 	return inventory[slot]; }
	@Override public String getInventoryName() { 			return name; }
	@Override public boolean hasCustomInventoryName() { 	return name.length() > 0; }
	@Override public int getInventoryStackLimit() { 		return 1; }
	@Override public boolean isUseableByPlayer(EntityPlayer player) { return true; }
	@Override public void openInventory() {}
	@Override public void closeInventory() {}
	@Override public boolean isItemValidForSlot(int slot, ItemStack item) { return true; }

	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagList items = new NBTTagList();
		for (int i = 0; i < getSizeInventory(); ++i) {
			if (getStackInSlot(i) != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte) i);
				getStackInSlot(i).writeToNBT(item);
				items.appendTag(item);
			}
		}
		nbt.setTag(tagName, items);
	}

	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList items = nbt.getTagList(tagName, nbt.getId());
		for (int i = 0; i < items.tagCount(); ++i) {
			NBTTagCompound item = items.getCompoundTagAt(i);
			byte slot = item.getByte("Slot");
			if (slot >= 0 && slot < getSizeInventory()) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(item);
			}
		}
	}
}
