# Java MNIST database
This example was created to show how a neural network can be created in Java using only standard libraries and fully contained.

This code contains a fully self contained implementation of a neural network that is then tested on the MNIST-database.
When I first learnt about backpropagation I tried to find an implementation of the algorithm that didnt use any libraries that wasn't standard in order to understand it on a fundamental level.

I did find some Java implementations, but all used some sort of library to implement backpropagation. 

If you are trying to understand how a very simple neural network can be implemented in Java without using any external library this repository is for you. 

Please feel free to give me some feedback on the code and how it could be improved. 

## Setup
To run the code simply run the Main method in the Main class.
This will create a neural network with 784 input neurons, 40 hidden neurons and 10 output neurons.

Each input neuron represents one pixel of the 28x28 image that is included in repository while each output neron represents the predicted number between 0 through 9. 

After the code has itirated trhough 55k images the ongoing results will be printed in the terminal. 
If the model predicts wrong, a newline will be printed in order to find the error easier. 

Some images are rather difficult to predict, even for a human. 
![alt text](http://neuralnetworksanddeeplearning.com/images/mnist_really_bad_images.png)
## ToDo
Implement the testing in a more proper fashion using the seperate testing file.






