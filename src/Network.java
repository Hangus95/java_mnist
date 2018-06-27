import java.util.*;
import java.io.*;


public class Network
{
    // The three layers of the network
    ArrayList<inputNeuron> inputs;
    ArrayList<hiddenNeuron> hidden;
    ArrayList<outputNeuron> outputs;
    
    
    final float learningRate = 0.5f;
    
    // Randomizer to initialize connections weights
    Random rand = new Random();
    
    ArrayList<String> data;
    
    List<int[][]> imgData;
    int[][] labels;
    
    int batches = 60000;
    int currentBatch = 0;
    
    
    int res;
    int maxOut;
    int correct = 0;

    public Network(int noInputs, int noHidden, int noOutputs)
    {
        inputs = new ArrayList<inputNeuron>();
        hidden = new ArrayList<hiddenNeuron>();
        outputs = new ArrayList<outputNeuron>();
        
        createNetwork( noInputs,  noHidden,  noOutputs);
        createConnections();
        //data = new ArrayList<String>();
        prepData();
        
        
        
        while(currentBatch < batches){
            feedForward();
            bpropagate();
            
                
                if(currentBatch > batches - 5000 ){
                   System.out.println("Acc: " + res + " Output: " + maxOut);
                    if(res != maxOut)
                        System.out.println();
                    else
                        correct++;
                }
            
            reset();
            currentBatch++;
            
        }
        float b = currentBatch - 55000.0f;
        float a = correct / b;
        System.out.println("Last 5000 images correct prediction rate:" + a*100 + "%");
        
    }
    
   
    
    private void createNetwork(int noInputs, int noHidden, int noOutputs){
        for(int i = 0; i < noInputs; i++)
            inputs.add(new inputNeuron());
            
        for(int i = 0; i < noHidden; i++)
            hidden.add(new hiddenNeuron());
            
        for(int i = 0; i < noOutputs; i++)
            outputs.add(new outputNeuron());
            
    }
    
    private void createConnections(){
        for(inputNeuron in : inputs){
            for(hiddenNeuron hn : hidden){
                connection c = new connection(in, hn, rand.nextFloat() * 2 -1);
                in.outConnections.add(c);
                hn.inConnections.add(c);
            }
        }
        
         for(hiddenNeuron hn : hidden){
            for(outputNeuron on : outputs){
                connection c = new connection(hn, on, rand.nextFloat() * 2 -1);
                hn.outConnections.add(c);
                on.inConnections.add(c);
            }
            
        }
    
       
    }
    
    
    
    private void feedForward(){

        for(int i = 0; i < inputs.size(); i++){
            float dataIn = imgData.get(currentBatch)[i/28][i % 28]; 
            dataIn = 1.0f / 256.0f * dataIn;
            inputs.get(i).input = dataIn;
            inputs.get(i).output = dataIn;
            
        }
        
        for(int i = 0; i < hidden.size(); i++){
           hidden.get(i).calcInput();
           hidden.get(i).calcOutput();
           
        }
        
        for(int i = 0; i < outputs.size(); i++){
           outputs.get(i).calcInput();
           outputs.get(i).calcOutput();
          
        }



    }
   
    
    public void bpropagate(){
        int max = 0;
        float maxo = 0;
        
        for(int i = 0; i < outputs.size(); i++){
            outputNeuron on = outputs.get(i);
            on.delta = -(labels[currentBatch][i]-on.output)*on.output*(1-on.output);
            
            if(on.output > maxo){
                maxo = on.output;
                max = i;
            }
            if(labels[currentBatch][i] == 1)
                res = i;
                
            for(connection c : on.inConnections)
                c.dW = on.delta*c.from.output;
                
        }
        maxOut = max;
                
        for(hiddenNeuron hn : hidden){
            
            for(connection c: hn.outConnections)
                hn.delta += c.to.delta*c.weight;
            
            hn.delta *=hn.output*(1-hn.output);
            
            for(connection c: hn.inConnections)
                c.dW = c.to.delta*c.from.output;
            
        }
            

        for(outputNeuron on : outputs)
            for(connection c: on.inConnections)
                c.setWeight( c.getWeight() - learningRate*c.dW);
            
        for(hiddenNeuron hn : hidden)
            for(connection c: hn.inConnections)
                c.setWeight( c.getWeight() - learningRate*c.dW);

        
    }
    
    public void reset(){
        for(inputNeuron in : inputs){
            in.input = 0;
            in.output = 0;
        }
        for(hiddenNeuron hn : hidden){
            hn.input = 0;
            hn.output = 0;
        }
        for(outputNeuron on : outputs){
            on.input = 0;
            on.output = 0;
        }
            
        maxOut = 0;
        res = 0;
    }
    
    public float feedForward(float a, float b){
        float[] arr = new float[2];
        arr[0] = a;
        arr[1] = b;
        for(int i = 0; i < inputs.size(); i++){
            float dataIn = arr[i];
            inputs.get(i).input = dataIn;
            inputs.get(i).output = dataIn;
            
        }
        
        for(int i = 0; i < hidden.size(); i++){
           hidden.get(i).calcInput();
           hidden.get(i).calcOutput();
           
        }
        
        
        outputs.get(0).calcInput();
        outputs.get(0).calcOutput();
        float retOut = outputs.get(0).output;
        reset();
        return retOut;
    }
    
    public void prepData(){
        imgData = MnistReader.getImages("train-images-idx3-ubyte");
        labels = new int[imgData.size()][10];   
        
        int tmp[] = MnistReader.getLabels("train-labels-idx1-ubyte");
        
        for(int i = 0; i < imgData.size(); i++){
            int label = tmp[i];
            for(int labelNo = 0; labelNo < 10; labelNo++){
                if(labelNo == label)
                    labels[i][labelNo] = 1;
                else
                    labels[i][labelNo] = 0;
            }
            
        }

    }
    
 
    
  
    

 
}
