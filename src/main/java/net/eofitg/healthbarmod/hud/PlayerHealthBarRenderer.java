package net.eofitg.healthbarmod.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerHealthBarRenderer {

    // —— Configurable via commands —— //
    public static boolean ENABLED = true;             // Whether health bar rendering is enabled
    public static boolean SHOW_SELF = false;          // Whether to show own health bar
    public static boolean HIDE_WHEN_SNEAKING = false; // Hide when sneaking
    public static double  MAX_DISTANCE = 24.0;        // Maximum render distance
    public static float   SCALE = 0.0165F;            // Overall scale (font/bar size)
    public static float   BAR_WIDTH = 70f;            // Bar width in pixels (before scaling)
    public static float   BAR_HEIGHT = 7f;            // Bar height in pixels
    public static float   BAR_MARGIN = 10f;           // Margin between bar and name text (pixels)
    public static float   X_OFFSET = 0f;              // Horizontal offset (block units)
    public static float   Y_OFFSET = 1f;              // Vertical offset (block units)
    public static float   Z_OFFSET = 0f;              // Depth offset (block units)
    public static boolean VERTICAL = false;           // false = horizontal, true = vertical

    private final Minecraft mc = Minecraft.getMinecraft();

    // Use RenderPlayerEvent.Post to overlay the health bar after player model is rendered
    @SubscribeEvent
    public void onRenderPlayerPost(RenderPlayerEvent.Post event) {
        if (!ENABLED) return;
        if (!(event.entity instanceof EntityPlayer)) return;

        EntityPlayer p = (EntityPlayer) event.entity;

        // Whether to show self
        if (!SHOW_SELF && p == mc.thePlayer) return;

        // Distance limit
        double distSq = mc.thePlayer.getDistanceSqToEntity(p);
        if (distSq > MAX_DISTANCE * MAX_DISTANCE) return;

        // Hide when sneaking
        if (HIDE_WHEN_SNEAKING && p.isSneaking()) return;

        // Do not show invisible players
        if (p.isInvisible()) return;

        double x = event.x;
        double y = event.y + p.height;
        double z = event.z;

        renderHealthBar(p, x, y, z);
    }

    private void renderHealthBar(EntityPlayer p, double x, double y, double z) {
        FontRenderer fr = mc.fontRendererObj;

        float health = p.getHealth();
        float max = p.getMaxHealth();
        if (max <= 0f) max = 20f;
        float ratio = Math.max(0f, Math.min(1f, health / max));

        // Bar color: green -> yellow -> red
        int barColor = lerpColor(0xFF00FF00, 0xFFFFFF00, 1f - Math.min(ratio * 2f, 1f));
        if (ratio < 0.5f) {
            float t = (0.5f - ratio) / 0.5f;
            barColor = lerpColor(0xFFFFFF00, 0xFFFF0000, t);
        }

        String text = (int)Math.ceil(health) + "/" + (int)max;

        GlStateManager.pushMatrix();

        // Translate to player's head
        GlStateManager.translate(x, y + Y_OFFSET, z);

        // Rotate to face the camera (health bar + text rotate together)
        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0f, 1f, 0f);

        // Apply local offset so X_OFFSET is not affected by camera rotation
        GlStateManager.translate(X_OFFSET, 0, Z_OFFSET);

        // In first-person view, additionally rotate along X axis
        if (mc.gameSettings.thirdPersonView == 0) {
            GlStateManager.rotate(mc.getRenderManager().playerViewX, 1f, 0f, 0f);
        }

        // Scale
        GlStateManager.scale(SCALE, SCALE, SCALE);

        // Render on top
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();

        float w = BAR_WIDTH;
        float h = BAR_HEIGHT;
        int bg = 0xAA000000;

        if (VERTICAL) {
            // Background
            drawRect(-w / 2, -fr.FONT_HEIGHT - BAR_MARGIN - h, w, h, bg);
            // Health foreground (top-down)
            float filled = h * ratio;
            drawRect(-w / 2, -fr.FONT_HEIGHT - BAR_MARGIN - h, w, filled, barColor | 0xFF000000);
        } else {
            drawRect(-w / 2, -fr.FONT_HEIGHT - BAR_MARGIN - h, w, h, bg);
            float filled = w * ratio;
            drawRect(-w / 2 + (w - filled), -fr.FONT_HEIGHT - BAR_MARGIN - h, filled, h, barColor | 0xFF000000);
        }

        // Text outline, rotate local matrix 180° independently
        int textWidth = fr.getStringWidth(text);
        int tx = -textWidth / 2;
        int ty = -fr.FONT_HEIGHT;

        GlStateManager.pushMatrix();              // Create new local matrix
        GlStateManager.rotate(180f, 0f, 0f, 1f); // Rotate 180° around Z axis
        fr.drawString(text, tx + 1, -ty + 1, 0xAA000000, false);
        fr.drawString(text, tx, -ty, 0xFFFFFFFF, false);
        GlStateManager.popMatrix();               // Restore

        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }

    // Simple rectangle drawing
    private void drawRect(float x, float y, float w, float h, int color) {
        float a = ((color >> 24) & 255) / 255.0F;
        float r = ((color >> 16) & 255) / 255.0F;
        float g = ((color >> 8)  & 255) / 255.0F;
        float b = ( color        & 255) / 255.0F;

        GlStateManager.disableTexture2D();
        GlStateManager.color(r, g, b, a);
        net.minecraft.client.renderer.Tessellator tess = net.minecraft.client.renderer.Tessellator.getInstance();
        net.minecraft.client.renderer.WorldRenderer wr = tess.getWorldRenderer();
        wr.begin(7, net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION);
        wr.pos(x,     y + h, 0).endVertex();
        wr.pos(x + w, y + h, 0).endVertex();
        wr.pos(x + w, y,     0).endVertex();
        wr.pos(x,     y,     0).endVertex();
        tess.draw();
        GlStateManager.enableTexture2D();
    }

    // Color interpolation
    private int lerpColor(int c1, int c2, float t) {
        t = Math.max(0f, Math.min(1f, t));
        int a1 = (c1 >> 24) & 0xFF, r1 = (c1 >> 16) & 0xFF, g1 = (c1 >> 8) & 0xFF, b1 = c1 & 0xFF;
        int a2 = (c2 >> 24) & 0xFF, r2 = (c2 >> 16) & 0xFF, g2 = (c2 >> 8) & 0xFF, b2 = c2 & 0xFF;
        int a = (int)(a1 + (a2 - a1) * t);
        int r = (int)(r1 + (r2 - r1) * t);
        int g = (int)(g1 + (g2 - g1) * t);
        int b = (int)(b1 + (b2 - b1) * t);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
