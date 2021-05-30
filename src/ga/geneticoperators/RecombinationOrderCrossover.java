package ga.geneticoperators;

import algorithms.IntVectorIndividual;
import algorithms.Problem;
import ga.GeneticAlgorithm;
import jdk.jfr.Description;

public class RecombinationOrderCrossover<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {

    public RecombinationOrderCrossover(double probability) {
        super(probability);
    }

    private int[] child1, child2;

    private int cut1;
    private int cut2;

    @Override
    public void recombine(I ind1, I ind2) {
        child1 = new int[ind1.getNumGenes()];
        child2 = new int[ind2.getNumGenes()];
        cut1 = GeneticAlgorithm.random.nextInt(ind1.getNumGenes());
        do {
            cut2 = GeneticAlgorithm.random.nextInt(ind1.getNumGenes());
        } while (cut1 == cut2);
        if (cut1 > cut2) {
            int aux = cut1;
            cut1 = cut2;
            cut2 = aux;
        }

        //copy the inner cuts
        for (int i = cut1; i <= cut2; i++) {
            child1[i] = ind1.getGene(i);
            child2[i] = ind2.getGene(i);
        }

        genomeFiller(child1, ind1, ind2);
        genomeFiller(child2, ind2, ind1);

        for (int i = 0; i < ind1.getNumGenes(); i++) {
            ind1.setGene(i, child1[i]);
            ind2.setGene(i, child2[i]);
        }
    }

    private void genomeFiller(int[] child, I parent1, I parent2) {
        // fill the child previously filled with the inner genes from cuts from parent1 with parent2 genes
        // starting on (cut2 + 1) ignoring the ones already in child (inner cuts). genome last gene next gene is the first one of genome.
        int index, iterator, pos, x = cut2 + 1;
        int nElements = cut2 - cut1 + 1;
        int childLength = child.length;
        int nLeft = childLength - nElements;
        for (int i = cut2 + 1; nLeft != 0; i++) {
            iterator = (i % childLength);
            pos = (x % childLength);

            index = parent1.getIndexof(parent2.getGene(iterator));
            if (index <= cut2 && index >= cut1) {
                continue;
            }
            child[pos] = parent2.getGene(iterator);
            x++;
            nLeft--;
        }
    }


    @Override
    public String toString() {
        return "Order Crossover";
    }
}