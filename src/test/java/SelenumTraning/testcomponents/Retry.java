package SelenumTraning.testcomponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer{

	int count=0;
	int maxTime=0;
	@Override
	public boolean retry(ITestResult result) 
	{
		if(count<maxTime)
		{
			count++;
			return true;
		}
		return false;
	}

}
