package archem.entities;

import android.util.Log;

import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Material;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.Arrays;
import java.util.Map;

public class Molecule implements Cloneable
{
    public int x;
    public int y;
    public String formula;
    public Atom atoms[];
    public Bond bonds[];

    Node moleculeNode;

    public Molecule(String formula, Atom[] atoms, Bond[] bonds)
    {
        this.formula = formula;
        this.atoms = atoms;
        this.bonds = bonds;

        for (Bond b : bonds)
        {
            b.molecule = this;
        }
    }

    public Molecule()
    {
        super();
    }

    public void init()
    {
        for (Bond b : bonds)
        {
            b.init();
        }
        for (Atom a : atoms)
        {
            a.init();
        }
    }

    @Override
    public Molecule clone()
    {
        try
        {
            Molecule m = (Molecule) super.clone();

            m.atoms = new Atom[atoms.length];
            m.bonds = new Bond[bonds.length];

            for (int i = 0; i < atoms.length; i++) m.atoms[i] = atoms[i].clone();
            for (int i = 0; i < bonds.length; i++)
            {
                m.bonds[i] = bonds[i].clone();
                m.bonds[i].molecule = m;
            }

            System.out.println("InitB : " + Arrays.toString(atoms));
            System.out.println("InitB : " + Arrays.toString(m.atoms));

            m.init();

            System.out.println("InitA : " + Arrays.toString(atoms));
            System.out.println("InitA : " + Arrays.toString(m.atoms));

            return m;
        } catch (CloneNotSupportedException ignore)
        {

        }
        return null;
    }

    @Override
    public String toString()
    {
        return "Molecule{" +
                "formula='" + formula + '\'' +
                ", atoms=" + Arrays.toString(atoms) +
                ", bonds=" + Arrays.toString(bonds) +
                '}';
    }

    public void buildMolecule(ArFragment arFragment,AnchorNode anchorNode, Map<MaterialType, Material> materialMap)
    {
        Log.d("test", "buildMolecule: ");
        moleculeNode=Util.createSphere(arFragment,0, 0, 0, 0.0f, anchorNode,materialMap.get(MaterialType.NUCLEUS));

        //moleculeNode=new TransformableNode(arFragment.getTransformationSystem());
        //anchorNode.addChild(moleculeNode);
//        anchorNode.setLocalPosition(new Vector3(0,0.05f,0));
        Log.d("test", "Before BuildAtom");
        for(Atom a : atoms)
        {
            a.buildAtom(arFragment,moleculeNode,materialMap);
        }
        Log.d("test", "buildMolecule: atoms created");

        for(Bond b: bonds){
            Log.d("test", "CALLING BUILDBOND");
            b.buildBond(arFragment,moleculeNode,materialMap);
        }
        Log.d("test", "buildMolecule: bonds created");
    }
}
