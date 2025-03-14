package net.jammydodger101.candlelight.item.custom;

import net.jammydodger101.candlelight.block.custom.PlayerCandleBlock;
import net.jammydodger101.candlelight.component.ModDataComponentTypes;
import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/*
A compass that points to a specified player.
Specified player is set when right-clicking on their respective candle.
 */

public class PlayerCompassItem extends Item {

    // list of players names
    public static List<String> playerList = new ArrayList<>();

    public PlayerCompassItem(Settings settings) {
        super(settings);
    }

    // returns true if the playerList contains the target player
    public static boolean hasPlayer(ItemStack stack) {
        if (stack.get(ModDataComponentTypes.TARGET_PLAYER) != null) {
            return playerList.contains(stack.get(ModDataComponentTypes.TARGET_PLAYER));
        }
        return false;
    }

    public static boolean isPlayerOnline(String playerName, World world) {
        for (PlayerEntity player : world.getPlayers()) {
            if (player.getNameForScoreboard().toLowerCase().equals(playerName)) {
                return true;
            }
        }
        return false;
    }

    // returns the players position
    @Nullable
    public static GlobalPos createPlayerPos(ItemStack stack, World world) {
        // makes sure the data component actually has a player its targeting
        if (stack.get(ModDataComponentTypes.TARGET_PLAYER) != null) {
            String targetPlayer = stack.get(ModDataComponentTypes.TARGET_PLAYER);
            // checks the player is online (cant track a player thats offline)
            if (isPlayerOnline(targetPlayer, world)) {
                // negates an error (UNTESTED IN MULTIPLAYER)
                if (world.getPlayers().size() > playerList.indexOf(targetPlayer)) {
                    PlayerEntity targetPlayerEntity = world.getPlayers().get(playerList.indexOf(targetPlayer));
                    BlockPos playerPos = targetPlayerEntity.getBlockPos();
                    return GlobalPos.create(world.getRegistryKey(), playerPos);
                }

            }
        }
        return null;
    }

    // returns spawn pos
    @Nullable
    public static GlobalPos createSpawnPos(World world) {
        return world.getDimension().natural() ? GlobalPos.create(world.getRegistryKey(), world.getSpawnPos()) : null;
    }

    // gives compass glint if data component has player
    @Override
    public boolean hasGlint(ItemStack stack) {
        return PlayerCompassItem.hasPlayer(stack) || super.hasGlint(stack);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (world.isClient) {
            return ActionResult.PASS;
        }
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        // ignores this function if its used on a block that isnt a candlelight candle
        if (!(block instanceof PlayerCandleBlock candleBlock)) {
            return super.useOnBlock(context);
        } else {
            // gets the player related to the candle
            String targetPlayer = PlayerCandleHandler.candleOwners.get(PlayerCandleHandler.getListLocation(state.getBlock()));
            world.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.PLAYERS, 1.0F, 1.0F);
            ItemStack stack = context.getStack();
            // sets compass to track player even if not online
            stack.set(ModDataComponentTypes.TARGET_PLAYER, targetPlayer);
            // need line to set the candle to lit
            return ActionResult.SUCCESS;
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        // update player list
        playerList.clear();
        for (PlayerEntity player : world.getPlayers()) {
            playerList.add(player.getNameForScoreboard().toLowerCase());
        }

    }
}
