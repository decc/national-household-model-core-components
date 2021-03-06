package uk.org.cse.nhm.logging.logentry;

import java.io.IOException;

import org.joda.time.DateTime;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.ImmutableMap;

public class ProbeLogEntryTest {

	@Test
	public void test() throws JsonGenerationException, JsonMappingException, IOException {
		final ProbeLogEntry logEntry = new ProbeLogEntry("name", 33f, new DateTime(), 123, 456, new ImmutableMap.Builder<String,Object>().build());
		LogEntryTestUtility.testLogDeSerialisation(logEntry, ProbeLogEntry.class);
	}

}
