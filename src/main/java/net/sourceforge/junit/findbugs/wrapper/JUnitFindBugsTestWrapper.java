package net.sourceforge.junit.findbugs.wrapper;

import edu.umd.cs.findbugs.CheckBcel;
import edu.umd.cs.findbugs.FindBugs;
import edu.umd.cs.findbugs.FindBugs2;
import edu.umd.cs.findbugs.TextUICommandLine;

import net.sourceforge.junit.findbugs.wrapper.exception.JUnitWrapperException;
import net.sourceforge.junit.findbugs.wrapper.exception.JUnitWrapperFileNotFoundException;
import net.sourceforge.junit.findbugs.wrapper.exception.JUnitWrapperRuntimeException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

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
 * Parameters: http://findbugs.sourceforge.net/manual/running.html#d0e511
 *
 * Filter, define bug patterns to use with -include or -exclude parameters:
 * http://findbugs.sourceforge.net/manual/filter.html
 *
 * @author Clayton K. N. Passos
 * @email netstart@gmail.com
 *
 */
public class JUnitFindBugsTestWrapper {
    private String[] argumentsToFindBugs;
    private final Object testClassInstance;
    private CollectBugInstanceBugReporterObserver bugReporterObserver;
    private FindBugs2 findbugs2;

    public JUnitFindBugsTestWrapper(final Object testClassInstance) {
        this.testClassInstance = testClassInstance;
    }

    public void run(final String[] parameters)
            throws JUnitWrapperException {
        this.argumentsToFindBugs = parameters.clone();
        this.run();
    }

    public void run()
            throws JUnitWrapperFileNotFoundException {
        try {
            check();
            this.listParams(argumentsToFindBugs);

            findbugs2 = new FindBugs2();

            TextUICommandLine commandLine = new TextUICommandLine();
            FindBugs.processCommandLine(commandLine, argumentsToFindBugs, findbugs2);

            bugReporterObserver = new CollectBugInstanceBugReporterObserver();
            findbugs2.getBugReporter().addObserver(bugReporterObserver);

            FindBugs.runMain(findbugs2, commandLine);
        } catch (Exception e) {
            throw new JUnitWrapperFileNotFoundException(e);
        }
    }

    private void listParams(String[] parameters) {
        for (String parameter : parameters) {
            System.out.println(parameter);
        }
    }

    private void check()
            throws JUnitWrapperException {
        // Sanity-check the loaded BCEL classes
        if (!CheckBcel.check()) {
            throw new JUnitWrapperException("Versão do CheckBCel incorreta, verifique se no classpath existem duas classes com o mesmo pacote/classe: org.apache.bcel.generic.ObjectType");
        }
    }

    public JUnitFindBugsTestWrapper addParameterNestedTrue() {
        argumentsToFindBugs = ArrayUtils.add(argumentsToFindBugs, 0, "-nested:true");

        return this;
    }

    public JUnitFindBugsTestWrapper addParameterEffortMax() {
        argumentsToFindBugs = ArrayUtils.add(argumentsToFindBugs, 0, "-effort:max");

        return this;
    }

    public JUnitFindBugsTestWrapper addParameterLow() {
        argumentsToFindBugs = ArrayUtils.add(argumentsToFindBugs, 0, "-low");

        return this;
    }

    public JUnitFindBugsTestWrapper addParameterFolderCheck(String folderCheck) {
        argumentsToFindBugs = addFolderToCheck(testClassInstance, argumentsToFindBugs, folderCheck);

        return this;
    }

    public JUnitFindBugsTestWrapper addIncludeFilter(String filter) {
        argumentsToFindBugs = addIncludeFilter(testClassInstance, argumentsToFindBugs, filter);

        return this;
    }

    public JUnitFindBugsTestWrapper addExcludeFilter(String filter) {
        argumentsToFindBugs = addExcludeFilter(testClassInstance, argumentsToFindBugs, filter);

        return this;
    }

    public int getBugCount()
            throws JUnitWrapperException {
        if (findbugs2 == null) {
            throw new JUnitWrapperException("Need execute method run()");
        }

        return findbugs2.getBugCount();
    }

    public int getErrorCount()
            throws JUnitWrapperException {
        if (findbugs2 == null) {
            throw new JUnitWrapperException("Need execute method run()");
        }

        return findbugs2.getErrorCount();
    }

    public String getBugReporterMessage()
            throws JUnitWrapperException {
        if (bugReporterObserver == null) {
            throw new JUnitWrapperException("Need execute method run()");
        }

        return bugReporterObserver.getWriter().toString();
    }

    private String[] addFolderToCheck(final Object testClassInstance, final String[] parameters, final String folderToCheck) {
        File fileFolderToCheck = getFolderToCheck(folderToCheck);

        if (!containFiles(fileFolderToCheck, "class")) {
            throw new JUnitWrapperRuntimeException("nao foram encontrados arquivos .class");
        }

        if (isDirectoryExistent(fileFolderToCheck)) {
            String filterFile = fileFolderToCheck.getAbsolutePath();
            filterFile = filterFile.replaceFirst("/", "");

            return ArrayUtils.add(parameters, parameters.length, filterFile);
        }

        return parameters;
    }

    private File getFolderToCheck(final String folderToCheck) {
        File fileFolderToCheck = new File(folderToCheck);
        System.out.println("Starting Checkstyle on folder '" + fileFolderToCheck.getAbsolutePath());

        return fileFolderToCheck;
    }

    private boolean isDirectoryExistent(final File fileFolderToCheck) {
        return fileFolderToCheck.exists() && fileFolderToCheck.isDirectory();
    }

    private String[] addIncludeFilter(final Object testClassInstance, final String[] parameters, final String filter) {
        if (containFileFilter(testClassInstance, filter)) {
            String filterFile = testClassInstance.getClass().getResource(filter).getFile();
            filterFile = filterFile.replaceFirst("/", "");

            String[] args = ArrayUtils.add(parameters, 0, "-include");
            args = ArrayUtils.add(args, 1, filterFile);

            return args;
        }

        return parameters;
    }

    private String[] addExcludeFilter(final Object testClassInstance, final String[] parameters, final String filter) {
        if (containFileFilter(testClassInstance, filter)) {
            String filterFile = testClassInstance.getClass().getResource(filter).getFile();
            filterFile = filterFile.replaceFirst("/", "");

            String[] args = ArrayUtils.add(parameters, 0, "-exclude");
            args = ArrayUtils.add(args, 1, filterFile);

            return args;
        }

        return parameters;
    }

    private boolean containFileFilter(final Object testClassInstance, final String filter) {
        boolean containFilter = StringUtils.isNotEmpty(filter);

        return (containFilter && existFile(testClassInstance, filter));
    }

    private boolean existFile(final Object testClassInstance, final String filter) {
        return testClassInstance.getClass().getResource(filter) != null;
    }

    /**
     * Lists all files in a given folder
     */
    private void listFiles(final List<File> files, final File folder, final String extension) {
        if (folder.canRead()) {
            if (folder.isDirectory()) {
                for (File f : folder.listFiles()) {
                    listFiles(files, f, extension);
                }
            } else if (folder.toString().endsWith("." + extension)) {
                files.add(folder);
            }
        }
    }

    private boolean containFiles(final File folder, String extension) {
        List<File> files = new ArrayList<File>();
        listFiles(files, folder, extension);
        System.out.println("Found " + files.size() + " Java " + extension + " files.");

        return !files.isEmpty();
    }
}
