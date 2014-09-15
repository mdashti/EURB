package com.sharifpro.db;

/**
 * Provides an interface to allow implementation of
 * a ConnectionChecker by external tools.
 */
public interface IConnectionChecker {

    // Note:
    // This interface is currently implemented in the development tools
    // directory
    //     tools/dev
    // It is designed not to be shipped to customers
    // The implementor is
    //     net.project.database.ConnectionChecker
    // and is bundled in the
    //     tools/dev/build/lib/devtools.jar
    // jar file
    // This interface is copied to
    //    tools/dev/classes
    // to allow the development tools to implement the interface

    /**
     * Registers a connection as opened.
     * @param connection the connection opened
     */
    public void open(java.sql.Connection connection);

    /**
     * Registers a connection as closed.
     * @param connection the connection closed
     */
    public void close(java.sql.Connection connection);
}
