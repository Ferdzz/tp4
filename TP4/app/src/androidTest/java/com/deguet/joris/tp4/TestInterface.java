package com.deguet.joris.tp4;

import android.support.test.runner.AndroidJUnit4;

import com.deguet.joris.tp4.monnayeur.deguet.ChangeService;

import org.junit.runner.RunWith;

/**
 * Created by joris on 16-01-27.
 */
@RunWith(AndroidJUnit4.class)
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
