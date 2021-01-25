package dbfileorga;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DBBlock implements Iterable<Record> {
	public final static int BLOCKSIZE = 256;
	public final static char RECDEL = '|';
	public final static char DEFCHAR =  '\u0000';
	
	private char block[] = new char[BLOCKSIZE];
			
	/**
	 * Searches the record with number recNum in the DBBlock. 
	 * @param  RecNum is the number of Record 1 = first record
	 * @return record with the specified number or null if record was not found 
	 */
	public Record getRecord(int recNum){
		int currRecNum = 1; //first Record starts at 0
		for (int i = 0; i <block.length;++i){
			if (currRecNum == recNum){
				return getRecordFromBlock(i);
			}
			if (block[i] == RECDEL){
				currRecNum++;
			}
		}
		return null;
	}
	
	
	private Record getRecordFromBlock(int startPos) {
		int endPos = getEndPosOfRecord(startPos);
		if (endPos != -1){ 
			return new Record (Arrays.copyOfRange(block, startPos, endPos));
		}else{
			return null;
		}
	}

	private int getEndPosOfRecord(int startPos){
		int currPos = startPos;
		while( currPos < block.length ){
			if (block[currPos]==RECDEL){
				return currPos;
			}else{
				currPos++;
			}
		}
		return -1;
	}
	
	
	/**
	 * Returns the number of records that are in the block
	 * @return number of records stored in this DBBlock
	 */	
	public int getNumberOfRecords(){
		int count = 0;
		for (int i = 0; i <block.length;++i){
			if (block[i] == RECDEL){
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Inserts an record at the end of the block
	 * @param record the record to insert
	 * @return returns the last position (the position of the RECDEL char) of the inserted record 
	 * 		   returns -1 if the insert fails
	 */
	public int insertRecordAtTheEnd(Record record){
		int startPos = findEmptySpace();
		return insertRecordAtPos(startPos, record);
	}
	
	/**
	 * deletes the content of the block
	 */
	public void delete(){
		block = new char[BLOCKSIZE];
	}
	

	/**
	 * Inserts an record beginning at position startPos
	 * @param startPos the postition to start inserting the record
	 * @param record the record to insert
	 * @return returns the last position (the position of the RECDEL char) of the inserted record 
	 * 		   returns -1 if the insert fails
	 */	
	private int insertRecordAtPos(int startPos, Record record) {
		//we need to insert the record plus the RECDEL 
		int n = record.length();
		if (startPos+n+1 > block.length){
			return -1; // record does not fit into the block;
		}
		for (int i = 0; i < n; ++i) {
		    block[i+startPos] = record.charAt(i);
		}
		block[n+startPos]= RECDEL;
		return n+startPos;
	}

	


	private int findEmptySpace(){
		for (int i = 0; i <block.length;++i){
			if (block[i] == DEFCHAR){
				return i;
			}
		}
		return block.length;		
	}
	
	@Override
	public String toString(){
		String result = new String();
		for (int i = 0; i <block.length;++i){
			if (block[i] == DEFCHAR){
				return result;
			}
			if (block[i] == RECDEL){
				result += "\n";
			}else{
				result += block[i];
			}
			
		}
		return result; 
	}



	@Override
	public Iterator<Record> iterator() {
		return new BlockIterator();
	}
	
	
	private class BlockIterator implements Iterator<Record> {
	    private int currRec=0;
 
	    public  BlockIterator() {
            this.currRec = 0;
        }
	    
        public boolean hasNext() {
            if ( getRecord(currRec+1) != null)
                return true;
            else
                return false;
        }
 
        public Record next() {
        	Record result = getRecord(++currRec);
            if (result == null){
            	throw new NoSuchElementException();
            }else{
            	return result;
            }
        }
 
        @Override
        public void remove() {
        	throw new UnsupportedOperationException();
        }
    } 
	

}
