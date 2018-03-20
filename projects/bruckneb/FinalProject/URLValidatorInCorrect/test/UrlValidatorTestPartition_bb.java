import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
import junit.framework.TestCase;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class UrlValidatorTestPartition extends TestCase {
	
	@BeforeEach
	protected
	void setUp() throws Exception {
	}

	@AfterEach
	protected
	void tearDown() throws Exception {
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL without scheme.
     * <p>
     * <b>Expected Result:</b>
     * Not valid (so assertFalse)
     */
	@Test
	public void test001_InvalidScheme_blank() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
		long o = UrlValidator.ALLOW_ALL_SCHEMES;
		String sUrl = "//www.google.com";
		
		UrlValidator v = new UrlValidator(o);
		assertFalse( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a default scheme without overriding in UrlValidator-schemes.
     * <p>
     * <b>Expected Result:</b>
     * Valid (so assertTrue)
     */
	@Test
	public void test002_ValidScheme_Http() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
		String sUrl = "Http://www.google.com";
		
		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with an invalid scheme (3ttp) without overriding in UrlValidator-schemes.
     * <p>
     * <b>Expected Result:</b>
     * Invalid (so assertFalse)
     */
	@Test
	public void test003_InvalidScheme_3ttp() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
		String sUrl = "3ttp://www.google.com";
		
		UrlValidator v = new UrlValidator();
		assertFalse( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with an invalid scheme (3ttp) without overriding in UrlValidator-schemes.
     * <p>
     * <b>Expected Result:</b>
     * Invalid (so assertFalse)
     */
	@Test
	public void test004_InvalidScheme_3ttp_allowAllSchemes() {
		long o = UrlValidator.ALLOW_ALL_SCHEMES;
		String sUrl = "3ttp://www.google.com";
		
		UrlValidator v = new UrlValidator(o);
		assertFalse( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with an invalid scheme (3ttp) with RegexValidator override; 
     * still not okay to start with a number... 
     * <p>
     * <b>Expected Result:</b>
     * Invalid (so assertFalse)
     */
	@Test
	public void test005_InvalidScheme_3ttp_RegexValidatorMatch() {
		long o = UrlValidator.ALLOW_ALL_SCHEMES;
		RegexValidator authorityValidator = new RegexValidator("[A-Za-z0-9.]+");
		String sUrl = "3ttp://www.google.com";
		
		UrlValidator v = new UrlValidator(authorityValidator, o);
		assertFalse( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with an invalid scheme (3ttp), even with an override in UrlValidator-schemes, should fail.
     * <p>
     * <b>Expected Result:</b>
     * Invalid (so assertFalse)
     */
	@Test
	public void test006_InvalidScheme_3ttp_schemeProvided() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
		String [] s = {"file", "3ttp"};
		String sUrl = "3ttp://www.google.com";
		
		UrlValidator v = new UrlValidator(s);
		assertFalse( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with an invalid scheme (3ttp), even with an override in UrlValidator-schemes.
     * <p>
     * <b>Expected Result:</b>
     * Invalid (so assertFalse)
     */
	@Test
	public void test007_InvalidScheme_3ttp_allowAllSchemes() {
		String [] s = {"file", "3ttp"};
		long o = UrlValidator.ALLOW_ALL_SCHEMES;
		String sUrl = "3ttp://www.google.com";
		
		UrlValidator v = new UrlValidator(s, o);
		assertFalse( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a non-default scheme (file), with scheme provided
     * <p>
     * <b>Expected Result:</b>
     * Valid (so assertTrue)
     */
	@Test
	public void test008_NondefaultScheme_File_schemeProvided() {
		String [] s = {"file", "3ttp"};
		long o = UrlValidator.ALLOW_ALL_SCHEMES;
		String sUrl = "File://www.google.com";
		
		UrlValidator v = new UrlValidator(s);
		assertTrue( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a non-default scheme (file), with allow all schemes override
     * <p>
     * <b>Expected Result:</b>
     * Valid (so assertTrue)
     */
	@Test
	public void test009_NondefaultScheme_File_allowAllSchemes() {
		String [] s = {"file", "3ttp"};
		long o = UrlValidator.ALLOW_ALL_SCHEMES;
		String sUrl = "File://www.google.com";
		
		UrlValidator v = new UrlValidator(o);
		assertTrue( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a non-default scheme (file), with port (invalid),
     * with ALLOW_ALL_SCHEMES override
     * <p>
     * <b>Expected Result:</b>
     * Invalid (so assertFalse)
     */
	@Test
	public void test010_NondefaultScheme_FileWithPort_allowAllSchemes() {
		String [] s = {"file", "3ttp"};
		long o = UrlValidator.ALLOW_ALL_SCHEMES;
		String sUrl = "file://www.google.com:80";
		
		UrlValidator v = new UrlValidator(o);
		assertFalse( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a non-default scheme (file), with port (invalid),
     * with ALLOW_ALL_SCHEMES override
     * <p>
     * <b>Expected Result:</b>
     * Invalid (so assertFalse)
     */
	@Test
	public void test011_NondefaultScheme_UcaseFileWithPort_allowAllSchemes() {
		String [] s = {"file", "3ttp"};
		long o = UrlValidator.ALLOW_ALL_SCHEMES;
		String sUrl = "FILE://www.google.com:80";
		
		UrlValidator v = new UrlValidator(o);
		assertFalse( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a default scheme with blank port.
     * <p>
     * <b>Expected Result:</b>
     * Valid (so assertTrue)
     */
	@Test
	public void test012_ValidScheme_Http_BlankPort() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
		String sUrl = "Http://www.google.com:";
		
		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a default scheme with negative value for port.
     * <p>
     * <b>Expected Result:</b>
     * Invalid (so assertFalse)
     */
	@Test
	public void test013_ValidScheme_Http_NegativePort() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
		String sUrl = "Http://www.google.com:-1";
		
		UrlValidator v = new UrlValidator();
		assertFalse( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a default scheme at top of but within limits port.
     * <p>
     * <b>Expected Result:</b>
     * Valid (so assertTrue)
     */
	@Test
	public void test014_ValidScheme_Http_PortUbound() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
		String sUrl = "Http://www.google.com:65535";
		
		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a default scheme one above top of limits for port.
     * <p>
     * <b>Expected Result:</b>
     * Invalid (so assertFalse)
     */
	@Test
	public void test015_ValidScheme_Http_PortTooHigh() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
		String sUrl = "Http://www.google.com:65536";
		
		UrlValidator v = new UrlValidator();
		assertFalse( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a valid user and password.
     * <p>
     * <b>Expected Result:</b>
     * Valid (so assertTrue)
     */
	@Test
	public void test016_ValidUserPass() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
		String sUrl = "Http://user:pass@www.google.com";
		
		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a valid user.
     * <p>
     * <b>Expected Result:</b>
     * Valid (so assertTrue)
     */
	@Test
	public void test017_ValidUser() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
		String sUrl = "Http://user@www.google.com";
		
		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a invalid user and password.
     * <p>
     * <b>Expected Result:</b>
     * Invalid (so assertFalse)
     */
	@Test
	public void test018_InvalidUserPass() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
		String sUrl = "Http://user:@www.google.com";
		
		UrlValidator v = new UrlValidator();
		assertFalse( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a invalid user and password.
     * <p>
     * <b>Expected Result:</b>
     * Invalid (so assertFalse)
     */
	@Test
	public void test019_InvalidUserPass() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
		String sUrl = "Http://:pass@www.google.com";
		
		UrlValidator v = new UrlValidator();
		assertFalse( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a valid user and password.
     * <p>
     * <b>Expected Result:</b>
     * Invalid (so assertFalse)
     */
	@Test
	public void test020_InvalidUserPass() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
		String sUrl = "Http://:@www.google.com";
		
		UrlValidator v = new UrlValidator();
		assertFalse( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a valid IPv4
     * <p>
     * <b>Expected Result:</b>
     * Valid (so assertTrue)
     */
	@Test
	public void test021_IPv4_Valid() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
		String sUrl = "http://127.1.1.1";
		
		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid(sUrl) );
	}

    /**
     * <b>Test Summary:</b>
     * Tests URL with a invalid IPv4
     * <p>
     * <b>Expected Result:</b>
     * Invalid (so assertFalse)
     */
	@Test
	public void test022_IPv4_Invalid() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
		String sUrl = "http://127.1.1";
		
		UrlValidator v = new UrlValidator();
		assertFalse( v.isValid(sUrl) );
	}

    /**
     * <b>Test Summary:</b>
     * Tests URL with a invalid IPv4
     * <p>
     * <b>Expected Result:</b>
     * Invalid (so assertFalse)
     */
	@Test
	public void test023_IPv4_Invalid() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
		String sUrl = "http://256.1.1.1";
		
		UrlValidator v = new UrlValidator();
		assertFalse( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a invalid localhost
     * <p>
     * <b>Expected Result:</b>
     * Invalid (so assertFalse)
     */
	@Test
	public void test024_Localhost_NoOverride() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
//		String [] s = {"file", "localhost"};
//		long o = UrlValidator.ALLOW_LOCAL_URLS;
		String sUrl = "http://Localhost";
		
		UrlValidator v = new UrlValidator();
		assertFalse( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a valid localhost, overridden
     * <p>
     * <b>Expected Result:</b>
     * Valid (so assertTrue)
     */
	@Test
	public void test025_Localhost_Override() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
//		String [] s = {"file", "localhost"};
		long o = UrlValidator.ALLOW_LOCAL_URLS;
		String sUrl = "http://Localhost";
		
		UrlValidator v = new UrlValidator(o);
		assertTrue( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a valid IPv4
     * <p>
     * <b>Expected Result:</b>
     * Valid (so assertTrue)
     */
	@Test
	public void test026_IPv6_Valid() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
		String sUrl = "http://2001:0db8:0000:0000:0000:ff00:0042:8329";
		
		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a valid IPv4
     * <p>
     * <b>Expected Result:</b>
     * Valid (so assertTrue)
     */
	@Test
	public void test027_IPv6_Valid() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
		String sUrl = "http://2001:db8::ff00:42:8329";
		
		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a valid IPv4
     * <p>
     * <b>Expected Result:</b>
     * Valid (so assertTrue)
     */
	@Test
	public void test028_IPv6_Valid() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
		String sUrl = "http://::1";
		
		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a invalid IPv4
     * <p>
     * <b>Expected Result:</b>
     * Invalid (so assertFalse)
     */
	@Test
	public void test029_IPv6_InvalidContraction() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
		String sUrl = "http://2001:db8::ff00::42:8329";
		
		UrlValidator v = new UrlValidator();
		assertFalse( v.isValid(sUrl) );
	}
	
	/**
     * <b>Test Summary:</b>
     * Tests URL with a invalid path w/o override.
     * <p>
     * <b>Expected Result:</b>
     * Invalid (so assertFalse)
     */
	@Test
	public void test030_2Slash_AllowOff() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
		String sUrl = "Http://www.google.com/path//file";
		
		UrlValidator v = new UrlValidator();
		assertFalse( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a invalid path with override.
     * <p>
     * <b>Expected Result:</b>
     * Valid (so assertTrue)
     */
	@Test
	public void test031_2Slash_AllowOn() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
		long o = UrlValidator.ALLOW_2_SLASHES;
		String sUrl = "Http://www.google.com/path//file";
		
		UrlValidator v = new UrlValidator(o);
		assertTrue( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a valid path
     * <p>
     * <b>Expected Result:</b>
     * Valid (so assertTrue)
     */
	@Test
	public void test032_PathFileExtension() {
		// testPartition_Neg_VSchemes0_VAuthVal0_VOaas1_VOa2s0_VOnf0_VOalu0_NoScheme_AuthLC_NoUserPass_NoPort_NoPath_NoFile_NoQS_NoFrag
//		long o = UrlValidator.ALLOW_2_SLASHES;
		String sUrl = "Http://www.google.com/Path/File.txt";
		
		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a valid querystring
     * <p>
     * <b>Expected Result:</b>
     * Valid (so assertTrue)
     */
	@Test
	public void test033_Querystring() {
		String sUrl = "Http://www.google.com/Path/File.txt?";

		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a invalid querystring
     * <p>
     * <b>Expected Result:</b>
     * Invalid (so assertFalse)
     */
	@Test
	public void test034_Querystring() {
		String sUrl = "Http://www.google.com/Path/File.txt?key1";

		UrlValidator v = new UrlValidator();
		assertFalse( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a valid querystring
     * <p>
     * <b>Expected Result:</b>
     * Valid (so assertTrue)
     */
	@Test
	public void test035_Querystring() {
		String sUrl = "Http://www.google.com/Path/File.txt?key1=value1";

		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a valid querystring
     * <p>
     * <b>Expected Result:</b>
     * Valid (so assertTrue)
     */
	@Test
	public void test036_Querystring() {
		String sUrl = "Http://www.google.com/Path/File.txt?key1=value1&";

		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a invalid querystring
     * <p>
     * <b>Expected Result:</b>
     * Invalid (so assertFalse)
     */
	@Test
	public void test037_Querystring() {
		String sUrl = "Http://www.google.com/Path/File.txt?key1=value1&key2";

		UrlValidator v = new UrlValidator();
		assertFalse( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a valid querystring
     * <p>
     * <b>Expected Result:</b>
     * Valid (so assertTrue)
     */
	@Test
	public void test038_Querystring() {
		String sUrl = "Http://www.google.com/Path/File.txt?key1=value1&key2=value2";

		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a fragment
     * <p>
     * <b>Expected Result:</b>
     * Invalid (so assertFalse)
     */
	@Test
	public void test039_FragDisallowed() {
		long o = UrlValidator.NO_FRAGMENTS;
		String sUrl = "Http://www.google.com/Path/File.txt#";

		UrlValidator v = new UrlValidator(o);
		assertFalse( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a fragment
     * <p>
     * <b>Expected Result:</b>
     * Invalid (so assertFalse)
     */
	@Test
	public void test040_Querystring() {
		String sUrl = "Http://www.google.com/Path/File.txt#";

		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a fragment
     * <p>
     * <b>Expected Result:</b>
     * Invalid (so assertFalse)
     */
	@Test
	public void test041_Querystring() {
		long o = UrlValidator.NO_FRAGMENTS;
		String sUrl = "Http://www.google.com/Path/File.txt#frag";

		UrlValidator v = new UrlValidator(o);
		assertFalse( v.isValid(sUrl) );
	}
	
    /**
     * <b>Test Summary:</b>
     * Tests URL with a fragment
     * <p>
     * <b>Expected Result:</b>
     * Valid (so assertTrue)
     */
	@Test
	public void test042_Querystring() {
		String sUrl = "Http://www.google.com/Path/File.txt#frag";

		UrlValidator v = new UrlValidator();
		assertTrue( v.isValid(sUrl) );
	}		
}
