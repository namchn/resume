package com.nc.resume;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nc.resume._project.resume.util.InputSanitizer;

@SpringBootTest
class ResumeApplicationTests {

	
	@Test
	void contextLoads() {
	}
	
	
	@Autowired
	private InputSanitizer inputSanitizer;
	
	@Test
    void testSanitize_nullInput_returnsNull() {
        String result = inputSanitizer.sanitize(null);
        Assertions.assertNull(result);
    }
	
	@Test
    void testSanitize_htmlTagsEscaped() {
        String input = "<script>alert('xss')</script>";
        //String expected = "&lt;script&gt;alert(&#x27;xss&#x27;)&lt;/script&gt;";
        String expected = "";
        String result = inputSanitizer.sanitize(input);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testSanitize_quotesEscaped() {
        String input = "\"Hello\" 'World'";
        String expected = "&quot;Hello&quot; &#x27;World&#x27;";
        String result = inputSanitizer.sanitize(input);
        Assertions.assertEquals(expected, result);
    }

}
