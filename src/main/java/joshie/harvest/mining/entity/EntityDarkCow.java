package joshie.harvest.mining.entity;

import joshie.harvest.core.achievements.HFAchievements;
import joshie.harvest.core.helpers.EntityHelper;
import joshie.harvest.mining.MiningHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static joshie.harvest.mining.MiningTicker.MYSTRIL_FLOOR;

public class EntityDarkCow extends EntityMob {
    public EntityDarkCow(World world) {
        super(world);
        setSize(1.4F, 1.4F);
        setPathPriority(PathNodeType.WATER, 0.0F);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(34.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }

    @Override
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);
        EntityPlayer player = EntityHelper.getPlayerFromSource(cause);
        if (player != null) {
            player.addStat(HFAchievements.killCow);
        }
    }

    @Override
    protected boolean isValidLightLevel() {
        return MiningHelper.getFloor((int)posX >> 4, (int) posY) >= MYSTRIL_FLOOR &&
                EntityHelper.getEntities(EntityDarkCow.class, worldObj, new BlockPos(this), 32D, 32D).size() < 1;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_COW_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound() {
        return SoundEvents.ENTITY_COW_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_COW_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F);
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public float getEyeHeight() {
        return 1.3F;
    }
}
