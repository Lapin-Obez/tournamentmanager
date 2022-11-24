package tournamentmanager.core.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tournamentmanager.core.api.Participant;
import tournamentmanager.core.api.Status;
import tournamentmanager.core.api.Tournament;
import tournamentmanager.core.api.TournamentException;

import static org.junit.jupiter.api.Assertions.*;

class TournamentImplTest {

    TournamentImpl tournoi;
    @BeforeEach
    void init(){
       tournoi = new TournamentImpl();
    }
    //ajout méthode fonctionelle
    @Test
    void addParticipant() throws TournamentException {
        Participant p = new ParticipantImpl("nom");
        tournoi.addParticipant(p);
        assertTrue(tournoi.getParticipants().contains(p));
    }
    //ajout méthode fonctionelle
    @Test
    void addParticipantExceptionINPROGRESS() {
        tournoi.setStatus(Status.INPROGRESS);
        assertThrowsExactly(TournamentException.class,()->tournoi.addParticipant(new ParticipantImpl("nom")));
        }
    //ajout méthode fonctionelle
    @Test
    void addParticipantExceptionFINISHED() {
       tournoi.setStatus(Status.FINISHED);
        assertThrowsExactly(TournamentException.class,()->tournoi.addParticipant(new ParticipantImpl("nom")));
    }
    //ajout méthode fonctionelle
    @Test
    void start() throws TournamentException {
        for (int i =0;i<16;i++){
            tournoi.addParticipant(new ParticipantImpl(""+i));
        }
        tournoi.start();
        assertEquals(Status.INPROGRESS,tournoi.getStatus());
    }
    //ajout méthode fonctionelle
    @Test
    void startException1Participant() throws TournamentException {
        tournoi.addParticipant(new ParticipantImpl("nom"));
        assertThrowsExactly(TournamentException.class,()->tournoi.start());
    }
    //ajout méthode fonctionelle
    @Test
    void startExceptionPasPuissanceDeux() throws TournamentException {
        for (int i =0;i<12;i++){
            tournoi.addParticipant(new ParticipantImpl(""+i));
        }
        assertThrowsExactly(TournamentException.class,()->tournoi.start());

    }
    //ajout méthode fonctionelle
    @Test
    void startExceptionInProgress() throws TournamentException {
        for (int i =0;i<16;i++){
            tournoi.addParticipant(new ParticipantImpl(""+i));
        }
        tournoi.setStatus(Status.INPROGRESS);
        assertThrowsExactly(TournamentException.class,()->tournoi.start());

    }
    //ajout méthode fonctionelle
    @Test
    void startExceptionFinished() throws TournamentException {
        for (int i =0;i<16;i++){
            tournoi.addParticipant(new ParticipantImpl(""+i));
        }
        tournoi.setStatus(Status.FINISHED);
        assertThrowsExactly(TournamentException.class,()->tournoi.start());
    }

    @Test
    void end() {
    }

    @Test
    void getAllGames(){

    }

    @Test
    void getRounds() {
    }

    @Test
    void getGamesReadyToStart() {
    }

    @Test
    void getFinishedGames() {
    }

    @Test
    void getGamesInProgress() {
    }

    @Test
    void getFutureGames() {
    }

    @Test
    void computeFinalRanking() {
    }

    @Test
    void getStatus() {
        assertEquals(Status.NOTSTARTED,tournoi.getStatus());

    }
    @Test
    void getStatusINPROGRESS() {
        tournoi.setStatus(Status.INPROGRESS);
        assertEquals(Status.INPROGRESS,tournoi.getStatus());
    }
    @Test
    void getStatusFINISHED() {
        tournoi.setStatus(Status.FINISHED);
        assertEquals(Status.FINISHED,tournoi.getStatus());
    }
}