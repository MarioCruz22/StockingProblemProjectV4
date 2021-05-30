package ga.geneticoperators;

import algorithms.IntVectorIndividual;
import algorithms.Problem;
import ga.GeneticAlgorithm;

public class MutationInvertion<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public MutationInvertion(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        int cut1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        int cut2;
        do {
            cut2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        }while (cut1==cut2);
        if (cut1 > cut2) {
            int aux = cut1;
            cut1 = cut2;
            cut2 = aux;
        }

        while (cut1 < cut2){
            int aux = ind.getGene(cut2);
            ind.setGene(cut2, ind.getGene(cut1));
            ind.setGene(cut1, aux);
            cut1++;
            cut2--;
        }
    }

    @Override
    public String toString(){
        return "Invertion";
    }
}