package ga.geneticoperators;

import algorithms.IntVectorIndividual;
import algorithms.Problem;
import ga.GeneticAlgorithm;

public class RecombinationCycleCrossover<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {

    public RecombinationCycleCrossover(double probability) {
        super(probability);
    }

    private int[] child1, child2;

    @Override
    public void recombine(I ind1, I ind2) {
        int numGenes = ind1.getNumGenes(); // same as ind2
        child1 = new int[numGenes];
        child2 = new int[numGenes];

        int geneParent1, geneParent2 = ind1.getGene(0), indexGeneParent1;
        boolean cycle = false;
        while (!cycle) {
            //get gene and its index on parent 1
            indexGeneParent1 = ind1.getIndexof(geneParent2);
            geneParent1 = ind1.getGene(indexGeneParent1);

            //gene from parent 2 with same gene index from parent 1
            // X   X   X   X    Parent 1
            //     |
            // X   X   X   X    Parent 2
            geneParent2 = ind2.getGene(indexGeneParent1);

            child1[indexGeneParent1] = geneParent1;
            child2[indexGeneParent1] = geneParent2;

            if (ind1.getIndexof(geneParent2) == 0) { // cuz it starts on index 0
                cycle = true;
                break;
            }
        }

        for (int i = 0; i < numGenes; i++) {
            if (child1[i] < 1) { //same as test for child2
                child1[i] = ind2.getGene(i);
                child2[i] = ind1.getGene(i);
            }
        }

        for (int i = 0; i < numGenes; i++) {
            ind1.setGene(i, child1[i]);
            ind2.setGene(i, child2[i]);
        }

    }



    @Override
    public String toString() {
        return "Cycle Crossover";
    }
}