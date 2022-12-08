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
    @AjoutMethodeFonctionnelle
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
    @AjoutMethodeFonctionnelle
    void addMemeParticipant() throws TournamentException {
        Participant p1 = new ParticipantImpl("Don Pedro");
        assertTrue(game.getParticipants().isEmpty());
        game.addParticipant(p1);
        assertThrowsExactly(TournamentException.class,()->game.addParticipant(p1));
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void addParticipantExceptionTroisiemeParticipant() throws TournamentException {
        Participant p3 = new ParticipantImpl("Gabibou");
        game.addParticipant(p1);
        game.addParticipant(p2);
        assertThrowsExactly(TournamentException.class,()->game.addParticipant(p3));
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void addParticipantExceptionNull(){
        assertThrowsExactly(IllegalArgumentException.class,()->game.addParticipant(null));
    }

    @Test
    @AjoutMethodeFonctionnelle
    void addPoints() throws TournamentException {
        startGame();
        game.addPoints(p1,100);
        assertEquals(100,game.GetPoint(p1));
        game.addPoints(p1,-50);
        assertEquals(50,game.GetPoint(p1));
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void addPointsNotStarted() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        assertThrowsExactly(TournamentException.class,()->game.addPoints(p1,100));
    }
    @Test
    @AjoutMethodeFonctionnelle
    void addPointsFinished() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        game.setStatus(Status.FINISHED);
        assertThrowsExactly(TournamentException.class,()->game.addPoints(p1,100));
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void addPointsNull() throws TournamentException {
        startGame();
        assertThrowsExactly(IllegalArgumentException.class,()->game.addPoints(null,100));
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void addPointsNonPresent() throws TournamentException {
        startGame();
        Participant p3 = new ParticipantImpl("IncrusteMan");
        assertThrowsExactly(IllegalArgumentException.class,()->game.addPoints(p3,100));
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void start() throws TournamentException {
        startGame();
        assertEquals(Status.INPROGRESS,game.getStatus());
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void startExceptionGameVide() throws TournamentException {
        assertThrowsExactly(TournamentException.class,()->game.start());
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void startExceptionINPROGRESS() throws TournamentException {
        startGame();
        assertThrowsExactly(TournamentException.class,()->game.start());
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void startExceptionFINISHED() throws TournamentException {
        game = (GameImpl) playGame(game,p1,p2);
        assertThrowsExactly(TournamentException.class,()->game.start());
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void finish() throws TournamentException {
        game = (GameImpl) playGame(game,p1, p2);
        assertEquals(Status.FINISHED,game.getStatus());
        assertTrue(p2.isEliminated());
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void finishExceptionNotStarted() throws TournamentException{
        assertThrowsExactly(TournamentException.class,()->game.finish());
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void finishExceptionEquality() throws TournamentException{

        startGame();
        assertThrowsExactly(TournamentException.class,()->game.finish());
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void finishExceptionFINISHED() throws TournamentException{
        game.addParticipant(p1);
        game.addParticipant(p2);
        game.start();
        game.setStatus(Status.FINISHED);
        assertThrowsExactly(TournamentException.class,()->game.finish());
    }

    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
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
    @AjoutMethodeFonctionnelle
    void getWinner() throws TournamentException {
        Participant gagnant = new ParticipantImpl("CMWAKéGAGNé");
        game = (GameImpl) playGame(game,gagnant,new ParticipantImpl("Looser"));
        assertEquals(gagnant,game.getWinner());
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void getWinnerExceptionINPROGRESS() throws TournamentException {
        startGame();
        assertThrowsExactly(TournamentException.class,()->game.getWinner());
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void getWinnerExceptionNOTSTARTED() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        assertThrowsExactly(TournamentException.class,()->game.getWinner());
    }

    @Test
    @AjoutMethodeFonctionnelle
    void getLoserJ1() throws TournamentException {
        Participant looser = new ParticipantImpl("Looser");
        game = (GameImpl) playGame(game,new ParticipantImpl("CMWAKéGAGNé"),looser);
        assertEquals(looser,game.getLoser());
    }


    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
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
    @AjoutMethodeFonctionnelle
    void getLoserExceptionINPROGRESS() throws TournamentException {
        startGame();
        assertThrowsExactly(TournamentException.class,()->game.getLoser());
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void getLoserExceptionNOTSTARTED() throws TournamentException {
        Participant p1 = new ParticipantImpl("Don Pedro");
        Participant p2 = new ParticipantImpl("Margoulin");
        game.addParticipant(p1);
        game.addParticipant(p2);
        assertThrowsExactly(TournamentException.class,()->game.getLoser());
    }

    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void getStatusNOtStarted() throws TournamentException {
        assertEquals(Status.NOTSTARTED,game.getStatus());
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void getStatusINPROGRESS() throws TournamentException {
        game.setStatus(Status.INPROGRESS);
        assertEquals(Status.INPROGRESS,game.getStatus());
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void getStatusFINISHED() throws TournamentException {
        game.setStatus(Status.FINISHED);
        assertEquals(Status.FINISHED,game.getStatus());
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void getFollowingGame() {
        Game g1 = new GameImpl();
        g1.setFollowingGame(game);
        assertEquals(Optional.of(game),g1.getFollowingGame());
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void getFollowingGameFinale() {

        assertEquals(Optional.empty(),game.getFollowingGame());
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
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
    @AjoutMethodeFonctionnelle
    void getPreviousGamesEmpty(){
        assertEquals(Collections.EMPTY_LIST,game.getPreviousGames());
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void setFollowingGame() {
        Game g1 = new GameImpl();
        game.setFollowingGame(g1);
        assertEquals(Optional.of(g1),game.getFollowingGame());
    }
    //Ajout par méthode fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void setFollowingGameException() {
        assertThrowsExactly(IllegalArgumentException.class,()->game.setFollowingGame(null));
    }
    //test Méthode Fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
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
    @AjoutMethodeFonctionnelle
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
    @AjoutMethodeFonctionnelle
    void addPreviousGameExceptionDejaDedans() throws TournamentException {
        Game g1 = new GameImpl();
        game.addPreviousGame(g1);
        assertThrowsExactly(TournamentException.class,()->game.addPreviousGame(g1));
    }
    //test Méthode Fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
    void addPreviousGameExceptionINPROGRESS() throws TournamentException {
        Game g1 = new GameImpl();
        startGame();
        assertThrowsExactly(TournamentException.class,()->game.addPreviousGame(g1));
    }
    //test Méthode Fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
    void addPreviousGameExceptionFINISHED() throws TournamentException {
        Game g1 = new GameImpl();
        game.setStatus(Status.FINISHED);
        assertThrowsExactly(TournamentException.class,()->game.addPreviousGame(g1));
    }
    //test Méthode Fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
    void addPreviousGameExceptionNull() throws TournamentException {
        assertThrowsExactly(IllegalArgumentException.class,()->game.addPreviousGame(null));
    }

    //Ajout méthode structurelle
    @Test
    @AjoutMethodeStructurelle
    void addParticipantNull() {
        assertThrowsExactly(IllegalArgumentException.class,()->game.addParticipant(null));
    }


    //Ajout méthode structurelle
    @Test
    @AjoutMethodeStructurelle
    void addParticipantTroisiemeParticipant() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        Participant p3 = new ParticipantImpl("TroisiemeMan");
        assertThrowsExactly(TournamentException.class,()->game.addParticipant(p3));
    }


    //Ajout méthode structurelle
    @Test
    @AjoutMethodeStructurelle
    void addParticipantMemePartcipant() throws TournamentException {
        game.addParticipant(p1);
        assertThrowsExactly(TournamentException.class,()->game.addParticipant(p1));
    }


    //Ajout méthode structurelle
    @Test
    @AjoutMethodeStructurelle
    void addParticipantStructurelle() throws TournamentException {
        game.addParticipant(p1);
        assertEquals(1,game.getParticipants().size());
    }



    //Ajout méthode structurelle
    @Test
    @AjoutMethodeStructurelle
    void getLoserNotFinished() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        game.start();
        game.addPoints(p1,50);
        assertThrowsExactly(TournamentException.class,()->game.getLoser());
    }


    //Ajout méthode structurelle
    @Test
    @AjoutMethodeStructurelle
    void getLoserP1() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        game.start();

        game.addPoints(p2,50);
        game.finish();
        assertNotNull(game.getLoser());
        assertEquals(p1,game.getLoser());
    }


    //Ajout méthode structurelle
    @Test
    @AjoutMethodeStructurelle
    void getLoserP2() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        game.start();

        game.addPoints(p1,50);
        game.finish();
        assertNotNull(game.getLoser());
        assertEquals(p2,game.getLoser());
    }

    @Test
    @AjoutMethodeEliminationMutant
    void getLoserP2TuerMutant() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        game.start();

        game.addPoints(p1,50);
        game.addPoints(p2,50);
        game.setStatus(Status.FINISHED);
        assertNull(game.getLoser());
    }

}
