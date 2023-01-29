package dbfileorga;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class MitgliederDBTest {

	MitgliederDB db = new MitgliederDB(false);
	MitgliederTableAsArray RecordArray = new MitgliederTableAsArray();
	
	@Before
	public void setUp() throws Exception {
		db = new MitgliederDB(false);
    }
	
	@Test
	public void testGetNumberOfRecords(){
		assertEquals(35,db.getNumberOfRecords());
		db = new MitgliederDB();
		assertEquals(0,db.getNumberOfRecords());
	}
	
	
	@Test
	public void testGetBlockNumOfRecord(){
		for (int i=1; i<=35; ++i) {
			if (i<=5){
				assertEquals(0,db.getBlockNumOfRecord(i));
			}else if (i<=10){
				assertEquals(1,db.getBlockNumOfRecord(i));
			}else if (i<=15){
				assertEquals(2,db.getBlockNumOfRecord(i));
			}else if (i<=20){
				assertEquals(3,db.getBlockNumOfRecord(i));
			}else if (i<=25){
				assertEquals(4,db.getBlockNumOfRecord(i));
			}else if (i<=30){
				assertEquals(5,db.getBlockNumOfRecord(i));
			}else if (i<=35){
				assertEquals(6,db.getBlockNumOfRecord(i));
			}
		}
		assertEquals(-1,db.getBlockNumOfRecord(36));
		db = new MitgliederDB();
		assertEquals(-1,db.getBlockNumOfRecord(1));
	}
	
	@Test
	public void testIterator() {
		int recNum = 0;
		for(Record currRecord : db ){
			assertEquals(RecordArray.records[recNum], currRecord.toString());
			recNum++;
		}
		db = new MitgliederDB();
		assertFalse(db.iterator().hasNext());
	}
	
	@Test 
	public void testIteratorHasNext() {
		Iterator<Record> iter = db.iterator();
		for (int i=0; i<35; ++i) {
			assertTrue(iter.hasNext());
			iter.next();
		}
		assertFalse(iter.hasNext());
		
	}
	
	
	@Test(expected = NoSuchElementException.class)
	public void testIteratorNext(){
		Iterator<Record> iter = db.iterator();
		for (int i=0; i<35; ++i){
			iter.next();
		}
		iter.next();
	}	
	
		

}
