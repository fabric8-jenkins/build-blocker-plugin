package hudson.plugins.buildblocker;

import hudson.model.FreeStyleProject;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PipelineJobListenerTest {

    @Rule
    public JenkinsRule jenkins = new JenkinsRule();

    @Test
    public void should_add_blocking_properties_from_config_file() throws IOException {
        FreeStyleProject job = jenkins.createFreeStyleProject("testJob");

        BuildBlockerProperty blockingJobProperty = (BuildBlockerProperty) job.getProperty(BuildBlockerProperty.class);


        assertNotNull(blockingJobProperty);
        assertEquals(blockingJobProperty.getBlockingJobs(), ".*");
    }

}