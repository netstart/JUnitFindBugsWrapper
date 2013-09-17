package net.sourceforge.junit.findbugs.wrapper;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporterObserver;

import net.sourceforge.junit.findbugs.wrapper.exception.JUnitWrapperRuntimeException;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class CollectBugInstanceBugReporterObserver implements BugReporterObserver {
    private final CharArrayWriter writer;
    private final Collection<BugInstance> bugInstances;

    public CollectBugInstanceBugReporterObserver() {
        writer = new CharArrayWriter();
        bugInstances = new ArrayList<BugInstance>();
    }

    @Override
    public void reportBug(BugInstance bugInstance) {
        bugInstances.add(bugInstance);

        try {
            writer.write("\n");
            writer.write("Priority: " + bugInstance.getPriorityString() + "\n");
            writer.write("Priority type: " + bugInstance.getPriorityTypeString() + "\n");
            writer.write("Categoria: " + bugInstance.getBugPattern().getCategory() + "\n");

            writer.write("Problem: " + bugInstance.getMessageWithPriorityTypeAbbreviation() + "\n");
            writer.write("Short description: " + bugInstance.getBugPattern().getShortDescription() + "\n");
            writer.write("Detail: " + bugInstance.getBugPattern().getDetailPlainText());
        } catch (IOException e) {
            throw new JUnitWrapperRuntimeException(e);
        }
    }

    public CharArrayWriter getWriter() {
        return writer;
    }

    public Collection<BugInstance> getBugInstances() {
        return bugInstances;
    }

    public String getBugReporterMessage() {
        return writer.toString();
    }
}
