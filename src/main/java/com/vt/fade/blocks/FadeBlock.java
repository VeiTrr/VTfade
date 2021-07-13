package com.vt.fade.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class FadeBlock extends Block {

    public FadeBlock() {
        super(FabricBlockSettings.copy(Blocks.TNT));
    }
}
