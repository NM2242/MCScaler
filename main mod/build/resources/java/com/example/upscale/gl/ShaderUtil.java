package com.example.upscale.gl;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL11;
import java.io.*;
import java.nio.charset.StandardCharsets;
public class ShaderUtil {
    public static int loadProgram(String vertPath, String fragPath) {
        int vs = loadShader(GL20.GL_VERTEX_SHADER, vertPath);
        int fs = loadShader(GL20.GL_FRAGMENT_SHADER, fragPath);
        int program = GL20.glCreateProgram();
        GL20.glAttachShader(program, vs);
        GL20.glAttachShader(program, fs);
        GL20.glLinkProgram(program);
        if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException("Shader link failed: " + GL20.glGetProgramInfoLog(program));
        }
        GL20.glDeleteShader(vs);
        GL20.glDeleteShader(fs);
        return program;
    }
    private static int loadShader(int type, String path) {
        String src = readResource("/assets/upscale/shaders/" + path);
        int id = GL20.glCreateShader(type);
        GL20.glShaderSource(id, src);
        GL20.glCompileShader(id);
        if (GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException("Shader compile failed: " + GL20.glGetShaderInfoLog(id));
        }
        return id;
    }
    private static String readResource(String path) {
        try (InputStream is = ShaderUtil.class.getResourceAsStream(path)) {
            if (is == null) throw new RuntimeException("Missing resource: " + path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line).append("\n");
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}