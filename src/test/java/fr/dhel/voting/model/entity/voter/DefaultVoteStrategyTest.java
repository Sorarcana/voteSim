package fr.dhel.voting.model.entity.voter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import fr.dhel.voting.model.entity.candidate.Candidate;
import fr.dhel.voting.model.entity.politicalpos.PoliticalPosition;

@RunWith(MockitoJUnitRunner.class)
public class DefaultVoteStrategyTest {

    private DefaultVoteStrategy voteStrategy = new DefaultVoteStrategy();

    @Mock
    private Candidate jasonBourne;
    @Mock
    private Candidate kreutz;
    @Mock
    private PoliticalPosition politicalPosition;

    private Set<Candidate> getCandidates() {
        Set<Candidate> candidates = new HashSet<>();
        candidates.add(kreutz);
        candidates.add(jasonBourne);
        return candidates;
    }

    @Before
    public void setup() {
        when(politicalPosition.utility(jasonBourne)).thenReturn(BigDecimal.valueOf(0.475));
        when(politicalPosition.utility(kreutz)).thenReturn(new BigDecimal("0.001"));
    }

    @Test
    public void getBestCandidate_ShouldReturnTheCandidateWithHighestUtility() {
        var voter = new SimpleVoter(politicalPosition, false, voteStrategy);

        assertThat(voteStrategy.getBestCandidate(voter, getCandidates())).isEqualTo(jasonBourne);
    }
}
