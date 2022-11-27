package tournamentmanager.core.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantImplTestStructurelle {
    ParticipantImpl p;
    @BeforeEach
    void init(){
        p = new ParticipantImpl("Participant");
    }
    //Ajout methode structurelle
    @Test
    void getName() {
        assertEquals("Participant",p.getName());
    }


    //Ajout methode structurelle
    @Test
    void isEliminated() {
        assertFalse(p.isEliminated());
    }


    //Ajout methode structurelle
    @Test
    void eliminate() {
        p.eliminate();
        assertTrue(p.isEliminated());
    }


    //Ajout methode structurelle
    @Test
    void setName() {
        p.setName("newname");
        assertEquals("newname",p.getName());
    }
}