package net.swimmingtuna.pathtodivinity.mixin.Macabre;

import com.curseforge.macabre.procedures.ValamonEye2ProjectileHitsPlayerProcedure;
import com.curseforge.macabre.entity.ValamonEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Comparator;

@Mixin(value = ValamonEye2ProjectileHitsPlayerProcedure.class, remap = false)
public class ValamonEye2ProjectileHitsPlayerProcedureMixin {

    /**
     * @author SwimmingTuna
     * @reason Fix NullPointerException when no player or Valamon entity is found
     */
    @Overwrite
    public static void execute(LevelAccessor world, double x, double y, double z) {
        Entity player = world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 3.0, 3.0, 3.0), (e) -> true)
                .stream()
                .sorted(Comparator.comparingDouble(entity -> entity.distanceToSqr(x, y, z)))
                .findFirst()
                .orElse(null);

        if (player == null) {
            return;
        }

        Entity valamonEntity = world.getEntitiesOfClass(ValamonEntity.class, AABB.ofSize(new Vec3(x, y, z), 256.0, 256.0, 256.0), (e) -> true)
                .stream()
                .sorted(Comparator.comparingDouble(entity -> entity.distanceToSqr(x, y, z)))
                .findFirst()
                .orElse(null);

        if (valamonEntity == null) {
            return;
        }

        double valamonX = valamonEntity.getX();
        double valamonY = valamonEntity.getY();
        double valamonZ = valamonEntity.getZ();
        player.teleportTo(valamonX, valamonY, valamonZ);
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.teleport(valamonX, valamonY, valamonZ, player.getYRot(), player.getXRot());
        }
    }
}