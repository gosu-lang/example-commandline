import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

import java.nio.file.Path

class GosuCommandLineTask extends Exec {

    @Input
    String gosuHome

    protected final boolean WINDOWS = Os.isFamily(Os.FAMILY_WINDOWS)

    @Override
    public void setCommandLine(Iterable<?> args) {
//        this.project.getLogger().quiet('GosuCommandLineTask#setCommandLine')

        def theCommand

        if(WINDOWS) {
            theCommand = ['cmd', '/c', "$gosuHome\\bin\\gosu.bat"]
            theCommand.addAll(args)
        } else {
            theCommand = ["$gosuHome/bin/gosu"]
            theCommand.addAll(args)
        }

        super.setCommandLine(theCommand) //TODO actually use 'theCommand'
        this.project.getLogger().quiet('The command will be: ' + getCommandLine())
    }

}
