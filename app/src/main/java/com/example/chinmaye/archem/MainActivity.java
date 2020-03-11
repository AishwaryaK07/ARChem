package com.example.chinmaye.archem;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.*;
import java.util.*;

import com.google.android.filament.MaterialInstance;
import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Trackable;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.NodeParent;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.Material;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import archem.entities.Atom;
import archem.entities.Electron;
import archem.entities.MaterialType;
import archem.entities.Molecule;
import archem.entities.MoleculeCollection;

public class MainActivity extends AppCompatActivity
{
    private ArFragment arFragment;
    TextView tv;
    String formula;
    private static final float scale = 300.0f;
    private ModelRenderable sunRenderable;

    ViewRenderable cardplay;

    private AnimationThread currentAnimationThread;
    private int animationDelay = 10;
    private Molecule molecule;

    class AnimationThread extends Thread
    {
        public void run()
        {
            while (this == currentAnimationThread)
            {
                //Animation Logic

                if (molecule != null)
                {
                    for (Atom a : molecule.atoms)
                    {
                        a.update();
                    }
                }


                try
                {
                    Thread.sleep(animationDelay);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        this.currentAnimationThread = null;
    }

    @SuppressLint("VisibleForTests")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView);
        formula = getIntent().getStringExtra("formula");
        tv.setText(formula);
        Toast.makeText(getApplicationContext(), formula, Toast.LENGTH_SHORT).show();

        //......................
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        ArSceneView sv = arFragment.getArSceneView();
        Frame frame = sv.getArFrame();
        Scene scene = sv.getScene();


//        Molecule kcl= MoleculeCollection.getMolecule("KCl");
//
//        System.out.println(hcl.toString());
//        System.out.println(kcl.toString());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);


        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.d("test", "onClick");
//                addObject(Uri.parse("halo.sfb"));
//                addObject(Uri.parse("halo.sfa"));
//                MaterialFactory.makeTransparentWithColor(getApplicationContext(), new com.google.ar.sceneform.rendering.Color(1, 1, 1,1)).thenAccept(material ->
                MaterialFactory.makeOpaqueWithColor(getApplicationContext(), new com.google.ar.sceneform.rendering.Color(1, 1, 1, 1)).thenAccept(material ->
                {
                    Log.d("test", "creating molecule " + material);
                    try
                    {
                        createMolecule(material);
                       // addObject(Uri.parse("ring.sfb"));


                    } catch (Exception e)
                    {
                        Log.e("test", e.getMessage(), e);
                    }
                });
            }
        });

        fab1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (molecule != null)
                {
                    for (Atom a : molecule.atoms)
                    {
                        a.isMoving = true;
                    }
                }
            }
        });

    }

    private void createMolecule(Material material)
    {

//        AnchorNode anchorNode = getAnchorNode(material);
//        Log.d("test","befor creating sphere");
//        createSphere((float)0.1f, 0.1f, (float)0.1f, .05f, anchorNode,material);
//        Log.d("test","sphere1");
//        createSphere((float)0.3f, 0.3f, (float)0.2f, .05f, anchorNode,material);
//        Log.d("test","sphere2");
//        createSphere((float)0.2f, 0.2f, (float)0.2f, .05f, anchorNode,material);
//        Log.d("test","sphere3");
//        createSphere((float)0.5f, 0.5f, (float)0.1f, .05f, anchorNode,material);
//        Log.d("test","sphere4");
//        createSphere((float)0.9f, 0.1f, (float)0.4f, .05f, anchorNode,material);
//        Log.d("test","sphere5");
//        createSphere((float)0.2f, 0.2f, (float)0.2f, .05f, anchorNode,material);
//        Log.d("test","sphere6");


        try
        {
            this.molecule = MoleculeCollection.getMolecule(formula);
            AnchorNode anchorNode = getAnchorNode(material);

            //AnchorNode anchorNode = getAnchorNode(material);
            Log.d("test", "achorNode" + anchorNode);

            Material nucleusMaterial = material.makeCopy();
            Color nucleusColor = new Color(1, 1, 0, .0f);
            nucleusMaterial.setFloat4("color", nucleusColor);

            Material protonMaterial = material.makeCopy();
            Color protonColor = new Color(1, 0, 0, 1f);
            protonMaterial.setFloat4("color", protonColor);

            Material neutronMaterial = material.makeCopy();
            Color neutronColor = new Color(0, 0, 1, 1f);
            neutronMaterial.setFloat4("color", neutronColor);

            Material ringMaterial = material.makeCopy();
            Color ringColor = new Color(0.5f, 0.5f, .5f, 1f);
            ringMaterial.setFloat4("color", ringColor);

            Map<MaterialType, Material> materialMap = new HashMap<>();
            materialMap.put(MaterialType.NUCLEUS, nucleusMaterial);
            materialMap.put(MaterialType.PROTON, protonMaterial);
            materialMap.put(MaterialType.NEUTRON, neutronMaterial);
            materialMap.put(MaterialType.ELECTRON, material);
            materialMap.put(MaterialType.RING, ringMaterial);

            Log.d("test1", "Building molecule" + anchorNode);
            molecule.buildMolecule(arFragment, anchorNode, materialMap);
            Log.d("test", "Molecule built");

            currentAnimationThread = new AnimationThread();
            currentAnimationThread.start();

//            showChildren(arFragment.getArSceneView().getScene(), 0);
        } catch (Exception e)
        {
            Log.e("test", "Failed", e);
        }

        //arFragment.getArSceneView().getScene().addChild(anchorNode);

    }

    private void showChildren(NodeParent n, int depth)
    {
        String s = "";
        for (int i = 0; i < depth; i++)
            s += "\t";
        Log.d("test", s);

        Log.d("test", n.toString());

        for (Node n2 : n.getChildren())
        {
            showChildren(n2, depth + 1);
        }
    }

    private AnchorNode getAnchorNode(Material material)
    {
        //  Log.d("test", "getAnchorNode");
        Frame frame = arFragment.getArSceneView().getArFrame();
        Point point = getScreenCenter();
        Log.d("test", "SC " + point.toString());
        if (frame != null)
        {
            final List<HitResult> hits = frame.hitTest((float) point.x, (float) point.y);
            Log.d("test", "Hits :" + hits.toString());
            if (!hits.isEmpty())
            {
                ModelRenderable sphere1 = ShapeFactory.makeSphere(0f, new Vector3(0, 0, 0), material);
                Anchor a = hits.get(0).createAnchor();
                AnchorNode anchorNode = new AnchorNode(a);
//                anchorNode.setParent();
                arFragment.getArSceneView().getScene().addChild(anchorNode);
                //                TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
//                transformableNode.setRenderable(sphere1);
//                transformableNode.setParent(anchorNode);
//                //anchorNode.setLocalScale(new Vector3(.1f, .1f, .1f));
//                arFragment.getArSceneView().getScene().addChild(anchorNode);
//                transformableNode.select();

                return anchorNode;
            }

        }
        return null;
    }

    public static Node createSphere1(float x, float y, float z, float radius, Node parent, Material material)
    {
        Log.d("test", String.format("Create Sphere : %f %f %f %f", x, y, z, radius));
        Node base = new Node();

        Node sun = new Node();
        sun.setParent(base);
        sun.setLocalPosition(new Vector3(0.0f, 0.5f, 0.0f));

        Node sunVisual = new Node();
        sunVisual.setParent(sun);
//        sunVisual.setRenderable(sunRenderable);
        sunVisual.setLocalScale(new Vector3(0.5f, 0.5f, 0.5f));


        /*Node base = new Node();
        Node ren=new Node();

        ren.setParent(base);
        ModelRenderable sphere2 = ShapeFactory.makeSphere(radius, new Vector3(0f,0.05f,0f), material);
        ren.setRenderable(sphere2);
        ren.setLocalScale(new Vector3(0.0f,0.5f,0.0f));
        //TransformableNode ren=new TransformableNode(arFragment.getTransformationSystem());
        //transformableNode.setRenderable(sphere);

       //ren.setRenderable(sphere2);
       //ren.setParent(parent);
        //ren.setLocalPosition(new Vector3(x/scale,y/scale,z/scale));
        //parent.addChild(ren);

//        ren.setLocalRotation(new Quaternion(0,0,0,0));
//        ren.setLocalScale(new Vector3(1,1,1));
*/

        /*Node sun = new Node();
        sun.setParent(base);
        sun.setLocalPosition(new Vector3(0.0f, 0.5f, 0.0f));

        Node sunVisual = new Node();
        sunVisual.setParent(sun);
        sunVisual.setRenderable(sunRenderable);
        sunVisual.setLocalScale(new Vector3(0.5f, 0.5f, 0.5f));
*/

        return base;
    }




    private TransformableNode createCylinder(float x, float y, float z, float radius, float height, Node parent, Material material)
    {

//        Log.d("test", "createSphere: "+x+", "+y+", "+z+", "+radius+", "+parent);
        ModelRenderable sphere = ShapeFactory.makeCylinder(radius, height, new Vector3(x, y, z), material);
//        Log.d("test", "aftercreateSphere: "+x+", "+y+", "+z+", "+radius+", "+parent);
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setRenderable(sphere);
        transformableNode.setParent(parent);
        parent.addChild(transformableNode);
        // ModelRenderable.setLocalScale(new Vector3(.1f, .1f, .1f));
        //arFragment.getArSceneView().getScene().addChild(transformableNode);
        transformableNode.select();
//        Log.d("test", "cylinder has created");
        return transformableNode;

    }

    @Override
    public String toString()
    {
        return "MainActivity{" +
                "arFragment=" + arFragment +
                '}';
    }

    private void addObject(Uri parse)
    {
        Log.d("test", "adding object");
        Frame frame = arFragment.getArSceneView().getArFrame();
        Point point = getScreenCenter();
        Log.d("test", point.toString());
        if (frame != null)
        {
            final List<HitResult> hits = frame.hitTest((float) point.x, (float) point.y);
            Log.d("test", "Hits :" + hits.toString());

            for (int i = 0; i < hits.size(); i++)
            {
                final int j = i;
                Trackable trackable = hits.get(i).getTrackable();
                if (trackable instanceof Plane && ((Plane) trackable).isPoseInPolygon(hits.get(i).getHitPose()))
                {
//                    MaterialFactory.makeOpaqueWithColor(this, new com.google.ar.sceneform.rendering.Color(1,1,1)).thenAccept(material ->
//                    {
//                        ModelRenderable sphere = ShapeFactory.makeSphere(.1f, new Vector3(0, 0, 0), material);
//                        ModelRenderable ring=ShapeFactory.makeCylinder(.1f,.05f,new Vector3(0,.5f,0),material);
//                        addNode(arFragment,hits.get(j).createAnchor(),ring);
//                    } );

                    placeObject(arFragment, hits.get(i).createAnchor(), parse);
                }
            }
        }
    }

    private void placeObject(final ArFragment fragment, final Anchor createAnchor, Uri model)
    {

        Log.d("test", "placing object");
        ModelRenderable.builder().setSource(fragment.getContext(), model).build().thenAccept((new Consumer()
        {
            public void accept(Object var1)
            {
                this.accept((ModelRenderable) var1);
            }

            public final void accept(ModelRenderable it)
            {
                if (it != null)
                    MainActivity.this.addNode(arFragment, createAnchor, it);
                else
                    Log.d("test", "it==null");
            }
        })).exceptionally((new Function()
        {
            public Object apply(Object var1)
            {
                return this.apply((Throwable) var1);
            }

            @Nullable
            public final Void apply(Throwable it)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(it.getMessage()).setTitle("error!");
                AlertDialog dialog = builder.create();
                dialog.show();
                return null;
            }
        }));
    }

    private void addNode(ArFragment fragment, Anchor createAnchor, ModelRenderable renderable)
    {
        Log.d("test", "adding node");

        AnchorNode anchorNode = new AnchorNode(createAnchor);
        TransformableNode transformableNode = new TransformableNode(fragment.getTransformationSystem());
        transformableNode.setRenderable(renderable);
        transformableNode.setParent(anchorNode);
        anchorNode.setLocalScale(new Vector3(.1f, .1f, .1f));
        fragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();
    }

    private Point getScreenCenter()
    {
        View vw = findViewById(android.R.id.content);
        return new Point(vw.getWidth() / 2, vw.getHeight() / 2);
    }

}