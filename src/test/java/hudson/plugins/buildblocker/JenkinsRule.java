package hudson.plugins.buildblocker;

import hudson.model.Hudson;
import org.apache.commons.io.FileUtils;
import org.junit.internal.AssumptionViolatedException;
import org.jvnet.hudson.test.JenkinsRecipe;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

/**
 * Jenkins rule to inject the plugin config file
 */
public class JenkinsRule extends org.jvnet.hudson.test.JenkinsRule {

    @Override
    protected Hudson newHudson() throws Exception {
        ServletContext webServer = createWebServer();
        File home = homeLoader.allocate();
        setBlockPluginConfigFile(home);
        for (JenkinsRecipe.Runner r : recipes)
            r.decorateHome(this,home);
        try {
            return new Hudson(home, webServer, getPluginManager());
        } catch (InterruptedException x) {
            throw new AssumptionViolatedException("Jenkins startup interrupted", x);
        }
    }

    private void setBlockPluginConfigFile(File home) throws IOException {
        File sourceFile = new File(getClass().getResource("/hudson.plugins.buildblocker.BuildBlockerProperty.xml").getFile());
        FileUtils.copyFileToDirectory(sourceFile, home);
    }
}
