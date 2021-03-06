/*
 * Firebird Open Source AS3AP Benchmark suite
 *
 * Distributable under LGPL license.
 * You may obtain a copy of the License at http://www.gnu.org/copyleft/lgpl.html
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * LGPL License for more details.
 *
 * This file was created by members of the firebird development team.
 * All individual contributions remain the Copyright (C) of those
 * individuals.  Contributors to this file are either listed here or
 * can be obtained from a CVS history command.
 *
 * All rights reserved.
 */

package org.firebirdsql.benchmark;

import junit.framework.*;
import junit.extensions.TestSetup;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * This is AS3AP benchmarking suite.
 */
public abstract class BenchmarkSuite extends TestSuite {
    
    public static final boolean CREATE_DATABASE = true;

        
    private static BenchmarkDatabaseManager databaseManager;
    private static BenchmarkFixture fixture;
    
    public static BenchmarkDatabaseManager getDatabaseManager() {
        return databaseManager;
    }
    
    public static BenchmarkFixture getFixture() {
        return fixture;
    }
    
    protected BenchmarkDatabaseManager createDatabaseManager() throws SQLException {
        return getFixture().createDatabaseManager();
    }
    
    /**
     * Get benchmark test suite that will be executed.
     * 
     * @return instance of {@link Test} containing the suite.
     */
    public Test suite() {
        
        TestSetup setup = new TestSetup(this) {
            
            protected void setUp() throws Exception {
                fixture = createFixture();
                fixture.setUp(isCreateDatabase());

                databaseManager = createDatabaseManager();
                fillSuite();
            }

            protected void tearDown() throws Exception {
                fixture.tearDown(false);
            }
            
        };

        return setup;
    }
    
    public BenchmarkFixture createFixture() 
        throws ClassNotFoundException, NoSuchMethodException, 
        InvocationTargetException, InstantiationException, IllegalAccessException 
    {
        String className = BenchmarkConfiguration.getConfiguration().getFixtureClassName();
        
        Class fixtureClass = Class.forName(className);
        Constructor constructor = fixtureClass.getConstructor(
                new Class[]{File.class});
        
        return (BenchmarkFixture)constructor.newInstance(new Object[]{
                new File(BenchmarkConfiguration.getConfiguration().getDataPath())});
        
    }
    
    protected Test createOutputTest(String name) {
        return getFixture().createOutputTest(name);
    }
    
    protected Test createSelectTest(String name) {
        return getFixture().createSelectTest(name);
    }
    
    protected Test createJoinTest(String name) {
        return getFixture().createJoinTest(name);
    }
    
    protected Test createProjectionTest(String name) {
        return getFixture().createProjectionTest(name);
    }
    
    protected Test createAggregateTest(String name) {
        return getFixture().createAggregateTest(name);
    }

    protected Test createIndexTest(String name) {
        return getFixture().createIndexTest(name);
    }
    
    protected Test createUpdateTest(String name) {
        return getFixture().createUpdateTest(name);
    }
    
    protected boolean isCreateDatabase() {
        return CREATE_DATABASE;
    }

    /**
     * Fill the test suite. This method is called during the suite construction.
     * Each subclass should fill the suite by calling {@link #addTest(Test)}
     * or {@link #addTestSuite(Class)} methods.
     */    
    public abstract void fillSuite();
    
}