package test;

import com.webnobis.mastermind.model.Source;

public class GenericMain {

	public static void main(String[] args) {
		Source<Boolean> s = Source.of(null, Boolean.TRUE);
		
		System.out.println(s.getSources().stream().findAny().map(t -> t.getClass()).orElseThrow());
	}
	
	
	

}
