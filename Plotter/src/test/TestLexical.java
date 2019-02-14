package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ast.Function;
import syntax.Parser;
import syntax.Parser.ParseException;
import syntax.TokenScanner;

public class TestLexical {

	private TokenScanner tc;
	private Parser p;

	@Before
	public void setUp() throws Exception {
		tc = new TokenScanner();
		p = new Parser();
	}

	@After
	public void tearDown() throws Exception {
		tc = null;
		p = null;
	}

	@Test
	public void testExpression() {
		List<String> res = null;

		String s1 = "1/(e^(0.15*x))*sin(x)";
		try {
			res = tc.scan(s1);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Lexical analysis failed");
		}
		Function f=null;
		
		for(String s:res) {
			System.out.println(s);
		}
		
		try {
			f=p.parse(res);
		} catch (ParseException e) {
			e.printStackTrace();
			fail("Syntax failed");
		}
		
		System.out.println(f.toString());
		System.out.println("-------");
		List<String> errors=f.collectErrors();
		for(String s:errors) {
			System.out.println(s);
		}
		System.out.println(f.value(3));
		
	}

}
