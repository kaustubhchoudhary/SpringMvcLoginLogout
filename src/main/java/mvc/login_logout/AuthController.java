package mvc.login_logout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import mvc.login_logout.dao.User;
import mvc.login_logout.dao.UserDao;

@Controller
public class AuthController {

	@Autowired
	UserDao userDao;

	@GetMapping(value = { "/", "/openHomePage" })
	public String openIndexPage() {
		return "index";
	}

	@GetMapping("/openLoginPage")
	public String openLoginPage() {
		return "login";
	}
	
	@GetMapping("/openRegistrationPage")
	public String openRegistrationPage() {
		return "registration";
	}

	@PostMapping("/register")
	public String register(
			@ModelAttribute("user") User user, 
			Model model) {

		int result = userDao.registerUser(user);

		if (result == 1) {
			model.addAttribute("result", "Registered successfully. Login to continue");
			return "login";
		} else {
			model.addAttribute("result", "Registration failed. Try again");
			return "registration";
		}
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("user") User user, Model model, HttpServletRequest request) {
		
		System.out.println(user);
		
		try {
			User registeredUser = userDao.isUserRegistered(user.getUsername(), user.getPassword());

			if (registeredUser != null) {
				HttpSession session = request.getSession();
				session.setAttribute("user", registeredUser);
				
				System.out.println(session.getAttribute("user"));
				
				return "redirect:dashboard";
			} else {
				
				model.addAttribute("result", "Can't find credentials");
				return "login";
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			model.addAttribute("result", "Can't find credentials");
			return "index";
		}
	}

	@GetMapping("/dashboard")
	public String showDashboard(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");

		if (user != null) {
			model.addAttribute("user", user);
			return "dashboard";
		} else {
			return "redirect:openLoginPage";
		}
	}
	
	@GetMapping("/profile")
	public String showProfile(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");

		if (user != null) {
			model.addAttribute("user", user);
			return "profile";
		} else {
			return "redirect:openLoginPage";
		}
	}
	
	

	@GetMapping("/logout")
	public String processLogout(HttpSession session, 
			Model attr) {

		System.out.println(session.getAttribute("user"));
		session.invalidate();
		attr.addAttribute("result", "Logged out successfully");
		return "login";
	}

}
