package com.waveclient.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class WaveScreen extends Screen {
    private static final int BG = 0xE80A0D18;
    private static final int PANEL = 0xF0141928;
    private static final int PANEL_HI = 0xFF202A40;
    private static final int PURPLE = 0xFF9276FF;
    private static final int BLUE = 0xFF49B8FF;
    private static final int WHITE = 0xFFF2F4FF;
    private static final int MUTED = 0xFF929BB2;
    private final List<String> categories = List.of("Overview", "HUD", "Visual", "Profiles", "Settings");
    private final Map<String, List<String>> modules = new LinkedHashMap<>();
    private final Map<String, Boolean> enabled = new LinkedHashMap<>();
    private String category = "Overview";
    private String profile = "Default";

    public WaveScreen() {
        super(Text.literal("Wave Client"));
        modules.put("Overview", List.of("Welcome to Wave", "Fair-play client utilities", "Right Shift opens this menu"));
        modules.put("HUD", List.of("FPS Counter", "Coordinates", "Armor HUD", "Potion HUD", "Clock"));
        modules.put("Visual", List.of("Crosshair Style", "Wave Accent", "Monochrome Blocks Pack", "Menu Blur"));
        modules.put("Profiles", List.of("Default", "Minimal", "Stream"));
        modules.put("Settings", List.of("Purple / Blue Theme", "Open with Right Shift", "Reset to Defaults"));
        for (List<String> rows : modules.values()) for (String row : rows) enabled.put(row, false);
        enabled.put("FPS Counter", true);
        enabled.put("Coordinates", true);
        enabled.put("Wave Accent", true);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, width, height, 0x99060910);
        int panelW = Math.min(850, width - 36);
        int panelH = Math.min(520, height - 32);
        int x = (width - panelW) / 2;
        int y = (height - panelH) / 2;
        context.fill(x, y, x + panelW, y + panelH, BG);
        context.fill(x, y, x + 4, y + panelH, PURPLE);
        context.fill(x + 4, y, x + 7, y + panelH, BLUE);

        int sideW = 174;
        context.fill(x + 7, y, x + sideW, y + panelH, PANEL);
        context.drawText(textRenderer, Text.literal("WAVE"), x + 24, y + 22, WHITE, false);
        context.drawText(textRenderer, Text.literal("CLIENT  •  1.21.11"), x + 24, y + 40, MUTED, false);
        int navY = y + 84;
        for (String item : categories) {
            boolean active = item.equals(category);
            context.fill(x + 17, navY - 5, x + sideW - 13, navY + 17, active ? PANEL_HI : PANEL);
            if (active) context.fill(x + 17, navY - 5, x + 20, navY + 17, PURPLE);
            context.drawText(textRenderer, Text.literal(item), x + 29, navY, active ? WHITE : MUTED, false);
            navY += 34;
        }
        context.drawText(textRenderer, Text.literal("LOCAL PROFILE"), x + 24, y + panelH - 63, MUTED, false);
        context.drawText(textRenderer, Text.literal(profile), x + 24, y + panelH - 43, WHITE, false);

        int contentX = x + sideW + 24;
        context.drawText(textRenderer, Text.literal(category), contentX, y + 24, WHITE, false);
        context.drawText(textRenderer, Text.literal("Purple and blue client-side utilities"), contentX, y + 43, MUTED, false);
        int listY = y + 78;
        for (String row : modules.getOrDefault(category, List.of())) {
            int rowH = category.equals("Overview") ? 74 : 52;
            context.fill(contentX, listY, x + panelW - 22, listY + rowH - 6, PANEL);
            context.drawText(textRenderer, Text.literal(row), contentX + 14, listY + 10, WHITE, false);
            if (category.equals("Overview")) {
                context.drawText(textRenderer, Text.literal("A clean menu foundation for fair-play HUD and visual options."), contentX + 14, listY + 31, MUTED, false);
            } else if (category.equals("Profiles")) {
                context.drawText(textRenderer, Text.literal("Click to activate this local preset"), contentX + 14, listY + 29, MUTED, false);
                context.fill(x + panelW - 86, listY + 13, x + panelW - 36, listY + 34, row.equals(profile) ? 0xFF184F55 : PANEL_HI);
                context.drawText(textRenderer, Text.literal(row.equals(profile) ? "ACTIVE" : "LOAD"), x + panelW - 78, listY + 19, WHITE, false);
            } else {
                boolean on = enabled.getOrDefault(row, false);
                context.drawText(textRenderer, Text.literal("Client-side display option"), contentX + 14, listY + 29, MUTED, false);
                int sx = x + panelW - 70;
                context.fill(sx, listY + 16, sx + 40, listY + 34, on ? 0xFF5946B8 : 0xFF343A49);
                context.fill(on ? sx + 23 : sx + 3, listY + 18, on ? sx + 37 : sx + 17, listY + 32, WHITE);
            }
            listY += rowH;
        }
        context.drawText(textRenderer, Text.literal("WAVE  /  Right Shift  /  ESC to close"), contentX, y + panelH - 25, MUTED, false);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int panelW = Math.min(850, width - 36);
        int panelH = Math.min(520, height - 32);
        int x = (width - panelW) / 2;
        int y = (height - panelH) / 2;
        int sideW = 174;
        int navY = y + 79;
        for (String item : categories) {
            if (mouseX >= x + 17 && mouseX <= x + sideW - 13 && mouseY >= navY - 5 && mouseY <= navY + 17) {
                category = item;
                return true;
            }
            navY += 34;
        }
        int contentX = x + sideW + 24;
        int listY = y + 78;
        for (String row : modules.getOrDefault(category, List.of())) {
            if (mouseX >= contentX && mouseX <= x + panelW - 22 && mouseY >= listY && mouseY <= listY + 46) {
                if (category.equals("Profiles")) profile = row;
                else if (!category.equals("Overview")) enabled.put(row, !enabled.getOrDefault(row, false));
                return true;
            }
            listY += category.equals("Overview") ? 74 : 52;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() { return false; }
}
