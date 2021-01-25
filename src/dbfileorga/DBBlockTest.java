package dbfileorga;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class DBBlockTest {
	
	DBBlock block = new DBBlock(); 
	MitgliederTableAsArray RecordArray = new MitgliederTableAsArray();

    @Before
    public void setUp() throws Exception {
    	block = new DBBlock();

    }

	
	@Test
	public void testGetRecord() {
		getRecordOnEmptyBlock();
		getRecordWithManyRecors();
		
	}

	
	private void getRecordOnEmptyBlock() {
		assertNull(block.getRecord(0));
		assertNull(block.getRecord(1));
	}
	
	private void getRecordWithManyRecors() {
		insertRecords();
		for (int i = 0; i< 5; ++i){
			assertEquals(RecordArray.records[i], block.getRecord(i+1).toString());
		}
		assertNull(block.getRecord(6));
	}
	

	@Test
	public void testGetNumberOfRecords() {
		assertEquals(0,block.getNumberOfRecords());
		
		
	}


	private void insertRecords() {
		for (int i = 0; i< 5; ++i){
			block.insertRecordAtTheEnd(new Record(RecordArray.records[i]));
			assertEquals(i+1,block.getNumberOfRecords());
		}
	}

	@Test
	public void testInsertRecordAtTheEnd() {
		int lastpos=0;
		for (int i = 0; i< 5; ++i){
			int result = block.insertRecordAtTheEnd(new Record(RecordArray.records[i]));
			assertEquals(lastpos+RecordArray.records[i].length(),result);
			lastpos=result+1;
		}
		//block is full try to insert another record
		assertEquals(-1, block.insertRecordAtTheEnd(new Record(RecordArray.records[5])));
	}

	@Test
	public void testIterator() {
		assertFalse(block.iterator().hasNext());
		insertRecords();
		int recNum = 0;
		for(Record currRecord : block ){
			System.out.println(currRecord);
			assertEquals(RecordArray.records[recNum], currRecord.toString());
			recNum++;
		}		
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testIteratorNext(){
		block.iterator().next();
	}

}
