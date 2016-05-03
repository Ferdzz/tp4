package com.deguet.joris.tp4;

/**
 * Created by joris on 16-01-27.
 */
public interface TestInterface {

    /**
     * Renvoie le calculateur que vous avez implanté
     * @return
     */
    ChangeService service();

    /**
     * Renvoie vos nom et prénom
      * @return
     */
    String nomEtudiant() ;
}
