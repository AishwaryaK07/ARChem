package archem.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static archem.entities.AtomCollection.getAtom;

public class MoleculeCollection
{
    private static final Map<String, Molecule> moleculeMap = new HashMap<>();

    public static void addMolecule(Molecule molecule)
    {
        moleculeMap.put(molecule.formula, molecule);
    }

    public static Molecule getMolecule(String formula1)
    {
        return moleculeMap.get(formula1).clone();
    }

    public static List<Molecule> getMoleculeList()
    {
        List<Molecule> list=new ArrayList<>(moleculeMap.values());

        list.sort((m1,m2)->m1.formula.compareToIgnoreCase(m2.formula));

        return Collections.unmodifiableList(list);
//        for(Map.Entry<String,Molecule> e:moleculeMap.entrySet())
//        {
//            list.add(e.getValue());
//        }
    }

    static
    {
        addMolecule(new Molecule("HCl",
                new Atom[]{
                        getAtom("H", -15, 0,-7,0),
                        getAtom("Cl", 25, 0,14,0)
                },
                new Bond[]{
                        new ValenceBond(new int[]{0, 1}, 1, 0, 0)
                }
        ));
        addMolecule(new Molecule("C2H4",
                new Atom[]{
                        getAtom("H", -17, -7,12,12),
                        getAtom("H", 17, -7,5,12),
                        getAtom("H", -17, 7,-5,12),
                        getAtom("H", 17, 7,-12,12),
                        getAtom("C", -10, 0,-7,7),
                        getAtom("C", 10, 0,-7,-7)
                },
                new Bond[]{
                        new ValenceBond(new int[]{0, 4}, 3, 0, 0),
                        new ValenceBond(new int[]{1, 4}, 3, 0, 0),
                        new ValenceBond(new int[]{2, 5}, 3, 0, 0),
                        new ValenceBond(new int[]{3, 5}, 3, 0, 0),
                        new ValenceBond(new int[]{4, 5}, 3, 0, 0)
                }
        ));

        addMolecule(new Molecule("KCl",
                new Atom[]{
                        getAtom("K", -55, 0,-30,0),
                        getAtom("Cl", 35, 0,30,0)
                },
                new Bond[]{
                        new IonicBond(new int[]{0, 1}, new int[]{-1, 1})
                }
        ));

        addMolecule(new Molecule("CaS",
                new Atom[]{
                        getAtom("Ca", -55, 0,-30,0),
                        getAtom("S", 35, 0,30,0)
                },
                new Bond[]{
                        new IonicBond(new int[]{0, 1}, new int[]{-2, 2})
                }
        ));
    }

    @Override
    public String toString()
    {
        return "MoleculeCollection{}";
    }
}
