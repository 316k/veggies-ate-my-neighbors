/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.inf203.model.typespowerups;

import ca.qc.bdeb.inf203.model.Item;
import ca.qc.bdeb.inf203.model.Joueur;
import ca.qc.bdeb.inf203.model.PowerUp;

/**
 *
 * @author 1029172
 */
public class PlanteUnlock extends PowerUp {
    private Item item;

    public PlanteUnlock(Item item) {
        super(item.getNom());
        this.item = item;
    }

    @Override
    public void action() {
        Joueur.instance().debloquerItem(item);
    }
    
}