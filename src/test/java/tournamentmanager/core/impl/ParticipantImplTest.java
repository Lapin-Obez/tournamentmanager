package tournamentmanager.core.impl;

import org.junit.jupiter.api.Test;
import tournamentmanager.core.api.Participant;

import static org.junit.jupiter.api.Assertions.*;

public class ParticipantImplTest {
    Participant personne = new ParticipantImpl("Terracid");
    //Ajout par méthode fonctionnelle
    @Test
    void getName() {
        assertEquals("Terracid",personne.getName());
    }
    //Ajout par méthode fonctionnelle
    @Test
    void isEliminatedFalse() {
        assertFalse(personne.isEliminated());

    }
    //Ajout par méthode fonctionnelle
    @Test
    void isEliminatedTrue(){
        personne.eliminate();
        assertTrue(personne.isEliminated());
    }
    //Ajout par méthode fonctionnelle
    @Test
    void eliminate() {
        assertFalse(personne.isEliminated());
        personne.eliminate();
        assertTrue(personne.isEliminated());
    }
    //Ajout par méthode fonctionnelle
    @Test
    void setName() {
        personne.setName("Laink");
        assertEquals("Laink",personne.getName());
    }
}