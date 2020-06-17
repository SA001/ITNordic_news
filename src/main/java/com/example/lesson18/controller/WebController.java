package com.example.lesson18.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.lesson18.dto.RequestFormPassword;
import com.example.lesson18.model.BlogList;
import com.example.lesson18.model.Cat;
import com.example.lesson18.model.LentaHeadlines;
import com.example.lesson18.model.LentaNews;
import com.example.lesson18.model.User;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class WebController {

	private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>() {{
		put("vasya", new User("vasya","test1234", 20));
		put("sa", new User("sa"," ", 14));
	}};
		
		private ArrayList<BlogList> blogList;
		
		/*public BlogsStorage() {
			this.blogsList = new ArrayList<BlogItem>() {
				{
					add(new BlogList("Было бы неплохо сейчас махнуть куда-нибудь подальше.",
							 "admin@admin.com"));
					add(new BlogList(
							"Основатель Telegram Павел Дуров в своем официальном телеграм-канале объявил о прекращении работы над блокчейн-проектом TON."
									+ " Причина — решение суда по иску Комиссии"
									+ " по ценным бумагам и биржам США (SEC), запретившего выпуск"
									+ " криптовалюты Gram. Решение суда Дуров назвал несправедливым и"
									+ " предупредил мир о зависимости от США, которые могут закрыть"
									+ " любой банк или банковский счет в мире.",
						  "admin@admin.com"));
				}
			};
		}*/
		
		public void addBlogItem(BlogList blogItem) {
			this.blogList.add(blogItem);
		}

	}
	
	
	
	
	@GetMapping("/welcome")
	public String helloPage(Model model, @RequestParam(name="name", required = false)String name){
		Cat cat = new Cat("Мурзик", 5, "рыжий");
		
		String[] names = new String[] {"vasya", "petya"};
		model.addAttribute("names", names);
		return "welcome.html";
	};
	
	@GetMapping("/login")
	public String loginPage(Model model){
		model.addAttribute("form", new RequestFormPassword());
		return "login.html";
	}
	
	@PostMapping("/login")
	public String loginHandlerPage(Model model, @ModelAttribute RequestFormPassword form){
//	System.out.println(form);
//	model.addAttribute("form", new RequestFormPassword());
//	return "login.html";
		if (!users.containsKey(form.getLogin())) {
			model.addAttribute("error", true);
			model.addAttribute("form", new RequestFormPassword());
			return "login.html";
		}
		else if(!users.get(form.getLogin()).getPassword().equals(form.getPassword())) {
			model.addAttribute("error", true);
			model.addAttribute("form", new RequestFormPassword());
			return "login.html";	
		}
		else {		
			model.addAttribute("user", users.get(form.getLogin()));
			model.addAttribute("form", new RequestFormPassword());
			return "blog.html";
		}
		
		
	}	
	
	@GetMapping("/news.html")
	public String newsPage(Model model) throws Exception {

		var httpClient = HttpClient.newHttpClient();
		
		var httpRequest = HttpRequest.newBuilder()
				.GET()
				.uri(new URI("https://api.lenta.ru/lists/latest"))
				.build();
		
		var response = httpClient.send(httpRequest,  HttpResponse.BodyHandlers.ofString());		
		
		var objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
		
		var lenta = objectMapper.readValue(response.body(), LentaHeadlines.class);
		
		model.addAttribute("news", lenta.getHeadlines());
		
		return "news";
	}
	
	@GetMapping("/blog.html")
	public String blogPage(Model model) throws Exception {

		var httpClient = HttpClient.newHttpClient();
		
		var httpRequest = HttpRequest.newBuilder()
				.GET()
				.uri(new URI("https://api.lenta.ru/lists/latest"))
				.build();
		
		var response = httpClient.send(httpRequest,  HttpResponse.BodyHandlers.ofString());		
		
		var objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
		
		var lenta = objectMapper.readValue(response.body(), LentaHeadlines.class);
		
		model.addAttribute("news", lenta.getHeadlines());
		
		return "news";
	}
	
	@GetMapping("/blogs")
	public ModelAndView blogs() {
		var modelAndView = new ModelAndView();
		modelAndView.setViewName("views/blogs");
		//modelAndView.addObject("blogList", blogList.);
		//modelAndView.addObject("user", usersStorage.getUsers());

		return modelAndView;
	}
}
