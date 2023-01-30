import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumberSecondary;

/**
 * {@code NaturalNumber} represented as a {@code String} with implementations of
 * primary methods.
 *
 * @convention
 * 
 *             <pre>
 * [all characters of $this.rep are '0' through '9']  and
 * [$this.rep does not start with '0']
 *             </pre>
 * 
 * @correspondence
 * 
 *                 <pre>
 * this = [if $this.rep = "" then 0
 *         else the decimal number whose ordinary depiction is $this.rep]
 *                 </pre>
 *
 * @author Junbo Chen, Brett Emory
 *
 */
public class NaturalNumber3 extends NaturalNumberSecondary {

	/*
	 * Private members --------------------------------------------------------
	 */

	/**
	 * Representation of {@code this}.
	 */
	private String rep;

	/**
	 * Creator of initial representation.
	 */
	private void createNewRep() {

		this.rep = "";

	}

	/*
	 * Constructors -----------------------------------------------------------
	 */

	/**
	 * No-argument constructor.
	 */
	public NaturalNumber3() {

		this.createNewRep();

	}

	/**
	 * Constructor from {@code int}.
	 *
	 * @param i {@code int} to initialize from
	 */
	public NaturalNumber3(int i) {
		assert i >= 0 : "Violation of: i >= 0";

		if (i != 0) {
			this.rep = Integer.toString(i);
		} else {
			// zero represents the empty string
			this.rep = "";
		}

	}

	/**
	 * Constructor from {@code String}.
	 *
	 * @param s {@code String} to initialize from
	 */
	public NaturalNumber3(String s) {
		assert s != null : "Violation of: s is not null";
		assert s.matches("0|[1-9]\\d*") : "" + "Violation of: there exists n: NATURAL (s = TO_STRING(n))";

		if (s != "0") {
			this.rep = s;
		} else {
			// zero represents the empty string
			this.rep = "";
		}

	}

	/**
	 * Constructor from {@code NaturalNumber}.
	 *
	 * @param n {@code NaturalNumber} to initialize from
	 */
	public NaturalNumber3(NaturalNumber n) {
		assert n != null : "Violation of: n is not null";

		if (n.isZero()) {
			this.rep = "";
		} else {
			this.rep = n.toString();
		}
	}

	/*
	 * Standard methods -------------------------------------------------------
	 */

	@Override
	public final NaturalNumber newInstance() {
		try {
			return this.getClass().getConstructor().newInstance();
		} catch (ReflectiveOperationException e) {
			throw new AssertionError("Cannot construct object of type " + this.getClass());
		}
	}

	@Override
	public final void clear() {
		this.createNewRep();
	}

	@Override
	public final void transferFrom(NaturalNumber source) {
		assert source != null : "Violation of: source is not null";
		assert source != this : "Violation of: source is not this";
		assert source instanceof NaturalNumber3 : "" + "Violation of: source is of dynamic type NaturalNumberExample";
		/*
		 * This cast cannot fail since the assert above would have stopped execution in
		 * that case.
		 */
		NaturalNumber3 localSource = (NaturalNumber3) source;
		this.rep = localSource.rep;
		localSource.createNewRep();
	}

	/*
	 * Kernel methods ---------------------------------------------------------
	 */

	@Override
	public final void multiplyBy10(int k) {
		assert 0 <= k : "Violation of: 0 <= k";
		assert k < RADIX : "Violation of: k < 10";

		//Add k onto the end of $this.rep
		this.rep= this.rep +k; 
		
		//Maintain workoing order for 0
		if( this.rep,equals("0"))
		    this.rep = "";
	}

	@Override
	public final int divideBy10() {

		// Create the remainder variable that will be returned
		int remainder = 0;

		// see if $this.rep is > 0
		// if it is, use substrings to get the remainder
		// update $this.rep using substrings - the remainder
		// return the remainder.

		if (this.rep.length() > 0) {

			// remainder
			String lastDigit = this.rep.substring(this.rep.length() - 1);

			// update
			this.rep = this.rep.substring(0, this.rep.length() - 1);

			// convert remainder to int
			remainder = Integer.parseInt(lastDigit);
		}

		return remainder;
	}

	@Override
	public final boolean isZero() {

		// "" will correspond to 0 for this representation

		// This line will return a boolean
		return (this.rep.length() == 0);
	}

}
