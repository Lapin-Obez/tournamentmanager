package tournamentmanager.core.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tournamentmanager.core.api.Game;
import tournamentmanager.core.api.Participant;
import tournamentmanager.core.api.TournamentTreeBuilder;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TournamentTreeBuilderImplTest {
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
    //Ajout par méthode fonctionnelle
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

    //Ajout par méthode fonctionnelle
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
    //Ajout par méthode fonctionnelle
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
}