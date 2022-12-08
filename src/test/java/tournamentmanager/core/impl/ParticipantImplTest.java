package tournamentmanager.core.impl;

import org.junit.jupiter.api.Test;
import tournamentmanager.core.api.Participant;

import static org.junit.jupiter.api.Assertions.*;

public class ParticipantImplTest {
    Participant personne = new ParticipantImpl("Terracid");
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void getName() {
        assertEquals("Terracid",personne.getName());
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void isEliminatedFalse() {
        assertFalse(personne.isEliminated());

    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void isEliminatedTrue(){
        personne.eliminate();
        assertTrue(personne.isEliminated());
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void eliminate() {
        assertFalse(personne.isEliminated());
        personne.eliminate();
        assertTrue(personne.isEliminated());
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void setName() {
        personne.setName("Laink");
        assertEquals("Laink",personne.getName());
    }
}