package net.swimmingtuna.pathtodivinity.events;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.curseforge.macabre.entity.GomoriaHandProjEntity;
import com.curseforge.macabre.entity.GorepumpProjEntity;
import com.curseforge.macabre.entity.GutsEntity;
import com.curseforge.macabre.entity.PierceProjectileEntity;
import com.curseforge.macabre.init.MacabreModEntities;
import com.eeeab.eeeabsmobs.sever.init.EntityInit;
import com.github.L_Ender.cataclysm.entity.effect.Sandstorm_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Void_Vortex_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Wave_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.*;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.alexmodguy.alexscaves.server.entity.ACEntityRegistry;
import com.github.alexthe666.alexsmobs.entity.AMEntityRegistry;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.EntityDragonPart;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.kyanite.deeperdarker.content.DDEntities;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.yellowbrossproductions.illageandspillage.init.ModEntityTypes;
import fuzs.mutantmonsters.init.ModRegistry;
import net.arphex.init.ArphexModEntities;
import net.cursedwarrior.awakenedbosses.init.AwakenedBossesModEntities;
import net.mcreator.animatedmobsmod.init.AnimatedmobsmodModEntities;
import net.mcreator.borninchaosv.entity.PumpkinPistolProjectileEntity;
import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;
import net.mcreator.terramity.entity.SuperSnifferEntity;
import net.mcreator.terramity.entity.UltraSnifferEntity;
import net.mcreator.terramity.init.TerramityModEntities;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.soulsweaponry.entity.mobs.FreyrSwordEntity;
import net.soulsweaponry.registry.EntityRegistry;
import net.swimmingtuna.lotm.LOTM;
import net.swimmingtuna.lotm.beyonder.api.BeyonderClass;
import net.swimmingtuna.lotm.entity.PlayerMobEntity;
import net.swimmingtuna.lotm.init.BeyonderClassInit;
import net.swimmingtuna.lotm.util.BeyonderUtil;
import net.swimmingtuna.pathtodivinity.PTD;
import net.swimmingtuna.pathtodivinity.PTDGameRules;
import net.swimmingtuna.pathtodivinity.PTDUtil;
import net.zoniex.init.ZoniexModEntities;

import java.util.Map;


@Mod.EventBusSubscriber(modid = PTD.MOD_ID)
public class ModEvents {
    public static void commandEvent(CommandEvent event) {
        if (event.getParseResults().getContext().getSource().getEntity() instanceof ServerPlayer player) {
            CompoundTag tag = player.getPersistentData();
            if (tag.getInt("PTDCombatTimer") > 0) {
                String commandName = event.getParseResults().getReader().getString().split(" ")[0].toLowerCase();
                if (commandName.equals("/home") || commandName.equals("/spawn")) {
                    event.setCanceled(true);
                    player.sendSystemMessage(Component.literal("You are in combat and cannot use /home or /spawn!"));
                }
            }
        }
    }


    @SubscribeEvent
    public static void handleLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity living = event.getEntity();
        if (!living.level().isClientSide()) {
            EntityType<?> type = living.getType();
            CompoundTag tag = living.getPersistentData();
            int combatTimer = tag.getInt("PTDCombatTimer");
            if (combatTimer >= 1) {
                tag.putInt("PTDCombatTimer", combatTimer - 1);
            }

            int tickCount = living.tickCount;
            if (tickCount % 400 == 0 && living instanceof Player) {
                ItemStack mainHand = living.getMainHandItem();
                if (mainHand.isEnchanted() && mainHand.getEnchantmentLevel(Enchantments.PIERCING) > 0) {
                    Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(mainHand);
                    enchantments.remove(Enchantments.PIERCING);
                    living.sendSystemMessage(Component.literal("Piercing is banned").withStyle(ChatFormatting.RED));
                    EnchantmentHelper.setEnchantments(enchantments, mainHand);
                }
                PTDUtil.removeBannedItem(living);
            }

            if (living.tickCount % 40 == 0) {
                if (type == EntityRegistry.CHAOS_MONARCH.get()) {
                    BeyonderUtil.setPathway(living, BeyonderClassInit.MONSTER.get());
                    BeyonderUtil.setSequence(living, 7);
                } else if (type == EntityRegistry.DRAUGR_BOSS.get()) {
                    BeyonderUtil.setPathway(living, BeyonderClassInit.SPECTATOR.get());
                    BeyonderUtil.setSequence(living, 7);
                } else if (type == EntityRegistry.NIGHT_SHADE.get()) {
                    BeyonderUtil.setPathway(living, BeyonderClassInit.WARRIOR.get());
                    BeyonderUtil.setSequence(living, 7);
                } else if (type == ModEntities.Cloud_golem.get()) {
                    BeyonderUtil.setPathway(living, BeyonderClassInit.SPECTATOR.get());
                    BeyonderUtil.setSequence(living, 6);
                } else if (type == com.github.L_Ender.cataclysm.init.ModEntities.THE_LEVIATHAN.get()) {
                    BeyonderUtil.setPathway(living, BeyonderClassInit.MONSTER.get());
                    BeyonderUtil.setSequence(living, 6);
                } else if (living.getName().getString().equalsIgnoreCase("horseman")) {
                    BeyonderUtil.setPathway(living, BeyonderClassInit.SPECTATOR.get());
                    BeyonderUtil.setSequence(living, 6);
                } else if (type == EntityInit.NAMELESS_GUARDIAN.get()) {
                    BeyonderUtil.setPathway(living, BeyonderClassInit.WARRIOR.get());
                    BeyonderUtil.setSequence(living, 6);
                } else if (living.getName().getString().toLowerCase().contains("vessel")) {
                    BeyonderUtil.setPathway(living, BeyonderClassInit.MONSTER.get());
                    BeyonderUtil.setSequence(living, 5);
                } else if (type == EntityRegistry.MOONKNIGHT.get()) {
                    BeyonderUtil.setPathway(living, BeyonderClassInit.WARRIOR.get());
                    BeyonderUtil.setSequence(living, 5);
                } else if (type == BornInChaosV1ModEntities.LORD_PUMPKINHEAD.get()) {
                    BeyonderUtil.setPathway(living, BeyonderClassInit.SPECTATOR.get());
                    BeyonderUtil.setSequence(living, 5);
                } else if (type == TerramityModEntities.TRIAL_GUARDIAN.get()) {
                    BeyonderUtil.setPathway(living, BeyonderClassInit.SAILOR.get());
                    BeyonderUtil.setSequence(living, 5);
                } else if (type == EntityRegistry.DAY_STALKER.get()) {
                    if (living.tickCount % 4 == 0) {
                        for (Mob mob : living.level().getEntitiesOfClass(Mob.class, living.getBoundingBox().inflate(25))) {
                            if (mob.getType() == EntityRegistry.NIGHT_PROWLER.get()) {
                                BeyonderUtil.forceAlly(mob, living);
                            }
                        }
                    }
                    BeyonderUtil.setPathway(living, BeyonderClassInit.WARRIOR.get());
                    BeyonderUtil.setSequence(living, 4);
                } else if (type == EntityRegistry.NIGHT_PROWLER.get()) {
                    if (living.tickCount % 4 == 0) {
                        for (Mob mob : living.level().getEntitiesOfClass(Mob.class, living.getBoundingBox().inflate(25))) {
                            if (mob.getType() == EntityRegistry.DAY_STALKER.get()) {
                                BeyonderUtil.forceAlly(mob, living);
                            }
                        }
                    }
                    BeyonderUtil.setPathway(living, BeyonderClassInit.SAILOR.get());
                    BeyonderUtil.setSequence(living, 4);
                } else if (type == TerramityModEntities.ULTRA_SNIFFER.get()) {
                    if (BeyonderUtil.getPathway(living) == null) {
                        BeyonderClass[] pathways = {BeyonderClassInit.MONSTER.get(), BeyonderClassInit.WARRIOR.get(), BeyonderClassInit.SPECTATOR.get(), BeyonderClassInit.SAILOR.get()};
                        BeyonderClass randomPathway = pathways[living.getRandom().nextInt(pathways.length)];
                        BeyonderUtil.setPathway(living, randomPathway);
                        BeyonderUtil.setSequence(living, 3);
                    }
                } else if (type == ACEntityRegistry.BRAINIAC.get()) {
                    multiplyDamage(living, 1.3);
                } else if (type == com.github.L_Ender.cataclysm.init.ModEntities.KOBOLEDIATOR.get()) {
                    multiplyDamage(living, 1.2);
                } else if (type == AquamiraeEntities.MAW.get()) {
                    multiplyDamage(living, 1.5);
                } else if (type == ArphexModEntities.LONG_LEGS_FLY.get()) {
                    multiplyDamage(living, 2.0);
                } else if (type == ModEntities.Skeletosaurus.get()) {
                    multiplyDamage(living, 1.2);
                } else if (type == ArphexModEntities.SCORPION_STRIKER.get()) {
                    multiplyDamage(living, 1.5);
                } else if (type == ZoniexModEntities.BRUTALISER.get()) {
                    multiplyDamage(living, 1.4);
                } else if (type == EntityHandler.WROUGHTNAUT.get()) {
                    multiplyDamage(living, 1.2);
                } else if (type == BornInChaosV1ModEntities.DIRE_HOUND_LEADER.get()) {
                    multiplyDamage(living, 1.5);

                    // Sequence 8
                } else if (type == ACEntityRegistry.GUM_WORM.get()) {
                    multiplyDamage(living, 1.25);
                } else if (type == TerramityModEntities.DUSKROK.get()) {
                    multiplyDamage(living, 1.3);
                } else if (type == ModRegistry.MUTANT_SKELETON_ENTITY_TYPE.get()) {
                    multiplyDamage(living, 1.2);
                } else if (living.getName().getString().toLowerCase().contains("terrible") || living.getName().getString().toLowerCase().contains("puny")) {
                    multiplyDamage(living, 1.3);
                } else if (type == AMEntityRegistry.WARPED_MOSCO.get()) {
                    multiplyDamage(living, 1.3);
                } else if (type == EntityType.ELDER_GUARDIAN) {
                    multiplyDamage(living, 1.3);
                } else if (type == ModRegistry.MUTANT_ENDERMAN_ENTITY_TYPE.get()) {
                    multiplyDamage(living, 1.2);
                } else if (living.getName().getString().toLowerCase().contains("aero_guardian")) {
                    multiplyDamage(living, 1.6);
                } else if (type == BornInChaosV1ModEntities.MOTHER_SPIDER.get()) {
                    multiplyDamage(living, 2.0);
                } else if (type == ArphexModEntities.SPIDER_GOLIATH.get()) {
                    multiplyDamage(living, 1.3);
                } else if (type == TerramityModEntities.HELLROK.get()) {
                    multiplyDamage(living, 1.3);
                }

                // Sequence 7
                else if (type == ACEntityRegistry.FORSAKEN.get()) {
                    multiplyDamage(living, 1.5);
                } else if (type == ArphexModEntities.SPIDER_SNATCHER.get()) {
                    multiplyDamage(living, 1.5);
                } else if (type == MacabreModEntities.CRAWLER.get()) {
                    multiplyDamage(living, 2.0);
                } else if (living.getName().getString().toLowerCase().contains("doomharbor")) {
                    multiplyDamage(living, 1.75);
                } else if (type == EntityHandler.FROSTMAW.get()) {
                    multiplyDamage(living, 1.2);
                } else if (type == AquamiraeEntities.MAZE_MOTHER.get()) {
                    multiplyDamage(living, 1.6);
                } else if (type == ArphexModEntities.CENTIPEDE_EVICTOR.get()) {
                    multiplyDamage(living, 1.3);
                } else if (living.getName().getString().toLowerCase().contains("plague_bringer")) {
                    multiplyDamage(living, 1.4);
                } else if (type == DDEntities.STALKER.get()) {
                    multiplyDamage(living, 1.2);
                } else if (type == AnimatedmobsmodModEntities.ENDER_KING.get()) {
                    multiplyDamage(living, 0.8);
                } else if (living.getClass().getSimpleName().equals("LichEntity")) {
                    multiplyDamage(living, 1.1);
                } else if (type == AetherEntityTypes.SUN_SPIRIT.get()) {
                    multiplyDamage(living, 1.2);
                } else if (living.getClass().getSimpleName().equals("GauntletEntity")) {
                    multiplyDamage(living, 1.2);
                } else if (type == ArphexModEntities.SOLIFUGE_SKULKER.get()) {
                    multiplyDamage(living, 1.3);
                } else if (living.getClass().getSimpleName().equals("ObsidilithEntity")) {
                    multiplyDamage(living, 1.5);

                    //Sequence 6
                } else if (type == EntityType.WITHER) {
                    multiplyDamage(living, 1.1);
                } else if (living.getClass().getSimpleName().equals("VoidBlossomEntity")) {
                    multiplyDamage(living, 1.3);
                } else if (type == ModEntityTypes.Freakager.get()) {
                    multiplyDamage(living, 1.5);
                } else if (type == ArphexModEntities.WASP_NEMESIS.get()) {
                    multiplyDamage(living, 1.6);
                } else if (type == ModEntityTypes.Magispeller.get()) {
                    multiplyDamage(living, 1.5);
                } else if (type == BornInChaosV1ModEntities.LIFESTEALER.get()) {
                    multiplyDamage(living, 1.5);
                } else if (type == MacabreModEntities.THE_HOLLOW_MAN.get()) {
                    multiplyDamage(living, 1.4);
                } else if (type == BornInChaosV1ModEntities.SIR_PUMPKINHEAD.get()) {
                    multiplyDamage(living, 1.6);

                    // Sequence 5
                } else if (type == EntityRegistry.CHAOS_MONARCH.get()) {
                    multiplyDamage(living, 1.5);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.MONSTER.get());
                    BeyonderUtil.setSequence(living, 7);
                } else if (type == ArphexModEntities.CRAB_CONSTRICTOR.get()) {
                    multiplyDamage(living, 2.0);
                } else if (type == ArphexModEntities.SPIDER_REAPER.get()) {
                    multiplyDamage(living, 2.0);
                } else if (type == AquamiraeEntities.CAPTAIN_CORNELIA.get()) {
                    multiplyDamage(living, 1.3);
                } else if (type == EntityRegistry.DRAUGR_BOSS.get()) {
                    multiplyDamage(living, 1.2);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.SPECTATOR.get());
                    BeyonderUtil.setSequence(living, 7);
                } else if (type == EntityRegistry.NIGHT_SHADE.get()) {
                    multiplyDamage(living, 1.5);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.WARRIOR.get());
                    BeyonderUtil.setSequence(living, 7);
                } else if (type == EntityRegistry.ACCURSED_LORD_BOSS.get()) {
                    multiplyDamage(living, 1.7);
                } else if (type == EntityRegistry.RETURNING_KNIGHT.get()) {
                    multiplyDamage(living, 1.6);
                } else if (type == com.github.L_Ender.cataclysm.init.ModEntities.ENDER_GUARDIAN.get()) {
                    multiplyDamage(living, 1.6);

                    // Sequence 4
                } else if (type == ACEntityRegistry.HULLBREAKER.get()) {
                    multiplyDamage(living, 2.0);
                } else if (type == MacabreModEntities.GOMORIA.get()) {
                    multiplyDamage(living, 1.3);
                } else if (type == ModEntities.Cloud_golem.get()) {
                    BeyonderUtil.setPathway(living, BeyonderClassInit.SAILOR.get());
                    BeyonderUtil.setSequence(living, 6);
                } else if (type == MacabreModEntities.BAAL.get()) {
                    multiplyDamage(living, 1.2);
                } else if (type == ACEntityRegistry.LUXTRUCTOSAURUS.get()) {
                    multiplyDamage(living, 2.5);
                } else if (type == com.github.L_Ender.cataclysm.init.ModEntities.THE_LEVIATHAN.get()) {
                    BeyonderUtil.setPathway(living, BeyonderClassInit.MONSTER.get());
                    BeyonderUtil.setSequence(living, 6);
                } else if (type == com.github.L_Ender.cataclysm.init.ModEntities.SCYLLA.get()) {
                    multiplyDamage(living, 2.0);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.WARRIOR.get());
                    BeyonderUtil.setSequence(living, 7);
                } else if (type == TerramityModEntities.GOB.get()) {
                    multiplyDamage(living, 2.0);
                } else if (type == ArphexModEntities.SPIDER_PROWLER.get()) {
                    multiplyDamage(living, 2.5);
                } else if (living.getName().getString().equalsIgnoreCase("horseman")) {
                    BeyonderUtil.setPathway(living, BeyonderClassInit.SPECTATOR.get());
                    BeyonderUtil.setSequence(living, 6);
                } else if (type == MacabreModEntities.VALAMON.get()) {
                    multiplyDamage(living, 1.6);
                } else if (type == EntityInit.NAMELESS_GUARDIAN.get()) {
                    BeyonderUtil.setPathway(living, BeyonderClassInit.WARRIOR.get());
                    BeyonderUtil.setSequence(living, 6);

                    // Sequence 3
                } else if (living.getName().getString().toLowerCase().contains("vessel")) {
                    BeyonderUtil.setPathway(living, BeyonderClassInit.MONSTER.get());
                    BeyonderUtil.setSequence(living, 5);
                } else if (type == ArphexModEntities.SPIDER_MOTH.get()) {
                    multiplyDamage(living, 2.0);
                } else if (type == EntityRegistry.MOONKNIGHT.get()) {
                    multiplyDamage(living, 2.0);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.WARRIOR.get());
                    BeyonderUtil.setSequence(living, 5);
                } else if (type == ArphexModEntities.DRACONIC_VOIDLASHER.get()) {
                    multiplyDamage(living, 2.0);
                } else if (type == BornInChaosV1ModEntities.LORD_PUMPKINHEAD.get()) {
                    multiplyDamage(living, 1.2);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.SPECTATOR.get());
                    BeyonderUtil.setSequence(living, 5);
                } else if (type == ArphexModEntities.SCORPIOID_BLOODLUSTER.get()) {
                    multiplyDamage(living, 2.0);
                } else if (type == TerramityModEntities.TRIAL_GUARDIAN.get()) {
                    BeyonderUtil.setPathway(living, BeyonderClassInit.SAILOR.get());
                    BeyonderUtil.setSequence(living, 5);

                    // Sequence 2
                } else if (type == TerramityModEntities.SUPER_SNIFFER.get()) {
                    multiplyDamage(living, 1.2);
                } else if (type == EntityRegistry.DAY_STALKER.get()) {
                    BeyonderUtil.setPathway(living, BeyonderClassInit.WARRIOR.get());
                    BeyonderUtil.setSequence(living, 4);
                } else if (type == EntityRegistry.NIGHT_PROWLER.get()) {
                    BeyonderUtil.setPathway(living, BeyonderClassInit.SAILOR.get());
                    BeyonderUtil.setSequence(living, 4);
                } else if (type == TerramityModEntities.GUNDALF.get()) {
                    multiplyDamage(living, 1.5);
                }
            }


            if (type == ArphexModEntities.ROACH_RIVERSPAWN.get()) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 1, false, false));
            } else if (type == ArphexModEntities.CENTIPEDE_EVICTOR.get()) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 1, false, false));
            } else if (living.getName().getString().equalsIgnoreCase("horseman")) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 1, false, false));
            } else if (type == AwakenedBossesModEntities.HEROBRINE.get()) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 1, false, false));
            } else if (type == EntityHandler.UMVUTHI.get()) {
                living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 1, false, false));
            } else if (type == DDEntities.STALKER.get()) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 1, false, false));
            } else if (living.getName().getString().toLowerCase().contains("terrible_ten")) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 1, false, false));
            } else if (type == BornInChaosV1ModEntities.SPIRITOF_CHAOS.get()) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 2, false, false));
            } else if (type == EntityRegistry.MOONKNIGHT.get()) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 2, false, false));
            } else if (type == BornInChaosV1ModEntities.LORD_PUMPKINHEAD.get()) {
                if (living.tickCount % 3 == 0) {
                    for (Mob mob : living.level().getEntitiesOfClass(Mob.class, living.getBoundingBox().inflate(25))) {
                        if (!(mob instanceof PlayerMobEntity)) {
                            BeyonderUtil.forceAlly(mob, living);
                        }
                    }
                }
            } else if (type == EntityRegistry.DAY_STALKER.get()) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 2, false, false));
            } else if (type == EntityRegistry.NIGHT_PROWLER.get()) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 2, false, false));
            } else if (type == com.github.L_Ender.cataclysm.init.ModEntities.ANCIENT_ANCIENT_REMNANT.get()) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 1, false, false));
            } else if (type == com.github.L_Ender.cataclysm.init.ModEntities.MALEDICTUS.get()) {
                living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 2, false, false));
            } else if (type == com.github.L_Ender.cataclysm.init.ModEntities.SCYLLA.get()) {
                living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 2, false, false));
            }
            if (living instanceof EntityDragonBase dragon && tickCount % 300 == 0) {
                if (dragon.getDragonStage() >= 3 && dragon.getDragonStage() != 5) {
                    BeyonderUtil.applyMobEffect(dragon, MobEffects.DAMAGE_BOOST, 600, 2, true, true);
                    BeyonderUtil.applyMobEffect(dragon, MobEffects.DAMAGE_RESISTANCE, 600, 0, true, true);
                    BeyonderUtil.applyMobEffect(dragon, MobEffects.REGENERATION, 600, 2, true, true);
                } else if (dragon.getDragonStage() == 5) {
                    BeyonderUtil.applyMobEffect(dragon, MobEffects.DAMAGE_BOOST, 40, 4, true, true);
                    BeyonderUtil.applyMobEffect(dragon, MobEffects.DAMAGE_RESISTANCE, 40, 1, true, true);
                    BeyonderUtil.applyMobEffect(dragon, MobEffects.REGENERATION, 40, 3, true, true);
                }
            }
            if (living instanceof EntityDragonBase dragon && dragon.tickCount % 20 == 0) {
                if (!dragon.level().getGameRules().getBoolean(PTDGameRules.SHOULD_ALLOW_DRAGONS)) {
                    dragon.remove(Entity.RemovalReason.DISCARDED);
                    PTD.LOGGER.info("Despawned Dragon at{}, {}, {}", dragon.getX(), dragon.getY(), dragon.getZ());
                }
            }
            if (living instanceof UltraSnifferEntity ultraSniffer && ultraSniffer.getTarget() == null) {
                for (Player player : ultraSniffer.level().getEntitiesOfClass(Player.class, ultraSniffer.getBoundingBox().inflate(50))) {
                    if (!player.isCreative() && !player.isSpectator()) {
                        ultraSniffer.setTarget(player);
                    }
                }
                float health = ultraSniffer.getHealth();
                if (Float.isNaN(health) || health < 0.0F) {
                    ultraSniffer.setHealth(0.0F);
                }
            }
            if (living instanceof SuperSnifferEntity superSnifferEntity && superSnifferEntity.getTarget() == null) {
                for (Player player : superSnifferEntity.level().getEntitiesOfClass(Player.class, superSnifferEntity.getBoundingBox().inflate(50))) {
                    if (!player.isCreative() && !player.isSpectator()) {
                        superSnifferEntity.setTarget(player);
                    }
                }
            }
            if (living instanceof Mob mob && PTDUtil.isBeyonderEntity(mob) && tickCount % 10 == 0) {
                if (mob.getTarget() == null && combatTimer == 0 && mob.getHealth() < mob.getMaxHealth()) {
                    mob.setHealth(Math.min(mob.getMaxHealth(), mob.getHealth() + (mob.getMaxHealth() * 0.02f)));
                }
                if (mob.getTarget() != null && mob.getTarget() instanceof Player) {
                    Level level = mob.level();
                    AABB box = mob.getBoundingBox().inflate(1.0);
                    BlockPos.betweenClosedStream(box).forEach(pos -> {
                        BlockState state = level.getBlockState(pos);
                        if (!state.isAir() && state.getDestroySpeed(level, pos) >= 0 && pos.getY() >= mob.getY() + 1 && mob.getTarget().getY() > mob.getEyeY() && state != Blocks.WATER.defaultBlockState() && state != Blocks.LAVA.defaultBlockState()) {
                            level.destroyBlock(pos, true, mob);
                        }
                    });
                }
            }
        }
    }


    @SubscribeEvent
    public static void onEntityChangeTarget(LivingChangeTargetEvent event) {
        LivingEntity living = event.getEntity();
        CompoundTag tag = living.getPersistentData();
        BeyonderClass pathway = BeyonderUtil.getPathway(living);
        if (!living.level().isClientSide() && (event.getOriginalTarget() instanceof Player || event.getNewTarget() instanceof Player) && event.getOriginalTarget() != null && event.getNewTarget() != null) {
            if (PTDUtil.isBeyonderEntity(living) && living instanceof Mob mob) {
                float newTargetHealth = event.getNewTarget().getHealth();
                float originalTargetHealth = event.getOriginalTarget().getHealth();
                if (newTargetHealth > originalTargetHealth) {
                    event.setCanceled(true);
                } else if (event.getNewTarget().distanceTo(living) > event.getOriginalTarget().distanceTo(living) && event.getNewTarget().getHealth() > event.getOriginalTarget().getHealth()) {
                    event.setCanceled(true);
                }
            }
        }
    }


    @SubscribeEvent
    public static void hurtEvent(LivingHurtEvent event) {
        Entity entity = event.getEntity();
        CompoundTag tag = entity.getPersistentData();
        DamageSource source = event.getSource();
        Entity entitySource = source.getEntity();
        Entity directSource = source.getDirectEntity();
        Entity damageDealer = source.getEntity();
        if (damageDealer == null && source.getDirectEntity() != null) {
            damageDealer = source.getDirectEntity();
        }
        if (!event.getEntity().level().isClientSide()) {
            if (directSource instanceof GomoriaHandProjEntity projectile) {
                if (projectile.getOwner() != null && projectile.getOwner() instanceof Player) {
                    event.setAmount(event.getAmount() * 2.5f);
                }
            } else if (directSource instanceof GutsEntity projectile) {
                if (projectile.getOwner() != null && projectile.getOwner() instanceof Player) {
                    event.setAmount(event.getAmount() * 3.5f);
                }
            } else if (directSource instanceof GorepumpProjEntity projectile) {
                if (projectile.getOwner() != null && projectile.getOwner() instanceof Player) {
                    event.setAmount(event.getAmount() * 4.0f);
                }
            } else if (directSource instanceof PierceProjectileEntity projectile) {
                if (projectile.getOwner() != null && projectile.getOwner() instanceof Player) {
                    event.setAmount(event.getAmount() * 0.8f);
                }
            } else if (directSource instanceof PierceProjectileEntity) {
                event.setAmount(event.getAmount() * 4.0f);
            } else if (directSource instanceof PumpkinPistolProjectileEntity projectile) {
                if (projectile.getOwner() != null && projectile.getOwner() instanceof Player) {
                    event.setAmount(event.getAmount() * 7.0f);
                }
            } else if (directSource instanceof Tidal_Tentacle_Entity) {
                event.setAmount(event.getAmount() * 4.0f);
            } else if (directSource instanceof Wither_Howitzer_Entity projectile) {
                if (projectile.getOwner() != null && projectile.getOwner() instanceof Player player) {
                    boolean hasVoidAssault = false;
                    for (ItemStack itemStack : player.getInventory().items) {
                        if (itemStack.getItem() == ModItems.VOID_ASSULT_SHOULDER_WEAPON.get()) {
                            hasVoidAssault = true;
                            break;
                        }
                    }
                    if (hasVoidAssault) {
                        event.setAmount(event.getAmount() * 1.7f);
                    } else {
                        event.setAmount(event.getAmount() * 1.3f);
                    }
                }
            } else if (directSource instanceof Wither_Missile_Entity projectile) {
                if (projectile.getOwner() != null && projectile.getOwner() instanceof Player) {
                    event.setAmount(event.getAmount() * 1.3f);
                }
            } else if (directSource instanceof Sandstorm_Entity projectile) {
                if (projectile.getCaster() != null && projectile.getCaster() instanceof Player) {
                    event.setAmount(event.getAmount() * 1.5f);
                }
            } else if (directSource instanceof Phantom_Halberd_Entity projectile) {
                if (projectile.getCaster() != null && projectile.getCaster() instanceof Player) {
                    event.setAmount(event.getAmount() * 1.5f);
                }
            } else if (directSource instanceof Wave_Entity projectile) {
                if (projectile.getOwner() != null && projectile.getOwner() instanceof Player) {
                    event.setAmount(event.getAmount() * 1.5f);
                }
            } else if (directSource instanceof Void_Vortex_Entity projectile) {
                if (projectile.getOwner() != null && projectile.getOwner() instanceof Player) {
                    event.setAmount(event.getAmount() * 2.0f);
                }
            }  else if (directSource instanceof Cursed_Sandstorm_Entity projectile) {
                if (projectile.getOwner() != null && projectile.getOwner() instanceof Player) {
                    event.setAmount(event.getAmount() * 1.5f);
                }
            }  else if (directSource instanceof FreyrSwordEntity) {
                    event.setAmount(event.getAmount() * 1.8f);
            }


            tag.putInt("PTDCombatTimer", 200);
            if (entitySource instanceof LivingEntity livingEntity) {
                if (PTDUtil.isBeyonderEntity(livingEntity) && directSource instanceof Projectile) {
                    event.setAmount(event.getAmount() * 0.6f);
                }
            }


            if (damageDealer != null) {
                if (damageDealer.getPersistentData().getDouble("PTDDamageMultiplier") > 0) {
                    event.setAmount((float) (event.getAmount() * damageDealer.getPersistentData().getDouble("PTDDamageMultiplier")));
                }
            }
        }
    }


    @SubscribeEvent
    public static void entityJoinEvent(EntityJoinLevelEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            Entity entity = event.getEntity();
            if (entity instanceof LivingEntity living) {
                EntityType<?> type = living.getType();
                float maxHealth = living.getMaxHealth();
                if (!event.getEntity().level().getGameRules().getBoolean(PTDGameRules.SHOULD_ALLOW_DRAGONS)) {
                    if (event.getEntity() instanceof EntityDragonBase || event.getEntity() instanceof EntityDragonPart) {
                        event.setCanceled(true);
                    }
                }

                // Sequence 9
                if (type == IafEntityRegistry.CYCLOPS.get()) {
                    multiplyMaxHealth(living, 1.3);
                } else if (type == ModEntities.Overgrown_colossus.get()) {
                    multiplyMaxHealth(living, 1.5);
                } else if (type == ACEntityRegistry.BRAINIAC.get()) {
                    multiplyMaxHealth(living, 1.5);
                    multiplyDamage(living, 1.3);
                } else if (type == com.github.L_Ender.cataclysm.init.ModEntities.KOBOLEDIATOR.get()) {
                    multiplyMaxHealth(living, 1.0);
                    multiplyDamage(living, 1.2);
                } else if (type == EntityHandler.UMVUTHI.get()) {
                    multiplyMaxHealth(living, 1.0);
                } else if (type == AquamiraeEntities.MAW.get()) {
                    multiplyMaxHealth(living, 2.0);
                    multiplyDamage(living, 1.5);
                } else if (type == ArphexModEntities.ROACH_RIVERSPAWN.get()) {
                    multiplyMaxHealth(living, 49.0);
                } else if (type == ArphexModEntities.LONG_LEGS_FLY.get()) {
                    multiplyMaxHealth(living, 4.0);
                    multiplyDamage(living, 2.0);
                } else if (type == ModEntities.Skeletosaurus.get()) {
                    multiplyMaxHealth(living, 1.2);
                    multiplyDamage(living, 1.2);
                } else if (type == BornInChaosV1ModEntities.NIGHTMARE_STALKER.get()) {
                    multiplyMaxHealth(living, 1.2);
                } else if (type == ArphexModEntities.SCORPION_STRIKER.get()) {
                    multiplyMaxHealth(living, 1.5);
                    multiplyDamage(living, 1.5);
                } else if (type == ZoniexModEntities.BRUTALISER.get()) {
                    multiplyMaxHealth(living, 1.2);
                    multiplyDamage(living, 1.4);
                } else if (type == EntityHandler.WROUGHTNAUT.get()) {
                    multiplyMaxHealth(living, 1.0);
                    multiplyDamage(living, 1.2);
                } else if (type == BornInChaosV1ModEntities.DIRE_HOUND_LEADER.get()) {
                    multiplyMaxHealth(living, 1.25);
                    multiplyDamage(living, 1.5);

                    // Sequence 8
                } else if (type == ModEntities.BlastCannon.get()) {
                    multiplyMaxHealth(living, 1.25);
                } else if (type == ModEntities.Frostbitten_Golem.get()) {
                    multiplyMaxHealth(living, 1.3);
                } else if (type == ACEntityRegistry.GUM_WORM.get()) {
                    multiplyMaxHealth(living, 1.0);
                    multiplyDamage(living, 1.25);
                } else if (type == TerramityModEntities.DUSKROK.get()) {
                    multiplyMaxHealth(living, 1.3);
                    multiplyDamage(living, 1.3);
                } else if (type == ModRegistry.MUTANT_SKELETON_ENTITY_TYPE.get()) {
                    multiplyMaxHealth(living, 1.5);
                    multiplyDamage(living, 1.2);
                } else if (living.getName().getString().toLowerCase().contains("terrible") || living.getName().getString().toLowerCase().contains("puny")) {
                    multiplyMaxHealth(living, 1.0);
                    multiplyDamage(living, 1.3);
                } else if (type == AMEntityRegistry.WARPED_MOSCO.get()) {
                    multiplyMaxHealth(living, 1.0);
                    multiplyDamage(living, 1.3);
                } else if (type == IafEntityRegistry.SEA_SERPENT.get()) {
                    multiplyMaxHealth(living, 2.0);
                } else if (type == EntityType.ELDER_GUARDIAN) {
                    multiplyMaxHealth(living, 1.0);
                    multiplyDamage(living, 1.3);
                } else if (type == ModRegistry.MUTANT_ENDERMAN_ENTITY_TYPE.get()) {
                    multiplyMaxHealth(living, 1.4);
                    multiplyDamage(living, 1.2);
                } else if (living.getName().getString().toLowerCase().contains("aero_guardian")) {
                    multiplyMaxHealth(living, 1.0);
                    multiplyDamage(living, 1.6);
                } else if (type == ACEntityRegistry.TREMORSAURUS.get()) {
                    multiplyMaxHealth(living, 1.2);
                } else if (type == BornInChaosV1ModEntities.SPIRITOF_CHAOS.get()) {
                    multiplyMaxHealth(living, 4.0);
                } else if (type == BornInChaosV1ModEntities.MOTHER_SPIDER.get()) {
                    multiplyMaxHealth(living, 2.0);
                    multiplyDamage(living, 2.0);
                } else if (type == ArphexModEntities.SPIDER_GOLIATH.get()) {
                    multiplyMaxHealth(living, 1.5);
                    multiplyDamage(living, 1.3);
                } else if (type == TerramityModEntities.HELLROK.get()) {
                    multiplyMaxHealth(living, 1.3);
                    multiplyDamage(living, 1.3);
                } else if (type == ModRegistry.MUTANT_ZOMBIE_ENTITY_TYPE.get()) {
                    multiplyMaxHealth(living, 1.5);

                    // Sequence 7
                } else if (type == ACEntityRegistry.FORSAKEN.get()) {
                    multiplyMaxHealth(living, 1.3);
                    multiplyDamage(living, 1.5);
                } else if (type == ModEntities.Ancient_Guardian.get()) {
                    multiplyMaxHealth(living, 1.3);
                } else if (type == ArphexModEntities.SPIDER_SNATCHER.get()) {
                    multiplyMaxHealth(living, 1.0);
                    multiplyDamage(living, 1.5);
                } else if (type == MacabreModEntities.CRAWLER.get()) {
                    multiplyMaxHealth(living, 2.0);
                    multiplyDamage(living, 2.0);
                } else if (living.getName().getString().toLowerCase().contains("doomharbor")) {
                    multiplyMaxHealth(living, 1.0);
                    multiplyDamage(living, 1.75);
                } else if (type == EntityHandler.FROSTMAW.get()) {
                    multiplyMaxHealth(living, 1.2);
                    multiplyDamage(living, 1.2);
                } else if (type == AquamiraeEntities.MAZE_MOTHER.get()) {
                    multiplyMaxHealth(living, 1.5);
                    multiplyDamage(living, 1.6);
                } else if (type == ArphexModEntities.CENTIPEDE_EVICTOR.get()) {
                    multiplyMaxHealth(living, 1.0);
                    multiplyDamage(living, 1.3);
                } else if (living.getName().getString().toLowerCase().contains("plague_bringer")) {
                    multiplyMaxHealth(living, 1.0);
                    multiplyDamage(living, 1.4);
                } else if (type == DDEntities.STALKER.get()) {
                    multiplyMaxHealth(living, 1.0);
                    multiplyDamage(living, 1.2);
                } else if (type == AnimatedmobsmodModEntities.ENDER_KING.get()) {
                    multiplyMaxHealth(living, 0.8);
                    multiplyDamage(living, 0.8);
                } else if (entity.getClass().getSimpleName().equals("LichEntity")) {
                    multiplyMaxHealth(living, 1.0);
                    multiplyDamage(living, 1.1);
                } else if (type == ModEntities.Withered_Abomination.get()) {
                    multiplyMaxHealth(living, 1.2);
                } else if (type == AetherEntityTypes.SUN_SPIRIT.get()) {
                    multiplyMaxHealth(living, 1.0);
                    multiplyDamage(living, 1.2);
                } else if (entity.getClass().getSimpleName().equals("GauntletEntity")) {
                    multiplyMaxHealth(living, 1.1);
                    multiplyDamage(living, 1.2);
                } else if (type == ArphexModEntities.SOLIFUGE_SKULKER.get()) {
                    multiplyMaxHealth(living, 1.3);
                    multiplyDamage(living, 1.3);
                } else if (entity.getClass().getSimpleName().equals("ObsidilithEntity")) {
                    multiplyMaxHealth(living, 1.1);
                    multiplyDamage(living, 1.5);

                    //Sequence 6
                } else if (type == ModEntityTypes.Spiritcaller.get()) {
                    multiplyMaxHealth(living, 1.3);
                } else if (type == EntityType.WITHER) {
                    multiplyMaxHealth(living, 1.0);
                    multiplyDamage(living, 1.1);
                } else if (entity.getClass().getSimpleName().equals("VoidBlossomEntity")) {
                    multiplyMaxHealth(living, 1.2);
                    multiplyDamage(living, 1.3);
                } else if (type == ModEntityTypes.Freakager.get()) {
                    multiplyMaxHealth(living, 1.5);
                    multiplyDamage(living, 1.5);
                } else if (type == ModEntityTypes.Ragno.get()) {
                    multiplyMaxHealth(living, 1.5);
                } else if (type == ArphexModEntities.WASP_NEMESIS.get()) {
                    multiplyMaxHealth(living, 1.5);
                    multiplyDamage(living, 1.6);
                } else if (type == ModEntityTypes.Magispeller.get()) {
                    multiplyMaxHealth(living, 1.5);
                    multiplyDamage(living, 1.5);
                } else if (type == AwakenedBossesModEntities.HEROBRINE.get()) {
                    multiplyMaxHealth(living, 1.0);
                } else if (type == BornInChaosV1ModEntities.LIFESTEALER.get()) {
                    multiplyMaxHealth(living, 1.5);
                    multiplyDamage(living, 1.5);
                } else if (type == ModEntities.Lava_eater.get()) {
                    multiplyMaxHealth(living, 1.3);
                } else if (type == MacabreModEntities.THE_HOLLOW_MAN.get()) {
                    multiplyMaxHealth(living, 1.2);
                    multiplyDamage(living, 1.4);
                } else if (type == BornInChaosV1ModEntities.SIR_PUMPKINHEAD.get()) {
                    multiplyMaxHealth(living, 1.5);
                    multiplyDamage(living, 1.6);

                    // Sequence 5
                } else if (type == EntityRegistry.CHAOS_MONARCH.get()) {
                    multiplyMaxHealth(living, 1.8);
                    multiplyDamage(living, 1.5);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.MONSTER.get());
                    BeyonderUtil.setSequence(living, 7);
                } else if (type == com.github.L_Ender.cataclysm.init.ModEntities.THE_HARBINGER.get()) {
                    multiplyMaxHealth(living, 1.3);
                } else if (type == ArphexModEntities.CRAB_CONSTRICTOR.get()) {
                    multiplyMaxHealth(living, 2.0);
                    multiplyDamage(living, 2.0);
                } else if (type == ArphexModEntities.SPIDER_REAPER.get()) {
                    multiplyMaxHealth(living, 1.3);
                    multiplyDamage(living, 2.0);
                } else if (type == AquamiraeEntities.CAPTAIN_CORNELIA.get()) {
                    multiplyMaxHealth(living, 1.3);
                    multiplyDamage(living, 1.3);
                } else if (type == EntityRegistry.DRAUGR_BOSS.get()) {
                    multiplyMaxHealth(living, 1.5);
                    multiplyDamage(living, 1.2);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.SPECTATOR.get());
                    BeyonderUtil.setSequence(living, 7);
                } else if (type == EntityRegistry.NIGHT_SHADE.get()) {
                    multiplyMaxHealth(living, 2.5);
                    multiplyDamage(living, 1.5);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.WARRIOR.get());
                    BeyonderUtil.setSequence(living, 7);
                } else if (type == EntityRegistry.ACCURSED_LORD_BOSS.get()) {
                    multiplyMaxHealth(living, 2.0);
                    multiplyDamage(living, 1.7);
                } else if (type == EntityRegistry.RETURNING_KNIGHT.get()) {
                    multiplyMaxHealth(living, 1.6);
                    multiplyDamage(living, 1.6);
                } else if (type == com.github.L_Ender.cataclysm.init.ModEntities.ENDER_GUARDIAN.get()) {
                    multiplyMaxHealth(living, 1.7);
                    multiplyDamage(living, 1.6);

                    // Sequence 4
                } else if (type == ACEntityRegistry.HULLBREAKER.get()) {
                    multiplyMaxHealth(living, 2.0);
                    multiplyDamage(living, 2.0);
                } else if (type == MacabreModEntities.GOMORIA.get()) {
                    multiplyMaxHealth(living, 1.5);
                    multiplyDamage(living, 1.3);
                } else if (type == MacabreModEntities.GARGAMAW.get()) {
                    multiplyMaxHealth(living, 2.0);
                    //ADD SMALLER EXPLOSIONS
                } else if (type == com.github.L_Ender.cataclysm.init.ModEntities.IGNIS.get()) {
                    multiplyMaxHealth(living, 1.2);
                } else if (type == ModEntities.Cloud_golem.get()) {
                    multiplyMaxHealth(living, 2.0);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.SAILOR.get());
                    BeyonderUtil.setSequence(living, 6);
                } else if (type == MacabreModEntities.BAAL.get()) {
                    multiplyMaxHealth(living, 1.8);
                    multiplyDamage(living, 1.2);
                } else if (type == ACEntityRegistry.LUXTRUCTOSAURUS.get()) {
                    multiplyMaxHealth(living, 2.0);
                    multiplyDamage(living, 2.5);
                } else if (type == com.github.L_Ender.cataclysm.init.ModEntities.THE_LEVIATHAN.get()) {
                    multiplyMaxHealth(living, 1.0);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.MONSTER.get());
                    BeyonderUtil.setSequence(living, 6);
                } else if (type == com.github.L_Ender.cataclysm.init.ModEntities.SCYLLA.get()) {
                    multiplyMaxHealth(living, 4.0);
                    multiplyDamage(living, 2.0);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.WARRIOR.get());
                    BeyonderUtil.setSequence(living, 7);
                } else if (type == TerramityModEntities.GOB.get()) {
                    multiplyMaxHealth(living, 3.0);
                    multiplyDamage(living, 2.0);
                } else if (type == ArphexModEntities.SPIDER_PROWLER.get()) {
                    multiplyMaxHealth(living, 4.0);
                    multiplyDamage(living, 2.5);
                } else if (living.getName().getString().equalsIgnoreCase("horseman")) {
                    multiplyMaxHealth(living, 1.5);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.SPECTATOR.get());
                    BeyonderUtil.setSequence(living, 6);
                } else if (type == MacabreModEntities.VALAMON.get()) {
                    multiplyMaxHealth(living, 1.4);
                    multiplyDamage(living, 1.6);
                } else if (type == EntityInit.NAMELESS_GUARDIAN.get()) {
                    multiplyMaxHealth(living, 1.0);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.WARRIOR.get());
                    BeyonderUtil.setSequence(living, 6);
                } else if (type == com.github.L_Ender.cataclysm.init.ModEntities.MALEDICTUS.get()) {
                    multiplyMaxHealth(living, 4.0);
                    multiplyDamage(living, 2.5);
                } else if (type == com.github.L_Ender.cataclysm.init.ModEntities.ANCIENT_ANCIENT_REMNANT.get()) {
                    multiplyMaxHealth(living, 4.0);
                    multiplyDamage(living, 2.5);


                    // Sequence 3
                } else if (living.getName().getString().toLowerCase().contains("vessel")) {
                    multiplyMaxHealth(living, 4.0);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.MONSTER.get());
                    BeyonderUtil.setSequence(living, 5);
                } else if (type == ArphexModEntities.SPIDER_MOTH.get()) {
                    multiplyMaxHealth(living, 4.0);
                    multiplyDamage(living, 2.0);
                } else if (type == EntityRegistry.MOONKNIGHT.get()) {
                    multiplyMaxHealth(living, 4.0);
                    multiplyDamage(living, 2.0);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.WARRIOR.get());
                    BeyonderUtil.setSequence(living, 5);
                } else if (type == ArphexModEntities.DRACONIC_VOIDLASHER.get()) {
                    multiplyMaxHealth(living, 4.0);
                    multiplyDamage(living, 2.0);
                } else if (type == BornInChaosV1ModEntities.LORD_PUMPKINHEAD.get()) {
                    multiplyMaxHealth(living, 5.0);
                    multiplyDamage(living, 1.2);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.SPECTATOR.get());
                    BeyonderUtil.setSequence(living, 5);
                } else if (type == ArphexModEntities.SCORPIOID_BLOODLUSTER.get()) {
                    multiplyMaxHealth(living, 4.0);
                    multiplyDamage(living, 2.0);
                } else if (type == TerramityModEntities.TRIAL_GUARDIAN.get()) {
                    multiplyMaxHealth(living, 4.0);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.SAILOR.get());
                    BeyonderUtil.setSequence(living, 5);

                    // Sequence 2
                } else if (type == TerramityModEntities.SUPER_SNIFFER.get()) {
                    multiplyMaxHealth(living, 6.0);
                    multiplyDamage(living, 1.2);
                } else if (type == EntityRegistry.DAY_STALKER.get()) {
                    multiplyMaxHealth(living, 7.0);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.WARRIOR.get());
                    BeyonderUtil.setSequence(living, 4);
                } else if (type == EntityRegistry.NIGHT_PROWLER.get()) {
                    multiplyMaxHealth(living, 7.0);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.SAILOR.get());
                    BeyonderUtil.setSequence(living, 4);
                } else if (type == TerramityModEntities.GUNDALF.get()) {
                    multiplyMaxHealth(living, 6.0);
                    multiplyDamage(living, 1.5);

                    // Sequence 1
                } else if (type == TerramityModEntities.ULTRA_SNIFFER.get()) {
                    multiplyMaxHealthUltraSniffer(living, 10.0);
                    if (BeyonderUtil.getPathway(living) == null) {
                        BeyonderClass[] pathways = {BeyonderClassInit.MONSTER.get(), BeyonderClassInit.WARRIOR.get(), BeyonderClassInit.SPECTATOR.get(), BeyonderClassInit.SAILOR.get()};
                        BeyonderClass randomPathway = pathways[living.getRandom().nextInt(pathways.length)];
                        BeyonderUtil.setPathway(living, randomPathway);
                        BeyonderUtil.setSequence(living, 3);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void serverStartEvent(ServerStartingEvent event) {
        MinecraftServer server = event.getServer();
        Commands commands = server.getCommands();
        CommandSourceStack commandSource = server.createCommandSourceStack();
        try {
            commands.performPrefixedCommand(commandSource, "beyonderrecipe load");
        } catch (Exception e) {
            PTD.LOGGER.info("Failed to execute beyonderrecipe load command: {}", e.getMessage());
        }
    }

    public static boolean isEntityFromMod(Entity entity, String modId) {
        ResourceLocation entityId = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        if (entityId != null && entityId.getNamespace().equals(modId)) {
            return true;
        }
        String packageName = entity.getClass().getPackage().getName();
        return packageName.toLowerCase().contains(modId.toLowerCase());
    }

    public static void multiplyMaxHealth(LivingEntity living, double multiplier) {
        float multiplierAmount = (float) multiplier;
        float maxHealth = living.getMaxHealth();
        AttributeInstance maxHealthAttribute = living.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealthAttribute != null) {
            if (maxHealth < 10000) {
                maxHealthAttribute.setBaseValue(maxHealth * multiplierAmount);
            }
        }
        living.setHealth(maxHealth * multiplierAmount);
        LOTM.LOGGER.info("Multiplied{}'s health by {}", living.getName().getString(), multiplier);
    }

    public static void multiplyMaxHealthUltraSniffer(LivingEntity living, double multiplier) {
        float multiplierAmount = (float) multiplier;
        float maxHealth = living.getMaxHealth();
        AttributeInstance maxHealthAttribute = living.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealthAttribute != null) {
            maxHealthAttribute.setBaseValue(maxHealth * multiplierAmount + 1);
        }
        living.setHealth(maxHealth * multiplierAmount);
        LOTM.LOGGER.info("Multiplied{}'s health by {}", living.getName().getString(), multiplier);
    }

    private static void multiplyDamage(LivingEntity entity, double multiplier) {
        CompoundTag tag = entity.getPersistentData();
        tag.putDouble("PTDDamageMultiplier", multiplier);
    }
}