package fr.dhel.voting.model.entity.politicalpos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.byLessThan;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import fr.dhel.voting.model.entity.candidate.Candidate;

@RunWith(MockitoJUnitRunner.class)
public class IssuesBasedPoliticalPositionTest {

    private static final BigDecimal DELTA = new BigDecimal("0.001");

    @Mock
    private Candidate candidate1;
    @Mock
    private Candidate candidate2;
    @Mock
    private Candidate candidate3;
    @Mock
    private Candidate candidate4;

    @Test
    public void utility_ShouldReturnTheUtilityOfTheCandidate() {
        Issues issues = new Issues(Arrays.asList(1.0, 2.0, 0.5));
        IssuesBasedPoliticalPosition a = new IssuesBasedPoliticalPosition(issues);

        IssuesBasedPoliticalPosition b = new IssuesBasedPoliticalPosition(issues);

        when(candidate1.getPoliticalPosition()).thenReturn(b);

        assertThat(a.utility(candidate1)).isEqualTo(new BigDecimal("1.0"));
    }

    @Test
    public void utility_ShouldReturnTheUtilityOfTheCandidateWithMoreComplexValue() {
        Issues issues = new Issues(Arrays.asList(2.1906609296157953, 2.826840759936609));
        IssuesBasedPoliticalPosition a = new IssuesBasedPoliticalPosition(issues);

        issues = new Issues(Arrays.asList(0.61765, 0.99118));
        IssuesBasedPoliticalPosition b = new IssuesBasedPoliticalPosition(issues);

        issues = new Issues(Arrays.asList(4.38, 0.1));
        IssuesBasedPoliticalPosition c = new IssuesBasedPoliticalPosition(issues);

        issues = new Issues(Arrays.asList(1.12214, 1.80715));
        IssuesBasedPoliticalPosition d = new IssuesBasedPoliticalPosition(issues);

        issues = new Issues(Arrays.asList(2.52049, 1.77177));
        IssuesBasedPoliticalPosition e = new IssuesBasedPoliticalPosition(issues);

        when(candidate1.getPoliticalPosition()).thenReturn(b);
        when(candidate2.getPoliticalPosition()).thenReturn(c);
        when(candidate3.getPoliticalPosition()).thenReturn(d);
        when(candidate4.getPoliticalPosition()).thenReturn(e);

        assertThat(a.utility(candidate1)).isCloseTo(new BigDecimal("0.0028958"), byLessThan(DELTA))
                .isGreaterThan(a.utility(candidate2));
        assertThat(a.utility(candidate4)).isGreaterThan(a.utility(candidate1))
                .isGreaterThan(a.utility(candidate2)).isGreaterThan(a.utility(candidate3));
        assertThat(a.utility(candidate3)).isGreaterThan(a.utility(candidate1));
    }
}
