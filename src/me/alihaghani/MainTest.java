package me.alihaghani;

import org.json.JSONObject;
import org.junit.Before;

import java.io.FileNotFoundException;

import static me.alihaghani.Main.getData;
import static me.alihaghani.Main.totalBalance;
import static me.alihaghani.Main.totalCount;
import static org.junit.Assert.*;

/**
 * Created by Ali on 2016-05-18.
 */
public class MainTest {

    // In a real project, ideally we'd have static JSON files to use for tests

    @org.junit.Test
    public void testGetData() throws Exception {
        getData(1);
        assertEquals(17,644.77,totalBalance); // calculated totalBalance from 1.JSON
        assertEquals(10, totalCount);         // counted totalCount from 1.JSON
    }

    @org.junit.Test
    public void testGetData2() throws Exception {
        getData(2);
        assertEquals(7,969.61,totalBalance);    // calculated totalBalance from 2.JSON - excluding duplicates
        assertEquals(9, totalCount);           // counted totalCount from 2.JSON - excluding duplicates
    }

    @org.junit.Test (expected = FileNotFoundException.class)
    public void testGetData3() throws Exception {
        getData(0);
    }

}