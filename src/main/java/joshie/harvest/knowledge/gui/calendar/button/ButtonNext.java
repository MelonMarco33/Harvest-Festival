package joshie.harvest.knowledge.gui.calendar.button;

import joshie.harvest.api.calendar.Season;
import joshie.harvest.calendar.CalendarHelper;
import joshie.harvest.core.helpers.TextHelper;
import joshie.harvest.knowledge.gui.calendar.GuiCalendar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

import static joshie.harvest.knowledge.gui.calendar.GuiCalendar.CALENDAR_TEXTURE;

public class ButtonNext extends GuiButton {
    private final GuiCalendar gui;

    public ButtonNext(GuiCalendar calendar, int buttonId, int x, int y) {
        super(buttonId, x, y, "");
        gui = calendar;
        width = 12;
        height = 14;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (visible) {
            mc.getTextureManager().bindTexture(CALENDAR_TEXTURE);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
            int state = getHoverState(hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            drawTexturedModalRect(xPosition, yPosition, 220 + state * 12, 22, width, height);
            if (hovered) gui.addTooltip(TextHelper.translate("stats.next"));
            GlStateManager.color(1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        Season season = GuiCalendar.season;
        if (season == Season.WINTER) {
            GuiCalendar.season = Season.SPRING;
            GuiCalendar.year++;
        } else GuiCalendar.season = CalendarHelper.SEASONS[season.ordinal() + 1];

        //Init the gui to reload the buttons
        gui.initGui(); ///RELOOOOOOAD????????????????????????!!!!!!!!!!!!??
    }
}
