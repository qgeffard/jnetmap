// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.sio.jnetmap.domain;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sio.jnetmap.domain.Band;
import org.sio.jnetmap.domain.BandDataOnDemand;
import org.sio.jnetmap.domain.BandIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect BandIntegrationTest_Roo_IntegrationTest {
    
    declare @type: BandIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: BandIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: BandIntegrationTest: @Transactional;
    
    @Autowired
    private BandDataOnDemand BandIntegrationTest.dod;
    
    @Test
    public void BandIntegrationTest.testCountBands() {
        Assert.assertNotNull("Data on demand for 'Band' failed to initialize correctly", dod.getRandomBand());
        long count = Band.countBands();
        Assert.assertTrue("Counter for 'Band' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void BandIntegrationTest.testFindBand() {
        Band obj = dod.getRandomBand();
        Assert.assertNotNull("Data on demand for 'Band' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Band' failed to provide an identifier", id);
        obj = Band.findBand(id);
        Assert.assertNotNull("Find method for 'Band' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Band' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void BandIntegrationTest.testFindAllBands() {
        Assert.assertNotNull("Data on demand for 'Band' failed to initialize correctly", dod.getRandomBand());
        long count = Band.countBands();
        Assert.assertTrue("Too expensive to perform a find all test for 'Band', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Band> result = Band.findAllBands();
        Assert.assertNotNull("Find all method for 'Band' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Band' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void BandIntegrationTest.testFindBandEntries() {
        Assert.assertNotNull("Data on demand for 'Band' failed to initialize correctly", dod.getRandomBand());
        long count = Band.countBands();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Band> result = Band.findBandEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Band' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Band' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void BandIntegrationTest.testFlush() {
        Band obj = dod.getRandomBand();
        Assert.assertNotNull("Data on demand for 'Band' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Band' failed to provide an identifier", id);
        obj = Band.findBand(id);
        Assert.assertNotNull("Find method for 'Band' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyBand(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Band' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void BandIntegrationTest.testMergeUpdate() {
        Band obj = dod.getRandomBand();
        Assert.assertNotNull("Data on demand for 'Band' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Band' failed to provide an identifier", id);
        obj = Band.findBand(id);
        boolean modified =  dod.modifyBand(obj);
        Integer currentVersion = obj.getVersion();
        Band merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Band' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void BandIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Band' failed to initialize correctly", dod.getRandomBand());
        Band obj = dod.getNewTransientBand(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Band' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Band' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Band' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void BandIntegrationTest.testRemove() {
        Band obj = dod.getRandomBand();
        Assert.assertNotNull("Data on demand for 'Band' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Band' failed to provide an identifier", id);
        obj = Band.findBand(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Band' with identifier '" + id + "'", Band.findBand(id));
    }
    
}
