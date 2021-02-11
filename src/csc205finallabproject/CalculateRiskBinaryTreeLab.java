// Project Name: CSC205FinalLabProject
// File Name: BinaryTree
// Author: Cameron Baker
// Class: CSC205AA TTh 2pm
// Date: May 13, 2020
// Description: Generates a population based on rates from current US census 
// information and displays statistics on risk of severe symptoms for a given
// age group.

package csc205finallabproject;

import java.util.Random; // Used in generating individuals
import java.util.Scanner; // Used for taking user inputs

/**
 * @author Cameron Baker 
 * Main class of the program. Used for test data.
 */
public class CalculateRiskBinaryTreeLab {

    public static void main(String[] args) {
        Scanner type = new Scanner(System.in);
        BinaryTree primary = new BinaryTree(); // The binary tree we will be using
        System.out.println("This program estimates the risk percentage of \n"
                + "having immediate serious symptoms if infected with \n"
                + "Coronavirus given an age or age group and a population size.\n"
                + "Data is based off of US population and Coronavirus data.\n");

        System.out.print("Please enter population size(larger will be more accurate): ");
        /*
        Population is how many individuals will be generated when the program runs.
         */
        int population = type.nextInt();

        /*
        For every individual, generate an age and risk potential.
         */
        for (int i = 0; i < population; i++) {
            primary.add();
        }

        System.out.println("");
        System.out.println("A population has been generated. You can now query\n"
                + "an age group. Enter 0 for one to nine year olds or 1 for\n"
                + "ten to nineteen year olds, ect. up to 7(includes all over 70).\n");

        boolean repeat = true; //Loop for multiple queries
        while (repeat) {
            System.out.print("Enter an age group: ");
            /*
            Age groups increment in 10s. So entering zero would consist of one
            to nine, entering one would consist of ten to nineteen, and so on. 
             */
            int ageGroup = type.nextInt();
            type.nextLine(); // Clears new line inputs
            /*
            Stores an array containing population size in [1] and risk level 
            in [2].
             */
            double[] stats = primary.ageGroupHRCount(ageGroup);

            System.out.println("From a population of " + stats[1] + " people,\n"
                    + stats[0] + " are at high risk of having immediate serious symptoms.\n");
            System.out.print("Would you like to perform another query? (y/n) ");

            /*
            Allows user to end or continue using the program.
             */
            String reply = type.nextLine();
            repeat = "y".equals(reply);
            System.out.println("");
        }
    }
}

class BinaryTree {

    private Node root; // Top of the binary tree, center value

    BinaryTree() {
        /*
        Initialized to 50 because it is out center value of the ages will be be
        looking at
         */
        root = new Node(50);
    }

    /**
     * Generates a person and creates a node based on their age. If the age
     * already exists then the population of that age group is increased by one.
     * If the person is old or has preexisting conditions then the high risk
     * counter attached to the node is incremented.
     */
    void add() {
        /*
        Generates a randomized individual base off of current numbers in the
        Coronavirus pandemic.
         */
        Creator person = new Creator();
        Node newNode = new Node(person.age); // Node based on persons age
        Node pointerNode = root; // Node the will be followed through the program
        boolean exit = false; // Used to exit the loop

        while (exit == false) {
            /*
            If the new persons age is less than the pointers age we will either
            create a new node to the left of the current one if it doesn't exist
            or we will move to the next node if it does exist.
             */
            if (newNode.getAge() < pointerNode.getAge()) {
                if (pointerNode.getLeft() == null) {
                    newNode.setPop(); // Increase population of age
                    if (person.riskLevel == 1) {
                        newNode.setHRPop(); // Increase high risk population of age
                    }
                    pointerNode.setLeft(newNode);
                    exit = true;
                } else {
                    pointerNode = (pointerNode.getLeft());
                }
                /*
                If the new persons age is more than the pointers age we will 
                either create a new node to the right of the current one if it 
                doesn't exist or we will move to the next node if it does exist.
                 */
            } else if (newNode.getAge() > pointerNode.getAge()) {
                if (pointerNode.getRight() == null) {
                    newNode.setPop(); // Increase population of age
                    if (person.riskLevel == 1) {
                        newNode.setHRPop(); // Increase high risk population of age
                    }
                    pointerNode.setRight(newNode);
                    exit = true;
                } else {
                    pointerNode = (pointerNode.getRight());
                }
                /*
                If the age is already the same as the pointer node then increase
                the population numbers. The only case for this should be the
                root.
                */
            } else {
                pointerNode.setPop(); // Increase population of age
                if (person.riskLevel == 1) {
                    pointerNode.setHRPop(); // Increase high risk population of age
                }
                exit = true;
            }
        }
    }
    
    /**
     * This method will locate a node based on the age parameter. It searches
     * the tree from lowest age to highest. If found it will then return the 
     * node or if not found it will return an empty node with an age of -1. 
     * @param age What he age of the node we are searching for is
     * @return Node of age parameter
     */
    Node findAge(int age) {
        Node pointerNode = root; // Sets pointer to root
        Node empty = new Node(-1); // Node in the case we don't find Node(age)

        /*
        Loops while we have not found the age we are looking for.
        */
        while (pointerNode.getAge() != age) {
            /*
            If age is less than the age of the pointer node we will either
            return the empty node if there are no further nodes to progress to
            or we will move to the next node.
            */
            if (age < pointerNode.getAge()) {
                if (pointerNode.getLeft() == null) {
                    return empty;
                }
                pointerNode = pointerNode.getLeft();
                /*
                If age is more than the age of the pointer node we will either
                return the empty node if there are no further nodes to progress 
                to or we will move to the next node.
                */
            } else if (age > pointerNode.getAge()) {
                if (pointerNode.getRight() == null) {
                    return empty;
                }
                pointerNode = pointerNode.getRight();
                /*
                If the node doesn't exist then we return empty. This is acts as
                safety net.
                */
            } else {
                return empty;
            }
        }
        return pointerNode; // Returns the node if found
    }
    
    /**
     * This method will add up all of the individuals within an age range and
     * return a double containing the population and high risk individuals that
     * are within the age range.
     * 
     * @param ageGroup Which category we want to search. Entering 0 returns 
     * ages one to nine, entering 1 returns ten to nineteen, and so on up to
     * seven. From there all people over seventy are grouped together.
     * @return Returns a double array with [0] being total number of high risk 
     * individuals and [1] is total population.
     */
    double[] ageGroupHRCount(int ageGroup) {
        Node temp;
        double[] Hrisk = {0, 0}; // Established risk # and total population

        /*
        For which ever age group selected we will move through each node and 
        collect high risk individual count and total population.
        */
        switch (ageGroup) {
            case 0:
                for (int i = 1; i < 10; i++) {
                    /*
                    Makes sure that the age exists before trying to do math
                    with it.
                    */
                    if (findAge(i) != null) {
                        temp = findAge(i);
                        Hrisk[0] += temp.getHRPop();
                        Hrisk[1] += temp.getPop();
                    }
                }
                break;
            case 1:
                for (int i = 10; i < 20; i++) {
                    if (findAge(i) != null) {
                        temp = findAge(i);
                        Hrisk[0] += temp.getHRPop();
                        Hrisk[1] += temp.getPop();
                    }
                }
                break;
            case 2:
                for (int i = 20; i < 30; i++) {
                    if (findAge(i) != null) {
                        temp = findAge(i);
                        Hrisk[0] += temp.getHRPop();
                        Hrisk[1] += temp.getPop();
                    }
                }
                break;
            case 3:
                for (int i = 30; i < 40; i++) {
                    if (findAge(i) != null) {
                        temp = findAge(i);
                        Hrisk[0] += temp.getHRPop();
                        Hrisk[1] += temp.getPop();
                    }
                }
                break;
            case 4:
                for (int i = 40; i < 50; i++) {
                    if (findAge(i) != null) {
                        temp = findAge(i);
                        Hrisk[0] += temp.getHRPop();
                        Hrisk[1] += temp.getPop();
                    }
                }
                break;
            case 5:
                for (int i = 50; i < 60; i++) {
                    if (findAge(i) != null) {
                        temp = findAge(i);
                        Hrisk[0] += temp.getHRPop();
                        Hrisk[1] += temp.getPop();
                    }
                }
                break;
            case 6:
                for (int i = 60; i < 70; i++) {
                    if (findAge(i) != null) {
                        temp = findAge(i);
                        Hrisk[0] += temp.getHRPop();
                        Hrisk[1] += temp.getPop();
                    }
                }
                break;
            default:
                for (int i = 70; i < 101; i++) {

                    temp = findAge(i);
                    Hrisk[0] += temp.getHRPop();
                    Hrisk[1] += temp.getPop();

                }
                break;
        }
        return Hrisk;
    }
}


/*
Creates a single individual a randomized age and risk. The chance an individual
is a certain age or of a certain risk level is determined by current US census
information and risk levels being reported.
*/
class Creator {

    Random rand = new Random();
    int age;
    int riskLevel = 0; // 0 means low risk while 1 means high risk

    Creator() {
        /*
        Rolls are based on US population in millions.
        */
        int roll1 = rand.nextInt(327) + 1;
        int roll2 = rand.nextInt(327) + 1;

        /*
        If an roll is between 1 and 40 then a person between ages 1 and 9 are
        created. And so on. Ranges are based on ages in certain groups based on
        US census.
        */
        if (roll1 > 0 && roll1 <= 40) {
            age = rand.nextInt(9) + 1;
        } else if (roll1 > 40 && roll1 <= 82) {
            age = rand.nextInt(10) + 10;
        } else if (roll1 > 82 && roll1 <= 127) {
            age = rand.nextInt(10) + 20;
        } else if (roll1 > 127 && roll1 <= 171) {
            age = rand.nextInt(10) + 30;
        } else if (roll1 > 171 && roll1 <= 221) {
            age = rand.nextInt(10) + 40;
        } else if (roll1 > 221 && roll1 <= 264) {
            age = rand.nextInt(10) + 50;
        } else if (roll1 > 264 && roll1 <= 301) {
            age = rand.nextInt(10) + 60;
        } else {
            age = rand.nextInt(31) + 70;
        }

        /*
        If someone is 18 or older there is a chance the they will have a
        preexisting condition that will put them at high risk. Again, rates
        based off US census information.
        */
        if (age > 17) {
            if (roll2 > 0 && roll2 <= 90) {
                riskLevel = 1;
            }
            /*
            If an individual is 65 or older they are at high risk.
            */
        } else if (age > 64) {
            riskLevel = 1;
        }
    }
}

/*
Used in creating and manageing nodes. Each will represent an age population 
and the infomation within.
*/
class Node {

    private int age; // Age of group
    private int pop = 0; // Total population size of age
    private int HRPop = 0; // Low Risk Population Size
    private Node leftChild = null; // This node's left child
    private Node rightChild = null; // This node's right child

    /*
    Constructor that makes a node based on the age parameter.
    */
    public Node(int personAge) {
        this.age = personAge;
    }

    int getAge() { // Returns age
        return age;
    }

    int getPop() { // Returns total population of age group
        return pop;
    }

    int getHRPop() { // Returns high risk population of age group
        return HRPop;
    }

    void setPop() { // Increments the population by one
        pop++;
    }

    void setHRPop() { // Increments the high risk population by one
        HRPop++;
    }

    Node getLeft() { // Returns left child node
        return leftChild;
    }

    Node getRight() { // Returns right child node
        return rightChild;
    }

    void setLeft(Node node) { // Sets left child node
        leftChild = node;
    }

    void setRight(Node node) { // Sets right child node
        rightChild = node;
    }

    /*
    toString is unused in our tester but is still useful to have.
    */
    @Override
    public String toString() {
        return "In the population of " + age + " year olds, "
                + (getHRPop() / getPop())
                + "% are at high risk of severe symptoms.";
    }
}

/*
run:
This program estimates the risk percentage of 
having immediate serious symptoms if infected with 
Coronavirus given an age or age group and a population size.
Data is based off of US population and Coronavirus data.

Please enter population size(larger will be more accurate): 10000000

A population has been generated. You can now query
an age group. Enter 0 for one to nine year olds or 1 for
ten to nineteen year olds, ect. up to 7(includes all over 70).

Enter an age group: 3
From a population of 1345960.0 people,
371150.0 are at high risk of having immediate serious symptoms.

Would you like to perform another query? (y/n) y

Enter an age group: 0
From a population of 1222455.0 people,
0.0 are at high risk of having immediate serious symptoms.

Would you like to perform another query? (y/n) y

Enter an age group: 7
From a population of 795344.0 people,
218385.0 are at high risk of having immediate serious symptoms.

Would you like to perform another query? (y/n) 

BUILD SUCCESSFUL (total time: 5 seconds)
 */


/*
run:
This program estimates the risk percentage of 
having immediate serious symptoms if infected with 
Coronavirus given an age or age group and a population size.
Data is based off of US population and Coronavirus data.

Please enter population size(larger will be more accurate): 77

A population has been generated. You can now query
an age group. Enter 0 for one to nine year olds or 1 for
ten to nineteen year olds, ect. up to 7(includes all over 70).

Enter an age group: 1
From a population of 11.0 people,
0.0 are at high risk of having immediate serious symptoms.

Would you like to perform another query? (y/n) y

Enter an age group: 5
From a population of 14.0 people,
5.0 are at high risk of having immediate serious symptoms.

Would you like to perform another query? (y/n) y

Enter an age group: 7
From a population of 2.0 people,
0.0 are at high risk of having immediate serious symptoms.

Would you like to perform another query? (y/n) n

BUILD SUCCESSFUL (total time: 5 seconds)
*/
