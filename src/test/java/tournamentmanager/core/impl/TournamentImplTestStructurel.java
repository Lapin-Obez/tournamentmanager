package tournamentmanager.core.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tournamentmanager.core.api.Game;
import tournamentmanager.core.api.Participant;
import tournamentmanager.core.api.Status;
import tournamentmanager.core.api.TournamentException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TournamentImplTestStructurel {
    TournamentImpl tournoi;
    Participant p1;
    Participant p2;
    Participant p3;
    Participant p4;
    @BeforeEach
    void init(){
        tournoi = new TournamentImpl();
        p1 = new ParticipantImpl("Arthur");
        p2 = new ParticipantImpl("John");
        p3 = new ParticipantImpl("Sadie");
        p4 = new ParticipantImpl("Dutch");
    }
    //ajout méthode structurelle
    @Test
    void addParticipant() throws TournamentException {
        Participant p = new ParticipantImpl("nom");
        tournoi.addParticipant(p);
        assertTrue(tournoi.getParticipants().contains(p));
    }
    //ajout méthode structurelle
    @Test
    void addParticipantDéjàLà() throws TournamentException {
        Participant p = new ParticipantImpl("nom");
        tournoi.addParticipant(p);
        tournoi.addParticipant(p);
        assertTrue(tournoi.getParticipants().contains(p));
        assertEquals(1,tournoi.getParticipants().size());
    }
    //ajout méthode structurelle
    @Test
    void addParticipantExceptionNotNotStarted() {
        tournoi.setStatus(Status.INPROGRESS);
        assertThrowsExactly(TournamentException.class,()->tournoi.addParticipant(new ParticipantImpl("nom")));
    }

    //ajout méthode structurelle
    @Test
    void getAllGames() throws TournamentException {
        tournoi.addParticipant(p1);
        tournoi.addParticipant(p2);
        tournoi.addParticipant(p3);
        tournoi.addParticipant(p4);
        this.tournoi.start();
        Game g1;
        Game g2;
        Game g3 = new GameImpl();
        List<Game> matchs = tournoi.getGamesReadyToStart();
        g1 = matchs.get(0);
        g2 = matchs.get(1);
        List<Participant> conccurents;
        for (int i =0; i<2;i++){
            for (Game g:matchs) {

                conccurents = g.getParticipants();
                g.start();
                g.addPoints(conccurents.get(0),10);
                g.finish();
            }
            matchs = tournoi.getFutureGames();
            if (i == 0){
                g3 = matchs.get(0);
            }

        }
        assertTrue(tournoi.getAllGames().contains(g1));
        assertTrue(tournoi.getAllGames().contains(g2));
        assertTrue(tournoi.getAllGames().contains(g3));
    }

    //ajout méthode structurelle
    @Test
    void getGamesReadyToStart() throws TournamentException {
        tournoi.addParticipant(p1);
        tournoi.addParticipant(p2);
        tournoi.addParticipant(p3);
        tournoi.addParticipant(p4);
        tournoi.start();

        assertEquals(2,tournoi.getGamesReadyToStart().size());
    } //ajout méthode structurelle
    @Test
    void getGamesReadyToStartGameAlreadyStarted() throws TournamentException {
        tournoi.addParticipant(p1);
        tournoi.addParticipant(p2);
        tournoi.addParticipant(p3);
        tournoi.addParticipant(p4);
        tournoi.start();
        tournoi.getGamesReadyToStart().get(1).start();
        assertEquals(1,tournoi.getGamesReadyToStart().size());
    }

    @Test
    void getFinishedGames() throws TournamentException {
        tournoi.addParticipant(p1);
        tournoi.addParticipant(p2);
        tournoi.addParticipant(p3);
        tournoi.addParticipant(p4);
        this.tournoi.start();

        List<Game> matchs = tournoi.getGamesReadyToStart();
        List<Participant> conccurents;
        for (int i =0; i<2;i++){
            for (Game g:matchs) {
                conccurents = g.getParticipants();
                g.start();
                g.addPoints(conccurents.get(0),10);
                g.finish();
            }
            matchs = tournoi.getFutureGames();
        }
        tournoi.end();
        assertEquals(3,tournoi.getFinishedGames().size());
    }

}