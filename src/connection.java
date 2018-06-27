

public class connection
{
   
    Neuron from, to;
    float weight;
    float dW = 0;
   
    public connection(Neuron from, Neuron to, float weight)
    {
        this.weight = weight;
        this.from = from;
        this.to = to;
       
    }
    
    public float getWeight(){
        return weight;
    }
    
    public void setWeight(float weight){
        this.weight = weight;
    }

   
}
