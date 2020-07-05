package de.slag.mail.backend.adm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AdmConfigPropertyUtilsTest {

	@Test
	void testFindIdsFor() {
		Collection<String> keys = Arrays.asList("abc", "a.b.1.c", "a.b.1.d", "a.b.2.c", "a.b.1.d", "a.3.b.c");
		Collection<Long> foundIds = AdmConfigPropertyUtils.findIdsFor("a.b", keys);

		List<Long> asList = Arrays.asList(1l, 2l);
		List<Long> asList2 = new ArrayList<>(foundIds);
		Assertions.assertEquals(asList, asList2);
	}

}
