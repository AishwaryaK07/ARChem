package archem.entities;

import android.util.Log;

import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.rendering.Material;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Atom implements Cloneable, AnimationNode
{


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

    static final double factor = 1;

    public String name;
    public String symbol;
    public int N;  //Atomic numner
    public float A; //Atomic weight
    public int protons;
    public int neutrons;
    public int x;
    public int y;
    public int z;

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
        Node atomNode = Util.createSphere(arFragment, (float) (x) / Util.scale, 0f, (float) y / Util.scale, .0f, moleculeNode, materialMap.get(MaterialType.NUCLEUS));
//        moleculeNode.addChild(atomNode);

//        for (int i = 0; i < protons; i++)
        for(Hadron p : protonList)
        {
            float x = (r.nextFloat() * 2) - 1;
            float y = (r.nextFloat() * 2) - 1;
            float z = (r.nextFloat() * 2) - 1;
            p.n = Util.createSphere(arFragment, (float) (x) / Util.scale, (float) (0 + y) / Util.scale, (z) / Util.scale, 0.0025f, atomNode, materialMap.get(MaterialType.PROTON));
        }

//        for (int i = 0; i < neutrons; i++)
        for(Hadron n:neutronList)
        {
            float x = (r.nextFloat() * 2) - 1;
            float y = (r.nextFloat() * 2) - 1;
            float z = (r.nextFloat() * 2) - 1;
            n.n = Util.createSphere(arFragment, (x) / Util.scale, (0 + y) / Util.scale, (z) / Util.scale, 0.0025f, atomNode, materialMap.get(MaterialType.NEUTRON));
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
        }

        Log.d("test1", "after buildAtom");
    }






    @Override
    public void update()
    {
        for (Electron e : electrons) e.update();
        for (Hadron p : protonList) p.update();
        for (Hadron n : neutronList) n.update();
    }
}
