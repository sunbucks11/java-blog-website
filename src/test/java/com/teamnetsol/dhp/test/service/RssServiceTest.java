package com.teamnetsol.dhp.test.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.teamnetsol.dhp.entity.Item;
import com.teamnetsol.dhp.exception.RssException;
import com.teamnetsol.dhp.service.RssService;

public class RssServiceTest {

	private RssService rssService;

	@Before
	public void setUp() throws Exception {
		rssService = new RssService();
	}

	
	@Test
	public void testGetItemsFile() throws RssException {
		List<Item> items = rssService.getItems(new File("test-rss/javavids.xml"));
		assertEquals(10, items.size());
		Item firstItem = items.get(0);
		assertEquals("How to solve Source not found error during debug in Eclipse", firstItem.getTitle());
		assertEquals("22 06 2014 21:35:49", new SimpleDateFormat("dd MM yyyy HH:mm:ss").format(firstItem.getPublishedDate()));
	}

}
