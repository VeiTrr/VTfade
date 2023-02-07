package com.vt.fade.blocks;

import com.vt.fade.entities.FadeBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class FadeBlock extends Block {

    public FadeBlock() {
        super(FabricBlockSettings.copy(Blocks.TNT));
    }

    public static void primeFadeBlock(World world, BlockPos pos) {
        primeFadeBlock(world, pos, null);
    }

    private static void primeFadeBlock(World world, BlockPos pos, @Nullable LivingEntity igniter) {
        if (!world.isClient) {
            FadeBlockEntity tntEntity = new FadeBlockEntity(world, (double) pos.getX() + 0.5, pos.getY(), (double) pos.getZ() + 0.5, igniter);
            world.spawnEntity(tntEntity);
            world.playSound(null, tntEntity.getX(), tntEntity.getY(), tntEntity.getZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent(igniter, GameEvent.PRIME_FUSE, pos);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getMainHandStack();
        if (itemStack.getItem().equals(Items.FLINT_AND_STEEL) || itemStack.getItem().equals(Items.FIRE_CHARGE)) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            primeFadeBlock(world, pos, player);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isReceivingRedstonePower(pos)) {
            primeFadeBlock(world, pos);
            world.removeBlock(pos, false);
        }
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }

    @Override
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        if (!world.isClient) {
            FadeBlockEntity tntEntity = new FadeBlockEntity(world, (double) pos.getX() + 0.5, pos.getY(), (double) pos.getZ() + 0.5, explosion.getCausingEntity());
            int i = tntEntity.getFuse();
            tntEntity.setFuse((short) (world.random.nextInt(i / 4) + i / 8));
            world.spawnEntity(tntEntity);
        }
    }
}
