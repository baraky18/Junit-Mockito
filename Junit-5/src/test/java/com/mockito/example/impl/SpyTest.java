package com.mockito.example.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/*
 * when we are creating a mock without mocking one of its methods and we call a certain method, we get a default value..for example,
 * if I am creating a mock from ArrayList, add one object to the list and check its size - it would be 0.
 * Spy, on the other hand, are mocked and we can also call one of its un-mocked methods and ue it. for example, 
 * if I am creating a spy from ArrayList, add one object to the list and check its size - it would be 1.
 */
public class SpyTest {

	@Test
	public void testSpy() {
		List<String> arrayListSpy = Mockito.spy(ArrayList.class);
		assertEquals(0, arrayListSpy.size());
		arrayListSpy.add("Dummy");
		assertEquals(1, arrayListSpy.size());
	}
	
	@Test
	public void testMock() {
		List<String> arrayListMock = Mockito.mock(ArrayList.class);
		assertEquals(0, arrayListMock.size());
		arrayListMock.add("Dummy");
		assertEquals(0, arrayListMock.size());
	}
}
