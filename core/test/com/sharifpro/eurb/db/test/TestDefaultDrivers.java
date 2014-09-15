package com.sharifpro.eurb.db.test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;

import com.sharifpro.db.exception.DuplicateObjectException;
import com.sharifpro.db.meta.SQLDriver;
import com.sharifpro.util.log.ILogger;
import com.sharifpro.util.log.LoggerController;
import com.sharifpro.util.xml.XMLException;
import com.sharifpro.util.xml.XMLObjectCache;

public class TestDefaultDrivers {
	public static void main(String[] args) throws IOException, XMLException {
		ILogger s_log =
	               LoggerController.createLogger(TestDefaultDrivers.class);
		final XMLObjectCache<SQLDriver> _cache = new XMLObjectCache<SQLDriver>();
		
		URL url = TestDefaultDrivers.class.getClassLoader().getResource("default_drivers.xml");
		
		InputStreamReader isr = new InputStreamReader(url.openStream());
		try {
			_cache.load(isr, null, true);
		} catch (DuplicateObjectException ex) {
			// If this happens then this is a programming error as we said
			// in the above call to ingore these errors.
			s_log.error("Received an unexpected DuplicateObjectException", ex);
		} finally {
			isr.close();
		}
		
		Iterator<SQLDriver> itr = _cache.getAllForClass(SQLDriver.class);
		while (itr.hasNext()) {
			SQLDriver driver = itr.next();
			try {
				Class.forName(driver.getDriverClassName());
				System.out.print("+++ ");
			} catch (ClassNotFoundException e) {
				System.out.print(" -  ");
			}
			System.out.println(driver);
		}

	}
}
