package com.vt.fade.blocks;

import com.vt.fade.entities.FadeBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
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
    private static void primeSpawn(World world, BlockPos pos) {
        final PigEntity pig1 = new PigEntity(EntityType.PIG, world);
        final PigEntity pig2 = new PigEntity(EntityType.PIG, world);
        final PigEntity pig3 = new PigEntity(EntityType.PIG, world);
        final PigEntity pig4 = new PigEntity(EntityType.PIG, world);
        pig1.setPos(pos.getX() + 1, pos.getY() + 0.5, pos.getZ() + 1);
        world.spawnEntity(pig1);
        pig1.setNoGravity(true);
        pig1.setHealth(0.5F);
        pig1.wakeUp();
        pig2.setPos(pos.getX() - 1, pos.getY() + 0.5, pos.getZ() + 1);
        world.spawnEntity(pig2);
        pig2.setNoGravity(true);
        pig2.setHealth(0.5F);
        pig2.wakeUp();
        pig3.setPos(pos.getX() + 1, pos.getY() + 0.5, pos.getZ() - 1);
        world.spawnEntity(pig3);
        pig3.setNoGravity(true);
        pig3.setHealth(0.5F);
        pig3.wakeUp();
        pig4.setPos(pos.getX() - 1, pos.getY() + 0.5, pos.getZ() - 1);
        world.spawnEntity(pig4);
        pig4.setNoGravity(true);
        pig4.setHealth(0.5F);
        pig4.wakeUp();
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(4100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (pig1.isAlive() || pig2.isAlive() || pig3.isAlive() || pig4.isAlive()) {
                pig1.kill();
                pig2.kill();
                pig3.kill();
                pig4.kill();
            }
        });
        thread.start();

    }

    private static void primeFadeBlock(World world, BlockPos pos, @Nullable LivingEntity igniter) {
        if (!world.isClient) {
            FadeBlockEntity tntEntity = new FadeBlockEntity(world, (double) pos.getX() + 0.5, pos.getY(), (double) pos.getZ() + 0.5, igniter);
            world.spawnEntity(tntEntity);
            world.playSound(null, tntEntity.getX(), tntEntity.getY(), tntEntity.getZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent(igniter, GameEvent.PRIME_FUSE, pos);
            primeSpawn(world, pos);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player2, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player2.getStackInHand(hand);
        if (itemStack.isOf(Items.FLINT_AND_STEEL) || itemStack.isOf(Items.FIRE_CHARGE)) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            primeFadeBlock(world, pos, player2);
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            Item item = itemStack.getItem();
            if (!player2.isCreative()) {
                if (itemStack.isOf(Items.FLINT_AND_STEEL)) {
                    itemStack.damage(1, player2, player -> player.sendToolBreakStatus(hand));
                } else {
                    itemStack.decrement(1);
                }
            }
            player2.incrementStat(Stats.USED.getOrCreateStat(item));
            return ActionResult.success(world.isClient);
        }
        return super.onUse(state, world, pos, player2, hand, hit);
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
            primeSpawn(world, pos);
        }
    }
}
