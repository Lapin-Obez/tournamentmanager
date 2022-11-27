package tournamentmanager.core.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tournamentmanager.core.api.Game;
import tournamentmanager.core.api.Participant;
import tournamentmanager.core.api.Status;
import tournamentmanager.core.api.TournamentException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GameImplTest {
    GameImpl game;
    Participant p1;
    Participant p2;
    @BeforeEach
    void initialisationGame(){
        game = new GameImpl();
        p1 = new ParticipantImpl("Don Pedro");
        p2 = new ParticipantImpl("Margoulin");
    }
    void startGame() throws TournamentException {

        game.addParticipant(p1);
        game.addParticipant(p2);
        game.start();
    }
    Game playGame(Game g,Participant par1, Participant par2) throws TournamentException {
        g.addParticipant(par1);
        g.addParticipant(par2);
        g.start();
        g.addPoints(par1,100);
        g.finish();
        return g;
    }

    //Ajout par méthode fonctionnelle
    @Test
    void addParticipant() throws TournamentException {
        assertTrue(game.getParticipants().isEmpty());
        p1 = new ParticipantImpl("Don Pedro");
        p2 = new ParticipantImpl("Margoulin");
        game.addParticipant(p1);
        game.addParticipant(p2);
        List<Participant> li= game.getParticipants();
        assertEquals(2, li.size());
        assertTrue(game.getParticipants().contains(p1));
        assertTrue(game.getParticipants().contains(p2));
    }
    //Test ajouté pour le plaisir
    @Test
    void addMemeParticipant() throws TournamentException {
        Participant p1 = new ParticipantImpl("Don Pedro");
        assertTrue(game.getParticipants().isEmpty());
        game.addParticipant(p1);
        assertThrowsExactly(TournamentException.class,()->game.addParticipant(p1));
    }
    //Ajout par méthode fonctionnelle
    @Test
    void addParticipantExceptionTroisiemeParticipant() throws TournamentException {
        Participant p3 = new ParticipantImpl("Gabibou");
        game.addParticipant(p1);
        game.addParticipant(p2);
        assertThrowsExactly(TournamentException.class,()->game.addParticipant(p3));
    }
    //Ajout par méthode fonctionnelle
    @Test
    void addParticipantExceptionNull(){
        assertThrowsExactly(IllegalArgumentException.class,()->game.addParticipant(null));
    }

    @Test
    void addPoints() throws TournamentException {
        startGame();
        game.addPoints(p1,100);
        assertEquals(100,game.GetPoint(p1));
        game.addPoints(p1,-50);
        assertEquals(50,game.GetPoint(p1));
    }
    //Ajout par méthode fonctionnelle
    @Test
    void addPointsNotStarted() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        assertThrowsExactly(TournamentException.class,()->game.addPoints(p1,100));
    }
    @Test
    void addPointsFinished() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        game.setStatus(Status.FINISHED);
        assertThrowsExactly(TournamentException.class,()->game.addPoints(p1,100));
    }
    //Ajout par méthode fonctionnelle
    @Test
    void addPointsNull() throws TournamentException {
        startGame();
        assertThrowsExactly(IllegalArgumentException.class,()->game.addPoints(null,100));
    }
    //Ajout par méthode fonctionnelle
    @Test
    void addPointsNonPresent() throws TournamentException {
        startGame();
        Participant p3 = new ParticipantImpl("IncrusteMan");
        assertThrowsExactly(IllegalArgumentException.class,()->game.addPoints(p3,100));
    }
    //Ajout par méthode fonctionnelle
    @Test
    void start() throws TournamentException {
        startGame();
        assertEquals(Status.INPROGRESS,game.getStatus());
    }
    //Ajout par méthode fonctionnelle
    @Test
    void startExceptionGameVide() throws TournamentException {
        assertThrowsExactly(TournamentException.class,()->game.start());
    }
    //Ajout par méthode fonctionnelle
    @Test
    void startExceptionINPROGRESS() throws TournamentException {
        startGame();
        assertThrowsExactly(TournamentException.class,()->game.start());
    }
    //Ajout par méthode fonctionnelle
    @Test
    void startExceptionFINISHED() throws TournamentException {
        game = (GameImpl) playGame(game,p1,p2);
        assertThrowsExactly(TournamentException.class,()->game.start());
    }
    //Ajout par méthode fonctionnelle
    @Test
    void finish() throws TournamentException {
        game = (GameImpl) playGame(game,p1, p2);
        assertEquals(Status.FINISHED,game.getStatus());
        assertTrue(p2.isEliminated());
    }
    //Ajout par méthode fonctionnelle
    @Test
    void finishExceptionNotStarted() throws TournamentException{
        assertThrowsExactly(TournamentException.class,()->game.finish());
    }
    //Ajout par méthode fonctionnelle
    @Test
    void finishExceptionEquality() throws TournamentException{

        startGame();
        assertThrowsExactly(TournamentException.class,()->game.finish());
    }
    //Ajout par méthode fonctionnelle
    @Test
    void finishExceptionFINISHED() throws TournamentException{
        game.addParticipant(p1);
        game.addParticipant(p2);
        game.start();
        game.setStatus(Status.FINISHED);
        assertThrowsExactly(TournamentException.class,()->game.finish());
    }

    //Ajout par méthode fonctionnelle
    @Test
    void getParticipants() throws TournamentException {
        p1 = new ParticipantImpl("Don Pedro");
        p2 = new ParticipantImpl("Margoulin");
        game.addParticipant(p1);
        game.addParticipant(p2);
        assertEquals(2, game.getParticipants().size());
        assertTrue(game.getParticipants().contains(p1));
        assertTrue(game.getParticipants().contains(p2));
    }
    //Ajout par méthode fonctionnelle
    @Test
    void getWinner() throws TournamentException {
        Participant gagnant = new ParticipantImpl("CMWAKéGAGNé");
        game = (GameImpl) playGame(game,gagnant,new ParticipantImpl("Looser"));
        assertEquals(gagnant,game.getWinner());
    }
    //Ajout par méthode fonctionnelle
    @Test
    void getWinnerExceptionINPROGRESS() throws TournamentException {
        startGame();
        assertThrowsExactly(TournamentException.class,()->game.getWinner());
    }
    //Ajout par méthode fonctionnelle
    @Test
    void getWinnerExceptionNOTSTARTED() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        assertThrowsExactly(TournamentException.class,()->game.getWinner());
    }

    @Test
    void getLoserJ1() throws TournamentException {
        Participant looser = new ParticipantImpl("Looser");
        game = (GameImpl) playGame(game,new ParticipantImpl("CMWAKéGAGNé"),looser);
        assertEquals(looser,game.getLoser());
    }


    //Ajout par méthode fonctionnelle
    @Test
    void getLoserJ2() throws TournamentException {
        Participant p = new ParticipantImpl("un autre");
        Participant p2 = new ParticipantImpl("CMWAKéGAGNé");
        game.addParticipant(p);
        game.addParticipant(p2);
        game.start();
        game.addPoints(p2,100);
        game.finish();
        assertEquals(p,game.getLoser());
    }
    //Ajout par méthode fonctionnelle
    @Test
    void getLoserExceptionINPROGRESS() throws TournamentException {
        startGame();
        assertThrowsExactly(TournamentException.class,()->game.getLoser());
    }
    //Ajout par méthode fonctionnelle
    @Test
    void getLoserExceptionNOTSTARTED() throws TournamentException {
        Participant p1 = new ParticipantImpl("Don Pedro");
        Participant p2 = new ParticipantImpl("Margoulin");
        game.addParticipant(p1);
        game.addParticipant(p2);
        assertThrowsExactly(TournamentException.class,()->game.getLoser());
    }

    //Ajout par méthode fonctionnelle
    @Test
    void getStatusNOtStarted() throws TournamentException {
        assertEquals(Status.NOTSTARTED,game.getStatus());
    }
    //Ajout par méthode fonctionnelle
    @Test
    void getStatusINPROGRESS() throws TournamentException {
        game.setStatus(Status.INPROGRESS);
        assertEquals(Status.INPROGRESS,game.getStatus());
    }
    //Ajout par méthode fonctionnelle
    @Test
    void getStatusFINISHED() throws TournamentException {
        game.setStatus(Status.FINISHED);
        assertEquals(Status.FINISHED,game.getStatus());
    }
    //Ajout par méthode fonctionnelle
    @Test
    void getFollowingGame() {
        Game g1 = new GameImpl();
        g1.setFollowingGame(game);
        assertEquals(Optional.of(game),g1.getFollowingGame());
    }
    //Ajout par méthode fonctionnelle
    @Test
    void getFollowingGameFinale() {

        assertEquals(Optional.empty(),game.getFollowingGame());
    }
    //Ajout par méthode fonctionnelle
    @Test
    void getPreviousGames() throws TournamentException {
        Game g1 = new GameImpl();
        Game g2 = new GameImpl();
        game.addPreviousGame(g1);
        game.addPreviousGame(g2);
        assertEquals(2,game.getPreviousGames().size());
        assertTrue(game.getPreviousGames().contains(g1));
        assertTrue(game.getPreviousGames().contains(g2));
    }
    //Ajout par méthode fonctionnelle
    @Test
    void getPreviousGamesEmpty(){
        assertEquals(Collections.EMPTY_LIST,game.getPreviousGames());
    }
    //Ajout par méthode fonctionnelle
    @Test
    void setFollowingGame() {
        Game g1 = new GameImpl();
        game.setFollowingGame(g1);
        assertEquals(Optional.of(g1),game.getFollowingGame());
    }
    //Ajout par méthode fonctionnelle
    @Test
    void setFollowingGameException() {
        assertThrowsExactly(IllegalArgumentException.class,()->game.setFollowingGame(null));
    }
    //test Méthode Fonctionelle
    @Test
    void addPreviousGame() throws TournamentException {
        Game g1 = new GameImpl();
        Game g2 = new GameImpl();
        g1.setFollowingGame(game);
        g2.setFollowingGame(game);
        Participant gagnant1 = new ParticipantImpl("Valentin");
        Participant gagnant2 = new ParticipantImpl("Valentin");
        g1 = playGame(g1,gagnant1,new ParticipantImpl("Alan"));
        g2 = playGame(g2,gagnant2,new ParticipantImpl("Don Pedro"));
        game.addPreviousGame(g1);
        game.addPreviousGame(g2);
        assertEquals(2,game.getPreviousGames().size());
        assertTrue(game.getParticipants().contains(gagnant1));
        assertTrue(game.getParticipants().contains(gagnant2));
    }
    //test Méthode Fonctionelle
    @Test
    void addPreviousGameExceptionTropDelem() throws TournamentException {
        Game g1 = new GameImpl();
        Game g2 = new GameImpl();
        Game g3 = new GameImpl();
        game.addPreviousGame(g1);
        game.addPreviousGame(g2);
        assertThrowsExactly(TournamentException.class,()->game.addPreviousGame(g3));
    }
    //test Méthode Fonctionelle
    @Test
    void addPreviousGameExceptionDejaDedans() throws TournamentException {
        Game g1 = new GameImpl();
        game.addPreviousGame(g1);
        assertThrowsExactly(TournamentException.class,()->game.addPreviousGame(g1));
    }
    //test Méthode Fonctionelle
    @Test
    void addPreviousGameExceptionINPROGRESS() throws TournamentException {
        Game g1 = new GameImpl();
        startGame();
        assertThrowsExactly(TournamentException.class,()->game.addPreviousGame(g1));
    }
    //test Méthode Fonctionelle
    @Test
    void addPreviousGameExceptionFINISHED() throws TournamentException {
        Game g1 = new GameImpl();
        game.setStatus(Status.FINISHED);
        assertThrowsExactly(TournamentException.class,()->game.addPreviousGame(g1));
    }
    //test Méthode Fonctionelle
    @Test
    void addPreviousGameExceptionNull() throws TournamentException {
        assertThrowsExactly(IllegalArgumentException.class,()->game.addPreviousGame(null));
    }
}
