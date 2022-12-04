package tournamentmanager.core.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tournamentmanager.core.api.Game;
import tournamentmanager.core.api.Participant;
import tournamentmanager.core.api.Status;
import tournamentmanager.core.api.TournamentException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GameImplTestStructurelle {
    GameImpl game;
    Participant p1;
    Participant p2;
    @BeforeEach
    void initialisationGame(){
        game = new GameImpl();
        p1 = new ParticipantImpl("Don Pedro");
        p2 = new ParticipantImpl("Margoulin");
    }



    //Ajout méthode structurelle
    @Test
    void addParticipantNull() {
        assertThrowsExactly(IllegalArgumentException.class,()->game.addParticipant(null));
    }


    //Ajout méthode structurelle
    @Test
    void addParticipantTroisiemeParticipant() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        Participant p3 = new ParticipantImpl("TroisiemeMan");
        assertThrowsExactly(TournamentException.class,()->game.addParticipant(p3));
    }


    //Ajout méthode structurelle
    @Test
    void addParticipantMemePartcipant() throws TournamentException {
        game.addParticipant(p1);
        assertThrowsExactly(TournamentException.class,()->game.addParticipant(p1));
    }


    //Ajout méthode structurelle
    @Test
    void addParticipant() throws TournamentException {
        game.addParticipant(p1);
        assertEquals(1,game.getParticipants().size());
    }



    //Ajout méthode structurelle
    @Test
    void getLoserNotFinished() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        game.start();
        game.addPoints(p1,50);
        assertThrowsExactly(TournamentException.class,()->game.getWinner());
    }


    //Ajout méthode structurelle
    @Test
    void getLoserP1() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        game.start();

        game.addPoints(p2,50);
        game.finish();

        assertEquals(p1,game.getLoser());
    }


    //Ajout méthode structurelle
    @Test
    void getLoserP2() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        game.start();

        game.addPoints(p1,50);
        game.finish();

        assertEquals(p2,game.getLoser());
    }
}