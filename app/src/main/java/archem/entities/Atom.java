package archem.entities;

import android.util.Log;

import com.example.chinmaye.archem.MainActivity;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Material;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

public class Atom implements Cloneable, AnimationNode
{

    public String str1 = "ring1.sfb";
    public String str2 = "ring2.sfb";
    public String str3 = "ring3.sfb";


    static final int[] MAX_ELECTRON = {2, 8, 18, 32, 50, 72, 98, 128};
    static final double[] angles = {0, 180, 90, 270, 45, 225, 135, 315};
    static final double[] rangles;

    static
    {
        rangles = new double[angles.length];
        for (int i = 0; i < angles.length; i++)
        {
            rangles[i] = Math.toRadians(angles[i]);
        }
    }

    static final float factor = 1;

    public String name;
    public String symbol;
    public int N;  //Atomic numner
    public float A; //Atomic weight
    public int protons;
    public int neutrons;
    public int x;
    public int y;
    public int z;
    public int targetx;
    public int targety;
    public int targetz;
    public boolean isMoving=false;

    public int[] configuration;

    public List<Electron> electrons = new ArrayList<>();
    public List<Integer> orbitRadius = new ArrayList<>();
    public List<Hadron> protonList = new ArrayList<>();
    public List<Hadron> neutronList = new ArrayList<>();

    Node atomNode;

    public Atom(String name, String symbol, int n, float a, int protons, int neutrons, int... configuration)
    {
        this.name = name;
        this.symbol = symbol;
        N = n;
        A = a;
        this.protons = protons;
        this.neutrons = neutrons;
        this.configuration = configuration;
    }

    public void init()
    {
        float basetheta = .01f;

        for (int orbit = 0; orbit < configuration.length; orbit++)
        {
            int nelectrons = configuration[orbit];
            int radius = (int) (factor * (orbit + 1) * 10);
            orbitRadius.add(radius);

            for (int i = 0; i < nelectrons; i++)
            {


                double ra = rangles[i];
                double x = radius * Math.cos(ra);
                double y = radius * Math.sin(ra);

                Electron e = new Electron(x, y);
                e.r = (radius / Util.scale);
                e.theta = (float) ra;
                e.deltatheta = (float) (basetheta - (basetheta / 8) * orbit);
                electrons.add(e);
            }

            for (int i = 0; i < protons; i++)
            {
//                double ra = rangles[i];
//                double x = radius * Math.cos(ra);
//                double y = radius * Math.sin(ra);

                Hadron e = new Hadron(x, y, z);
                e.r = (radius / Util.scale);
                e.theta = (float) (Math.random()*Math.PI);
                e.phi = (float) (Math.random()*Math.PI);
                e.deltatheta = (float) Math.random() / 50;
                e.deltaphi = (float) Math.random() / 50;
                protonList.add(e);
            }
            for (int i = 0; i < neutrons; i++)
            {
//                double ra = rangles[i];
//                double x = radius * Math.cos(ra);
//                double y = radius * Math.sin(ra);

                Hadron e = new Hadron(x, y, z);
                e.r = (radius / Util.scale);
                e.theta = (float) (Math.random()*Math.PI);
                e.phi = (float) (Math.random()*Math.PI);
                e.deltatheta = (float) Math.random() / 50;
                e.deltaphi = (float) Math.random() / 50;
                neutronList.add(e);
            }


        }
//        electrons = Collections.unmodifiableList(electrons);
//        orbitRadius = Collections.unmodifiableList(orbitRadius);
    }

    public Atom clone()
    {
        try
        {
            Atom a = (Atom) super.clone();
            a.configuration = new int[configuration.length];
            System.arraycopy(configuration, 0, a.configuration, 0, configuration.length);
            a.orbitRadius = new ArrayList<>(orbitRadius);
            a.electrons = new ArrayList<>(electrons);
            a.protonList=new ArrayList<>(protonList);
            a.neutronList=new ArrayList<>(neutronList);
            return a;
        } catch (CloneNotSupportedException ignore)
        {
        }
        return null;
    }

    @Override
    public String toString()
    {
        return "Atom{" +
                "name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", N=" + N +
                ", A=" + A +
                ", x=" + x +
                ", y=" + y +
                ", configuration=" + Arrays.toString(configuration) +
                ", electrons=" + electrons +
                ", orbitRadius=" + orbitRadius +
                '}';
    }

    public void buildAtom(ArFragment arFragment, Node moleculeNode, Map<MaterialType, Material> materialMap)
    {
        Log.d("test1", "BuildAtom");
        Random r = new Random();
      //  Log.d("test1", "creating atom");

        atomNode = Util.createSphere(arFragment, (float) (x) / Util.scale, 0f, (float) y / Util.scale, .0f, moleculeNode, materialMap.get(MaterialType.NUCLEUS));
        Util.createView(arFragment, (float) (x) / Util.scale, -0.05f, (float) y / Util.scale, .0f,atomNode,this);


        for(Hadron p : protonList)
        {
            float x = (r.nextFloat() * 2) - 1;
            float y = (r.nextFloat() * 2) - 1;
            float z = (r.nextFloat() * 2) - 1;
            p.n = Util.createSphere(arFragment, (float) (x) / Util.scale, (float) (0 + y) / Util.scale, (z) / Util.scale, 0.0025f, atomNode, materialMap.get(MaterialType.PROTON));
            Log.d("test3","protons position"+ p.n.getLocalPosition());
        }

//        for (int i = 0; i < neutrons; i++)
        for(Hadron n:neutronList)
        {
            float x = (r.nextFloat() * 2) - 1;
            float y = (r.nextFloat() * 2) - 1;
            float z = (r.nextFloat() * 2) - 1;
            n.n = Util.createSphere(arFragment, (x) / Util.scale, (0 + y) / Util.scale, (z) / Util.scale, 0.0025f, atomNode, materialMap.get(MaterialType.NEUTRON));
            Log.d("test3","neutrons position"+ n.n.getLocalPosition());
        }

//            //add rings
//            for (int i = 0; i < a.configuration.length; i++)
//            {
//                Material m = nucleusMaterial.makeCopy();
//                m.setFloat3("color", new Color(.2f + i / 10f, .2f + i / 10f, .2f + i / 10f));
//                TransformableNode atomNode1 = createCylinder((float) a.x / scale, 0f, (float) a.y / scale, (i + 1) * 10 / scale, 0.001f * (a.configuration.length - i), atomNode, m);
//            }


        for (Electron e : electrons)
        {
           // Log.d("test", "creating electron");
//                createSphere((float) (e.x+a.x)/scale, 0f, (float) (a.y+e.y)/scale, .0005f, atomNode,material);
            e.n = Util.createSphere(arFragment, (float) (e.x) / Util.scale, 0f, (float) (e.y) / Util.scale, .005f, atomNode, materialMap.get(e.materialType));

            Vector3 vec = Util.getWorldLocation(e.n);

            Log.d("test3","electron position "+ e.n.getLocalPosition());
            Log.d("test3","printing getWorldLocation "+ vec);
        }

      //  MainActivity.createRing(arFragment, (float) (x) / Util.scale, 0f, (float) (y) / Util.scale, .005f, atomNode, materialMap.get(MaterialType.RING));
      //  MainActivity.createRing(arFragment, (float) (x) / Util.scale, 0f, (float) (y) / Util.scale, .001f, atomNode, materialMap.get(MaterialType.RING));
      //  MainActivity.createRing(arFragment, (float) (x) / Util.scale, 0f, (float) (y) / Util.scale, .008f, atomNode, materialMap.get(MaterialType.RING));

        for (int orbit = 0; orbit < configuration.length; orbit++)
        {
//            int nrings = configuration[orbit];
            float ring_radius =  ((factor * (orbit + 1) * 10)/Util.scale)/20;
            System.out.println("ring_radius = " + ring_radius);
            Util.createRing(str1,arFragment, (float) (0f) / Util.scale, 0f, (float) (0f) / Util.scale, ring_radius, atomNode, materialMap.get(MaterialType.RING));
            Util.createRing(str2,arFragment, (float) (0f) / Util.scale, 0f, (float) (0f) / Util.scale, ring_radius, atomNode, materialMap.get(MaterialType.RING));
            Util.createRing(str3,arFragment, (float) (0f) / Util.scale, 0f, (float) (0f) / Util.scale, ring_radius, atomNode, materialMap.get(MaterialType.RING));
        }

        Log.d("test1", "after buildAtom");
    }






    @Override
    public void update()
    {
        if(isMoving && atomNode!=null)
        {
            Vector3 pos=atomNode.getLocalPosition();
            float sx=pos.x*Util.scale;
            float sy=pos.y*Util.scale;
            float sz=pos.z*Util.scale;

            float dx = targetx - sx;
            float dy = targety - sy;
            float dz = targetz - sz;
          //  Log.d("test4", "dx: "+dx);
            Log.d("test4", "vslue of x " +symbol+" from x "+sx+" to "+(sx+dx));
            Log.d("test4", "value of y " +symbol+" from y "+sx+" to "+(sy+dy));
            Log.d("test4", "value of z " +symbol+" from z "+sx+" to "+(sz+dz));

            double dist=Math.sqrt((dx*dx)+(dy*dy)+(dz*dz))*10;
         //   Log.d("test4", "distance: "+dist);
            dx/=dist;
            dy/=dist;
            dz/=dist;
         //   Log.d("test4", "dx after: "+dx);

            Vector3 newpos=new Vector3((float)(sx+dx)/Util.scale, (float)(sy+dy)/Util.scale, (float)(sz+dz)/Util.scale);
//            Log.d("test4", "newpos: "+newpos);
            atomNode.setLocalPosition(newpos);
        }

        for (Electron e : electrons) e.update();
        for (Hadron p : protonList) p.update();
        for (Hadron n : neutronList) n.update();
    }
}
