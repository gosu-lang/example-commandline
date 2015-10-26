import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.Input

class GosuCommandLineTask extends Exec {

    @Input
    String gosuHome

    protected final boolean WINDOWS = Os.isFamily(Os.FAMILY_WINDOWS)

    public GosuCommandLineTask() {
        standardOutput = new ByteArrayOutputStream()
    }
    
    @Override
    public void setCommandLine(Iterable<?> args) {

        def theCommand

        if(WINDOWS) {
            theCommand = ['cmd', '/c', "$gosuHome\\bin\\gosu.bat"]
            theCommand.addAll(args)
        } else {
            theCommand = ["$gosuHome/bin/gosu"]
            theCommand.addAll(args)
        }

        super.setCommandLine(theCommand)
        this.project.getLogger().quiet('The command for task ' + this.name + ' will be: ' + getCommandLine())
    }
    
}
