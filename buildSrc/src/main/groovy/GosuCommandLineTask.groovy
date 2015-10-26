import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class GosuCommandLineTask extends Exec {

//    @Input List<String> gosuArgs

    protected final boolean WINDOWS = Os.isFamily(Os.FAMILY_WINDOWS)

    @Override
    public void setCommandLine(Iterable<?> args) {
//        this.project.getLogger().quiet('GosuCommandLineTask#setCommandLine')

        def theCommand

        if(WINDOWS) {
            theCommand = ['cmd', '/c', 'gosu.bat']
            theCommand.addAll(args)
        } else {
            theCommand = ['./gosu']
            theCommand.addAll(args)
        }

        super.setCommandLine(args) //TODO actually use 'theCommand'
//        this.project.getLogger().quiet('The command will be: ' + getCommandLine())
    }

    @TaskAction
    void start() {}

//        def theCommand
//
//        if(WINDOWS) {
//            theCommand = ['cmd', '/c', 'gosu.bat']
//            theCommand.addAll(gosuArgs)
//        } else {
//            theCommand = ['./gosu.sh']
//            theCommand.addAll(gosuArgs)
//        }
//
//        commandLine = theCommand

//        //store the output instead of printing to the console:
//        standardOutput = new ByteArrayOutputStream()
//
//        ext.output = {
//            return standardOutput.toString()
//        }

//    }

}
