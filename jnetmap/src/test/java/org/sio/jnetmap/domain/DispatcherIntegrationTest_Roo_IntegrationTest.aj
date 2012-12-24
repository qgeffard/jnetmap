// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.sio.jnetmap.domain;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sio.jnetmap.domain.Dispatcher;
import org.sio.jnetmap.domain.DispatcherDataOnDemand;
import org.sio.jnetmap.domain.DispatcherIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect DispatcherIntegrationTest_Roo_IntegrationTest {
    
    declare @type: DispatcherIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: DispatcherIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: DispatcherIntegrationTest: @Transactional;
    
    @Autowired
    private DispatcherDataOnDemand DispatcherIntegrationTest.dod;
    
    @Test
    public void DispatcherIntegrationTest.testCountDispatchers() {
        Assert.assertNotNull("Data on demand for 'Dispatcher' failed to initialize correctly", dod.getRandomDispatcher());
        long count = Dispatcher.countDispatchers();
        Assert.assertTrue("Counter for 'Dispatcher' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void DispatcherIntegrationTest.testFindDispatcher() {
        Dispatcher obj = dod.getRandomDispatcher();
        Assert.assertNotNull("Data on demand for 'Dispatcher' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Dispatcher' failed to provide an identifier", id);
        obj = Dispatcher.findDispatcher(id);
        Assert.assertNotNull("Find method for 'Dispatcher' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Dispatcher' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void DispatcherIntegrationTest.testFindAllDispatchers() {
        Assert.assertNotNull("Data on demand for 'Dispatcher' failed to initialize correctly", dod.getRandomDispatcher());
        long count = Dispatcher.countDispatchers();
        Assert.assertTrue("Too expensive to perform a find all test for 'Dispatcher', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Dispatcher> result = Dispatcher.findAllDispatchers();
        Assert.assertNotNull("Find all method for 'Dispatcher' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Dispatcher' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void DispatcherIntegrationTest.testFindDispatcherEntries() {
        Assert.assertNotNull("Data on demand for 'Dispatcher' failed to initialize correctly", dod.getRandomDispatcher());
        long count = Dispatcher.countDispatchers();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Dispatcher> result = Dispatcher.findDispatcherEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Dispatcher' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Dispatcher' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void DispatcherIntegrationTest.testFlush() {
        Dispatcher obj = dod.getRandomDispatcher();
        Assert.assertNotNull("Data on demand for 'Dispatcher' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Dispatcher' failed to provide an identifier", id);
        obj = Dispatcher.findDispatcher(id);
        Assert.assertNotNull("Find method for 'Dispatcher' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyDispatcher(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Dispatcher' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void DispatcherIntegrationTest.testMergeUpdate() {
        Dispatcher obj = dod.getRandomDispatcher();
        Assert.assertNotNull("Data on demand for 'Dispatcher' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Dispatcher' failed to provide an identifier", id);
        obj = Dispatcher.findDispatcher(id);
        boolean modified =  dod.modifyDispatcher(obj);
        Integer currentVersion = obj.getVersion();
        Dispatcher merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Dispatcher' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void DispatcherIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Dispatcher' failed to initialize correctly", dod.getRandomDispatcher());
        Dispatcher obj = dod.getNewTransientDispatcher(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Dispatcher' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Dispatcher' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Dispatcher' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void DispatcherIntegrationTest.testRemove() {
        Dispatcher obj = dod.getRandomDispatcher();
        Assert.assertNotNull("Data on demand for 'Dispatcher' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Dispatcher' failed to provide an identifier", id);
        obj = Dispatcher.findDispatcher(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Dispatcher' with identifier '" + id + "'", Dispatcher.findDispatcher(id));
    }
    
}
