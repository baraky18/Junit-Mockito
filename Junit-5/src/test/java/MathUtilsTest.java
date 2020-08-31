import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestReporter;

/*
 * few important facts and practices about Junit:
 * 1. by default, Junit creates a new instance of the class before each method runs (it can be annotated with @TestInstance(Lifecycle.PER_METHOD),
 *    but it's not mandatory). this is to prevent us from creating a class member variable and change its value inside some methods.
 * 2. if the class is annotated with @TestInstance(Lifecycle.PER_CLASS), methods annotated with @BeforeAll and @AfterAll 
 *    don't have to be static since an instance of the class is created before those methods are executed
 * 3. methods annotated with @Test is going to be run with no particular order. this is another reason why we shouldn't create a member variable
 *    and use it in different methods that's not annotated with @BeforeAll, @AfterAll
 * 4. A good practice is that tests should not be depended on each other nor on class member variables.
 * 5. A bad practice is that a class is annotated with @TestInstance(Lifecycle.PER_CLASS), 
 *    then only one instance of the class will be created per all of the tests
 * 
 * conditional execution annotations:
 * we can run several tests conditionally by using the following annotations: @EnableOnOs(OS.LINUX), @EnabledOnJre(JRE>JAVA_11), @EnabledIf,
 * @EnabledIfSystemProperty, @EnabledIfEnvironmentVariable
 */
@TestInstance(Lifecycle.PER_METHOD)
public class MathUtilsTest {

	private MathUtils mathUtils;
	private TestInfo testInfo; 
	private TestReporter testReporter;
	
	/*
	 * code annotated with @BeforeAll and @AfterAll will run even before the a new instance of the class test is created.
	 * therefore, we must use this on static methods only to prevent a situation when we are referring to the class instance
	 */
	@BeforeAll
	public static void beforeAllInit() {
		System.out.println("This is running before all");
	}
	
	/*
	 * code annotated with @BeforeEach will run before each method. this is helping us prevent initialization of the MathUtils class 
	 * in every method. TestInfo and TestReporter are injected class of JUnit (we don't have to include them in the method signature),
	 * they allow us to log in a better way.
	 */
	@BeforeEach
	public void init(TestInfo testInfo, TestReporter testReporter) {
		this.mathUtils = new MathUtils();
		this.testReporter = testReporter;
		this.testInfo = testInfo;
		System.out.println("This is running before each test. the test diplay name is: " + testInfo.getDisplayName());
		testReporter.publishEntry("123");
	}
	
	/*
	 * @DisplayName is to display the string inside the annotation in the Junit console instead of the method name.
	 * "assumeTrue" method, assumes that a value is true and only if it's true, executes the test, if not, test is aborted (but not failed)
	 */
	@Test
	@DisplayName("Adding two numbers")
	public void testAdd(){
		int expected = 2;
		int actual = mathUtils.add(1, 1);
		assertEquals(expected, actual, "The add method should add two numbers");
	}
	
	/*
	 * "assertAll" method asserts multiple lambdas at the same time. if one of them fails, the test failes
	 */
	@Test
	@DisplayName("Adding two numbers many times")
	public void testManyAdd(){
		assertAll(
				() -> assertEquals(4, mathUtils.add(3, 1)),
				() -> assertEquals(4, mathUtils.add(4, 0)),
				() -> assertEquals(4, mathUtils.add(5, -1))
				);
	}
	
	/*
	 * @Nested is to group tests together by a certain subject.
	 */
	@Nested
	@DisplayName("Multiply method")
	class MultiplyTest{
		
		@Test
		@DisplayName("Multiply by zero")
		public void multiplyByZero() {
			assertEquals(0, mathUtils.multiply(0, 1));
		}
		
		@Test
		@DisplayName("Multiply by negative number")
		public void multiplyByNegativeNumber() {
			assertEquals(-2, mathUtils.multiply(-1, 2));
		}
	}
	
	/*
	 * assertEquals method allows us to do lazy loading of the String that's being thrown when the test fails - meaning that
	 * the String is created only when the test fails and not every time. we do it by passing a lambda and not a String.
	 * basically, we do it when the creation of the String is expensive.
	 */
	@Test
	public void multiplyTest() {
		int expected = -2;
		int actual = mathUtils.multiply(-1, 2);
		assertEquals(expected, actual, () -> "The multiply method should return " + expected + " but returned " + actual);
	}
	
	/*
	 * @RepeatedTest is for a case that I want to repeat the test several times for some reason (maybe to use Math.random() inside).
	 * I can leave the signature of the method with no parameters, yet I can also have RepetitionInfo parameter in the method
	 * and thus, consider the repetition information inside the method
	 */
	@RepeatedTest(3)
	public void multiplyRepeatedTest(RepetitionInfo repetitionInfo) {
		if(repetitionInfo.getCurrentRepetition() == 1) {
			// do something
		}
		assertEquals(-2, mathUtils.multiply(-1, 2));
	}
	
	/*
	 * "assumeTrue" method, assumes that a value is true and only if it's true, executes the test, if not, test is aborted (but not failed)
	 */
	@Test
	public void verifyBooleanIsTrueAndRunAdd(){
		assumeTrue(mathUtils.getExample());
		int expected = 3;
		int actual = mathUtils.add(2, 1);
		assertEquals(expected, actual, "The add method should add two numbers");
	}
	
	@Test
	public void testComputeCircleArea() {
		assertEquals(314.1592653589793, mathUtils.computeCircleArea(10), "Should return right circle area");
	}
	
	@Test
	public void testDivide(){
		assertThrows(ArithmeticException.class, () -> mathUtils.divide(1, 0), "Divide by zero");
	}
	
	/*
	 * when coding in TDD (test driven design) we are actually writing the tests prior to the actual methods.
	 * in that case we are going to have many failed tests (before the method is even written). the way to tell Junit
	 * that those tests shouldn't run is to use the @Disabled annotation - Junit will skip those tests
	 */
	@Test
	@Disabled
	public void failTest() {
		fail();
	}
	
	/*
	 * we can tag certain tests by using @Tag. this allows us to run only tests that are tagged with a certain tag.
	 * we do that by creating a new run configuration in eclipse -> include and exclude tags -> marking the include tags ->
	 * putting the tags to include
	 */
	@Test
	@Tag("Prod")
	@DisplayName("Test add with tag production tests")
	public void testAddWithTagProductionTests(){
		assertEquals(2, mathUtils.add(1, 1), "The add method should add two numbers");
	}
	
	@Test
	@Tag("Dev")
	@DisplayName("Test add with tag Dev tests")
	public void testAddWithTagDevTests(){
		assertEquals(2, mathUtils.add(1, 1), "The add method should add two numbers");
	}
}
