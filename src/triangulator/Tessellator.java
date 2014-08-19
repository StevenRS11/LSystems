package triangulator;

import java.util.ArrayList;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.GLUtessellatorCallbackAdapter;
import org.lwjgl.util.glu.tessellation.GLUtessellatorImpl;

/**
 * A class to interface with the GLU tessellator.
 * Constructs a series of primitives to draw the complex shape. You describe to it. 
 *
 * A single instance of this class can be reused as many times as desired.
 *
 * @author Quew8
 */
public class Tessellator extends GLUtessellatorImpl {
    /**
     * A list of the primitives
     */
    public ArrayList<Primitive> primitives = new ArrayList<Primitive>();

    public Tessellator() {
        GLUtessellatorCallbackAdapter callback = new GLUtessellatorCallbackAdapter() {
            @Override
            public void begin(int type) {
                Tessellator.this.primitives.add(new Primitive(type));
            }

            @Override
            public void vertex(Object vertexData) {
                Integer coords = (Integer) vertexData;
                Tessellator.this.getLastPrimitive().vertices.add(coords);
            }

            @Override
            public void error(int errnum) {
                throw new RuntimeException("GLU Error " + GLU.gluErrorString(errnum));
            }

        };
        this.gluTessCallback(GLU.GLU_TESS_BEGIN, callback);
        this.gluTessCallback(GLU.GLU_TESS_VERTEX, callback);
        this.gluTessCallback(GLU.GLU_TESS_ERROR, callback);
    }

    @Override
    public void gluBeginPolygon() {
        super.gluBeginPolygon();
        primitives.clear();
    }

    private Primitive getLastPrimitive() {
        return primitives.get(primitives.size() - 1);
    }
}
