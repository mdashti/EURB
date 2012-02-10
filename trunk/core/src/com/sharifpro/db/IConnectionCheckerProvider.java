package com.sharifpro.db;


/**
 * An interface used for determining whether a ConnectionChecker is
 * available.
 */
public interface IConnectionCheckerProvider {

    // Note:
    // This interface is currently implemented in the development tools
    // directory
    //     tools/dev
    // It is designed not to be shipped to customers
    // The implementor is
    //     net.project.database.ConnectionCheckerProvider
    // and is bundled in the
    //     tools/dev/build/lib/devtools.jar
    // jar file
    // This interface is copied to
    //    tools/dev/classes
    // to allow the development tools to implement the interface

    /**
     * Gets a singleton ConnectionChecker.
     * <b>Note:</b> It is up to the implementor of IConnectionChecker
     * to ensure that it is a singleton.
     * @return the ConnectionChecker
     */
    public IConnectionChecker getConnectionChecker();

}

