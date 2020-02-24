package archem.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class AtomCollection
{
//    private static class Holder
//    {
//        static final AtomCollection INSTANCE = new AtomCollection();
//    }
//
//    public static AtomCollection getInstance()
//    {
//        return Holder.INSTANCE;
//    }

    static
    {
        atomMap = new TreeMap<>();
        symbolMap = new HashMap<String, Atom>();

        addAtom(new Atom("Hydrogen", "H", 1, 1.00784F,1,0, 1));
        addAtom(new Atom("Helium", "He", 2, 4.0026F,2,2, 2));
        addAtom(new Atom("Lithium", "Li", 3, 6.941F,3,4, 2, 1));
        addAtom(new Atom("Beryllium", "Be", 4, 9.0121F,4,5,2, 2));
        addAtom(new Atom("Boron", "B", 5, 10.811F, 5,6,2, 3));
        addAtom(new Atom("Carbon", "C", 6, 12.010F,6,6, 2, 4));
        addAtom(new Atom("Nitrogen", "N", 7, 14.006F, 7,7,2, 5));
        addAtom(new Atom("Oxygen", "O", 8, 15.999F, 8,8,2, 6));
        addAtom(new Atom("Fluorine", "F", 9, 18.998F,9,10, 2, 7));
        addAtom(new Atom("Sodium", "Na", 11, 22.9897F,11,12, 2, 8, 1));
        addAtom(new Atom("Magnesium", "Mg", 12, 24.305F,12,12, 2, 8, 2));
        addAtom(new Atom("Aluminium", "Al", 13, 26.981F,13,14, 2, 8, 3));
        addAtom(new Atom("Silicon", "Si", 14, 28.085F,14,14, 2, 8, 4));
        addAtom(new Atom("Phosphorous", "P", 15, 30.973F, 15,16,2, 8, 5));
        addAtom(new Atom("Sulphur", "S", 16, 32.065F,16,16, 2, 8, 6));
        addAtom(new Atom("Chlorine", "Cl", 17, 35.453F,17,18, 2, 8, 7));
        addAtom(new Atom("Potassium", "K", 19, 39.098F,19,20, 2, 8, 8, 1));
        addAtom(new Atom("Calcium", "Ca", 20, 40.078F,20,20, 2, 8, 8, 2));
        addAtom(new Atom("Scandium", "Sc", 21, 44.955F,21,24, 2, 8, 9, 2));
        addAtom(new Atom("Titanium", "Ti", 22, 47.867F,22,26, 2, 8, 10, 2));
        addAtom(new Atom("Vanadium", "V", 23, 50.941F,23,28, 2, 8, 11, 2));
        addAtom(new Atom("Chromium", "Cr", 24, 51.996F, 24,28,2, 8, 13, 1));
        addAtom(new Atom("Manganese", "Mn", 25, 54.938F,25,30, 2, 8, 13, 2));
        addAtom(new Atom("Iron", "Fe", 26, 55.845F,26,30, 2, 8, 14, 2));
        addAtom(new Atom("Cobalt", "Co", 27, 58.993F, 27,32,2, 8, 15, 2));
        addAtom(new Atom("Nickel", "Ni", 28, 58.693F, 28,31,2, 8, 16, 2));
        addAtom(new Atom("Copper", "Cu", 29, 63.546F,29,35, 2, 8, 18, 1));
        addAtom(new Atom("Zinc", "Zn", 30, 65.409F,30,35, 2, 8, 18, 2));
        addAtom(new Atom("Gallium", "Ga", 31, 69.723F,31,39, 2, 8, 18, 3));
        addAtom(new Atom("Germanium", "Ge", 32, 72.64F, 32,41,2, 8, 18, 4));
        addAtom(new Atom("Arsenic", "As", 33, 74.9216F,33,42, 2, 8, 18, 5));
        addAtom(new Atom("Selenium", "Se", 34, 78.96F,34,45, 2, 8, 18, 6));
        addAtom(new Atom("Bromine", "Br", 35, 79.904F, 35,45,2, 8, 18, 7));
       // addAtom(new Atom("Krypton", "Kr", 36, 83.798F, 2, 8, 18, 8));
        addAtom(new Atom("Rubidium", "Rb", 37, 85.467F,37,48, 2, 8, 18, 8, 1));
        addAtom(new Atom("Strontium", "Sr", 38, 87.62F,38,50 ,2, 8, 18, 8, 2));
        addAtom(new Atom("Yttrium", "Y", 39, 88.905F,39,50, 2, 8 ,18, 9, 2));
        addAtom(new Atom("Zirconium", "Zr", 40, 91.224F,40,51, 2, 8, 18, 10, 2));
        addAtom(new Atom("Niobium", "Nb", 41, 92.906F,41,52, 2, 8, 18, 12, 1));
        addAtom(new Atom("Molybdenum", "Mol", 42, 95.94F,42,54, 2, 8, 18, 13, 1));
        addAtom(new Atom("Silver", "Ag", 47, 107.868F,47,61, 2, 8, 18, 18, 1));
        addAtom(new Atom("Cadmium", "cd", 48, 112.411F, 48,64,2, 8, 18, 18, 2));
        addAtom(new Atom("Indium", "In", 49, 114.818F, 49,66,2, 8, 18, 18, 3));
        addAtom(new Atom("Tin", "Sn", 50, 118.710F,50,69, 2, 8, 18, 18, 4));
        addAtom(new Atom("Iodine", "I", 53, 126.904F,53,74, 2, 8, 18, 18, 7));
        addAtom(new Atom("Caesium", "Cs", 55, 132.905F,55,78, 2, 8, 18, 18, 8, 1));
        addAtom(new Atom("Barium", "Ba", 56, 137.327F,56,81, 2, 8, 18, 18, 8, 2));
        addAtom(new Atom("Tungsten", "W", 74, 183.84F,74,109, 2, 8, 18, 32, 12, 2));
        addAtom(new Atom("Platinum", "Pt", 78, 195.084F,78,117, 2, 8, 18, 32, 17, 1));
        addAtom(new Atom("Gold", "Au", 79, 196.966F,79,118, 2, 8, 18, 32, 18, 1));
        addAtom(new Atom("Mercury", "Hg", 80, 200.59F, 80,120,2, 8, 18, 32, 18, 2));
        addAtom(new Atom("Lead", "Pb", 82, 207.2F,82,125,2, 8, 18, 32, 18, 4));
        addAtom(new Atom("Radium", "Ra", 88, 226.0254F,88,138, 2, 8, 18, 32, 18, 8, 2));

    }

    public static SortedMap<Integer, Atom> atomMap;
    public static Map<String, Atom> symbolMap;

    public static void addAtom(Atom a)
    {
        atomMap.put(a.N, a);
        symbolMap.put(a.symbol,a);
    }

    public static Atom getAtom(int N)
    {
        return atomMap.get(N).clone();
    }

    public static Atom getAtom(String symbol, int newx, int newy)
    {
        Atom a=getAtom(symbol).clone();
        a.x=newx;
        a.y=newy;

        return a;
    }
    public static Atom getAtom(String symbol)
    {
        return symbolMap.get(symbol).clone();
    }

    @Override
    public String toString()
    {
        return "AtomCollection{}";
    }
}
