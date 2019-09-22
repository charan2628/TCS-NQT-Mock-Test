import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.StringWriter;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestQuestionNo1 {
	
	@DataProvider(name = "TEST_CASES", parallel = false)
	public Object[][] testCases() {
		return new Object[][] {
			{"testCase1.txt", "testCase1Sol.txt"},
			{"testCase2.txt", "testCase2Sol.txt"},
			{"testCase3.txt", "testCase3Sol.txt"}
		};
	}

	@Test(dataProvider = "TEST_CASES")
	public void test(String inputFile, String solutionFile) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(TestQuestionNo1.class.getResourceAsStream(solutionFile)));
		StringWriter writer = new StringWriter();

		QuestionNo1.reader = new BufferedReader(new InputStreamReader(TestQuestionNo1.class.getResourceAsStream(inputFile)));
		QuestionNo1.writer = new BufferedWriter(writer);
		
		long start = System.currentTimeMillis();
		QuestionNo1.main(null);
		long end = System.currentTimeMillis();
		Reporter.log(String.format("Test case: %s took %d ms", inputFile, (end-start)));
		
		Assert.assertEquals(writer.toString(), reader.readLine());
	}
}
