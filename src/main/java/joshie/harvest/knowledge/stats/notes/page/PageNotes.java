package joshie.harvest.knowledge.stats.notes.page;

import joshie.harvest.api.knowledge.Category;
import joshie.harvest.api.knowledge.Note;
import joshie.harvest.core.base.gui.BookPage;
import joshie.harvest.knowledge.HFKnowledge;
import joshie.harvest.knowledge.stats.GuiStats;
import joshie.harvest.knowledge.stats.button.ButtonNext;
import joshie.harvest.knowledge.stats.button.ButtonPrevious;
import joshie.harvest.knowledge.stats.button.ButtonTabRight;
import joshie.harvest.knowledge.stats.notes.button.ButtonNote;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class PageNotes extends BookPage<GuiStats> {
    private static Note note = HFKnowledge.BLUEPRINTS;
    private final List<Note> list;

    PageNotes(Category category, ItemStack stack) {
        super("note", category.getUnlocalizedName(), stack);
        this.list = new ArrayList<>();
        for (Note note: Note.REGISTRY) {
            if (note.getCategory() == category) {
                this.list.add(note);
            }
        }
    }

    public static void setNote(Note note) {
        PageNotes.note = note;
    }

    @Override
    public void initGui(GuiStats gui, List<GuiButton> buttonList, List<GuiLabel> labelList) {
        super.initGui(gui, buttonList, labelList);
        labelList.add(createLabel(TextFormatting.UNDERLINE + getDisplayName(), labelList.size(), gui.guiLeft + 60, gui.guiTop + 8, 60, 20));
        buttonList.add(new ButtonTabRight(gui, PageFarming.INSTANCE, buttonList.size(), 308, 32));
        buttonList.add(new ButtonTabRight(gui, PageActivities.INSTANCE, buttonList.size(), 308, 66));
        buttonList.add(new ButtonTabRight(gui, PageTown.INSTANCE, buttonList.size(), 308, 100));
        int j = 0;
        int k = 0;
        int added = 0;
        for (int i = start * 56; added < start * 56 + 56 && i < list.size(); i++) {
            Note note = list.get(i);
            if (k == 7) {
                k = 0;
                j++;
            }

            k++;

            added++;
            buttonList.add(new ButtonNote(gui, note, buttonList.size(), 3 + k * 18, 24 + j * 18));
        }

        int maxStart = list.size() / 112;
        if (start < maxStart) buttonList.add(new ButtonNext(gui, buttonList.size(), 273, 172));
        if (start != 0) buttonList.add(new ButtonPrevious(gui, buttonList.size(), 20, 172));
    }

    @Override
    public void drawScreen(int x, int y) {
        drawUnicodeFont(note.getDescription(), 165, 20, 125);
        if (note.getRender() != null) note.getRender().drawScreen(gui.mc, gui, x, y,  gui.guiLeft, gui.guiTop);
    }
}
