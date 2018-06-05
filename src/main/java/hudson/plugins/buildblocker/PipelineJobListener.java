package hudson.plugins.buildblocker;

import hudson.Extension;
import hudson.model.Descriptor;
import hudson.model.Item;
import hudson.model.Job;
import hudson.model.listeners.ItemListener;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Add default build blocking properties to workflow jobs to limit concurrent builds
 */

@Extension
public class PipelineJobListener extends ItemListener {

    private static final Logger LOG = Logger.getLogger(BuildBlockerProperty.class.getName());

    @Override
    public void onCreated(Item item) {
        super.onCreated(item);

        if(isNotAJob(item)) {
            LOG.log(Level.INFO, "Skipping to add build blocking property, as received item is not a job");
            return;
        }

        Job job = (Job)item;
        if(job.getProperty(BuildBlockerProperty.class) == null) {
            try {
                BuildBlockerProperty buildBlockerProperty = BuildBlockerProperty.getDefaultProperties();
                LOG.log(Level.INFO, "Adding blocking properties "+ buildBlockerProperty + " to job " + job.getName());
                job.addProperty(buildBlockerProperty);
            } catch (IOException e) {
                LOG.log(Level.SEVERE, "Exception occurred while adding blocking properties to job " + job, e);
            } catch (Descriptor.FormException e) {
                LOG.log(Level.SEVERE, "Exception occurred while adding blocking properties to job " + job, e);
            }
        }
    }

    private boolean isNotAJob(Item item) {
        return !(item instanceof Job);
    }
}
