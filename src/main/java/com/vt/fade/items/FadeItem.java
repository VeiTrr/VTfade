package com.vt.fade.items;


import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;


public class FadeItem extends Item {
	
	public FadeItem() {
        super(new FabricItemSettings().group(ItemGroup.MISC).maxCount(64));
    }

}
