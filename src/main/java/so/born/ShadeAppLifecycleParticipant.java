package so.born;

import org.apache.maven.AbstractMavenLifecycleParticipant;
import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.Xpp3DomBuilder;

import com.google.common.collect.Lists;

public class ShadeAppLifecycleParticipant extends AbstractMavenLifecycleParticipant {

    @Override
    public void afterProjectsRead(MavenSession session) throws MavenExecutionException {
        for (MavenProject project : session.getAllProjects()) {
            if (!"app".equals(project.getPackaging())) {
                continue;
            }
            Plugin shadePlugin = null;
            for (Plugin plugin : project.getModel().getBuild().getPlugins()) {
                if ("org.apache.maven.plugins:maven-shade-plugin".equals(plugin.getKey())) {
                    shadePlugin = plugin;
                }
            }
            if (shadePlugin == null) {
                shadePlugin = new Plugin();
                shadePlugin.setGroupId("org.apache.maven.plugins");
                shadePlugin.setArtifactId("maven-shade-plugin");
                shadePlugin.setVersion("1.6");
                project.getModel().getBuild().getPlugins().add(shadePlugin);
            }
            PluginExecution execution;
            if (shadePlugin.getExecutions().size() == 0) {
                execution = new PluginExecution();
                execution.setPhase("package");
                execution.setGoals(Lists.newArrayList("shade"));
                shadePlugin.getExecutions().add(execution);
            } else {
                execution = shadePlugin.getExecutions().get(0);
            }
            Xpp3Dom config = (Xpp3Dom) execution.getConfiguration();
            if (config == null) {
                config = new Xpp3Dom("configuration");
            }
            try {
                Xpp3Dom defaultConfig = Xpp3DomBuilder.build(getClass().getResourceAsStream("shade.xml"), "UTF8");
                execution.setConfiguration(Xpp3Dom.mergeXpp3Dom(config, defaultConfig));
            } catch (Exception e) {
                throw new MavenExecutionException("Invalid shade configuration", e);
            }
        }
    }
}
