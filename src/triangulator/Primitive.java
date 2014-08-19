package triangulator;

import java.util.ArrayList;
import org.lwjgl.opengl.GL11;

/**
 * A class to store a single OpenGL primitive (ie a triangle, triangle_loop etc.) returned 
 * from the tesselator.
 *
 * @author Quew8
 */
public class Primitive {
    /**
     * The OpenGL constant defining the type of this primitive 
     * 
     */
    public final int type;

    /**
     * A list of the indices of the vertices required to draw this primitive.
     * 
     */
    public final ArrayList<Integer> vertices = new ArrayList<Integer>();

    public Primitive(int type) {
        this.type = type;
    }

    private String getTypeString() {
        switch(type) {
        case GL11.GL_TRIANGLES: return "GL_TRIANGLES";
        case GL11.GL_TRIANGLE_STRIP: return "GL_TRIANGLE_STRIP";
        case GL11.GL_TRIANGLE_FAN: return "GL_TRIANGLE_FAN";
        default: return Integer.toString(type);
        }
    }

    @Override
    public String toString() {
        String s = "New Primitive " + getTypeString();
        for(int i = 0; i < vertices.size(); i++) {
            s += "\nIndex: " + vertices.get(i);
        }
        return s;
    }
}

