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

        int geneInd1, geneInd1Pos;
        int geneInd2 = ind1.getGene(0); // 1st iteration has index 0 gene/alele of ind 1
        do {
            geneInd1 = ind1.getGene(ind1.getIndexof(geneInd2));
            geneInd1Pos = ind1.getIndexof(geneInd1);
            child1[geneInd1Pos] = geneInd1;
            geneInd2 = ind2.getGene(geneInd1Pos);
            child2[geneInd1Pos] = geneInd2;
        } while (ind1.getGene(0) != geneInd2);

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