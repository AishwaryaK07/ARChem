package archem.entities;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.chinmaye.archem.R;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Material;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import org.w3c.dom.Text;

import java.util.function.Consumer;

public class Util {

    public static float scale = 800.0f;
    public static float scale1 = 1000.0f;

    public static TransformableNode createSphere(ArFragment arFragment, float x, float y, float z, float radius, Node parent, Material material) {
        Log.d("test1", "createSphere: " + x + ", " + y + ", " + z + ", " + radius + ", " + parent);
        ModelRenderable sphere = ShapeFactory.makeSphere(radius, new Vector3(0, 0, 0), material);
        Log.d("test1", "aftercreateSphere: " + x + ", " + y + ", " + z + ", " + radius + ", " + parent);
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setRenderable(sphere);
//        transformableNode.setParent(parent);
        parent.addChild(transformableNode);
        transformableNode.setLocalPosition(new Vector3(x, y, z));
        transformableNode.setLocalScale(new Vector3(1f, 1f, 1f));
//        arFragment.getArSceneView().getScene().addChild(transformableNode);
//        transformableNode.select();
        Log.d("test1", "sphere has created");
        return transformableNode;
    }

    public static void createRing(String str,ArFragment arFragment, float x, float y, float z, float radius,final Node parent, Material material)
    {


        ModelRenderable.builder().setSource(arFragment.getContext(), Uri.parse(str)).build().thenAccept((new Consumer()
        {
            public void accept(Object var1)
            {
                this.accept((ModelRenderable) var1);
            }

            public final void accept(ModelRenderable it)
            {
                if (it != null)
                {
                    TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
                    transformableNode.setRenderable(it);
//        transformableNode.setParent(parent);
                    parent.addChild(transformableNode);
                    transformableNode.setLocalPosition(new Vector3(x, y, z));
                    Vector3 scalar=new Vector3(1f, 1f, 1f).scaled(radius);
                    System.out.println("scalar = " + scalar);
                    transformableNode.setLocalScale(scalar);

//        arFragment.getArSceneView().getScene().addChild(transformableNode);
//        transformableNode.select();
                    Log.d("test1", "sphere has created");

                } else
                {
                    Log.d("test", "it==null");
                }
            }
        }));
    }

    public static void createView(ArFragment arFragment, float x, float y, float z, float radius, Node anchorNode, Atom atom) {

        ViewRenderable.builder()
                .setView(arFragment.getContext(), R.layout.playcard)
                .build()
                .thenAccept(viewRenderable -> {
                    ViewRenderable playcard;
                    playcard = viewRenderable;
                    TransformableNode nameView = new TransformableNode(arFragment.getTransformationSystem());

                    nameView.setLocalPosition(new Vector3(x, y, z));
                    //nameView.setLocalScale(new Vector3(0.1f,0.1f,0.1f));
                    nameView.setLocalRotation(Quaternion.axisAngle(new Vector3(-90,0,0),1));
                    nameView.setParent(anchorNode);
                    nameView.setRenderable(playcard);

                    View view = (View) playcard.getView();
                    TextView text_name=view.findViewById(R.id.atom_name);
                    text_name.setText(atom.name);
                   /* TextView text_symbol=view.findViewById(R.id.atom_symbol);
                    text_name.setText(atom.symbol);
                    TextView text_n=view.findViewById(R.id.atom_n);
                    text_name.setText(atom.N);
                   // TextView text_a=view.findViewById(R.id.atom_a);
                    //text_name.setText((int) (atom.A));
                    TextView text_protons=view.findViewById(R.id.atom_protons);
                    text_name.setText(atom.protons);
                    TextView text_neutrons=view.findViewById(R.id.atom_neutrons);
                    text_name.setText(atom.neutrons);
*/

                    Log.d("test1","creating playcard "+atom.name);
                    text_name.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            anchorNode.setParent(null);
                        }
                    });
                    /*
                    text_symbol.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            anchorNode.setParent(null);
                        }
                    });
                    text_protons.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            anchorNode.setParent(null);
                        }
                    });
                    text_neutrons.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            anchorNode.setParent(null);
                        }
                    });
                    text_n.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            anchorNode.setParent(null);
                        }
                    });
*/
                });
    }

    public static Vector3 getWorldLocation(Node n)
    {
        if(n instanceof AnchorNode)
            return Vector3.zero();

        Vector3 cp = n.getLocalPosition();
        Vector3 pp = getWorldLocation(n.getParent());
       // Vector3 result = Vector3.add(cp,pp);
      //  Log.d("test3","getWorldLocation "+ result);
        return Vector3.add(cp,pp);

    }

}
