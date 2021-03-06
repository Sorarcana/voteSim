package fr.dhel.voting.model.system;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import fr.dhel.voting.model.entity.candidate.Candidate;
import fr.dhel.voting.model.system.ballot.Ballot;

@RunWith(MockitoJUnitRunner.class)
public class ApprovalSystemTest {

    @Mock
    private Ballot ballotForMartyAndDoc;
    @Mock
    private Ballot ballotForMarty;
    @Mock
    private Ballot ballotForBiff;
    @Mock
    private Ballot ballotForNoone;
    @Mock
    private Candidate marty;
    @Mock
    private Candidate doc;
    @Mock
    private Candidate biff;

    @Test
    public void shortName_ShouldReturnTheName() {
        ApprovalSystem as = new ApprovalSystem();

        assertThat(as.shortName()).isEqualTo("AS");
    }

    Set<Candidate> getCandidates() {
        Set<Candidate> candidates = new HashSet<>();
        candidates.add(marty);
        candidates.add(doc);
        candidates.add(biff);
        return candidates;
    }

    @Test
    public void createBallot_ShouldReturnABallotBuilder() {
        assertThat(new ApprovalSystem().createBallot(getCandidates())).isNotNull();
    }

    @Test
    public void countVotes_ShouldElectTheMostApprovedCandidate() {
        ApprovalSystem as = new ApprovalSystem();

        var candidates = getCandidates();

        when(ballotForMartyAndDoc.computeResults())
                .thenReturn(Arrays.asList(Pair.of(marty, 1.0), Pair.of(doc, 1.0)));
        when(ballotForMarty.computeResults()).thenReturn(Arrays.asList(Pair.of(marty, 1.0)));
        when(ballotForBiff.computeResults()).thenReturn(Arrays.asList(Pair.of(biff, 1.0)));
        when(ballotForNoone.computeResults()).thenReturn(Collections.emptyList());

        List<Ballot> votes = Arrays.asList(ballotForMartyAndDoc, ballotForMarty, ballotForBiff,
                ballotForNoone);

        assertThat(as.countVotes(votes, candidates).getElectedCandidate()).isEqualTo(marty);
    }
}
