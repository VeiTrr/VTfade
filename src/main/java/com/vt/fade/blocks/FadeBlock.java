package com.vt.fade.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class FadeBlock extends Block {

    public FadeBlock() {
        super(FabricBlockSettings.copy(Blocks.TNT));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getMainHandStack();
        if(itemStack.getItem() .equals(Items.FLINT_AND_STEEL)) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            world.createExplosion(player,pos.getX(),pos.getY(),pos.getZ(),10F, Explosion.DestructionType.BREAK);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
