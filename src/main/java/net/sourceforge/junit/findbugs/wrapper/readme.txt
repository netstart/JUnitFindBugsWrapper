Usage: findbugs [general options] -textui [command line options...] [jar/zip/class files, directories...]
General options:
  -jvmArgs args    Pass args to JVM
  -maxHeap size    Maximum Java heap size in megabytes (default=768)
  -javahome <dir>  Specify location of JRE
  
General FindBugs options:
    -project <project>                       analyze given project
    -home <home directory>                   specify FindBugs home directory
    -pluginList <jar1[;jar2...]>             specify list of plugin Jar files to load
    -effort[:min|less|default|more|max]      set analysis effort level
    -adjustExperimental                      lower priority of experimental Bug Patterns
    -workHard                                ensure analysis effort is at least 'default'
    -conserveSpace                           same as -effort:min (for backward compatibility)
    -showPlugins                             show list of available detector plugins
    
Output options:
    -timestampNow                            set timestamp of results to be current time
    -quiet                                   suppress error messages
    -longBugCodes                            report long bug codes
    -progress                                display progress in terminal window
    -release <release name>                  set the release name of the analyzed application
    -experimental                            report of any confidence level including experimental bug patterns
    -low                                     report warnings of any confidence level
    -medium                                  report only medium and high confidence warnings [default]
    -high                                    report only high confidence warnings
    -maxRank <rank>                          only report issues with a bug rank at least as scary as that provided
    -sortByClass                             sort warnings by class
    -xml[:withMessages]                      XML output (optionally with messages)
    -xdocs                                   xdoc XML output to use with Apache Maven
    -html[:stylesheet]                       Generate HTML output (default stylesheet is default.xsl)
    -emacs                                   Use emacs reporting format
    -relaxed                                 Relaxed reporting mode (more false positives!)
    -train[:outputDir]                       Save training data (experimental); output dir defaults to '.'
    -useTraining[:inputDir]                  Use training data (experimental); input dir defaults to '.'
    -redoAnalysis <filename>                 Redo analysis using configureation from previous analysis
    -sourceInfo <filename>                   Specify source info file (line numbers for fields/classes)
    -projectName <project name>              Descriptive name of project
    -reanalyze <filename>                    redo analysis in provided file
    -output <filename>                       Save output in named file
    -nested[:true|false]                     analyze nested jar/zip archives (default=true)
    
Output filtering options:
    -bugCategories <cat1[,cat2...]>          only report bugs in given categories
    -onlyAnalyze <classes/packages>          only analyze given classes and packages; end with .* to indicate classes in a package, .- to indicate a package prefix
    -excludeBugs <baseline bugs>             exclude bugs that are also reported in the baseline xml output
    -exclude <filter file>                   exclude bugs matching given filter
    -include <filter file>                   include only bugs matching given filter
    -applySuppression                        Exclude any bugs that match suppression filter loaded from fbp file
    
Detector (visitor) configuration options:
    -visitors <v1[,v2...]>                   run only named visitors
    -omitVisitors <v1[,v2...]>               omit named visitors
    -chooseVisitors <+v1,-v2,...>            selectively enable/disable detectors
    -choosePlugins <+p1,-p2,...>             selectively enable/disable plugins
    -adjustPriority <v1=(raise|lower)[,...]> raise/lower priority of warnings for given visitor(s)
    
Project configuration options:
    -auxclasspath <classpath>                set aux classpath for analysis
    -auxclasspathFromInput                   read aux classpath from standard input
    -sourcepath <source path>                set source path for analyzed classes
    -exitcode                                set exit code of process
    -noClassOk                               output empty warning file if no classes are specified
    -xargs                                   get list of classfiles/jarfiles from standard input rather than command line
    -cloud <id>                              set cloud id
    -cloudProperty <key=value>               set cloud property
    -bugReporters <name,name2,-name3>        bug reporter decorators to explicitly enable/disable
    -printConfiguration                      print configuration and exit, without running analysis
    -version                                 print version, check for updates and exit, without running analysis