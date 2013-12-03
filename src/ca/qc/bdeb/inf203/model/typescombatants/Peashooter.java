package ca.qc.bdeb.inf203.model.typescombatants;

import ca.qc.bdeb.inf203.model.Combattant;
import ca.qc.bdeb.inf203.model.Etats;
import ca.qc.bdeb.inf203.model.RepresentationImage;
import java.util.HashMap;

/**
 *
 * @author Guillaume Riou
 */
public class Peashooter extends Combattant {

    public Peashooter() {
        super();
        this.attaqueRate = 0;
        this.vitesse = 0;
        this.attaque = 30;
        this.hitbox.width = 80;
        this.hitbox.height = 80;
        this.nbImagesParActions = new HashMap<>();
        this.nbImagesParActions.put(Etats.DEPLACEMENT, 4);
        this.nbImagesParActions.put(Etats.ATTENTELIGNEDEVUE, 11);
        this.sprite = new RepresentationImage(new String[]{"plants", "pea-shooter", "normal"});
    }

    public Peashooter(Combattant c) {
        super(c);
    }

    @Override
    public Combattant tic() {
        Combattant retour = null;
        switch (etat) {
            case ATTAQUE:
                retour = action();
                break;
            case DEPLACEMENT:
                deplacer();
                break;

        }
        return retour;
    }

    /**
     * l'action à distance d'un peashooter est de retourner un pois
     *
     * @return
     */
    @Override
    public Combattant action() {

        boolean tousMorts = true;
        for (Combattant combattant : cibles) {
            if (combattant.getVie() > 0) {
                tousMorts = false;
            }
        }
        if (tousMorts) {
            this.etat = Etats.ATTENTELIGNEDEVUE;
        }
        return new Pois();
    }
}
