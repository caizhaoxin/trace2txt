package com.panda.trace;

import java.util.ArrayList;
import java.util.List;

public class TraceFile {
	TraceFileHeader header=new TraceFileHeader();
	List<TraceRecord> records=new ArrayList<TraceRecord>();
	public class TraceFileHeader {
		String kTraceMagicValue;
		int trace_version;
		int kTraceHeaderLength;
		long start_time_;
		int record_size;
		@Override
		public String toString() {
			return "TraceFileHeader{" +
					"kTraceMagicValue='" + kTraceMagicValue + '\'' +
					", trace_version=" + trace_version +
					", kTraceHeaderLength=" + kTraceHeaderLength +
					", start_time_=" + start_time_ +
					", record_size=" + record_size +
					'}';
		}
	}
}

