package archem.entities;

import android.util.Log;

import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Material;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class Util
{
    public static float scale = 300.0f;
    public static TransformableNode createSphere(ArFragment arFragment, float x, float y, float z, float radius, Node parent, Material material)
    {
        Log.d("test1", "createSphere: " + x + ", " + y + ", " + z + ", " + radius + ", " + parent);
        ModelRenderable sphere = ShapeFactory.makeSphere(radius, new Vector3(0,0,0), material);
        Log.d("test1", "aftercreateSphere: " + x + ", " + y + ", " + z + ", " + radius + ", " + parent);
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setRenderable(sphere);
//        transformableNode.setParent(parent);
        parent.addChild(transformableNode);
        transformableNode.setLocalPosition(new Vector3(x,y,z));
        transformableNode.setLocalScale(new Vector3(1f, 1f, 1f));
//        arFragment.getArSceneView().getScene().addChild(transformableNode);
//        transformableNode.select();
        Log.d("test1", "sphere has created");
        return transformableNode;
    }
}
