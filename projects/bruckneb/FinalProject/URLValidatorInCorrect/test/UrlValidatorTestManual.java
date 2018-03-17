import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
import junit.framework.TestCase;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class UrlValidatorTestManual extends TestCase {
	
	@BeforeEach
	protected
	void setUp() throws Exception {
	}

	@AfterEach
	protected
	void tearDown() throws Exception {
	}


	public UrlValidatorTestManual(String testName) {
			super(testName);
	}
	
	@Test
	public void testManualTestPos_httpLC_AuthLC_NoPort_NoPath_NoFile_NoQS() {
		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid("http://www.google.com"));
	}
	
	@Test
	public void testManualTestPos_httpUC_AuthLC_NoPort_NoPath_NoFile_NoQS() {
		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid("HTTP://www.google.com"));
	}
	
	@Test
	public void testManualTestPos_httpLC_AuthUC_NoPort_NoPath_NoFile_NoQS() {
		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid("http://WWW.GOOGLE.COM"));
	}
	
	@Test
	public void testManualTestPos_httpUC_AuthUC_NoPort_NoPath_NoFile_NoQS() {
		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid("HTTP://WWW.GOOGLE.COM"));
	}
	
	@Test
	public void testManualTestPos_ftpLC_AuthLC_NoPort_NoPath_NoFile_NoQS() {
		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid("ftp://www.google.com"));
	}
	
	@Test
	public void testManualTestPos_fileLC_AuthLC_NoPort_NoPath_File_NoQS() {
		String[] schemes = {"file"};
		UrlValidator v = new UrlValidator(schemes);
		assertTrue( v.isValid("file://www.google.com/file.txt"));
	}
	
	@Test
	public void testManualTestPos_fileUC_AuthLC_NoPort_NoPath_File_NoQS() {
		String[] schemes = {"file"};
		UrlValidator v = new UrlValidator(schemes);
		assertTrue( v.isValid("FILE://www.google.com/file.txt"));
	}
	
	@Test
	public void testManualTestNeg_fileLC_AuthLC_Port_NoPath_File_NoQS() {
		String[] schemes = {"file"};
		UrlValidator v = new UrlValidator(schemes);
		assertFalse( v.isValid("file://www.google.com:80/file.txt"));
	}
	
	@Test
	public void testManualTestNeg_fileUC_AuthLC_Port_NoPath_File_NoQS() {
		String[] schemes = {"file"};
		UrlValidator v = new UrlValidator(schemes);
		assertFalse( v.isValid("FILE://www.google.com:80/file.txt"));
	}
	
	@Test
	public void testManualTestPos_httpLC_AuthLC_Port_NoPath_NoFile_NoQS() {
		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid("http://www.google.com:80/"));
	}
	
	@Test
	public void testManualTestPos_httpLC_AuthLC_NoPort_NoPath_File_NoQS() {
		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid("http://www.google.com/testfile.htm"));
	}
	
	@Test
	public void testManualTestPos_httpLC_AuthLC_NoPort_Path_File_NoQS() {
		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid("http://www.google.com/test/testfile.htm"));
	}
	
	@Test
	public void testManualTestPos_httpLC_AuthLC_NoPort_NoPath_NoFile_QS() {
		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid("http://www.google.com/test?qs1=s1&qs2=s2"));
	}
	
	@Test
	public void testManualTestPos_labelLength63() {
		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid("http://www.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijk.com"));
	}
	
	@Test
	public void testManualTestNeg_labelLength64() {
		UrlValidator v = new UrlValidator();
		assertFalse( v.isValid("http://www.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijkl.com/test?qs1=s1&qs2=s2"));
	}
	
	@Test
	public void testManualTestNeg_noScheme1() {
		UrlValidator v = new UrlValidator();
		assertFalse( v.isValid("://www.google.com"));
	}
	
	@Test
	public void testManualTestNeg_noScheme2() {
		UrlValidator v = new UrlValidator();
		assertFalse( v.isValid("www.google.com"));
	}
	
	@Test
	public void testManualTestNeg_invalidScheme() {
		UrlValidator v = new UrlValidator();
		assertFalse( v.isValid("3TTP://www.google.com/"));
	}
	
}
