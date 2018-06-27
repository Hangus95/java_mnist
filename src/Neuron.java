import java.util.*;


public class Neuron
{
    // instance variables - replace the example below with your own
    public ArrayList<connection> inConnections;
    public ArrayList<connection> outConnections;
    
    float output = 0;
    float input = 0;
    float weightToOutput = 0;
    float delta = 0;
    
    
    /**
     * Constructor for objects of class Neuron
     */
    public Neuron()
    {
        inConnections = new ArrayList<connection>();
        outConnections = new ArrayList<connection>();
    }
    
    public static float sigmoid(float in) {
        return 1.0f / (1.0f + (float) Math.exp(-in));
    }
    
    public void calcInput(){
        for( connection c : inConnections){
            float in = c.weight * c.from.output;
            input += in;
        }
    }
    
    public void calcOutput(){
        output = sigmoid(input);
    }
  
}
