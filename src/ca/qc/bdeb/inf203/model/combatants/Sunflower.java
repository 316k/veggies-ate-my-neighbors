package ca.qc.bdeb.inf203.model.combatants;

import ca.qc.bdeb.inf203.model.Action;
import ca.qc.bdeb.inf203.model.Combattant;
import ca.qc.bdeb.inf203.model.Entite;
import ca.qc.bdeb.inf203.model.Etat;
import ca.qc.bdeb.inf203.model.RepresentationImage;
import ca.qc.bdeb.inf203.model.powerups.Soleil;
import java.awt.Rectangle;

/**
 * Plante qui produit du soleil.
 *
 * @author Guillaume Riou, Nicolas Hurtubise
 */
public class Sunflower extends Combattant {


    public Sunflower() {
        super();
        this.vie = 10;
        initialise();
    }

    @Override
    protected final void initialise() {
        super.initialise();
        this.gentil = true;
        this.hitbox.width = 80;
        this.hitbox.height = 80;
        this.lineOfSight = new Rectangle();
        this.animationFrameRate = 6;

        this.nbrImagesAnimation.put(Etat.ATTENTE, 4);
        this.nbrImagesAnimation.put(Etat.DEPLACEMENT, 5);
        this.nbrImagesAnimation.put(Etat.ATTAQUE, 1);

        this.vitesseAction.put(Action.ACTION, 1 / 30f);

        this.sprites.put(Etat.ATTENTE, new RepresentationImage(new String[]{"plants", "sunflower"}));

        this.etat = Etat.ATTENTE;
    }

    @Override
    public Entite tic() {
        Entite soleil = null;
        switch (etat) {
            case ATTENTE:
                soleil = action(getNbrActions(Action.ACTION));
                break;
        }

        return soleil;
    }

    /**
     * 
     *
     * @return un soleil si nbFois >0
     */
    @Override
    public Entite action(int nbFois) {

//        for (int i = 0; i < nbFois; i++) {
//
//            long ts = System.currentTimeMillis();
//            Soleil soleil = null;
//
//            if (this.soleil != null && !this.soleil.isUsed()) {
//                dernierSoleilTimestamp = ts;
//            } else if (ts - dernierSoleilTimestamp >= tempsGenerationSoleil * 1000.0) {
//                soleil = new Soleil(25, hitbox.x, hitbox.y);
//                dernierSoleilTimestamp = ts;
//                this.soleil = soleil;
//            }
//        }
        if (nbFois > 0) {
            return new Soleil(25, hitbox.x, hitbox.y);
        } else {
            return null;
        }
    }
}
