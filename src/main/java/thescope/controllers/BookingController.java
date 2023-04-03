package thescope.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import thescope.services.BookingService;

@Controller
public class BookingController {
	
	private final BookingService bookingService;

	@Autowired
	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@GetMapping("/bookings") // get request
	public String selectGet(Model model) {

		model.addAttribute("content", "bookings");
		//	model.addAttribute("bookings",bookings);  // map content to html elements
		return "index";
	}
}
