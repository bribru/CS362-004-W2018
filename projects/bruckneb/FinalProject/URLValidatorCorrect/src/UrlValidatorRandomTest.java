import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

// store an iteration, the random 

public class UrlValidatorRandomTest {
	private static final long TEST_ITERATIONS = 100;
	
	private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String lower = upper.toLowerCase(Locale.ROOT);
	private static final String digits = "0123456789";
	private static final String other = "~`!@#$%^&*()_-+={}[]\\\"\'|;:,<.>/?";
	private static final String alnum = upper + lower + digits;
	private static final String alnumother = upper + lower + digits + other;
    /**
     * Protocol scheme (e.g. http, ftp, https).
     */
    private static final String SCHEME_REGEX = "^\\p{Alpha}[\\p{Alnum}\\+\\-\\.]*";
    private static final Pattern SCHEME_PATTERN = Pattern.compile(SCHEME_REGEX);
    
    private static Set<String> allowedSchemes;
    private static final String[] DEFAULT_SCHEMES = {"http", "https", "ftp"}; // Must be lower-case
    
	public static void main(String [] args) {
		//UrlValidator v = new UrlValidator();
		// in each iteration, generate the variables for the string 
		// for this initial version, UrlValidator randomization is not present
		// for each randomized variable going into the string, validate it against 
		// a rule set for that element, which typically consists of:
		//   - format check
		//   - length check
		//   - range check (e.g., port, IPv4)
		// generally these rules do NOT consist of domain checks (e.g., 
		// given the amount of change to domains and the need to validate using
		// the DomainValidator class (which is a program under test), the domain
		// value will be set static for the initial implementation
		
		//assertTrue( v.isValid("http://www.google.com"));
		//
		// The general pattern for URLs is as follows:
		// scheme:[//[user[:password]@]host[:port]][/path][?query][#fragment]
		//
		// The UrlValidator constructor allows for three different parameters in any combination
		// schemes allows for schemes other than the three DEFAULT_SCHEMES ("http", "https", "ftp")
		// 
		//
		//scheme
		//authority
		//path
		
		int optScheme = 0, lenMinScheme = 0, lenMaxScheme = 9; // 0 to 9 bytes
		//List<String> uvScheme;
        List<String> uvSchemeSet = new ArrayList<String>();

		String strUrl, strScheme, strUserPass, strHost, strPort, strPath, strQuery, strFragment;
		RegexValidator rvAuthorityValidator = null;
		
		long uvoAllowAllSchemes, uvoAllow2Slashes, uvoNoFragments, uvoAllowLocalUrls, uvOptions;
		
		boolean valIsValid, valScheme, valAuthority, valPort, valPath, valQuery, valFragment;
		
		Random generator = new Random(1);
		Random genScheme = new Random(2);
		
		// for scheme:
		//   - randomly determine whether to include scheme (95% include / 5% exclude)
		//   - randomly generate a scheme
		//   - randomly determine whether schemes are provided to UrlValidator CTOR
		//   - randomly determine whether this scheme is in schemes (if provided to UrlValidator CTOR)
		for (int i=0; i<TEST_ITERATIONS; i++) {
			// TO DO: randomize the UrlValidator better
			// String[] schemes, RegexValidator authorityValidator, long options
			// e.g., need to randomize whether to allow all schemes

			// GENERATE URL-SCHEME ================================================
			// 
			optScheme = generator.nextInt(lenMaxScheme - lenMinScheme + 1) + lenMinScheme;
			switch (optScheme) {
				case 0:
					strScheme = "";
					break;
				case 1:
					strScheme = "http";
					break;
				case 2:
					strScheme = "https";
					break;
				case 3:
					strScheme = "ftp";
					break;
				case 4:
					strScheme = "file";
					break;
				default: // in case of 5+, just use the optScheme as length of the random scheme
					RandomString rsScheme = new RandomString(optScheme, genScheme);
					strScheme = rsScheme.nextString();
					rsScheme = null;
					break;
			}
			// GENERATE URL-AUTHORITY_USERPASS ================================
			// very simple and with one option for invalid characters
			if (generator.nextBoolean())
				strUserPass = "";  // ~50%
			else if (generator.nextBoolean())
				strUserPass = "user:password@";  // ~25%
			else if (generator.nextBoolean())
				strUserPass = "user@";  // ~12%
			else {
				RandomString rs = new RandomString(8, generator, alnum); // alnumother);
				strUserPass = rs.nextString() + ":" + rs.nextString() + "@";  // ~6.25%
			}
			// GENERATE URL-AUTHORITY_DOMAIN ==================================
			// very simple and with one option for invalid characters
			// TO DO: fix this so it can catch domain name length issues
			strHost = "";
			switch(generator.nextInt(2)) {
			case 0: // dns
				RandomString rs = new RandomString(5, generator, alnum);
				for (int iDomainElts=generator.nextInt(4)+1; iDomainElts>=0; iDomainElts--) {
					strHost += rs.nextString();
					if (iDomainElts > 0)
						strHost += ".";
				}
				break;
			case 1: // ipv4
				strHost = "128.0.0.1";
				break;
			case 2: // ipv6
				strHost = "01:01:01:01:01:01:01:01";
				break;
			}

			// GENERATE URL-PORT ==============================================
			if (generator.nextBoolean())
				strPort = "80";
			else
				strPort = "";
			
			// GENERATE URL-PATH ==============================================
			if (generator.nextBoolean())
				strPath = "test/test.htm";
			else
				strPath = "";
			
			// GENERATE URL-QUERY =============================================
			if (generator.nextBoolean())
				strQuery = "param1=val1&param2=val2";
			else
				strQuery = "";
			
			// GENERATE URL-FRAGMENT ==========================================
			if (generator.nextBoolean())
				strFragment = "test";
			else
				strFragment = "";
			
			strUrl = "";
			if (strScheme != "")
				strUrl = strScheme + "://";
			if (strUserPass != "")
				strUrl += strUserPass;
			strUrl += strHost;
			if (strPort != "")
				strUrl += ":" + strPort;
			if (strPath != "")
				strUrl += "/" + strPath;
			if (strQuery != "")
				strUrl += "?" + strQuery;
			if (strFragment != "")
				strUrl += "#" + strFragment;
			
			// DETERMINE URLVALIDATOR OPTIONS ==================================
			uvoAllowAllSchemes = generator.nextInt(2) << 0; // ALLOW_ALL_SCHEMES
			uvoAllow2Slashes = generator.nextInt(2) << 1; // ALLOW_2_SLASHES
			uvoNoFragments = generator.nextInt(2) << 2; // NO_FRAGMENTS
			uvoAllowLocalUrls = generator.nextInt(2) << 3; // ALLOW_LOCAL_URLS
			uvOptions = uvoAllowAllSchemes + uvoAllow2Slashes + uvoNoFragments + uvoAllowLocalUrls;
			
			// DETERMINE SCHEME-RELATED URLVALIDATOR-SCHEMES ===================
			// randomly determine whether to include scheme in UrlValidator CTOR
			uvSchemeSet.clear();
			if (generator.nextBoolean()) {
				// add a few schemes that don't match (>10 chars)
				// TO DO: randomize the inclusion (or lack thereof) of multiple schemes
				uvSchemeSet.add("longprotocol1");
				uvSchemeSet.add("longprotocol2");
				uvSchemeSet.add("longprotocol3");
				// randomly determine whether a scheme should be  included that matches 
				// the scheme of the URL being evaluated
				if (generator.nextBoolean()) {
					// add a matching scheme
					uvSchemeSet.add(strScheme);
				}	
			}
			// and convert this set into an array
			// convert uvSchemeSet List<String> to String []
			String[] uvSchemeArray = new String[uvSchemeSet.size()];
			uvSchemeArray = uvSchemeSet.toArray(uvSchemeArray);	
			
			
			// DETERMINE URLVALIDATOR REGEXVALIDATOR AUTHORITYVALIDATOR ========
			if (generator.nextBoolean())
				rvAuthorityValidator = new RegexValidator("[A-Za-z0-9.]+");
			else
				rvAuthorityValidator = null;

			// NOW TURN TO DETERMINING EXPECTED RESULTS ========================
			// VALIDATIONS / ORACLE ============================================

			// VALIDATE SCHEME ----------------------------------------------------------
			// setup allowedSchemes either empty, or
			if ((uvOptions & UrlValidator.ALLOW_ALL_SCHEMES) > 0) {
				allowedSchemes = Collections.emptySet();
	        } else {
	            if (uvSchemeSet.size() == 0) {
	                uvSchemeArray = DEFAULT_SCHEMES;
	            }
	            allowedSchemes = new HashSet<String>(uvSchemeArray.length);
	            for(int k=0; k < uvSchemeArray.length; k++) {
	                allowedSchemes.add(uvSchemeArray[k].toLowerCase(Locale.ENGLISH));
	            }
	        }
			
			if (strScheme == "") {
				valScheme = false;
			}
			else if (!SCHEME_PATTERN.matcher(strScheme).matches()) {
	        	valScheme = false;
	        }
			else if (((uvOptions & UrlValidator.ALLOW_ALL_SCHEMES) == 0) && !allowedSchemes.contains(strScheme.toLowerCase(Locale.ENGLISH))) {
	        	valScheme = false;
	        }
			else {
				valScheme = true;
			}
			
			// VALIDATE AUTHORITY -------------------------------------------------------
			// valAuthority
			
			// VALIDATE PORT ------------------------------------------------------------
			// valPort
			
			// VALIDATE PATH ------------------------------------------------------------
			// valPath
			
			// VALIDATE QUERY -----------------------------------------------------------
			// valQuery
			
			// VALIDATE FRAGMENT --------------------------------------------------------
			// valFragment			
			
			// AND FINALLY, INSTANTIATE THE VALIDATOR WITH APPROPRIATE OPTIONS
			// AND CALL ISVALID ================================================
			UrlValidator v = new UrlValidator(uvSchemeArray.length > 0 ? uvSchemeArray : null, 
					rvAuthorityValidator == null ? rvAuthorityValidator : null, uvOptions);
			valIsValid = v.isValid(strUrl);

			System.out.println("TC-" + i + ": strUrl = " + strUrl);
			for (String s : uvSchemeArray)
				System.out.println("  UrlValidator-schemes = " + s);
			System.out.println("  UrlValidator-authval = " + rvAuthorityValidator);
			System.out.println("  UrlValidator-options-0 (ALLOW_ALL_SCHEMES) = " + (uvOptions & (1 << 0)));
			System.out.println("  UrlValidator-options-1 (ALLOW_2_SLASHES)   = " + (uvOptions & (1 << 1)));
			System.out.println("  UrlValidator-options-2 (NO_FRAGMENTS)      = " + (uvOptions & (1 << 2)));
			System.out.println("  UrlValidator-options-3 (ALLOW_LOCAL_URLS)  = " + (uvOptions & (1 << 3)));
			System.out.println("  isValid = " + valIsValid);
			System.out.println("    valScheme = " + valScheme);
		}
		
	}
}
