package so.born;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import com.google.common.base.Strings;

@Mojo(name = "validate", requiresProject = true, requiresDependencyResolution = ResolutionScope.NONE)
public class ShadeAppValidatorMojo extends AbstractMojo {

    @Parameter(property="mainClass", defaultValue="${mainClass}")
    private String mainClass;

    public void execute() throws MojoExecutionException, MojoFailureException {
        if (Strings.isNullOrEmpty(mainClass)) {
            throw new MojoFailureException("property ${mainClass} not defined, required for packaging \"app\"");
        }
    }
}
