
public class MathUtils {

	private boolean example = false;
	
	public int add(int a, int b) {
		return a+b;
	}
	
	public double computeCircleArea(double radius) {
		return Math.PI * radius * radius;
	}
	
	public int divide(int a, int b) {
		return a/b;
	}
	
	public int multiply(int a, int b) {
		return a*b;
	}
	
	public boolean getExample() {
		return this.example;
	}
}
