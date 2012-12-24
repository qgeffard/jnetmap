// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.sio.jnetmap.domain;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sio.jnetmap.domain.Sonde;
import org.sio.jnetmap.domain.SondeDataOnDemand;
import org.sio.jnetmap.domain.SondeIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect SondeIntegrationTest_Roo_IntegrationTest {
    
    declare @type: SondeIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: SondeIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: SondeIntegrationTest: @Transactional;
    
    @Autowired
    private SondeDataOnDemand SondeIntegrationTest.dod;
    
    @Test
    public void SondeIntegrationTest.testCountSondes() {
        Assert.assertNotNull("Data on demand for 'Sonde' failed to initialize correctly", dod.getRandomSonde());
        long count = Sonde.countSondes();
        Assert.assertTrue("Counter for 'Sonde' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void SondeIntegrationTest.testFindSonde() {
        Sonde obj = dod.getRandomSonde();
        Assert.assertNotNull("Data on demand for 'Sonde' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Sonde' failed to provide an identifier", id);
        obj = Sonde.findSonde(id);
        Assert.assertNotNull("Find method for 'Sonde' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Sonde' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void SondeIntegrationTest.testFindAllSondes() {
        Assert.assertNotNull("Data on demand for 'Sonde' failed to initialize correctly", dod.getRandomSonde());
        long count = Sonde.countSondes();
        Assert.assertTrue("Too expensive to perform a find all test for 'Sonde', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Sonde> result = Sonde.findAllSondes();
        Assert.assertNotNull("Find all method for 'Sonde' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Sonde' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void SondeIntegrationTest.testFindSondeEntries() {
        Assert.assertNotNull("Data on demand for 'Sonde' failed to initialize correctly", dod.getRandomSonde());
        long count = Sonde.countSondes();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Sonde> result = Sonde.findSondeEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Sonde' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Sonde' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void SondeIntegrationTest.testFlush() {
        Sonde obj = dod.getRandomSonde();
        Assert.assertNotNull("Data on demand for 'Sonde' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Sonde' failed to provide an identifier", id);
        obj = Sonde.findSonde(id);
        Assert.assertNotNull("Find method for 'Sonde' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifySonde(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Sonde' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void SondeIntegrationTest.testMergeUpdate() {
        Sonde obj = dod.getRandomSonde();
        Assert.assertNotNull("Data on demand for 'Sonde' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Sonde' failed to provide an identifier", id);
        obj = Sonde.findSonde(id);
        boolean modified =  dod.modifySonde(obj);
        Integer currentVersion = obj.getVersion();
        Sonde merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Sonde' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void SondeIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Sonde' failed to initialize correctly", dod.getRandomSonde());
        Sonde obj = dod.getNewTransientSonde(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Sonde' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Sonde' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Sonde' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void SondeIntegrationTest.testRemove() {
        Sonde obj = dod.getRandomSonde();
        Assert.assertNotNull("Data on demand for 'Sonde' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Sonde' failed to provide an identifier", id);
        obj = Sonde.findSonde(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Sonde' with identifier '" + id + "'", Sonde.findSonde(id));
    }
    
}