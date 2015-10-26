import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test
import org.junit.Assert
import spock.lang.Specification

class InlineScriptTest extends Specification {

    def "test sumpin'"() {
        given:
        def expectedOutput = 'poopy'
        System.out = Mock(PrintStream)

        Project project = ProjectBuilder.builder().build()
        def task = project.task('testTask', type: GosuCommandLineTask)
        task.commandLine = ['wut']
//        task {
//            gosuArgs = ['wut']
//        }

        when:
        task.execute()

        then:
        1 * System.out.println(expectedOutput)
    }
    
//    @Test
//    void testSomething() {
//        def expectedOutput = 'poopy'
//
//
//        Assert.fail()
//    }
}
