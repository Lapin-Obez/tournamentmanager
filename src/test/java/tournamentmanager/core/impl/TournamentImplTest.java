package tournamentmanager.core.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tournamentmanager.core.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TournamentImplTest {

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

    void ajout2Participant() throws TournamentException {
        tournoi.addParticipant(new ParticipantImpl("Manuel"));
        tournoi.addParticipant(new ParticipantImpl("Macheté"));
    }
    //ajout méthode fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
    void addParticipant() throws TournamentException {
        Participant p = new ParticipantImpl("nom");
        tournoi.addParticipant(p);
        assertTrue(tournoi.getParticipants().contains(p));
    }

    //ajout méthode fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
    void addParticipantExceptionINPROGRESS() {
        tournoi.setStatus(Status.INPROGRESS);
        assertThrowsExactly(TournamentException.class,()->tournoi.addParticipant(new ParticipantImpl("nom")));
        }
    //ajout méthode fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
    void addParticipantExceptionFINISHED() {
       tournoi.setStatus(Status.FINISHED);
        assertThrowsExactly(TournamentException.class,()->tournoi.addParticipant(new ParticipantImpl("nom")));
    }
    //ajout méthode fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
    void start() throws TournamentException {
        for (int i =0;i<16;i++){
            tournoi.addParticipant(new ParticipantImpl(""+i));
        }
        tournoi.start();
        assertEquals(Status.INPROGRESS,tournoi.getStatus());
    }
    //ajout méthode fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
    void startException1Participant() throws TournamentException {
        tournoi.addParticipant(new ParticipantImpl("nom"));
        assertThrowsExactly(TournamentException.class,()->tournoi.start());
    }
    //ajout méthode fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
    void startExceptionPasPuissanceDeux() throws TournamentException {
        for (int i =0;i<12;i++){
            tournoi.addParticipant(new ParticipantImpl(""+i));
        }
        assertThrowsExactly(TournamentException.class,()->tournoi.start());

    }
    //ajout méthode fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
    void startExceptionInProgress() throws TournamentException {
        for (int i =0;i<16;i++){
            tournoi.addParticipant(new ParticipantImpl(""+i));
        }
        tournoi.setStatus(Status.INPROGRESS);
        assertThrowsExactly(TournamentException.class,()->tournoi.start());

    }
    //ajout méthode fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
    void startExceptionFinished() throws TournamentException {
        for (int i =0;i<16;i++){
            tournoi.addParticipant(new ParticipantImpl(""+i));
        }
        tournoi.setStatus(Status.FINISHED);
        assertThrowsExactly(TournamentException.class,()->tournoi.start());
    }
    //ajout methode Fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void end() throws TournamentException {
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
        assertEquals(Status.FINISHED,tournoi.getStatus());
    }
    //ajout methode Fonctionnelle
    @Test
    @AjoutMethodeFonctionnelle
    void endExceptionGameNOtFinished() throws TournamentException {
        tournoi.addParticipant(p1);
        tournoi.addParticipant(p2);
        tournoi.addParticipant(p3);
        tournoi.addParticipant(p4);
        this.tournoi.start();

        List<Game> matchs = tournoi.getGamesReadyToStart();
        List<Participant> conccurents;

        for (Game g:matchs) {
            conccurents = g.getParticipants();
            g.start();
            g.addPoints(conccurents.get(0),10);
            g.addPoints(conccurents.get(1),20);
            g.finish();
        }
        //Erreur detécté, il ne joue aucune game mais l'erreur ne provient pas de la méthode end
        assertThrowsExactly(TournamentException.class,()->tournoi.end());
    }
    //ajout méthode fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
    void endExceptionNotStart() {
        assertThrowsExactly(TournamentException.class,()->tournoi.end());
    }
    //ajout méthode fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
    void endExceptionGameFINISHED() throws TournamentException {
        tournoi.addParticipant(p1);
        tournoi.addParticipant(p2);
        tournoi.setStatus(Status.FINISHED);
        assertThrowsExactly(TournamentException.class,()->tournoi.end());
    }
    //ajout méthode fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
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


    //ajout méthode fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
    void getRounds() throws TournamentException {
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
        assertEquals(2,tournoi.getRounds().size());
        assertEquals(2,tournoi.getRounds().get(0).size());
        assertEquals(1,tournoi.getRounds().get(1).size());
    }


    //ajout méthode fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
    void getGamesReadyToStart() throws TournamentException {
        tournoi.addParticipant(p1);
        tournoi.addParticipant(p2);
        tournoi.addParticipant(p3);
        tournoi.addParticipant(p4);
        tournoi.start();

        assertEquals(2,tournoi.getGamesReadyToStart().size());
    }


    //ajout méthode fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
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


    //ajout méthode fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
    void getGamesInProgress() throws TournamentException {
        tournoi.addParticipant(p1);
        tournoi.addParticipant(p2);
        tournoi.addParticipant(p3);
        tournoi.addParticipant(p4);
        this.tournoi.start();

        List<Game> matchs = tournoi.getGamesReadyToStart();
        List<Participant> conccurents;

        for (Game g:matchs) {
            conccurents = g.getParticipants();
            g.start();
            g.addPoints(conccurents.get(0),10);
        }
        assertEquals(2,tournoi.getGamesInProgress().size());
    }


    //ajout méthode fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
    void getFutureGames() throws TournamentException {
        tournoi.addParticipant(p1);
        tournoi.addParticipant(p2);
        tournoi.addParticipant(p3);
        tournoi.addParticipant(p4);
        this.tournoi.start();

        List<Game> matchs = tournoi.getGamesReadyToStart();
        List<Participant> conccurents;

        for (Game g:matchs) {
            conccurents = g.getParticipants();
            g.start();
            g.addPoints(conccurents.get(0),10);
        }
        assertEquals(1,tournoi.getFutureGames().size());
    }


    //ajout méthode fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
    void computeFinalRanking() throws TournamentException {
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
        assertEquals(3,tournoi.computeFinalRanking().size());
        assertEquals(1,tournoi.computeFinalRanking().get(0).size());
        assertEquals(1,tournoi.computeFinalRanking().get(1).size());
        assertEquals(2,tournoi.computeFinalRanking().get(2).size());
    }

    //ajout méthode fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
    void computeFinalRankingExceptionNotEnded() throws TournamentException {
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
        assertThrowsExactly(TournamentException.class,()->tournoi.computeFinalRanking());
    }
    @Test
    @AjoutMethodeFonctionnelle
    void computeFinalRankingExceptionNotStarted() throws TournamentException {
        tournoi.addParticipant(p1);
        tournoi.addParticipant(p2);
        tournoi.addParticipant(p3);
        tournoi.addParticipant(p4);
        assertThrowsExactly(TournamentException.class,()->tournoi.computeFinalRanking());
    }


    //ajout méthode fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
    void getStatus() {
        assertEquals(Status.NOTSTARTED,tournoi.getStatus());

    }


    //ajout méthode fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
    void getStatusINPROGRESS() {
        tournoi.setStatus(Status.INPROGRESS);
        assertEquals(Status.INPROGRESS,tournoi.getStatus());
    }


    //ajout méthode fonctionelle
    @Test
    @AjoutMethodeFonctionnelle
    void getStatusFINISHED() {
        tournoi.setStatus(Status.FINISHED);
        assertEquals(Status.FINISHED,tournoi.getStatus());
    }

    //ajout méthode structurelle
    @Test
    @AjoutMethodeStructurelle
    void addParticipantStructurelle() throws TournamentException {
        Participant p = new ParticipantImpl("nom");
        tournoi.addParticipant(p);
        assertTrue(tournoi.getParticipants().contains(p));
    }
    //ajout méthode structurelle
    @Test
    @AjoutMethodeStructurelle
    void addParticipantDéjàLà() throws TournamentException {
        Participant p = new ParticipantImpl("nom");
        tournoi.addParticipant(p);
        tournoi.addParticipant(p);
        assertTrue(tournoi.getParticipants().contains(p));
        assertEquals(1,tournoi.getParticipants().size());
    }
    //ajout méthode structurelle
    @Test
    @AjoutMethodeStructurelle
    void addParticipantExceptionNotNotStarted() {
        tournoi.setStatus(Status.INPROGRESS);
        assertThrowsExactly(TournamentException.class,()->tournoi.addParticipant(new ParticipantImpl("nom")));
    }

    //ajout méthode structurelle
    @Test
    @AjoutMethodeStructurelle
    void getAllGamesStructurelle() throws TournamentException {
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
    @AjoutMethodeStructurelle
    void getGamesReadyToStartStructurelle() throws TournamentException {
        tournoi.addParticipant(p1);
        tournoi.addParticipant(p2);
        tournoi.addParticipant(p3);
        tournoi.addParticipant(p4);
        tournoi.start();

        assertEquals(2,tournoi.getGamesReadyToStart().size());
    }
    //ajout méthode structurelle
    @Test
    @AjoutMethodeStructurelle
    void getGamesReadyToStartGameAlreadyStarted() throws TournamentException {
        tournoi.addParticipant(p1);
        tournoi.addParticipant(p2);
        tournoi.addParticipant(p3);
        tournoi.addParticipant(p4);
        tournoi.start();
        tournoi.getGamesReadyToStart().get(1).start();
        assertEquals(1,tournoi.getGamesReadyToStart().size());
    }
    //Ajout méthode structurelle
    @Test
    @AjoutMethodeStructurelle
    void getFinishedGamesStructurelle() throws TournamentException {
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
    //Ajout pour tuer les mutants
    @Test
    @AjoutMethodeEliminationMutant
    void startTuerMutant() throws TournamentException {
        tournoi.addParticipant(p1);
        tournoi.addParticipant(p2);
        tournoi.start();
        assertEquals(Status.INPROGRESS,tournoi.getStatus());
    }
}