package ca.qc.bdeb.inf203.model;

import ca.qc.bdeb.inf203.model.combatants.Veggie;
import ca.qc.bdeb.inf203.model.powerups.PlanteUnlock;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * Vague en 2 phases, aléatoire, dispersée, ...
 *
 * @author Guillaume Riou, Nicolas Hurtubise
 */
public class Vague {

    private static Combattant[] archetypesPossibles = {new Veggie()};
    private static PlanteUnlock[] unlocks = {new PlanteUnlock(new Item("body-snatcher", 10, 200, new Veggie()), new Point(150, 150))};
    /**
     * Random
     */
    private static Random rdm = new Random();
    /**
     * Pourcentage d'augmentation du nombre de veggies
     */
    private static double pourcentageAugmentationVeggies = 1.2;
    /**
     * Combatants à générer pour cette vague.
     */
    private final Combattant[] archetypes;
    /**
     * Nombre de combatants à générer par index de type.
     */
    private int[] nbParArchetype;
    private final int nbInitial;
    /**
     * Delais moyen entre les spawns en milisecondes.
     */
    private long delaisMoyen;
    private boolean massiveAttack = false;

    public boolean isMassiveAttack() {
        return massiveAttack;
    }
    /**
     * Delais efficace, est changé après chaque spawn.
     */
    private long delais;
    /**
     * temps en milis qui s'est passé depuis le dernier spawn.
     */
    private long depuisDernierSpawn;
    private long lastTimestamp;

    public Vague(Combattant[] archetypes, int[] nbParArchetype, int delaisMoyen) {
        this.archetypes = archetypes;
        this.nbParArchetype = nbParArchetype;
        this.nbInitial = getRemainingVeggies();
        this.delaisMoyen = delaisMoyen;
        this.delais = delaisMoyen;
        this.lastTimestamp = System.currentTimeMillis();
        setDelais();
    }

    public long getDelaisMoyen() {
        return delaisMoyen;
    }

    public static void setPourcentageAugmentationVeggies(double pourcentageAugmentationVeggies) {
        Vague.pourcentageAugmentationVeggies = pourcentageAugmentationVeggies;
    }

    private void setDelais() {
        int variation = (int) (this.delaisMoyen * 0.5);
        this.delais = delaisMoyen + Vague.rdm.nextInt(variation) - variation;
    }

    public int getRemainingVeggies() {
        int totalRestant = 0;
        for (int i : nbParArchetype) {
            totalRestant += i;
        }
        return totalRestant;
    }

    public boolean isSpawnReady() {
        long temps = System.currentTimeMillis();
        this.depuisDernierSpawn += (temps - this.lastTimestamp);
        if (this.depuisDernierSpawn > this.delais) {
            return true;
        }
        this.lastTimestamp = temps;
        return false;
    }

    public Combattant spawn() {

        if (this.getRemainingVeggies() == 0) {
            return null;
        }

        int combattantIndex;

        // On choisit aléatoirement un type de combattant dans ceux qui restent
        do {
            combattantIndex = Vague.rdm.nextInt(this.archetypes.length);
        } while (this.nbParArchetype[combattantIndex] == 0);

        this.nbParArchetype[combattantIndex]--;

        // Panique de fin de vague
        if (getRemainingVeggies() < (this.nbInitial / 2)) {
            this.delaisMoyen = 400;
            this.massiveAttack = true;
        }

        this.depuisDernierSpawn = 0;
        this.setDelais();

        return archetypes[combattantIndex].clone();
    }

    /**
     * Le nombre de veggies dans une vague donnée
     *
     * @ViveLesMéthodesInutilementRécursives
     *
     * @param numeroVague Le numéro de vague à utiliser
     * @return Le nombre de veggies dans une vague donnée
     */
    public static int getNombreVeggie(int numeroVague) {
        if (numeroVague == 1) {
            return 5;
        }

        return (int) (Vague.getNombreVeggie(numeroVague - 1) * pourcentageAugmentationVeggies);
    }

    /**
     * Génère une vague dont la difficulté dépend du numéro de la vague.
     *
     * @param numeroVague numéro de la vague voulue
     * @return
     */
    public static Vague generateVague(int numeroVague) {
        float multiplicateur = 1 + ((numeroVague / archetypesPossibles.length) - 1) * 0.2f;
        ArrayList<Combattant> combattantsAL = new ArrayList<>();
        for (int i = 0; i < (numeroVague % archetypesPossibles.length) + 1; i++) {
            Combattant ajout = archetypesPossibles[i].clone();
            ajout.multiplyStats(multiplicateur);
            if (numeroVague != 1) {
                ajout.getSprite().setColorisation(
                        new int[]{rdm.nextInt(255), rdm.nextInt(255), rdm.nextInt(255)});
            }
            combattantsAL.add(ajout);
        }
        Combattant[] combattants = combattantsAL.toArray(new Combattant[combattantsAL.size()]);
        System.out.println(combattants.length + "lalalalala");
        int[] nbrCombattantsParType = {getNombreVeggie(numeroVague)};
        Vague vague = new Vague(combattants, nbrCombattantsParType, (int) (5000 / multiplicateur));
        return vague;
    }
}
