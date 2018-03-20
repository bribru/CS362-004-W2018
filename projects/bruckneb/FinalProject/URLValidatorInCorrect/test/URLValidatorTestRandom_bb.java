

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import junit.framework.TestCase;

public class URLValidatorTestRandom extends TestCase {

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
}
