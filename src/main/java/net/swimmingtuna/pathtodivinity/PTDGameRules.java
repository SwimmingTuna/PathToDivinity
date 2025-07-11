package net.swimmingtuna.pathtodivinity;

import net.minecraft.world.level.GameRules;

public class PTDGameRules {

    public static final GameRules.Key<GameRules.BooleanValue> SHOULD_ALLOW_DRAGONS = GameRules.register("shouldAllowDragons", GameRules.Category.MOBS, GameRules.BooleanValue.create(true));



}