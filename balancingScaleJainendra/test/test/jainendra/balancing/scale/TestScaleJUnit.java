package test.jainendra.balancing.scale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jainendra.balancing.scale.ScaleBalancer;

public class TestScaleJUnit {
    private List<String> input = new ArrayList<String>();
    private List<String> output = new ArrayList<String>();

	@Before
	public void setUp() throws Exception {
		input.add("B1,10,B2");
		input.add("B2,B3,4");
		input.add("B3,7,8");

		output.add("B1,25,0");
		output.add("B2,0,13");
		output.add("B3,1,0");
	}

	@Test
	public void test() {
		ScaleBalancer scaleBalancer = new ScaleBalancer(1, 0);
		for(String str : input){
			String[] scaleInfo = str.split(",");
			String scaleName = scaleInfo[0].trim();
			String scaleLeft = scaleInfo[1].trim();
			String scaleRight = scaleInfo[2].trim();
			scaleBalancer.addScale(scaleName, scaleLeft, scaleRight);
		}
		
		List<String> result = scaleBalancer.getBalancingInfoList();
		String[] expecteds = new String[output.size()];
		String[] actuals = new String[result.size()];
		output.toArray(expecteds);
		result.toArray(actuals);
		System.out.println("Expected Output:");
		System.out.println(Arrays.toString(expecteds));
		System.out.println("Actual Output:");
		System.out.println(Arrays.toString(actuals));
		Assert.assertArrayEquals(expecteds, actuals);
	}
}
