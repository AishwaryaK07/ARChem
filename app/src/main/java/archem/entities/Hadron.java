package archem.entities;

import android.util.Log;

import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Hadron
{
    Node n;
    public double x;
    public double y;
    public double z;
    public float r;
    public float theta;
    public float phi;
    public float deltaphi;
    public float deltatheta = .01f;

    public Hadron(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString()
    {
        return "Hadron[" +
                "x=" + x +
                ", y=" + y + "z=" + z +
                ']';
    }


    public void update()
    {
        theta += deltatheta;
        phi += deltaphi;
//        double radians=Math.toRadians(theta);
        x = 0.01 * cos(theta) * sin(phi);
        y = 0.01 * cos(theta);
        z = 0.01 * sin(theta) * sin(phi);

    //    Log.d("test", "hydron: update: " + x + "," + y + " ," + z);

        if (n != null)
        {
            n.setLocalPosition(new Vector3((float) x, (float) y, (float) z));
        } else
        {
            Log.d("test", "null node");
        }
    }


}
