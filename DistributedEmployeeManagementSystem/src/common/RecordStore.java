package common;

import java.util.ArrayList;
import java.util.HashMap;

public class RecordStore {
	private HashMap<Character, ArrayList<Record>> records;
	
	public RecordStore() {
		this.records = new HashMap<Character, ArrayList<Record>>();
	}
	
	public Record read(String recordID) {
		for (ArrayList<Record> records : this.records.values()) {
			for (Record record : records) {
				if (record.recordID.equals(recordID)) {
					return record;
				}
			}
		}
		return null;
	}
	
	public void write(Record record) {
		Character index = record.lastName.toUpperCase().charAt(0);
		ArrayList<Record> records = null;
		if (!this.records.containsKey(index)) {
			records = new ArrayList<Record>();
			this.records.put(index, records);
		} else {
			records = this.records.get(index);
		}
		records.add(record);
	}
}
