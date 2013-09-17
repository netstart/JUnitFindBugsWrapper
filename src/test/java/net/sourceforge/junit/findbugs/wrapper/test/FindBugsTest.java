
package net.sourceforge.junit.findbugs.wrapper.test;

import edu.umd.cs.findbugs.CheckBcel;
import edu.umd.cs.findbugs.FindBugs;
import edu.umd.cs.findbugs.FindBugs2;
import edu.umd.cs.findbugs.TextUICommandLine;

import net.sourceforge.junit.findbugs.wrapper.CollectBugInstanceBugReporterObserver;
import net.sourceforge.junit.findbugs.wrapper.JUnitFindBugsTestWrapper;
import net.sourceforge.junit.findbugs.wrapper.exception.JUnitWrapperException;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * A list of bug patterns reported by FindBugs (402):
 * http://findbugs.sourceforge.net/bugDescriptions.html
 *
 * A Comparison of Bug Finding Tools for Java (ESC/Java, Bandera, FindBugs,
 * JLint, PMD) http://www.cs.umd.edu/~jfoster/papers/issre04.pdf
 *
 * How to write custom detector:
 * http://www.ibm.com/developerworks/java/library/j-findbug2
 *
 * @author Clayton K. N. Passos
 * @email netstart@gmail.com
 *
 */
public class FindBugsTest {
    @Test
    public void junitFindBugsTestWrapper()
            throws JUnitWrapperException {
        JUnitFindBugsTestWrapper w = new JUnitFindBugsTestWrapper(this);
        w.addParameterNestedTrue();
        w.addParameterLow();
        w.addParameterEffortMax();
        w.addParameterFolderCheck("target/classes");
        w.addIncludeFilter("");
        w.addExcludeFilter("");

        w.run();

        int bugCount = w.getBugCount();
        int errorCount = w.getErrorCount();
        String bugReporterMessage = w.getBugReporterMessage();

        Assert.assertTrue(bugCount + " findbugs bugs found. " + bugReporterMessage, bugCount == 0);
        Assert.assertTrue(errorCount + " findbugs error found. " + bugReporterMessage, errorCount == 0);
    }

    @Test
    public void junitFindBugsTestWrapperWithExcludeFilter()
            throws JUnitWrapperException {
        JUnitFindBugsTestWrapper w = new JUnitFindBugsTestWrapper(this);
        w.addParameterNestedTrue();
        w.addParameterLow();
        w.addParameterEffortMax();
        w.addParameterFolderCheck("target/classes");
        w.addExcludeFilter("findbugs.xml");

        w.run();

        int bugCount = w.getBugCount();
        int errorCount = w.getErrorCount();
        String bugReporterMessage = w.getBugReporterMessage();

        Assert.assertTrue(bugCount + " findbugs bugs found. " + bugReporterMessage, bugCount == 0);
        Assert.assertTrue(errorCount + " findbugs error found. " + bugReporterMessage, errorCount == 0);
    }

    /**
     * Exemplo de uso
     */
    @Test
    @Ignore
    public void junitFindBugsTestWrapperRunParameters()
            throws JUnitWrapperException {
        String[] args =
            new String[] {
                             "-nested:true",
                             "-low",
                             "-effort:max",
                             "C:/dev/projetos/crm/seu_projeto_modulo_1/target/classes",
                             "C:/dev/projetos/crm/seu_projeto_modulo_2/target/classes"
            };

        JUnitFindBugsTestWrapper w = new JUnitFindBugsTestWrapper(this);
        w.run(args);

        int bugCount = w.getBugCount();
        int errorCount = w.getErrorCount();
        String bugReporterMessage = w.getBugReporterMessage();

        Assert.assertTrue(bugCount + " findbugs bugs found. " + bugReporterMessage, bugCount == 0);
        Assert.assertTrue(errorCount + " findbugs error found. " + bugReporterMessage, errorCount == 0);
    }

    @Test
    @Ignore
    public void rascunhoTest()
            throws Exception {
        // "-outputFile", "C:/t/t.txt",
        //	"C:/dev/projetos/crm/../target/GvtWebOauthServicesEAR-1.0.0/lib" 
        // "-exclude", "src/test/java/br/com/architecture/sanity/check/findbugs/findbugs.xml"
        String[] args =
            new String[] {
                             "-nested:true",
                             "-low",
                             "-effort:max",
                             "C:/dev/projetos/crm/..../target/classes",
                             "C:/dev/projetos/crm/../target/classes"
            };

        // Sanity-check the loaded BCEL classes
        if (!CheckBcel.check()) {
            System.exit(1);
        }

        FindBugs2 f = new FindBugs2();
        TextUICommandLine commandLine = new TextUICommandLine();
        FindBugs.processCommandLine(commandLine, args, f);

        CollectBugInstanceBugReporterObserver bugReporterObserver = new CollectBugInstanceBugReporterObserver();
        f.getBugReporter().addObserver(bugReporterObserver);

        FindBugs.runMain(f, commandLine);

        int bugCount = f.getBugCount();
        int errorCount = f.getErrorCount();

        Assert.assertTrue(bugCount + " findbugs bugs found. " + bugReporterObserver.getWriter().toString(), bugCount == 0);
        Assert.assertTrue(errorCount + " findbugs error found. " + bugReporterObserver.getWriter().toString(), errorCount == 0);
    }
}
