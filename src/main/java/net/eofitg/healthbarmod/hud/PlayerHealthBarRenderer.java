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
    public static boolean FACE_PLAYER = false;        // Whether health bar is always facing player
    public static double  MAX_DISTANCE = 24.0;        // Maximum render distance
    public static float   SCALE = 0.0165F;            // Overall scale (font/bar size)
    public static float   X_OFFSET = 0f;              // Horizontal offset (block units)
    public static float   Y_OFFSET = 0f;              // Vertical offset (block units)
    public static float   Z_OFFSET = 0f;              // Depth offset (block units)
    public static float   BAR_WIDTH = 70f;            // Bar width in pixels (before scaling)
    public static float   BAR_HEIGHT = 7f;            // Bar height in pixels
    public static float   BAR_MARGIN = 10f;           // Margin between bar and name text (pixels)
    public static float BAR_ROTATION = 0f;            // Rotation angle of health bar
    public static float BAR_X_OFFSET = 0f;            // Horizontal offset of health bar (block units)
    public static float BAR_Y_OFFSET = 0f;            // Vertical offset of health bar (block units)
    public static float BAR_Z_OFFSET = 0f;            // Depth offset of health bar (block units)

    private final Minecraft mc = Minecraft.getMinecraft();
    private final static float DefaultYOffset = 1f;

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
        double y = event.y + p.height + DefaultYOffset;
        double z = event.z;

        renderHealthBar(p, x, y, z);
    }

    private void renderHealthBar(EntityPlayer p, double x, double y, double z) {
        FontRenderer fr = mc.fontRendererObj;

        float health = p.getHealth();
        float maxHealth = p.getMaxHealth();
        float absorption = p.getAbsorptionAmount();
        if (maxHealth <= 0f) maxHealth = 20f;

        // Text showing real health + absorption
        String text = "";
        if ((int)Math.ceil(absorption) > 0)
            text +=  (int)Math.ceil(absorption) + "+";
        text += (int)Math.ceil(health) + "/" + (int)maxHealth;

        // Calculate ratio relative to total health (health + absorption)
        float totalHealth = Math.max(health + absorption, maxHealth);
        float ratioBase = totalHealth > 0 ? health / totalHealth : 0;
        float ratioAbsorb = totalHealth > 0 ? absorption / totalHealth : 0;
        float ratio = Math.max(0f, Math.min(1f, health / maxHealth));

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y + Y_OFFSET, z);
        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0f, 1f, 0f);
        GlStateManager.translate(X_OFFSET, 0, Z_OFFSET);
        if (FACE_PLAYER) {
            GlStateManager.rotate(mc.getRenderManager().playerViewX, 1f, 0f, 0f);
        }
        GlStateManager.scale(SCALE, SCALE, SCALE);
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();

        float w = BAR_WIDTH;
        float h = BAR_HEIGHT;
        float r = BAR_ROTATION;
        int bg = 0xAA000000;

        // Base color for health bar (green → yellow → red)
        int barColorBase = lerpColor(0xFF00FF00, 0xFFFFFF00, 1f - Math.min(health / maxHealth * 2f, 1f));
        if (health / maxHealth < 0.5f) {
            float t = (0.5f - health / maxHealth) / 0.5f;
            barColorBase = lerpColor(0xFFFFFF00, 0xFFFF0000, t);
        }

        // Health bar
        float cx = 0;
        float cy = -fr.FONT_HEIGHT - BAR_MARGIN - h / 2f;
        GlStateManager.pushMatrix();
        GlStateManager.translate(cx, cy, 0f);
        GlStateManager.rotate(r, 0f, 0f, 1f);
        GlStateManager.translate(-cx, -cy, 0f);
        GlStateManager.translate(BAR_X_OFFSET, BAR_Y_OFFSET, BAR_Z_OFFSET);
        drawRect(-w / 2, -fr.FONT_HEIGHT - BAR_MARGIN - h, w, h, bg);
        if (absorption > 0f) {
            float filledBase = w * ratioBase;
            float filledAbsorb = w * ratioAbsorb;
            drawRect(-w / 2 + (w - filledBase), -fr.FONT_HEIGHT - BAR_MARGIN - h, filledBase, h, barColorBase | 0xFF000000);
            drawRect(-w / 2 + (w - filledBase - filledAbsorb), -fr.FONT_HEIGHT - BAR_MARGIN - h, filledAbsorb, h, 0xFFFFD700);
        }
        else {
            float filled = w * ratio;
            drawRect(-w / 2 + (w - filled), -fr.FONT_HEIGHT - BAR_MARGIN - h, filled, h, barColorBase | 0xFF000000);
        }
        GlStateManager.popMatrix();

        // Text outline
        int textWidth = fr.getStringWidth(text);
        int tx = -textWidth / 2;
        int ty = -fr.FONT_HEIGHT;
        GlStateManager.pushMatrix();
        GlStateManager.rotate(180f, 0f, 0f, 1f);
        fr.drawString(text, tx + 1, -ty + 1, 0xAA000000, false);
        fr.drawString(text, tx, -ty, 0xFFFFFFFF, false);
        GlStateManager.popMatrix();

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
