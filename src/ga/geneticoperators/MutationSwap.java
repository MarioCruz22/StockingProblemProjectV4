package ga.geneticoperators;

import algorithms.IntVectorIndividual;
import algorithms.Problem;
import ga.GeneticAlgorithm;

public class MutationSwap<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public MutationSwap(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        //TODO
        int cut1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        int cut2;
        do {
            cut2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        }while (cut1==cut2);

        int aux = ind.getGene(cut2);
        ind.setGene(cut2, ind.getGene(cut1));
        ind.setGene(cut1, aux);
    }

    @Override
    public String toString(){
        return "Swap";
    }
}