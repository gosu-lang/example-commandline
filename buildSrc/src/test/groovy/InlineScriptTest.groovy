import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.Sync
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.springframework.boot.test.OutputCapture
import spock.lang.Specification

class InlineScriptTest extends Specification {

    private Project project
    private final String gosuConfigurationName = 'gosu'
    private Task extractGosu
    private boolean WINDOWS = Os.isFamily(Os.FAMILY_WINDOWS)
    private String LF = System.getProperty("line.separator")

    @Rule
    OutputCapture capture = new OutputCapture()
    
    def setup() {
        project = ProjectBuilder.builder().build()
        
        project.repositories {
            mavenCentral()
        }
        
        project.configurations.create(gosuConfigurationName)
        
        String gosuFullDistroNotation = 'org.gosu-lang.gosu:gosu:1.8.1:full@'
        gosuFullDistroNotation += WINDOWS ? 'zip' : 'tar.gz'
        
        project.dependencies.add(gosuConfigurationName, gosuFullDistroNotation)
        
//        project.getLogger().quiet('project.buildDir shall be: ' + project.buildDir.getAbsolutePath())
        
        extractGosu = project.task('extractGosu', type: Sync) {
//            from project.zipTree(project.configurations.getByName(gosuConfigurationName).singleFile)
            from project.tarTree(project.resources.gzip(project.configurations.getByName(gosuConfigurationName).singleFile))
            into "$project.buildDir/gosu"
        }
        extractGosu.execute()
    }
    
    def "test sumpin'"() {
        given:
        def expectedOutput = 'hello' + LF

        GosuCommandLineTask task = project.task('testTask', type: GosuCommandLineTask, dependsOn: extractGosu)
        task.commandLine = ["$project.buildDir/gosu/gosu-1.8.1/bin/gosu", '-e', """print('hello')"""]

        when:
        capture.flush()
        task.execute()

        then:
        capture.toString().endsWith(expectedOutput)
    }

}
