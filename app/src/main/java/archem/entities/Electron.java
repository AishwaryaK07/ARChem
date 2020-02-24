package archem.entities;

import android.util.Log;

import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;

public class Electron implements AnimationNode
{
    Node n;
    public double x;
    public double y;
    public float r;
    public float theta;
    public float deltatheta=.01f;
    public MaterialType materialType=MaterialType.ELECTRON;

    public Electron(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString()
    {
        return "Electron[" +
                "x=" + x +
                ", y=" + y +
                ']';
    }

    @Override
    public void update()
    {
        theta+=deltatheta;
//        double radians=Math.toRadians(theta);
        x=r*Math.cos(theta);
        y=r*Math.sin(theta);

    //    Log.d("test","electron: update: "+x+","+y);

        n.setLocalPosition(new Vector3((float)x,0,(float)y));
    }
}
