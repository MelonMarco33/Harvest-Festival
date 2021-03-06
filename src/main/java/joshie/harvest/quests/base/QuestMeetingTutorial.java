package joshie.harvest.quests.base;

import joshie.harvest.api.buildings.Building;
import joshie.harvest.api.npc.NPC;
import joshie.harvest.api.npc.NPCEntity;
import joshie.harvest.api.quests.Quest;
import joshie.harvest.api.quests.QuestQuestion;
import joshie.harvest.town.TownHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Set;

import static joshie.harvest.quests.Quests.YULIF_MEET;

public abstract class QuestMeetingTutorial extends QuestQuestion {
    protected final Building building;
    protected final ItemStack buildingStack;

    public QuestMeetingTutorial(QuestSelection selection, Building building, NPC... npc) {
        super(selection);
        this.setNPCs(npc);
        this.building = building;
        this.buildingStack = building.getSpawner();
    }

    @Override
    public boolean canStartQuest(Set<Quest> active, Set<Quest> finished) {
        return finished.contains(YULIF_MEET);
    }

    @Override
    public boolean isNPCUsed(EntityPlayer player, NPCEntity entity) {
        return getNPCs().contains(entity.getNPC()) && hasBuilding(player);
    }

    protected boolean hasBuilding(EntityPlayer player) {
        return TownHelper.getClosestTownToEntity(player, false).hasBuilding(building);
    }
}
