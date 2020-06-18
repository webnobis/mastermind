package test;

import java.util.Objects;

import com.webnobis.mastermind.model.Source;

public class GenericMain {

	public static void main(String[] args) {
		Source<Boolean> s = Source.of(null, Boolean.TRUE);
		
		System.out.println(s.getSources().stream().filter(Objects::nonNull).findAny().map(Object::getClass).orElseThrow());
	}
	
	
	

}
