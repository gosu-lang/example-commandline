import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.Sync
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Ignore
import org.junit.Rule
import org.springframework.boot.test.OutputCapture
import spock.lang.Specification

class GosuCommandLineTest extends Specification {

    private Project project
    private final String gosuVersion = '1.8.1'
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
        
        String gosuFullDistroNotation = "org.gosu-lang.gosu:gosu:$gosuVersion:full@"
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
    
    def "Run an inline script from the commandline"() {
        given:
        def expectedOutput = 'Hello world!' + LF

        GosuCommandLineTask task = project.task('testTask', type: GosuCommandLineTask, dependsOn: extractGosu) {
            gosuHome =  "$project.buildDir/gosu/gosu-$gosuVersion"
            commandLine = ['-e', """print('Hello world!')"""]
        }

        when:
        capture.flush()
        task.execute()

        then:
        capture.toString().endsWith(expectedOutput)
    }

//    @Ignore
//    def "Run a simple Gosu Program with no dependencies or arguments"() {
//        given:
//        def expectedOutput = 'hello!' + LF
//
//        File simpleGosuProgram = project.file('simple/HelloWorld.gsp')// new File("$project.rootDir/simple/HelloWorld.gsp")
//        project.getLogger().quiet('simple/HelloWorld.gsp should be: ' + simpleGosuProgram.getAbsolutePath())
//        simpleGosuProgram.createNewFile()
//        
//        simpleGosuProgram << """
//        print("hello!")
//        """
//
//        GosuCommandLineTask task = project.task('testTask', type: GosuCommandLineTask, dependsOn: extractGosu) {
//            gosuHome =  "$project.buildDir/gosu/gosu-$gosuVersion"
//            commandLine = [simpleGosuProgram.absolutePath]
//        }
//
//        when:
//        capture.flush()
//        task.execute()
//
//        then:
//        capture.toString().endsWith(expectedOutput)
//    }
    
}
