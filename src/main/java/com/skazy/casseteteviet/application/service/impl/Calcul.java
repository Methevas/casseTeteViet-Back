package com.skazy.casseteteviet.application.service.impl;

import com.skazy.casseteteviet.infrastructure.entite.Solution;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Calcul {

    private static int[] listeValeurs;
    private final List<Solution> listeSolutions;
    private long tempsDecalcul = 0;

    public Calcul() {
        listeValeurs = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        listeSolutions = new ArrayList<>();
    }

    /**
     * Lance la fonction recursive initiale afin de calculer toutes les solutions qui donne 66
     */
    public List<Solution> calculerSolutions() {
        // on supprime toutes les solutions existantes
        // considérant qu'on à déjà calculé les solutions et qu'elles ne changeront pas, on peut eventuellement
        // intégrer le fait de "simplement" renvoyer la liste déjà calculé pour gagner du temps et eviter d'utiliser
        // des ressources inutilement.
        listeSolutions.clear();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        intervertirValeursTableau(listeValeurs, 0, listeValeurs.length - 1);
        stopWatch.stop();
        tempsDecalcul = stopWatch.getTotalTimeMillis();
        return listeSolutions;
    }

    /**
     * Fonction recursive qui intervertit toutes les valeurs du tableau.
     *
     * @param listeValeurs int[]
     * @param debutIndex   int
     * @param finIndex     int
     */
    private void intervertirValeursTableau(int[] listeValeurs, int debutIndex, int finIndex) {
        // si on est arrivé "au bout" du tableau, on peut donc calculer la solution et si elle est égale à 66, l'ajouter à la liste des solutions.
        if (debutIndex == finIndex) {
            if (caclulEgale66(listeValeurs)) {
                Solution solution = new Solution();
                String valeurs = fonctionMagique(listeValeurs);
                solution.setValeurs(valeurs);
                //on considère que les solutions trouvées ici sont forcément vrai
                solution.setStatut(true);
                listeSolutions.add(solution);
            }
        } else {
            for (int i = debutIndex; i <= finIndex; i++) {
                intervertirAetB(listeValeurs, debutIndex, i);
                // on va appeler à nouveau la fonction afin de générer les permutations suivantes
                intervertirValeursTableau(listeValeurs, debutIndex + 1, finIndex);
                // une fois la fonction recursive terminé pour cette partie de la boucle,
                // on rétablie le tableau dans son état d'origine.
                intervertirAetB(listeValeurs, debutIndex, i);
            }
        }
    }

    /**
     * intervertit dans le tableau, 2 valeurs données en entré
     *
     * @param listeValeurs int[]
     * @param a            int
     * @param b            int
     */
    private static void intervertirAetB(int[] listeValeurs, int a, int b) {
        int temp = listeValeurs[a];
        listeValeurs[a] = listeValeurs[b];
        listeValeurs[b] = temp;
    }

    /**
     * Renvoi si la liste de valeurs donnée en entrée donne 66 avec un calcul spécifique
     *
     * @param listeValeurs int[]
     * @return boolean
     */
    public static boolean caclulEgale66(int[] listeValeurs) {
        int resultat = listeValeurs[0] + ((13 * listeValeurs[1]) / listeValeurs[2]) + listeValeurs[3] + (12 * listeValeurs[4]) - listeValeurs[5] - 11 + ((listeValeurs[6] * listeValeurs[7]) / listeValeurs[8])- 10;
        return 66 == resultat;
    }

    /**
     * Méthode permettant de recuperer le temps de calcul de la derniere execution
     *
     * @return long
     */
    public Long getTempsDecalcul() {
        return tempsDecalcul;
    }

    /**
     * Fonction de Mapping obscure qui devrait avoir sa place nul part je pense (ou dans un Mapper au pire)
     *
     * @param listeValeurs int[]
     * @return String
     */
    private static String fonctionMagique(int[] listeValeurs) {
        return Arrays.stream(listeValeurs).boxed().toList().stream().map(Object::toString).collect(Collectors.joining(","));
    }

}
