package net.gradle.com.springbootexpample.controller;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import net.gradle.com.springbootexpample.model.Author;
import net.gradle.com.springbootexpample.model.Books;
import net.gradle.com.springbootexpample.model.BooksAuthor;
import net.gradle.com.springbootexpample.model.BooksFilter;
import net.gradle.com.springbootexpample.repository.BookAuthorsRepository;
import net.gradle.com.springbootexpample.service.AuthorService;
import net.gradle.com.springbootexpample.service.BooksService;
import net.gradle.com.springbootexpample.service.SecurityService;
import net.gradle.com.springbootexpample.service.UserService;

@RestController
@RequestMapping("")
public class LibraryController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private AuthorService authorService;
    
    @Autowired
    private BookAuthorsRepository repository;
    
    @Autowired
    private BooksService booksService;

    @GetMapping("/add")
	public ModelAndView showCreateForm(Books book) {
    	ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("authors", authorService.findAllAuthors());
		modelAndView.setViewName("add-book");
	    return modelAndView;
	}

	@RequestMapping("/add-book")
	public ModelAndView createBook(Books book, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView();
		if (result.hasErrors()) {
			modelAndView.setViewName("add-book");
		    return modelAndView;
		}
		booksService.createBook(book);
		return new ModelAndView("redirect:" + "/list-books");
	}
	
	@GetMapping("/author/add")
	public ModelAndView showAuthorCreateForm(Author author) {
    	ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("add-author");
	    return modelAndView;
	}

	@RequestMapping("/add-author")
	public ModelAndView createAuthor(Author author, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if (result.hasErrors()) {
            modelAndView.setViewName("add-author");
            return modelAndView;
        }
        authorService.createAuthor(author);
        return new ModelAndView("redirect:" + "/list-authors");
	}
	
	@RequestMapping("/list-books")
	public ModelAndView showlistbookform(Author author) {
    	ModelAndView modelAndView = new ModelAndView();
    	List<Books> books = booksService.findAllBooks();
    	modelAndView.addObject("books", books);
		modelAndView.setViewName("list-books");
	    return modelAndView;
	}
	
	@RequestMapping("/list-authors")
	public ModelAndView showlistauthorform(Author author) {
    	ModelAndView modelAndView = new ModelAndView();
    	List<Author> authors = authorService.findAllAuthors();
    	modelAndView.addObject("authors", authors);
		modelAndView.setViewName("list-authors");
	    return modelAndView;
	}
	
	@RequestMapping("/search-books")
	public ModelAndView getBooksList(Author author) {
    	ModelAndView modelAndView = new ModelAndView();
    	List<Author> authors = authorService.findAllAuthors();
    	modelAndView.addObject("authors", authors);
		modelAndView.setViewName("list-authors");
	    return modelAndView;
	}
	
	@RequestMapping(value = "/book/fetch", method = RequestMethod.POST)
	protected List<Books> getBooksList(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody BooksFilter filter) throws Exception {
		if(filter.getAuthornames() != null) {
			List<Author> authors = authorService.findAuthor(filter.getAuthornames());
			List<Long> ids = authors.stream().map(Author::getId).collect(Collectors.toList());
			List<Long> bookingIds = repository.findAll().stream().filter(data -> ids.contains(data.getAuthor_id())).collect(Collectors.toList()).stream()
					.map(BooksAuthor::getBook_id).collect(Collectors.toList());
			filter.setIds(bookingIds);
 		}
		List<Books> books = booksService.findBooks(filter);
		return books;
	}

    private boolean deteremineAuthorities() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ServletRequestAttributes attr = (ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        boolean isUser = false;
        boolean isAdmin = false;
        final Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("INTERN")) {
                isUser = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ADMIN")) {
                isAdmin = true;
                break;
            }
        }
        return isAdmin;
    }

//
//    @PostMapping("/employee")
//    public Employee createEmployee(@Valid @RequestBody Employee employee) {
//        Employee employee1 = employeeRepository.save(employee);
//        setASDClientEmployee(employee1);
//        return employee1;
//    }

//    @GetMapping("/employee/{id}")
//    public Employee getEmployeeById(@PathVariable(value = "id") Long employeeId) {
//        Key key = new Key("test", "employee", employeeId);
//        Record record = asdClient.get(policy, key);
//        Employee employee1 = new Employee();
//        System.out.println(asdClient.get(policy, key));
//        if(record!= null && record.getInt("id")!= 0) {
//            employee1.setId(record.getInt("id"));
//            employee1.setEmailId(record.getString("lastName"));
//            employee1.setLastName(record.getString("firstName"));
//            employee1.setFirstName(record.getString("emailId"));
//            return employee1;
//        } else {
//            Optional<Employee> employee = employeeRepository.findById(employeeId);
//            Employee employeeDetails = employee.get();
//            setASDClientEmployee(employeeDetails);
//            return employeeDetails;
//        }
//    }
//
//    private void setASDClientEmployee(Employee employee) {
//        Key key = new Key("test", "employee", employee.getId());
//        Bin Id = new Bin("id", employee.getId());
//        Bin lastName = new Bin("lastName", employee.getLastName());
//        Bin firstName = new Bin("firstName", employee.getFirstName());
//        Bin emailId = new Bin("emailId", employee.getEmailId());
//        asdClient.put(policy, key, Id, lastName, firstName, emailId);
//        System.out.println(asdClient.get(policy, key));
//    }
//
//    @PutMapping("/employee/{id}")
//    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
//                                                   @Valid @RequestBody Employee employeeDetails){
//        Optional<Employee> employee = employeeRepository.findById(employeeId);
//        Employee employee1 = employee.get();
//        employee1.setEmailId(employeeDetails.getEmailId());
//        employee1.setLastName(employeeDetails.getLastName());
//        employee1.setFirstName(employeeDetails.getFirstName());
//        setASDClientEmployee(employee1);
//        final Employee updatedEmployee = employeeRepository.save(employee1);
//        return ResponseEntity.ok(updatedEmployee);
//    }
//
//    @DeleteMapping("/employee/{id}")
//    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId) {
//        Key key = new Key("test", "employee", employeeId);
//        Record record = asdClient.get(policy, key);
//        if(record!= null && record.getInt("id") != 0) {
//            asdClient.delete(policy, key);
//        }
//        Optional<Employee> employee = employeeRepository.findById(employeeId);
//        Employee employee1 = employee.get();
//        employeeRepository.delete(employee1);
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("deleted", Boolean.TRUE);
//        return response;
//    }
}