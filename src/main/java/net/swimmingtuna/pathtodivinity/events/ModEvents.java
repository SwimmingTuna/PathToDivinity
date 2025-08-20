package net.swimmingtuna.pathtodivinity.events;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.curseforge.macabre.entity.GomoriaHandProjEntity;
import com.curseforge.macabre.entity.GorepumpProjEntity;
import com.curseforge.macabre.entity.GutsEntity;
import com.curseforge.macabre.entity.PierceProjectileEntity;
import com.curseforge.macabre.init.MacabreModEntities;
import com.eeeab.eeeabsmobs.sever.entity.effects.EntityGuardianLaser;
import com.eeeab.eeeabsmobs.sever.init.EntityInit;
import com.github.L_Ender.cataclysm.entity.effect.Sandstorm_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Void_Vortex_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Wave_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.*;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.alexmodguy.alexscaves.server.entity.ACEntityRegistry;
import com.github.alexthe666.alexsmobs.entity.AMEntityRegistry;
import com.kyanite.deeperdarker.content.DDEntities;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.yellowbrossproductions.illageandspillage.init.ModEntityTypes;
import fuzs.mutantmonsters.init.ModRegistry;
import net.arphex.configuration.ConfigurationSettingsConfiguration;
import net.arphex.entity.CentipedeEvictorEntity;
import net.arphex.entity.SpiderProwlerEntity;
import net.arphex.init.ArphexModBlocks;
import net.arphex.init.ArphexModEntities;
import net.arphex.init.ArphexModItems;
import net.cursedwarrior.awakenedbosses.init.AwakenedBossesModEntities;
import net.mcreator.animatedmobsmod.init.AnimatedmobsmodModEntities;
import net.mcreator.borninchaosv.entity.PumpkinPistolProjectileEntity;
import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;
import net.mcreator.terramity.entity.DuskrokEntity;
import net.mcreator.terramity.entity.SuperSnifferEntity;
import net.mcreator.terramity.entity.UltraSnifferEntity;
import net.mcreator.terramity.init.TerramityModEntities;
import net.mcreator.terramity.init.TerramityModItems;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.soulsweaponry.entity.mobs.DayStalker;
import net.soulsweaponry.entity.mobs.FreyrSwordEntity;
import net.soulsweaponry.entity.mobs.NightProwler;
import net.soulsweaponry.registry.EntityRegistry;
import net.swimmingtuna.lotm.LOTM;
import net.swimmingtuna.lotm.beyonder.api.BeyonderClass;
import net.swimmingtuna.lotm.entity.PlayerMobEntity;
import net.swimmingtuna.lotm.init.BeyonderClassInit;
import net.swimmingtuna.lotm.item.BeyonderAbilities.Monster.ProbabilityManipulationFortune;
import net.swimmingtuna.lotm.util.BeyonderUtil;
import net.swimmingtuna.pathtodivinity.PTD;
import net.swimmingtuna.pathtodivinity.PTDGameRules;
import net.swimmingtuna.pathtodivinity.PTDUtil;
import net.zoniex.init.ZoniexModEntities;
import org.thecelestialworkshop.celestisynth.common.entity.projectile.CrescentiaDragon;
import org.thecelestialworkshop.celestisynth.common.entity.projectile.FrostboundShard;
import org.thecelestialworkshop.celestisynth.common.entity.projectile.SolarisBomb;

import java.util.Map;


@Mod.EventBusSubscriber(modid = PTD.MOD_ID)
public class ModEvents {

    @SubscribeEvent
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
    public static void rightClickItemEvent(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack itemStack = event.getItemStack();
        Level level = player.level();
        if (!itemStack.getItem().getDescriptionId().equals("item.faded_conquest_2.abyssal_device")) {
            return;
        }
        if (level.isClientSide()) {
            return;
        }
        ServerLevel serverLevel = (ServerLevel) level;
        Vec3 playerPos = player.position();
        CommandSourceStack commandSource = new CommandSourceStack(CommandSource.NULL, playerPos, Vec2.ZERO, serverLevel, 4, "", Component.literal(""), serverLevel.getServer(), null).withSuppressedOutput();
        serverLevel.getServer().getCommands().performPrefixedCommand(commandSource, "summon faded_conquest_2:vessel_of_calamity");
        itemStack.shrink(1);
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getHand() == event.getEntity().getUsedItemHand()) {
            Player player = event.getEntity();
            Level level = player.level();
            int x = event.getPos().getX();
            int y = event.getPos().getY();
            int z = event.getPos().getZ();
            if (level.getBlockState(BlockPos.containing(x, y, z)).getBlock() == ArphexModBlocks.CRAWLING_ALTAR.get()) {
                event.setCanceled(true);
                if (level.getBlockState(BlockPos.containing(x, y - 1.0, z)).getBlock() == Blocks.END_ROD) {
                    if (level.getBlockState(BlockPos.containing(x, y - 2.0, z)).getBlock() == Blocks.BEDROCK) {
                        if (level.getBlockState(BlockPos.containing(x + 5.0, y + 1.0, z)).getBlock() != ArphexModBlocks.SCORCH.get()) {
                            if (!player.level().isClientSide()) {
                                player.displayClientMessage(Component.literal("The pillars are not all lit"), true);
                            }
                        } else if (level.getBlockState(BlockPos.containing(x - 5.0, y + 1.0, z)).getBlock() != ArphexModBlocks.SCORCH.get()) {
                            if (!player.level().isClientSide()) {
                                player.displayClientMessage(Component.literal("The pillars are not all lit"), true);
                            }
                        } else if (level.getBlockState(BlockPos.containing(x, y + 1.0, z + 5.0)).getBlock() != ArphexModBlocks.SCORCH.get()) {
                            if (!player.level().isClientSide()) {
                                player.displayClientMessage(Component.literal("The pillars are not all lit"), true);
                            }
                        } else if (level.getBlockState(BlockPos.containing(x, y + 1.0, z - 5.0)).getBlock() != ArphexModBlocks.SCORCH.get() && !(Boolean) ConfigurationSettingsConfiguration.CRAWLING_ONLY.get()) {
                            if (!player.level().isClientSide()) {
                                player.displayClientMessage(Component.literal("The pillars are not all lit"), true);
                            }
                        } else {
                            label:
                            {
                                if (player instanceof ServerPlayer serverPlayer) {
                                    if (serverPlayer.level() instanceof ServerLevel && serverPlayer.getAdvancements().getOrStartProgress(serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation("arphex:crawling_portal_activated"))).isDone()) {
                                        break label;
                                    }
                                }

                                if (player instanceof ServerPlayer serverPlayer) {
                                    Advancement advancement = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation("arphex:crawling_portal_activated"));
                                    AdvancementProgress advancementProgress = serverPlayer.getAdvancements().getOrStartProgress(advancement);
                                    if (!advancementProgress.isDone()) {
                                        for (String criteria : advancementProgress.getRemainingCriteria()) {
                                            serverPlayer.getAdvancements().award(advancement, criteria);
                                        }
                                    }
                                }
                            }

                            if (level instanceof ServerLevel serverLevel) {
                                serverLevel.getServer().getCommands().performPrefixedCommand((new CommandSourceStack(CommandSource.NULL, new Vec3(x, y - 1.0, z), Vec2.ZERO, serverLevel, 4, "", Component.literal(""), serverLevel.getServer(), (Entity) null)).withSuppressedOutput(), "fill ~-2 ~ ~-2 ~2 ~ ~2 arphex:crawling_portal");
                            }

                            if (level instanceof ServerLevel serverLevel) {
                                serverLevel.getServer().getCommands().performPrefixedCommand((new CommandSourceStack(CommandSource.NULL, new Vec3(x, y - 2.0, z), Vec2.ZERO, serverLevel, 4, "", Component.literal(""), serverLevel.getServer(), (Entity) null)).withSuppressedOutput(), "fill ~-2 ~ ~-2 ~2 ~ ~2 bedrock");
                            }

                            level.setBlock(BlockPos.containing(x + 3.0, y - 2.0, z), Blocks.BEDROCK.defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x + 4.0, y - 2.0, z), Blocks.BEDROCK.defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x - 3.0, y - 2.0, z), Blocks.BEDROCK.defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x - 4.0, y - 2.0, z), Blocks.BEDROCK.defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x, y - 2.0, z + 3.0), Blocks.BEDROCK.defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x, y - 2.0, z + 4.0), Blocks.BEDROCK.defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x, y - 2.0, z - 3.0), Blocks.BEDROCK.defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x, y - 2.0, z - 4.0), Blocks.BEDROCK.defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x + 1.0, y - 2.0, z + 1.0), Blocks.BEDROCK.defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x + 1.0, y - 2.0, z - 1.0), Blocks.BEDROCK.defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x - 1.0, y - 2.0, z + 1.0), Blocks.BEDROCK.defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x - 1.0, y - 2.0, z - 1.0), Blocks.BEDROCK.defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x - 3.0, y - 2.0, z - 1.0), Blocks.BEDROCK.defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x - 3.0, y - 2.0, z + 1.0), Blocks.BEDROCK.defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x - 1.0, y - 2.0, z - 3.0), Blocks.BEDROCK.defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x + 1.0, y - 2.0, z - 3.0), Blocks.BEDROCK.defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x + 3.0, y - 2.0, z - 1.0), Blocks.BEDROCK.defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x + 3.0, y - 2.0, z + 1.0), Blocks.BEDROCK.defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x - 1.0, y - 2.0, z + 3.0), Blocks.BEDROCK.defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x + 1.0, y - 2.0, z + 3.0), Blocks.BEDROCK.defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x + 3.0, y - 1.0, z), ((Block) ArphexModBlocks.CRAWLING_PORTAL.get()).defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x + 4.0, y - 1.0, z), ((Block) ArphexModBlocks.CRAWLING_PORTAL.get()).defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x - 3.0, y - 1.0, z), ((Block) ArphexModBlocks.CRAWLING_PORTAL.get()).defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x - 4.0, y - 1.0, z), ((Block) ArphexModBlocks.CRAWLING_PORTAL.get()).defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x, y - 1.0, z + 3.0), ((Block) ArphexModBlocks.CRAWLING_PORTAL.get()).defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x, y - 1.0, z + 4.0), ((Block) ArphexModBlocks.CRAWLING_PORTAL.get()).defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x, y - 1.0, z - 3.0), ((Block) ArphexModBlocks.CRAWLING_PORTAL.get()).defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x, y - 1.0, z - 4.0), ((Block) ArphexModBlocks.CRAWLING_PORTAL.get()).defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x + 1.0, y - 1.0, z + 1.0), ((Block) ArphexModBlocks.CRAWLING_PORTAL.get()).defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x + 1.0, y - 1.0, z - 1.0), ((Block) ArphexModBlocks.CRAWLING_PORTAL.get()).defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x - 1.0, y - 1.0, z + 1.0), ((Block) ArphexModBlocks.CRAWLING_PORTAL.get()).defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x - 1.0, y - 1.0, z - 1.0), ((Block) ArphexModBlocks.CRAWLING_PORTAL.get()).defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x - 3.0, y - 1.0, z - 1.0), ((Block) ArphexModBlocks.CRAWLING_PORTAL.get()).defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x - 3.0, y - 1.0, z + 1.0), ((Block) ArphexModBlocks.CRAWLING_PORTAL.get()).defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x - 1.0, y - 1.0, z - 3.0), ((Block) ArphexModBlocks.CRAWLING_PORTAL.get()).defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x + 1.0, y - 1.0, z - 3.0), ((Block) ArphexModBlocks.CRAWLING_PORTAL.get()).defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x + 3.0, y - 1.0, z - 1.0), ((Block) ArphexModBlocks.CRAWLING_PORTAL.get()).defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x + 3.0, y - 1.0, z + 1.0), ((Block) ArphexModBlocks.CRAWLING_PORTAL.get()).defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x - 1.0, y - 1.0, z + 3.0), ((Block) ArphexModBlocks.CRAWLING_PORTAL.get()).defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x + 1.0, y - 1.0, z + 3.0), ((Block) ArphexModBlocks.CRAWLING_PORTAL.get()).defaultBlockState(), 3);
                            level.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
                            if (level instanceof ServerLevel serverLevel) {
                                LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(serverLevel);
                                entityToSpawn.moveTo(Vec3.atBottomCenterOf(BlockPos.containing(x, y, z)));
                                entityToSpawn.setVisualOnly(true);
                                serverLevel.addFreshEntity(entityToSpawn);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void craftEvent(PlayerEvent.ItemCraftedEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide()) {
            ItemStack craftedItem = event.getCrafting();
            if (craftedItem.is(ArphexModItems.ABYSSAL_CRYSTAL.get())) {
                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    ItemStack slot = player.getInventory().getItem(i);
                    if (slot.is(ArphexModItems.ABYSSAL_CRYSTAL.get())) {
                        player.getInventory().removeItem(i, 1);
                        break;
                    }
                }
                for (int i = 0; i < 4; i++) {
                    player.addItem(ArphexModItems.ABYSSAL_SHARD.get().getDefaultInstance());
                }
                player.sendSystemMessage(Component.literal("You can't craft this item.").withStyle(ChatFormatting.RED));

            } else if (craftedItem.is(ArphexModItems.VOID_GEODE.get())) {
                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    ItemStack slot = player.getInventory().getItem(i);
                    if (slot.is(ArphexModItems.VOID_GEODE.get())) {
                        player.getInventory().removeItem(i, 1);
                        break;
                    }
                }
                for (int i = 0; i < 4; i++) {
                    player.addItem(ArphexModItems.VOID_GEODE_SHARD.get().getDefaultInstance());
                }
                player.sendSystemMessage(Component.literal("You can't craft this item.").withStyle(ChatFormatting.RED));

            } else if (craftedItem.is(ArphexModItems.FIRE_OPAL.get())) {
                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    ItemStack slot = player.getInventory().getItem(i);
                    if (slot.is(ArphexModItems.FIRE_OPAL.get())) {
                        player.getInventory().removeItem(i, 1);
                        break;
                    }
                }
                for (int i = 0; i < 4; i++) {
                    player.addItem(ArphexModItems.FIRE_OPAL_SHARD.get().getDefaultInstance());
                }
                player.sendSystemMessage(Component.literal("You can't craft this item.").withStyle(ChatFormatting.RED));
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
            if (tickCount % 200 == 0 && living instanceof Player) {
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
                    BeyonderUtil.setPathway(living, BeyonderClassInit.SAILOR.get());
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
                    if (living instanceof DayStalker dayStalker && dayStalker.isPhaseTwo()) {
                        boolean x = tag.getBoolean("isPhaseTwo");
                        tag.putBoolean("isPhaseTwo", false);
                        if (!x) {
                            multiplyMaxHealth(living, 2);
                        }
                        multiplyDamage(dayStalker, 2.6);
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
                    if (living instanceof NightProwler nightProwler && nightProwler.isPhaseTwo()) {
                        boolean x = tag.getBoolean("isPhaseTwo");
                        multiplyDamage(nightProwler, 0.75);
                        if (!x) {
                            tag.putBoolean("isPhaseTwo", false);
                            multiplyMaxHealth(living, 2);
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
                    multiplyDamage(living, 1.0);
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
                    multiplyDamage(living, 1.4);
                } else if (type == TerramityModEntities.HELLROK.get()) {
                    multiplyDamage(living, 1.3);
                    living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1, 40, false, false));
                }

                // Sequence 7
                else if (type == ACEntityRegistry.FORSAKEN.get()) {
                    multiplyDamage(living, 1.5);
                } else if (type == ArphexModEntities.SPIDER_SNATCHER.get()) {
                    multiplyDamage(living, 1.6);
                } else if (type == MacabreModEntities.CRAWLER.get()) {
                    multiplyDamage(living, 6.0);
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
                    multiplyDamage(living, 0.8);
                    multiplyMaxHealth(living, 1.5);
                } else if (type == AnimatedmobsmodModEntities.ENDER_KING.get()) {
                    multiplyDamage(living, 0.8);
                } else if (living.getClass().getSimpleName().equals("LichEntity")) {
                    multiplyDamage(living, 1.1);
                } else if (type == AetherEntityTypes.SUN_SPIRIT.get()) {
                    multiplyDamage(living, 1.2);
                } else if (living.getClass().getSimpleName().equals("GauntletEntity")) {
                    multiplyDamage(living, 2.0);
                } else if (type == ArphexModEntities.SOLIFUGE_SKULKER.get()) {
                    multiplyDamage(living, 1.8);
                } else if (living.getClass().getSimpleName().equals("ObsidilithEntity")) {
                    multiplyDamage(living, 1.1);

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
                    multiplyDamage(living, 2.0);
                    multiplyMaxHealth(living, 1.4);
                } else if (type == MacabreModEntities.THE_HOLLOW_MAN.get()) {
                    multiplyDamage(living, 1.7);
                } else if (type == BornInChaosV1ModEntities.SIR_PUMPKINHEAD.get()) {
                    multiplyDamage(living, 2.8);
                } else if (living.getName().getString().toLowerCase().contains("dyrolian")) {
                    multiplyDamage(living, 1.2);

                    // Sequence 5
                } else if (type == EntityRegistry.CHAOS_MONARCH.get()) {
                    multiplyDamage(living, 4.5);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.MONSTER.get());
                    BeyonderUtil.setSequence(living, 7);
                } else if (type == ArphexModEntities.CRAB_CONSTRICTOR.get()) {
                    multiplyDamage(living, 2.0);
                } else if (type == ArphexModEntities.SPIDER_REAPER.get()) {
                    multiplyDamage(living, 2.0);
                } else if (type == AquamiraeEntities.CAPTAIN_CORNELIA.get()) {
                    multiplyDamage(living, 1.6);
                    multiplyMaxHealth(living, 1.3);
                } else if (type == EntityRegistry.DRAUGR_BOSS.get()) {
                    multiplyDamage(living, 1.2);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.SPECTATOR.get());
                    BeyonderUtil.setSequence(living, 7);
                } else if (type == EntityRegistry.NIGHT_SHADE.get()) {
                    multiplyDamage(living, 1.5);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.WARRIOR.get());
                    BeyonderUtil.setSequence(living, 7);
                } else if (type == EntityRegistry.ACCURSED_LORD_BOSS.get()) {
                    multiplyDamage(living, 2.0);
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
                    multiplyDamage(living, 1.7);
                } else if (type == ACEntityRegistry.LUXTRUCTOSAURUS.get()) {
                    multiplyDamage(living, 4.0);
                } else if (type == com.github.L_Ender.cataclysm.init.ModEntities.THE_LEVIATHAN.get()) {
                    BeyonderUtil.setPathway(living, BeyonderClassInit.MONSTER.get());
                    BeyonderUtil.setSequence(living, 6);
                } else if (type == com.github.L_Ender.cataclysm.init.ModEntities.SCYLLA.get()) {
                    multiplyDamage(living, 2.0);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.WARRIOR.get());
                    BeyonderUtil.setSequence(living, 7);
                } else if (type == TerramityModEntities.GOB.get()) {
                    multiplyDamage(living, 3.6);
                    living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 1, false, false));
                } else if (type == ArphexModEntities.SPIDER_PROWLER.get()) {
                    multiplyDamage(living, 2.5);
                    multiplyMaxHealth(living, 1.4);
                } else if (living.getName().getString().equalsIgnoreCase("horseman")) {
                    BeyonderUtil.setPathway(living, BeyonderClassInit.SPECTATOR.get());
                    BeyonderUtil.setSequence(living, 6);
                } else if (type == MacabreModEntities.VALAMON.get()) {
                    multiplyDamage(living, 2.1);
                } else if (type == EntityInit.NAMELESS_GUARDIAN.get()) {
                    BeyonderUtil.setPathway(living, BeyonderClassInit.WARRIOR.get());
                    BeyonderUtil.setSequence(living, 6);

                    // Sequence 3
                } else if (living.getName().getString().toLowerCase().contains("vessel")) {
                    BeyonderUtil.setPathway(living, BeyonderClassInit.MONSTER.get());
                    BeyonderUtil.setSequence(living, 5);
                    multiplyDamage(living, 2.5);
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
            if (living instanceof UltraSnifferEntity ultraSniffer) {
                if (ultraSniffer.getTarget() == null) {
                    for (Player player : ultraSniffer.level().getEntitiesOfClass(Player.class, ultraSniffer.getBoundingBox().inflate(50))) {
                        if (!player.isCreative() && !player.isSpectator()) {
                            ultraSniffer.setTarget(player);
                        }
                    }
                    float health = ultraSniffer.getHealth();
                    if (Float.isNaN(health) || health < 0.0F) {
                        ultraSniffer.setHealth(0.0F);
                    }
                    boolean isPhaseTwo = ultraSniffer.getEntityData().get(UltraSnifferEntity.DATA_phase_two);
                    if (isPhaseTwo) {
                        int fullHeal = ultraSniffer.getPersistentData().getInt("PtDFullHeal");
                        if (fullHeal <= 100) {
                            ultraSniffer.getPersistentData().putInt("PtDFullHeal", fullHeal + 1);
                            ultraSniffer.setHealth(ultraSniffer.getMaxHealth());
                            ultraSniffer.getPersistentData().putInt("age", 0);
                        }
                    } else if (ultraSniffer.tickCount <= 100) {
                        ultraSniffer.setHealth(ultraSniffer.getMaxHealth());
                        ultraSniffer.getPersistentData().putInt("age", 0);
                    }
                }
                if (BeyonderUtil.currentPathwayMatchesNoException(living, BeyonderClassInit.SPECTATOR.get())) {
                    multiplyDamage(living, 0.8);
                } else if (BeyonderUtil.currentPathwayMatchesNoException(living, BeyonderClassInit.SAILOR.get())) {
                    multiplyDamage(living, 0.9);
                } else if (BeyonderUtil.currentPathwayMatchesNoException(living, BeyonderClassInit.MONSTER.get())) {
                    if (!ultraSniffer.getPersistentData().getBoolean("PtDGaveLuck")) {
                        ultraSniffer.getPersistentData().putDouble("luck", 5000);
                        ultraSniffer.getPersistentData().putBoolean("PtDGaveLuck", true);
                    }
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
    public static void livingDropEvent(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        if (!entity.level().isClientSide()) {
            if (entity instanceof CentipedeEvictorEntity) {
                event.setCanceled(true);
                ItemStack stack = new ItemStack(ArphexModItems.ABYSSAL_SHARD.get());
                ItemEntity itemEntity = new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), stack);
                itemEntity.setNoPickUpDelay();
                itemEntity.teleportTo(entity.getX(), entity.getY(), entity.getZ());
                itemEntity.setUnlimitedLifetime();
                entity.level().addFreshEntity(itemEntity);
            } else if (entity instanceof SpiderProwlerEntity) {
                event.setCanceled(true);
                ItemStack stack = new ItemStack(ArphexModItems.FIRE_OPAL_SHARD.get());
                ItemEntity itemEntity = new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), stack);
                itemEntity.setNoPickUpDelay();
                itemEntity.teleportTo(entity.getX(), entity.getY(), entity.getZ());
                itemEntity.setUnlimitedLifetime();
                entity.level().addFreshEntity(itemEntity);
            }
        }
    }

    @SubscribeEvent
    public static void livingDeathEvent(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        if (!event.getEntity().level().isClientSide()) {
            if (entity.getName().getString().contains("vessel")) {
                ItemStack stack = new ItemStack(TerramityModItems.POCKET_UNIVERSE.get());
                ItemEntity itemEntity = new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), stack);
                itemEntity.setNoPickUpDelay();
                itemEntity.teleportTo(entity.getX(), entity.getY(), entity.getZ());
                itemEntity.setUnlimitedLifetime();
                entity.level().addFreshEntity(itemEntity);
            } else if (event.getEntity() instanceof DuskrokEntity) {
                ItemStack stack = new ItemStack(Items.NETHERITE_SCRAP);
                ItemEntity itemEntity = new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), stack);
                itemEntity.setNoPickUpDelay();
                itemEntity.teleportTo(entity.getX(), entity.getY(), entity.getZ());
                itemEntity.setUnlimitedLifetime();
                entity.level().addFreshEntity(itemEntity);
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
                if (projectile.getOwner() != null) {
                    if (projectile.getOwner() instanceof Player) {
                        event.setAmount(event.getAmount() * 2.5f);
                    } else {
                        event.setAmount(event.getAmount() * 0.6f);
                    }
                }
            } else if (directSource instanceof SolarisBomb) {
                event.setAmount(event.getAmount() * 5.0f);
            } else if (directSource instanceof CrescentiaDragon) {
                event.setAmount(event.getAmount() * 2.0f);
            } else if (directSource instanceof FrostboundShard) {
                event.setAmount(event.getAmount() * 6.5f);
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
            } else if (directSource instanceof Tidal_Tentacle_Entity tidalTentacleEntity) {
                if (tidalTentacleEntity.getCreatorEntity() != null && tidalTentacleEntity.getCreatorEntity() instanceof Player) {
                    event.setAmount(event.getAmount() * 4.0f);
                }
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
            } else if (directSource instanceof Cursed_Sandstorm_Entity projectile) {
                if (projectile.getOwner() != null && projectile.getOwner() instanceof Player) {
                    event.setAmount(event.getAmount() * 1.5f);
                }
            } else if (directSource instanceof FreyrSwordEntity) {
                event.setAmount(event.getAmount() * 1.8f);
            } else if (directSource instanceof EntityGuardianLaser projectile) {
                if (projectile.getOwner() != null && !(projectile.getOwner() instanceof Player)) {
                    event.setAmount(event.getAmount() * 0.6f);
                }
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

                // Sequence 9
                if (type == ModEntities.Overgrown_colossus.get()) {
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
                    multiplyMaxHealth(living, 10.0);
                    multiplyDamage(living, 0.4);
                } else if (type == ArphexModEntities.LONG_LEGS_FLY.get()) {
                    multiplyMaxHealth(living, 4.0);
                    multiplyDamage(living, 1.0);
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
                } else if (living.getName().getString().toLowerCase().contains("terrible") || living.getName().getString().toLowerCase().contains("puny")) { //Terrible Ten
                    multiplyMaxHealth(living, 1.0);
                    multiplyDamage(living, 1.3);
                } else if (type == AMEntityRegistry.WARPED_MOSCO.get()) {
                    multiplyMaxHealth(living, 1.0);
                    multiplyDamage(living, 1.3);
                } else if (type == EntityType.ELDER_GUARDIAN) {
                    multiplyMaxHealth(living, 1.0);
                    multiplyDamage(living, 1.3);
                } else if (type == ModRegistry.MUTANT_ENDERMAN_ENTITY_TYPE.get()) {
                    multiplyMaxHealth(living, 1.4);
                    multiplyDamage(living, 1.2);
                } else if (living.getName().getString().toLowerCase().contains("aero_guardian")) { //Aero Guardian
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
                    multiplyDamage(living, 1.4);
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
                    multiplyDamage(living, 1.6);
                } else if (type == MacabreModEntities.CRAWLER.get()) {
                    multiplyMaxHealth(living, 2.0);
                    multiplyDamage(living, 6.0);
                } else if (living.getName().getString().toLowerCase().contains("doomharbor")) { //Doomharbor Lich
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
                } else if (living.getName().getString().toLowerCase().contains("plague_bringer")) { //Plague Bringer
                    multiplyMaxHealth(living, 0.7);
                    multiplyDamage(living, 1.7);
                } else if (type == DDEntities.STALKER.get()) {
                    multiplyMaxHealth(living, 1.0);
                    multiplyDamage(living, 1.2);
                } else if (type == AnimatedmobsmodModEntities.ENDER_KING.get()) {
                    multiplyMaxHealth(living, 0.8);
                    multiplyDamage(living, 0.8);
                } else if (entity.getClass().getSimpleName().equals("LichEntity")) { //Lich (Bosses of Mass Destruction)
                    multiplyMaxHealth(living, 1.0);
                    multiplyDamage(living, 1.1);
                } else if (type == ModEntities.Withered_Abomination.get()) {
                    multiplyMaxHealth(living, 1.2);
                } else if (type == AetherEntityTypes.SUN_SPIRIT.get()) {
                    multiplyMaxHealth(living, 1.0);
                    multiplyDamage(living, 1.2);
                } else if (entity.getClass().getSimpleName().equals("GauntletEntity")) { //Nether Gauntlet
                    multiplyMaxHealth(living, 1.1);
                    multiplyDamage(living, 2.0);
                } else if (type == ArphexModEntities.SOLIFUGE_SKULKER.get()) {
                    multiplyMaxHealth(living, 1.3);
                    multiplyDamage(living, 1.8);
                } else if (entity.getClass().getSimpleName().equals("ObsidilithEntity")) { //Obsidilith
                    multiplyMaxHealth(living, 1.8);
                    multiplyDamage(living, 1.2);

                    //Sequence 6
                } else if (type == ModEntityTypes.Spiritcaller.get()) {
                    multiplyMaxHealth(living, 1.3);
                } else if (type == EntityType.WITHER) {
                    multiplyMaxHealth(living, 1.0);
                    multiplyDamage(living, 1.1);
                } else if (entity.getClass().getSimpleName().equals("VoidBlossomEntity")) { //Void Blossom
                    multiplyMaxHealth(living, 1.2);
                    multiplyDamage(living, 1.3);
                } else if (type == ModEntityTypes.Freakager.get()) {
                    multiplyMaxHealth(living, 1.5);
                    multiplyDamage(living, 1.5);
                } else if (type == ModEntityTypes.Ragno.get()) {
                    multiplyMaxHealth(living, 1.5);
                } else if (type == ArphexModEntities.WASP_NEMESIS.get()) {
                    multiplyMaxHealth(living, 1.5);
                    multiplyDamage(living, 1.9);
                } else if (type == ModEntityTypes.Magispeller.get()) {
                    multiplyMaxHealth(living, 1.5);
                    multiplyDamage(living, 1.5);
                } else if (type == AwakenedBossesModEntities.HEROBRINE.get()) {
                    multiplyMaxHealth(living, 1.0);
                    multiplyDamage(living, 1.2);
                } else if (type == BornInChaosV1ModEntities.LIFESTEALER.get()) {
                    multiplyMaxHealth(living, 1.5);
                    multiplyDamage(living, 1.5);
                } else if (type == ModEntities.Lava_eater.get()) {
                    multiplyMaxHealth(living, 1.7);
                    multiplyDamage(living, 1.2);
                } else if (type == MacabreModEntities.THE_HOLLOW_MAN.get()) {
                    multiplyMaxHealth(living, 1.2);
                    multiplyDamage(living, 1.7);
                } else if (type == BornInChaosV1ModEntities.SIR_PUMPKINHEAD.get()) {
                    multiplyMaxHealth(living, 2.2);
                    multiplyDamage(living, 2.8);
                } else if (living.getName().getString().toLowerCase().contains("dyrolian")) {
                    multiplyDamage(living, 1.3);
                    multiplyMaxHealth(living, 0.5);

                    // Sequence 5
                } else if (type == EntityRegistry.CHAOS_MONARCH.get()) {
                    multiplyMaxHealth(living, 1.8);
                    multiplyDamage(living, 4.5);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.MONSTER.get());
                    BeyonderUtil.setSequence(living, 7);
                    living.getPersistentData().putDouble("luck", 500);
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
                    multiplyMaxHealth(living, 2.5);
                    multiplyDamage(living, 1.7);
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
                    multiplyMaxHealth(living, 2.5);
                    multiplyDamage(living, 4.0);
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
                    multiplyDamage(living, 3.6);
                } else if (type == ArphexModEntities.SPIDER_PROWLER.get()) {
                    multiplyMaxHealth(living, 4.0);
                    multiplyDamage(living, 2.5);
                } else if (living.getName().getString().equalsIgnoreCase("horseman")) { //Pumpkin Horseman
                    multiplyMaxHealth(living, 1.5);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.SPECTATOR.get());
                    BeyonderUtil.setSequence(living, 6);
                } else if (type == MacabreModEntities.VALAMON.get()) {
                    multiplyMaxHealth(living, 1.4);
                    multiplyDamage(living, 1.6);
                } else if (type == EntityInit.NAMELESS_GUARDIAN.get()) {
                    multiplyMaxHealth(living, 1.7);
                    multiplyDamage(living, 2.2);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.WARRIOR.get());
                    BeyonderUtil.setSequence(living, 6);
                } else if (type == com.github.L_Ender.cataclysm.init.ModEntities.MALEDICTUS.get()) {
                    multiplyMaxHealth(living, 4.0);
                    multiplyDamage(living, 2.5);
                } else if (type == com.github.L_Ender.cataclysm.init.ModEntities.ANCIENT_ANCIENT_REMNANT.get()) {
                    multiplyMaxHealth(living, 4.0);
                    multiplyDamage(living, 2.5);


                    // Sequence 3
                } else if (living.getName().getString().toLowerCase().contains("vessel")) { //Vessel of Calamity
                    multiplyMaxHealth(living, 4.0);
                    multiplyDamage(living, 2.5);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.MONSTER.get());
                    BeyonderUtil.setSequence(living, 5);
                    ProbabilityManipulationFortune.giveFortuneEvents(living);
                    living.getPersistentData().putDouble("luck", 3000);
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
                    multiplyMaxHealth(living, 4.0);
                    multiplyDamage(living, 1.1);
                    BeyonderUtil.setPathway(living, BeyonderClassInit.WARRIOR.get());
                    BeyonderUtil.setSequence(living, 4);
                } else if (type == EntityRegistry.NIGHT_PROWLER.get()) {
                    multiplyMaxHealth(living, 3.0);
                    multiplyDamage(living, 0.4);
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
            commands.performPrefixedCommand(commandSource, "beyonderentity add soulsweapons:chaos_monarch lotm:monster 7");
            commands.performPrefixedCommand(commandSource, "beyonderentity add legendary_monsters:cloud_golem lotm:sailor 6");
            commands.performPrefixedCommand(commandSource, "beyonderentity add faded_conquest_2:vessel_of_calamity lotm:monster 5");
            commands.performPrefixedCommand(commandSource, "beyonderentity add soulsweapons:day_stalker lotm:warrior 4");
            commands.performPrefixedCommand(commandSource, "beyonderentity add soulsweapons:draugr_boss lotm:spectator 7");
            commands.performPrefixedCommand(commandSource, "beyonderentity add soulsweapons:night_shade lotm:warrior 7");
            commands.performPrefixedCommand(commandSource, "beyonderentity add cataclysm:the_leviathan lotm:monster 6");
            commands.performPrefixedCommand(commandSource, "beyonderentity add soulsweapons:moonknight lotm:warrior 5");
            commands.performPrefixedCommand(commandSource, "beyonderentity add soulsweapons:night_prowler lotm:sailor 4");
            commands.performPrefixedCommand(commandSource, "beyonderentity add sleepy_hollows:horseman lotm:spectator 6");
            commands.performPrefixedCommand(commandSource, "beyonderentity add born_in_chaos_v1:lord_pumpkinhead lotm:spectator 5");
            commands.performPrefixedCommand(commandSource, "beyonderentity add eeeabsmobs:nameless_guardian lotm:warrior 6");
            commands.performPrefixedCommand(commandSource, "beyonderentity add terramity:trial_guardian lotm:sailor 5");
            commands.performPrefixedCommand(commandSource, "beyonderentity add soulsweapons:chaos_monarch lotm:monster 7");
            commands.performPrefixedCommand(commandSource, "beyonderentity add soulsweapons:chaos_monarch lotm:monster 7");
            int random = (int) BeyonderUtil.getPositiveRandomInRange(4);
            if (random == 0) {
                commands.performPrefixedCommand(commandSource, "beyonderentity add terramity:ultra_sniffer lotm:spectator 3");
            } else if (random == 1) {
                commands.performPrefixedCommand(commandSource, "beyonderentity add terramity:ultra_sniffer lotm:warrior 3");
            } else if (random == 2) {
                commands.performPrefixedCommand(commandSource, "beyonderentity add terramity:ultra_sniffer lotm:sailor 3");
            } else {
                commands.performPrefixedCommand(commandSource, "beyonderentity add terramity:ultra_sniffer lotm:monster 3");
            }

        } catch (Exception e) {
            PTD.LOGGER.info("Failed to execute beyonderrecipe load command: {}", e.getMessage());
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.getServer().getTickCount() % 1200 == 0) {
            MinecraftServer server = event.getServer();
            float foundSniffers = 0;
            for (ServerLevel level : server.getAllLevels()) {
                for (Entity entity : level.getAllEntities()) {
                    if (entity.getType() == TerramityModEntities.ULTRA_SNIFFER.get()) {
                        foundSniffers++;
                    }
                }
            }
            if (foundSniffers == 0) {
                Commands commands = server.getCommands();
                CommandSourceStack commandSource = server.createCommandSourceStack();
                int random = (int) BeyonderUtil.getPositiveRandomInRange(4);
                if (random == 0) {
                    commands.performPrefixedCommand(commandSource, "beyonderentity add terramity:ultra_sniffer lotm:spectator 3");
                } else if (random == 1) {
                    commands.performPrefixedCommand(commandSource, "beyonderentity add terramity:ultra_sniffer lotm:warrior 3");
                } else if (random == 2) {
                    commands.performPrefixedCommand(commandSource, "beyonderentity add terramity:ultra_sniffer lotm:sailor 3");
                } else {
                    commands.performPrefixedCommand(commandSource, "beyonderentity add terramity:ultra_sniffer lotm:monster 3");
                }
            }
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
        if (!living.getPersistentData().getBoolean("maxHealthMultiplied")) {
            float multiplierAmount = (float) multiplier;
            float maxHealth = living.getMaxHealth();
            AttributeInstance maxHealthAttribute = living.getAttribute(Attributes.MAX_HEALTH);
            if (maxHealthAttribute != null) {
                if (maxHealth < 10000) {
                    maxHealthAttribute.setBaseValue(maxHealth * multiplierAmount);
                }
            }
            living.setHealth(maxHealth * multiplierAmount);
            living.getPersistentData().putBoolean("maxHealthMultiplied", true);
            LOTM.LOGGER.info("Multiplied{}'s health by {}", living.getName().getString(), multiplier);
        }
    }

    public static void multiplyMaxHealthUltraSniffer(LivingEntity living, double multiplier) {
        if (!living.getPersistentData().getBoolean("maxHealthMultiplied")) {

            float multiplierAmount = (float) multiplier;
            float maxHealth = living.getMaxHealth();
            AttributeInstance maxHealthAttribute = living.getAttribute(Attributes.MAX_HEALTH);
            if (maxHealthAttribute != null) {
                maxHealthAttribute.setBaseValue(maxHealth * multiplierAmount + 1);
            }
            living.setHealth(maxHealth * multiplierAmount);
            living.getPersistentData().putBoolean("maxHealthMultiplied", true);
            LOTM.LOGGER.info("Multiplied{}'s health by {}", living.getName().getString(), multiplier);
        }
    }

    private static void multiplyDamage(LivingEntity entity, double multiplier) {
        if (!entity.getPersistentData().getBoolean("damageMultiplied")) {
            CompoundTag tag = entity.getPersistentData();
            tag.putDouble("PTDDamageMultiplier", multiplier);
            entity.getPersistentData().putBoolean("damageMultiplied", true);
        }
    }
}