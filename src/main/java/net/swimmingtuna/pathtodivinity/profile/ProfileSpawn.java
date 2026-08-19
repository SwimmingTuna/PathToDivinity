package net.swimmingtuna.pathtodivinity.profile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

/**
 * Returns a player to the world spawn after a profile switch.
 *
 * <p>A profile is a separate life, so it starts where lives start rather than wherever the other profile
 * happened to be standing. This also closes the obvious abuse: hopping to Safemode to walk somewhere dangerous
 * and hopping back.
 */
public final class ProfileSpawn {

    /** How far out from the world spawn to look for somewhere to stand, in blocks. */
    private static final int SEARCH_RADIUS = 16;

    private ProfileSpawn() {
    }

    public static void sendToWorldSpawn(ServerPlayer player, MinecraftServer server) {
        ServerLevel overworld = server.overworld();
        BlockPos destination = findStandableSpawn(overworld);

        // The ServerPlayer overload takes care of dropping the camera and any vehicle, and of the dimension
        // change when the player was somewhere other than the overworld.
        player.teleportTo(overworld,
                destination.getX() + 0.5D, destination.getY(), destination.getZ() + 0.5D,
                overworld.getSharedSpawnAngle(), 0.0F);
        player.setDeltaMovement(Vec3.ZERO);
        player.resetFallDistance();
    }

    /**
     * A block at the world spawn the player can actually stand in.
     *
     * <p>The configured spawn block is tried first, so a {@code /setworldspawn} inside a build is honoured
     * exactly. Everything after that widens the search a ring at a time and lands on the surface, which is what
     * saves a player whose spawn point has since been walled in, flooded or blown up.
     */
    private static BlockPos findStandableSpawn(ServerLevel level) {
        BlockPos spawn = level.getSharedSpawnPos();

        for (int radius = 0; radius <= SEARCH_RADIUS; radius++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    // Only the perimeter of each ring; the inside was covered by a smaller radius.
                    if (radius > 0 && Math.abs(dx) != radius && Math.abs(dz) != radius) {
                        continue;
                    }
                    BlockPos found = standableInColumn(level, spawn.getX() + dx, spawn.getZ() + dz,
                            radius == 0 ? spawn.getY() : null);
                    if (found != null) {
                        return found;
                    }
                }
            }
        }

        // Nothing within the search radius qualified — a spawn out over the void or inside a solid mass. The
        // surface of the spawn column is still a better answer than refusing to move the player.
        return level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, spawn);
    }

    @Nullable
    private static BlockPos standableInColumn(ServerLevel level, int x, int z, @Nullable Integer preferredY) {
        if (preferredY != null) {
            BlockPos preferred = new BlockPos(x, preferredY, z);
            if (isStandable(level, preferred)) {
                return preferred;
            }
        }
        BlockPos surface = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                new BlockPos(x, 0, z));
        return isStandable(level, surface) ? surface : null;
    }

    private static boolean isStandable(ServerLevel level, BlockPos pos) {
        if (level.isOutsideBuildHeight(pos) || level.isOutsideBuildHeight(pos.above())
                || !level.getWorldBorder().isWithinBounds(pos)) {
            return false;
        }

        BlockPos floorPos = pos.below();
        BlockState floor = level.getBlockState(floorPos);
        if (!floor.isFaceSturdy(level, floorPos, Direction.UP) || floor.is(Blocks.MAGMA_BLOCK)) {
            return false;
        }

        // Ask the game the same question it asks when anything moves: does a standing player fit here?
        AABB body = EntityType.PLAYER.getDimensions()
                .makeBoundingBox(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
        return level.noCollision(body) && isClear(level, pos) && isClear(level, pos.above());
    }

    /** Nothing to drown in, burn in, or catch fire from. Collision is checked separately. */
    private static boolean isClear(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.getFluidState().isEmpty() && !state.is(BlockTags.FIRE);
    }
}
