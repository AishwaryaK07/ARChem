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

public abstract class Bond implements Cloneable {
    Molecule molecule;

    Node bondNode;

    public abstract void init();

    public Bond clone() {
        try {
            return (Bond) super.clone();
        } catch (CloneNotSupportedException ignore) {

        }
        return null;
    }

    public abstract void buildBond(ArFragment arFragment, Node moleculeNode, Map<MaterialType, Material> materialMap);

}

class ValenceBond extends Bond {
    public int[] atoms;
    public int x;
    public int y;
    public int shared_electrons;

    private static final double[][][] array =
            {

                    //1 shared electron
                    {
                            {0, 0.5}, {0, -0.5}
                    },

                    //2 shared
                    {
                            {-0.5, 0.5}, {0.5, 0.5}, {-0.5, -0.5}, {0.5, -0.5}
                    },

                    //3 shared
                    {
                            {0, 0.5}, {0, -0.5}, {-1, 1}, {1, 1}, {-1, -1}, {1, -1}
                    }
            };


    List<Node> bondNodes = new ArrayList<>();

    public ValenceBond(int[] atoms, int shared_electrons, int x, int y) {
        this.x = x;
        this.y = y;
        this.shared_electrons = shared_electrons;
        this.atoms = atoms;
    }

    public void buildBond(ArFragment arFragment, Node moleculeNode, Map<MaterialType, Material> materialMap) {
        double[][] electrons = array[shared_electrons - 1];
        for (int i = 0; i < electrons.length; i++) {
            double x = electrons[i][0];
            double y = electrons[i][1];
            //Log.d("test","a"+x.toString());
            Log.d("test", "AB " + x);
            Log.d("test", "AB " + y);
            Node bondNode = Util.createSphere(arFragment, (float) (x) / Util.scale, 0f, (float) y / Util.scale, .005f, moleculeNode, materialMap.get(MaterialType.NUCLEUS));
            bondNodes.add(bondNode);
        }
    }

    public void init() {
       /* for (int i : atoms) {
            Atom a = molecule.atoms[i];
            //a.electrons.subList(a.electrons.size() - shared_electrons, a.electrons.size()).clear();
            a.configuration[a.configuration.length - 1] -= shared_electrons;
        }*/
    }

    public ValenceBond clone() {
        ValenceBond vb = (ValenceBond) super.clone();
        vb.atoms = new int[atoms.length];
        System.arraycopy(atoms, 0, vb.atoms, 0, atoms.length);
        return vb;
    }
}

class IonicBond extends Bond {
    public int[] atoms;
    public int[] electron_transfer;

    public IonicBond(int[] atoms, int[] electron_transfer) {
        this.electron_transfer = electron_transfer;
        this.atoms = atoms;
    }

    public void init() {
      /*  for (int i = 0; i < atoms.length; i++) {
            Atom a = molecule.atoms[atoms[i]];
            int[] c = a.configuration;
            c[c.length - 1] += electron_transfer[i];
            if (c[c.length - 1] == 0) {
                int[] t = a.configuration;
                a.configuration = new int[a.configuration.length - 1];
                System.arraycopy(t, 0, a.configuration, 0, a.configuration.length);
            }
        }*/
    }

    @Override
    public String toString() {
        return "IonicBondFrag{" +
                "atoms=" + Arrays.toString(atoms) +
                ", electron_transfer=" + Arrays.toString(electron_transfer) +
                '}';
    }

    public IonicBond clone() {
        IonicBond ib = (IonicBond) super.clone();
        ib.atoms = new int[atoms.length];
        ib.electron_transfer = new int[electron_transfer.length];


        System.arraycopy(atoms, 0, ib.atoms, 0, atoms.length);
        System.arraycopy(electron_transfer, 0, ib.electron_transfer, 0, electron_transfer.length);
        return ib;
    }

    @Override
    public void buildBond(ArFragment arFragment, Node moleculeNode, Map<MaterialType, Material> materialMap)
    {
        /*Log.d("test1", "BuildBond");
        int index=0;
        for(int et:this.electron_transfer)
        {
            if(et>0)
            {
                Atom a=molecule.atoms[index];

                for(int i=a.electrons.size()-et; i<a.electrons.size();i++)
                {
                    //moleculeNode=Util.createSphere(arFragment,0, 0, 0, 0.0f, anchorNode,materialMap.get(MaterialType.NUCLEUS));
                    Log.d("test", "node: " );
                    a.electrons.get(i).materialType=MaterialType.PROTON;
                    Node n=a.electrons.get(i).n;
                    if(n!=null)
                    {
                        n.getRenderable().setMaterial(materialMap.get(MaterialType.PROTON));
                    }
                }
            }
            index++;
        }*/

    }
    public  void update(ArFragment arFragment, Node moleculeNode, Map<MaterialType, Material> materialMap) {
        for (int i = 0; i < atoms.length; i++) {
            Atom a = molecule.atoms[atoms[i]];
            int[] c = a.configuration;
            c[c.length - 1] += electron_transfer[i];
            if (c[c.length - 1] == 0) {
                int[] t = a.configuration;
                a.configuration = new int[a.configuration.length - 1];
                System.arraycopy(t, 0, a.configuration, 0, a.configuration.length);
            }
        }
        Log.d("test1", "BuildBond");
        int index = 0;
        for (int et : this.electron_transfer) {
            if (et > 0) {
                Atom a = molecule.atoms[index];

                for (int i = a.electrons.size() - et; i < a.electrons.size(); i++) {
                    //moleculeNode=Util.createSphere(arFragment,0, 0, 0, 0.0f, anchorNode,materialMap.get(MaterialType.NUCLEUS));
                    Log.d("test", "node: ");
                    a.electrons.get(i).materialType = MaterialType.PROTON;
                    Node n = a.electrons.get(i).n;
                    if (n != null) {
                        n.getRenderable().setMaterial(materialMap.get(MaterialType.PROTON));
                    }
                }
            }
            index++;
        }

    }


}
