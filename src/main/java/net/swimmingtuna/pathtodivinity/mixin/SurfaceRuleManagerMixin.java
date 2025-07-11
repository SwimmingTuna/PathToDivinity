package net.swimmingtuna.pathtodivinity.mixin;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import terrablender.api.SurfaceRuleManager;

import java.util.Map;

@Mixin(value = SurfaceRuleManager.class, remap = false)
public class SurfaceRuleManagerMixin {

    @Redirect(method = "getNamespacedRules", at = @At(value = "INVOKE",
            target = "com/google/common/collect/ImmutableMap$Builder.putAll(Ljava/util/Map;)Lcom/google/common/collect/ImmutableMap$Builder;"))
    private static ImmutableMap.Builder<String, SurfaceRules.RuleSource> filterNullValues(
            ImmutableMap.Builder<String, SurfaceRules.RuleSource> builder,
            Map<String, SurfaceRules.RuleSource> map) {

        if (map != null) {
            for (Map.Entry<String, SurfaceRules.RuleSource> entry : map.entrySet()) {
                if (entry.getValue() != null) {
                    try {
                        builder.put(entry.getKey(), entry.getValue());
                    } catch (Exception e) {
                        System.err.println("Failed to add surface rule for namespace '" + entry.getKey() + "': " + e.getMessage());
                    }
                } else {
                    System.out.println("Skipping null surface rule for namespace: " + entry.getKey());
                }
            }
        }

        return builder;
    }
}
