package tournamentmanager.core.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tournamentmanager.core.api.Game;
import tournamentmanager.core.api.Participant;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TournamentTreeBuilderImplTestStructurelle {
    TournamentTreeBuilderImpl treeBuilder;
    Participant p1;
    Participant p2;
    Participant p3;
    Participant p4;
    @BeforeEach
    void init(){
        treeBuilder = new TournamentTreeBuilderImpl();
        p1 = new ParticipantImpl("Arthur");
        p2 = new ParticipantImpl("John");
        p3 = new ParticipantImpl("Sadie");
        p4 = new ParticipantImpl("Dutch");
    }

    //Ajout méthode structurelle
    @Test
    void buildAllRounds() {
        List<Participant> li =new LinkedList<>();
        li.add(p1);
        li.add(p2);
        li.add(p3);
        li.add(p4);
        List<List<Game>> resultat = treeBuilder.buildAllRounds(li);
        assertEquals(2,resultat.size());
    }

    //Ajout méthode structurelle
    @Test
    void buildInitialRoundListeImpair() {
        //Ce cas n'est pas censé être possible à obtenir
        List li = new LinkedList<>();
        li.add(p1);
        li.add(p2);
        li.add(p3);
        assertThrowsExactly(RuntimeException.class,()->treeBuilder.buildInitialRound(li));
    }
    //Ajout méthode structurelle
    @Test
    void buildInitialRound() {
        List<Participant> li =new LinkedList<>();
        li.add(p1);
        li.add(p2);
        li.add(p3);
        li.add(p4);
        List<Game> resultat = treeBuilder.buildInitialRound(li);
        assertEquals(2,resultat.size());
    }

    //Ajout méthode structurelle
    @Test
    void buildNextRound() {
        List<Participant> li =new LinkedList<>();
        li.add(p1);
        li.add(p2);
        li.add(p3);
        li.add(p4);
        List<Game> premierRound = treeBuilder.buildInitialRound(li);
        List<Game> resultat = treeBuilder.buildNextRound(premierRound);
        assertEquals(1,resultat.size());
    }
    /*
    //Ajout méthode structurelle
    //Tentative de lancement d'un erreur mais ne créer pas la situation souhaité
    @Test
    void buildNextRoundImpair() {
        List<Participant> li =new LinkedList<>();
        li.add(p1);
        li.add(p2);
        li.add(p3);
        li.add(p4);
        li.add(new ParticipantImpl("participant5"));
        li.add(new ParticipantImpl("participant6"));
        List<Game> premierRound = treeBuilder.buildInitialRound(li);
        assertThrowsExactly(RuntimeException.class,()->treeBuilder.buildNextRound(premierRound));
    }*/
}