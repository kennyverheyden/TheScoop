package thescope.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import thescope.models.Movie;
import thescope.processors.LoginProcessor;
import thescope.services.BookingService;
import thescope.services.MovieService;
import thescope.services.UserService;

@Controller
public class HomeController {

	@Autowired
	private MovieService movieService;
	@Autowired
	private UserService userService;
	@Autowired
	private LoginProcessor loginProcessor; // Auto login for development
	@Autowired
	private BookingService bookingService; // When the customer logged out, erase last booking (scheduleID)

	public HomeController() {}

	// **** AUTO LOGIN ADMIN account ****
	boolean doAutoTestLogin(boolean on)
	{
		if(on && userService.getUserName()==null && userService.getSecret()==null){
			loginProcessor.setUserName("admin@thescope.com");
			//loginProcessor.setUserName("customer@thescope.com");
			loginProcessor.setSecret("test");
			if(loginProcessor.login())
			{
				System.out.println("AUTOLOGON ADMIN = activated");
			}
			return true; }
		return false;
	}

	@GetMapping("/") // get request
	public String selectGet(Model model) {

		// ***** AUTO LOGIN ADMIN account *****
		// ***** Development mode *************

		if(doAutoTestLogin(false)) // <-- TRUE or FALSE
			return "redirect:/";

		// ***********************************

		
		// When the customer logged out, erase last booking (scheduleID)
		bookingService.setBookedSchedule(null);
		
		model.addAttribute("content", "home"); // redirect to movie view (home.html)
		return "index";
	}

	@PostMapping("/home/findMovie") 
	public String findMovie(@RequestParam (required = false) String searchString, Model model, RedirectAttributes rm){

		List<Movie> movies=movieService.findMoviesByGenre(searchString);
		if(movies.isEmpty()) // Search movie titles when the user did not search on genre
		{
			System.out.println(searchString);
			movies = movieService.findMoviesByTitle(searchString);
		}

		model.addAttribute("content", "home");
		if(!movies.isEmpty()) {
			model.addAttribute("title", "Your search result for: " +searchString);
		}
		model.addAttribute("movies",movies);  // map content to html elements
		return "index";
	}
}
