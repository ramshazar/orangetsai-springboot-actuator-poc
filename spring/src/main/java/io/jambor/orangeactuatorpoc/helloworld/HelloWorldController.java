package io.jambor.orangeactuatorpoc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@RequestMapping("/api/hello")
		public ResponseEntity answer(){

			return new ResponseEntity<>("Hello World.", HttpStatus.OK);
		}
	@RequestMapping("/api/directory/hello")
		public ResponseEntity answer2(){

			return new ResponseEntity<>("Hello World, but in a directory", HttpStatus.OK);
		}
}
