package com.example.upscale.render;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL20;
public class UpscaleRenderer {
    private static int lowResFbo = 0, lowResColor = 0, lowResDepth = 0;
    private static int width = 0, height = 0;
    private static int fsrEasuProgram = 0, fsrRcasProgram = 0;
    public static void ensure(int screenW, int screenH, float scale) {
        int targetW = Math.max(1, (int)(screenW * scale));
        int targetH = Math.max(1, (int)(screenH * scale));
        if (lowResFbo == 0 || targetW != width || targetH != height) {
            destroy();
            width = targetW; height = targetH;
            lowResFbo = GL30.glGenFramebuffers(); GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, lowResFbo);
            lowResColor = GL11.glGenTextures(); GL11.glBindTexture(GL11.GL_TEXTURE_2D, lowResColor);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, 0);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
            GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, lowResColor, 0);
            lowResDepth = GL30.glGenRenderbuffers(); GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, lowResDepth);
            GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL30.GL_DEPTH24_STENCIL8, width, height);
            GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT, GL30.GL_RENDERBUFFER, lowResDepth);
            GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        }
        if (fsrEasuProgram == 0) fsrEasuProgram = com.example.upscale.gl.ShaderUtil.loadProgram("fsr3_easu.vert", "fsr3_easu.frag");
        if (fsrRcasProgram == 0) fsrRcasProgram = com.example.upscale.gl.ShaderUtil.loadProgram("fsr3_rcas.vert", "fsr3_rcas.frag");
    }
    public static void bindLowRes() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, lowResFbo);
        GL11.glViewport(0, 0, width, height);
    }
    public static void presentToScreen(int screenW, int screenH) {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        GL11.glViewport(0, 0, screenW, screenH);
        GL20.glUseProgram(fsrEasuProgram);
        drawFullscreenQuad(lowResColor);
        GL20.glUseProgram(0);
        // Debug overlay: small blue quad (world-present)
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(0.0f, 0.4f, 1.0f, 0.25f);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-1f, 1f); GL11.glVertex2f(-0.8f, 1f);
        GL11.glVertex2f(-0.8f, 0.8f); GL11.glVertex2f(-1f, 0.8f);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }
    private static void drawFullscreenQuad(int texture) {
        if (texture != 0) GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
        GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
        GL11.glTexCoord2f(0f, 0f); GL11.glVertex2f(-1f, -1f);
        GL11.glTexCoord2f(1f, 0f); GL11.glVertex2f( 1f, -1f);
        GL11.glTexCoord2f(0f, 1f); GL11.glVertex2f(-1f,  1f);
        GL11.glTexCoord2f(1f, 1f); GL11.glVertex2f( 1f,  1f);
        GL11.glEnd();
    }
    public static void destroy() {
        if (lowResColor != 0) { GL11.glDeleteTextures(lowResColor); lowResColor = 0; }
        if (lowResDepth != 0) { GL30.glDeleteRenderbuffers(lowResDepth); lowResDepth = 0; }
        if (lowResFbo != 0) { GL30.glDeleteFramebuffers(lowResFbo); lowResFbo = 0; }
    }
}