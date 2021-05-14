package tournamentmanager.core.impl;

import tournamentmanager.core.api.Game;
import tournamentmanager.core.api.Participant;
import tournamentmanager.core.api.Status;
import tournamentmanager.core.api.TournamentException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameImpl implements Game {

    private Map<Participant, Integer> participants = new HashMap<>();
    private Status status = Status.NOTSTARTED;
    private Game followingGame;
    private Game previousGame1;
    private Game previousGame2;

    @Override
    public void addParticipant(Participant participant) throws TournamentException {
        if (participant == null) {
            throw new IllegalArgumentException("The participant cannot be null.");
        }
        if (this.participants.size() >= 2) {
            throw new TournamentException("Cannot add participant to game, game already has two participants.");
        }
        this.participants.put(participant, 0);
    }

    @Override
    public void addPoints(Participant participant, int points) throws TournamentException {
        if (participant == null) {
            throw new IllegalArgumentException("The participant cannot be null.");
        }
        if (this.status != Status.INPROGRESS) {
            throw new TournamentException("Cannot change status of a game that is not in progress.");
        }
        if (participants.containsKey(participant)) {
            participants.put(participant, participants.get(participant) + points);
        } else {
            throw new TournamentException("Cannot add points to a participants that is not part of this game.");
        }
    }


    @Override
    public void start() throws TournamentException {
        if (this.status != Status.NOTSTARTED) {
            throw new TournamentException("Cannot start a game that has already started.");
        } else if (this.participants.size() < 2) {
            throw new TournamentException("Cannot start a game that does not have two participants.");
        }
        this.status = Status.INPROGRESS;
    }

    @Override
    public void finish() throws TournamentException {
        this.finish(false);
    }

    @Override
    public void finish(boolean force) throws TournamentException {

        if (!force && this.status != Status.INPROGRESS) {
            throw new TournamentException("Cannot finish a game that has not started.");
        }

        if (this.participants.size() > 1) {
            List<Integer> scores = List.copyOf(this.participants.values());
            if (scores.get(0) == scores.get(1)) {
                throw new TournamentException("Cannot set the game to 'finished', the scores are ex-aequo. A winner is required.");
            }
        }

        this.status = Status.FINISHED;

        if (this.getWinner().isPresent() && this.followingGame != null) {
            this.followingGame.addParticipant(this.getWinner().get());
        }
        if (this.getLoser().isPresent()) {
            this.getLoser().get().setEliminated(true);
        }

    }


    @Override
    public void registerForfeit(Participant participant) throws TournamentException {
        if (participant == null) {
            throw new IllegalArgumentException("A participant cannot be null.");
        }
        if (this.participants.containsKey(participant)) {
            this.participants.put(participant, -1);
        } else {
            throw new TournamentException("Cannot register forfeit for a participant that is not part of this game.");
        }
        this.status = Status.FINISHED;
    }

    @Override
    public List<Participant> getParticipants() {
        return List.copyOf(this.participants.keySet());
    }


    @Override
    public Optional<Participant> getWinner() throws TournamentException {
        if (this.status != Status.FINISHED) {
            throw new TournamentException("Cannot retrieve winner, the game is not finished.");
        }
        List<Participant> plist = List.copyOf(this.participants.keySet());
        Participant p1 = plist.get(0);
        Participant p2 = plist.get(1);
        if (this.participants.get(p1) > this.participants.get(p2)) {
            return Optional.of(p1);
        } else if (this.participants.get(p2) > this.participants.get(p1)) {
            return Optional.of(p2);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Participant> getLoser() throws TournamentException {
        if (this.status != Status.FINISHED) {
            throw new TournamentException("Cannot retrieve loser, the game is not finished.");
        }
        List<Participant> plist = List.copyOf(this.participants.keySet());
        Participant p1 = plist.get(0);
        Participant p2 = plist.get(1);
        if (this.participants.get(p1) < this.participants.get(p2)) {
            return Optional.of(p1);
        } else if (this.participants.get(p2) < this.participants.get(p1)) {
            return Optional.of(p2);
        } else {
            return Optional.empty();
        }

    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public Optional<Game> getFollowingGame() {
        return Optional.ofNullable(this.followingGame);
    }

    @Override
    public Optional<Game> getPreviousGame1() {
        return Optional.ofNullable(this.previousGame1);
    }

    @Override
    public Optional<Game> getPreviousGame2() {
        return Optional.ofNullable(this.previousGame2);
    }

    @Override
    public void setFollowingGame(Game game) {
        if (game == null) {
            throw new IllegalArgumentException("A game cannot be null.");
        }
        this.followingGame = game;
    }

    @Override
    public void setPreviousGame1(Game game) {
        if (game == null) {
            throw new IllegalArgumentException("A game cannot be null.");
        }
        this.previousGame1 = game;
    }

    @Override
    public void setPreviousGame2(Game game) {
        if (game == null) {
            throw new IllegalArgumentException("A game cannot be null.");
        }
        this.previousGame2 = game;
    }


}
