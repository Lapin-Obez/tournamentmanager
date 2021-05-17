package tournamentmanager.core.api;

import java.util.List;
import java.util.Set;


/**
 * Set of services to build the Tournament tree from a list of participants.
 * <p>
 * A Tournament tree is composed of a list of rounds, each round composed of a list of nodes.
 * <p>
 * Except for the final GameNode, each node is connected to a node of the next round. When a participant wins in a node, he or she goes to the following node in the next round.
 */
public interface TournamentTreeBuilder {

    /**
     * Build a complete Tournament tree for all rounds of the Tournament, using a list of participants.
     * <p>
     * A Tournament tree is simply a list of rounds. Since a round is a list of
     * GameNodes, a Tournament tree is a list of lists of GameNodes.
     * <p>
     * See buildInitialRound() regarding how the first round is built, and
     * buildNextRound() regarding how all other round is built.
     *
     * @param participants The list with all participants.
     * @return The complete tournament tree.
     * @throws TournamentException If the amount of players is so high that the resulting tournament size is
     *                             higher than Integer.MAX_VALUE.
     */
    List<List<GameNode>> buildAllRounds(List<Participant> participants) throws TournamentException;

    /**
     * Build the first round of a tournament tree. This first round is special because it is initially filled with all participants.
     *
     * @param participants The list with all participants.
     * @return The initial round of the tournament tree.
     * @throws TournamentException If the amount of players is so high that the resulting
     *                             tournament size is higher than Integer.MAX_VALUE.
     */
    List<GameNode> buildInitialRound(List<Participant> participants) throws TournamentException;

    /**
     * Given an already created round, build the next round of a tournament tree.
     * <p>
     * Each node of this next round is connected to two nodes from the previous round.
     *
     * @param previousRound The round that precedes this newly created round.
     * @return The round that follows the given previous round.
     */
    List<GameNode> buildNextRound(List<? extends GameNode> previousRound);


}
