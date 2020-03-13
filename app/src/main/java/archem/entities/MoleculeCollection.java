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
                        getAtom("H", -45, 0,-20,0),
                        getAtom("Cl", 65, 0,40,0)
                },
                new Bond[]{
                        new ValenceBond(new int[]{0, 1}, 1, 10, 0)
                }
        ));

        addMolecule(new Molecule("F2",
                new Atom[]{
                        getAtom("F", -65, 0,-40,0),
                        getAtom("F", 65, 0,40,0)
                },
                new Bond[]{
                        new ValenceBond(new int[]{0, 1}, 1, 0, 0),
                        new ValenceBond(new int[]{1, 0}, 1, 0, 0)
                }
        ));

        addMolecule(new Molecule("O2",
                new Atom[]{
                        getAtom("O", -65, 0,-40,0),
                        getAtom("O", 65, 0,40,0)
                },
                new Bond[]{
                        new ValenceBond(new int[]{0, 1}, 2, 0, 0),
                        new ValenceBond(new int[]{1, 0}, 2, 0, 0)
                }
        ));

        addMolecule(new Molecule("N2",
                new Atom[]{
                        getAtom("N", -65, 0,-40,0),
                        getAtom("N", 65, 0,40,0)
                },
                new Bond[]{
                        new ValenceBond(new int[]{0, 1}, 3, 0, 0),
                        new ValenceBond(new int[]{1, 0}, 3, 0, 0)
                }
        ));

        addMolecule(new Molecule("C2H4",
                new Atom[]{
                        getAtom("H", -67, -7,42,12),
                        getAtom("H", 67, -7,45,12),
                        getAtom("H", -67, 7,-45,12),
                        getAtom("H", 67, 7,-42,12),
                        getAtom("C", -40, 0,-27,7),
                        getAtom("C", 40, 0,-27,-7)
                },
                new Bond[]{
                        new ValenceBond(new int[]{0, 4}, 1, 0, 0),
                        new ValenceBond(new int[]{1, 4}, 1, 0, 0),
                        new ValenceBond(new int[]{2, 5}, 1, 0, 0),
                        new ValenceBond(new int[]{3, 5}, 1, 0, 0),
                        new ValenceBond(new int[]{4, 5}, 2, 0, 0)
                }
        ));

        addMolecule(new Molecule("KCl",
                new Atom[]{
                        getAtom("K", -85, 0,-40,0),
                        getAtom("Cl", 65, 0,40,0)
                },
                new Bond[]{
                        new IonicBond(new int[]{0, 1}, new int[]{-1, 1})
                }
        ));

        addMolecule(new Molecule("NaCl",
                new Atom[]{
                        getAtom("Na", -85, 0,-40,0),
                        getAtom("Cl", 65, 0,40,0)
                },
                new Bond[]{
                        new IonicBond(new int[]{0, 1}, new int[]{-1, 1})
                }
        ));

        addMolecule(new Molecule("BeO",
                new Atom[]{
                        getAtom("Be", -85, 0,-40,0),
                        getAtom("O", 65, 0,40,0)
                },
                new Bond[]{
                        new IonicBond(new int[]{0, 1}, new int[]{-1, 1})
                }
        ));


        addMolecule(new Molecule("NaF",
                new Atom[]{
                        getAtom("Na", -85, 0,-30,0),
                        getAtom("F", 45, 0,30,0)
                },
                new Bond[]{
                        new IonicBond(new int[]{0, 1}, new int[]{-1, 1})
                }
        ));

        addMolecule(new Molecule("LiF",
                new Atom[]{
                        getAtom("Li", -65, 0,-30,0),
                        getAtom("F", 65, 0,40,0)
                },
                new Bond[]{
                        new IonicBond(new int[]{0, 1}, new int[]{-1, 1})
                }
        ));

        addMolecule(new Molecule("MgO",
                new Atom[]{
                        getAtom("Mg", -85, 0,-30,0),
                        getAtom("O", 65, 0,30,0)
                },
                new Bond[]{
                        new IonicBond(new int[]{0, 1}, new int[]{-2, 2})
                }
        ));

        addMolecule(new Molecule("K2O",
                new Atom[]{
                        getAtom("K", -85, 0,-30,0),
                        getAtom("K", 85, 0,30,0),
                        getAtom("O", 0, 0,1,0)
                },
                new Bond[]{
                        new IonicBond(new int[]{0, 2}, new int[]{-1, 1}),
                        new IonicBond(new int[]{1, 2}, new int[]{-1, 1})
                }
        ));


        addMolecule(new Molecule("CaS",
                new Atom[]{
                        getAtom("Ca", -85, 0,-30,0),
                        getAtom("S", 65, 0,30,0)
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
