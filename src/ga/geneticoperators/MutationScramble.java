package ga.geneticoperators;

import algorithms.IntVectorIndividual;
import algorithms.Problem;
import ga.GeneticAlgorithm;

public class MutationScramble<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public MutationScramble(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        int cut1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        int cut2;
        do {
            cut2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        } while (cut1 == cut2);
        if (cut1 > cut2) {
            int aux = cut1;
            cut1 = cut2;
            cut2 = aux;
        }

        // make a copy of the aleles to randomize
        int nAleles = cut2 - cut1 + 1;
        int[] genomePartCopy = new int[nAleles];
        for (int i = 0; i < nAleles; i++) {
            genomePartCopy[i] = ind.getGene(i + cut1);
        }

        //change aleles position randomly
        int pos = cut1;
        int aleleToInsert;
        while (pos <= cut2) {
            boolean repeated = false;
            aleleToInsert = genomePartCopy[GeneticAlgorithm.random.nextInt(nAleles)];
            for (int i = cut1; i < pos; i++) {
                if (ind.getGene(i) == aleleToInsert) {
                    repeated = true;
                    break;
                }
            }
            if (!repeated) {
                ind.setGene(pos++, aleleToInsert);
            }
        }
    }

    @Override
    public String toString() {
        return "Scramble";
    }
}