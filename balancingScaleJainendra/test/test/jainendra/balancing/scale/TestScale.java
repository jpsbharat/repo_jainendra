package test.jainendra.balancing.scale;

import java.util.Scanner;

import com.jainendra.balancing.scale.ScaleBalancer;

public class TestScale {

	public static void main(String[] args) {
		ScaleBalancer scaleBalancer = new ScaleBalancer(1, 0);
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String line = scanner.nextLine();
			if (line == null || "".equals(line)
					|| line.equalsIgnoreCase("exit")) {
				break;
			}

			if (line.startsWith("#")) {
				continue;
			}

			String[] scaleInfo = line.split(",");
			String scaleName = scaleInfo[0].trim();
			String scaleLeft = scaleInfo[1].trim();
			String scaleRight = scaleInfo[2].trim();
			scaleBalancer.addScale(scaleName, scaleLeft, scaleRight);
		}
		scanner.close();
		System.out.println(scaleBalancer.getBalancingInfo());
	}
}
