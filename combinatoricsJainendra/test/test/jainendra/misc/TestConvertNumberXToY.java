package test.jainendra.misc;

import com.jainendra.misc.ConvertNumberXToY;
import com.jainendra.misc.ConvertNumberXToY.StepResult;

public class TestConvertNumberXToY {

	public static void main(String[] args) {
		StepResult sr = ConvertNumberXToY.getMinOperationToConvertNumberX2NumberY(3, 7);
		System.out.println(sr);
		sr = ConvertNumberXToY.getMinOperationToConvertNumberX2NumberY(4, 7);
		System.out.println(sr);
		sr = ConvertNumberXToY.getMinOperationToConvertNumberX2NumberY(5, 7);
		System.out.println(sr);
		sr = ConvertNumberXToY.getMinOperationToConvertNumberX2NumberY(4, 9);
		System.out.println(sr);
		sr = ConvertNumberXToY.getMinOperationToConvertNumberX2NumberY(4, 11);
		System.out.println(sr);
	}
}
