package tournamentmanager.core.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tournamentmanager.core.api.Game;
import tournamentmanager.core.api.Participant;
import tournamentmanager.core.api.Status;
import tournamentmanager.core.api.TournamentException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameImplTest {
    Game game;

    @BeforeEach
    void initialisationGame(){
        game = new GameImpl();
    }
    void startGame() throws TournamentException {
        Participant p1 = new ParticipantImpl("Don Pedro");
        Participant p2 = new ParticipantImpl("Margoulin");
        game.addParticipant(p1);
        game.addParticipant(p2);
        game.start();
    }

    @Test
    void addParticipant() throws TournamentException {
        assertTrue(game.getParticipants().isEmpty());
        Participant p1 = new ParticipantImpl("Don Pedro");
        Participant p2 = new ParticipantImpl("Margoulin");
        game.addParticipant(p1);
        game.addParticipant(p2);
        List<Participant> li= game.getParticipants();
        assertTrue(li.size()==2);
        assertEquals("Don Pedro",li.get(1).getName());
        assertEquals("Margoulin",li.get(0).getName());
    }
    @Test
    void addMemeParticipant() throws TournamentException {
        Participant p1 = new ParticipantImpl("Don Pedro");
        assertTrue(game.getParticipants().isEmpty());
        game.addParticipant(p1);
        game.addParticipant(p1);
        assertFalse(game.getParticipants().size()==2);
    }
    @Test
    void addParticipantException() throws TournamentException {
        assertThrowsExactly(IllegalArgumentException.class,()->game.addParticipant(null));
        Participant p1 = new ParticipantImpl("Don Pedro");
        Participant p2 = new ParticipantImpl("Margoulin");
        Participant p3 = new ParticipantImpl("Gabibou");
        game.addParticipant(p1);
        game.addParticipant(p2);
        assertThrowsExactly(TournamentException.class,()->game.addParticipant(p3));
    }

    @Test
    void addPoints(){

    }
    @Test
    void addPointsNull() throws TournamentException {
        startGame();
        assertThrowsExactly(IllegalArgumentException.class,()->game.addPoints(null,100));
    }

    @Test
    void start() {

    }

    @Test
    void finish() {
    }

    @Test
    void getParticipants() throws TournamentException {
        Participant p1 = new ParticipantImpl("Don Pedro");
        Participant p2 = new ParticipantImpl("Margoulin");
        game.addParticipant(p1);
        game.addParticipant(p2);
        assertTrue(game.getParticipants().size()==2);
        assertEquals("Don Pedro",game.getParticipants().get(1).getName());
        assertEquals("Margoulin",game.getParticipants().get(0).getName());
    }

    @Test
    void getWinner() {
    }

    @Test
    void getLoser() {
    }

    @Test
    void getStatus() throws TournamentException {
        assertEquals(Status.NOTSTARTED,game.getStatus());
        Participant p1 = new ParticipantImpl("Don Pedro");
        Participant p2 = new ParticipantImpl("Margoulin");
        game.addParticipant(p1);
        game.addParticipant(p2);
        game.start();
        assertEquals(Status.INPROGRESS,game.getStatus());
        game.addPoints(p1,100);
        game.finish();
        assertEquals(Status.FINISHED,game.getStatus());
    }

    @Test
    void getFollowingGame() {
    }

    @Test
    void getPreviousGames() {
    }

    @Test
    void setFollowingGame() {
    }

    @Test
    void addPreviousGame() {
    }
}