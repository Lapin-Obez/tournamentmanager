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
    void addPointsNull() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        game.start();
        assertThrowsExactly(IllegalArgumentException.class,()->game.addPoints(null,10));
    }


    //Ajout méthode structurelle
    @Test
    void addPointsNOtINPROGRESS() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        assertThrowsExactly(TournamentException.class,()->game.addPoints(p2,10));
    }


    //Ajout méthode structurelle
    @Test
    void addPoints() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        game.start();
        game.addPoints(p2,10);
        assertEquals(10,game.GetPoint(p2));
    }


    //Ajout méthode structurelle
    @Test
    void addPointsParticipantNotInGam() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        game.start();
        Participant p3 = new ParticipantImpl("Paslà");
        assertThrowsExactly(IllegalArgumentException.class,()->game.addPoints(p3,10));
    }


    //Ajout méthode structurelle
    @Test
    void startPasAssezDeParticipant() throws TournamentException{
        game.addParticipant(p1);
        assertThrowsExactly(TournamentException.class,()->game.start());
    }


    //Ajout méthode structurelle
    @Test
    void startGameDejaLancé() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        game.setStatus(Status.INPROGRESS);
        assertThrowsExactly(TournamentException.class,()->game.start());
    }


    //Ajout méthode structurelle
    @Test
    void start() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        game.start();
        assertEquals(Status.INPROGRESS,game.getStatus());
    }


    //Ajout méthode structurelle
    @Test
    void finishNotINPROGRESS() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        assertThrowsExactly(TournamentException.class,()->game.finish());
    }


    //Ajout méthode structurelle
    @Test
    void finishEquality() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);

        game.start();

        game.addPoints(p1,50);
        game.addPoints(p2,50);
        assertThrowsExactly(TournamentException.class,()->game.finish());
    }

    //Ajout méthode structurelle
    @Test
    void finishSetFollowingGame() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);

        Game suivante = new GameImpl();
        game.setFollowingGame(suivante);
        game.start();

        game.addPoints(p1,50);
        game.addPoints(p2,750);

        game.finish();

        assertEquals(Status.FINISHED,game.getStatus());
        assertTrue(suivante.getParticipants().contains(p2));
        assertTrue(p1.isEliminated());
    }

    //Ajout méthode structurelle
    @Test
    void finish() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);

        game.start();

        game.addPoints(p1,50);
        game.addPoints(p2,750);

        game.finish();

        assertEquals(Status.FINISHED,game.getStatus());
        assertTrue(p1.isEliminated());
    }


    //Ajout méthode structurelle
    @Test
    void getParticipants() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);

        List li = game.getParticipants();

        assertTrue(li.contains(p1));
        assertTrue(li.contains(p2));
        assertEquals(2,li.size());
    }


    //Ajout méthode structurelle
    @Test
    void getWinnerNOtFinished() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        game.start();
        game.addPoints(p1,50);
        assertThrowsExactly(TournamentException.class,()->game.getWinner());
    }


    //Ajout méthode structurelle
    @Test
    void getWinnerP1() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        game.start();

        game.addPoints(p1,50);
        game.finish();

        assertEquals(p1,game.getWinner());
    }


    //Ajout méthode structurelle
    @Test
    void getWinnerP2() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        game.start();

        game.addPoints(p2,50);
        game.finish();

        assertEquals(p2,game.getWinner());
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


    //Ajout méthode structurelle
    @Test
    void getStatus() {
        assertEquals(Status.NOTSTARTED,game.getStatus());
    }


    //Ajout méthode structurelle
    @Test
    void getFollowingGameNull() {
        assertEquals(Optional.empty(),game.getFollowingGame());
    }


    //Ajout méthode structurelle
    @Test
    void getFollowingGame() {
        Game suivante = new GameImpl();
        game.setFollowingGame(suivante);
        assertEquals(Optional.of(suivante),game.getFollowingGame());
    }


    //Ajout méthode structurelle
    @Test
    void getPreviousGames() throws TournamentException {
        Game precedente1 = new GameImpl();
        Game precedente2 = new GameImpl();

        game.addPreviousGame(precedente1);
        game.addPreviousGame(precedente2);

        List li = game.getPreviousGames();

        assertTrue(li.contains(precedente1));
        assertTrue(li.contains(precedente2));
    }


    //Ajout methode structurelle
    @Test
    void setFollowingGameNull() {
        assertThrowsExactly(IllegalArgumentException.class,()->game.setFollowingGame(null));
    }


    //Ajout methode structurelle
    @Test
    void setFollowingGame() {
        Game suivante = new GameImpl();
        game.setFollowingGame(suivante);
        assertEquals(Optional.of(suivante),game.getFollowingGame());
    }


    //Ajout methode structurelle
    @Test
    void addPreviousGameStarted() throws TournamentException {
        game.addParticipant(p1);
        game.addParticipant(p2);
        game.start();
        assertThrowsExactly(TournamentException.class,()->game.addPreviousGame(new GameImpl()));
    }

    //Ajout methode structurelle
    @Test
    void addPreviousGameNull() {
        assertThrowsExactly(IllegalArgumentException.class,()->game.addPreviousGame(null));
    }


    //Ajout methode structurelle
    @Test
    void addPreviousGameMemeGame() throws TournamentException {
        Game precedente = new GameImpl();
        game.addPreviousGame(precedente);
        assertThrowsExactly(TournamentException.class,()->game.addPreviousGame(precedente));
    }


    //Ajout methode structurelle
    @Test
    void addPreviousGameTroisièmeGame() throws TournamentException {
        Game precedente1 = new GameImpl();
        Game precedente2 = new GameImpl();
        Game precedente3 = new GameImpl();
        game.addPreviousGame(precedente1);
        game.addPreviousGame(precedente2);
        assertThrowsExactly(TournamentException.class,()->game.addPreviousGame(precedente3));
    }


    //Ajout methode structurelle
    @Test
    void addPreviousGame() throws TournamentException {
        Game precedente1 = new GameImpl();
        Game precedente2 = new GameImpl();


        game.addPreviousGame(precedente1);
        game.addPreviousGame(precedente2);

        assertTrue(game.getPreviousGames().contains(precedente1));
        assertTrue(game.getPreviousGames().contains(precedente2));
    }
}