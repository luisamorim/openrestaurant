package br.com.amorim;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@Api
public class MenuController {

	private static MenuItems menuItems = new MenuItems();
	
	static {
		List<MenuItem> items = new ArrayList<MenuItem>();
		
		items.add(new MenuItem(1, "Spaghetti and Meatballs",
				"Seasoned meatballs on top of freshly-made spaghetti. Served with a robust tomato sauce.", 9.0,
				"entrees", "https://cdn.pixabay.com/photo/2014/10/12/17/14/spaghetti-485759_1280.jpg"));
		
		items.add(new MenuItem(2, "Margherita Pizza",
				"Tomato sauce, fresh mozzarella, basil, and extra-virgin olive oil.", 10.0, "entrees",
				"https://cdn.pixabay.com/photo/2015/10/17/20/22/margherita-pizza-993274_1280.jpg"));
		
		items.add(new MenuItem(3, "Grilled Steelhead Trout Sandwich",
				"Pacific steelhead trout* with lettuce, tomato, and red onion.", 9.0, "entrees",
				"https://cdn.pixabay.com/photo/2018/01/09/00/37/food-3070610_1280.jpg"));
		
		items.add(new MenuItem(4, "Pesto Linguini",
				" sliced beef with yellow onions and garlic in a vinegar-soy sauce. Served with steamed jasmine rice and sautéed vegetables.",
				9.0, "entrees", "https://cdn.pixabay.com/photo/2017/03/23/19/57/asparagus-2169305_1280.jpg"));
		
		
		items.add(new MenuItem(5, "Chicken Noodle Soup",
				"Delicious chicken simmered alongside yellow onions, carrots, celery, and bay leaves, chicken stock.",
				3.0, "appetizers", "https://cdn.pixabay.com/photo/2016/07/28/19/53/food-1549260_1280.jpg"));
		
		
		items.add(new MenuItem(6, "Italian Salad",
				"Garlic, red onions, tomatoes, mushrooms, and olives on top of romaine lettuce.", 5.0, "appetizers",
				"https://cdn.pixabay.com/photo/2018/01/12/10/19/basil-3077929_1280.jpg"));
		
		menuItems.setItems(items);
	}
	
	
	@RequestMapping(path="/categories",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResultForCategories> fetchCategories(){
		
		ResultForCategories categories = new ResultForCategories();
		List<String> categoryList = new ArrayList<String>();
		categoryList.add("entrees");
		categoryList.add("appetizers");
		categories.setCategories(categoryList);
		
		ResponseEntity<ResultForCategories> response = 
				new ResponseEntity<ResultForCategories>(categories,HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(path="/menu",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<MenuItems> fetchMenuItems(@RequestParam String category){
		System.out.println("call GET - menu");
		System.out.println("category: " + category);
		List<MenuItem> filter = new ArrayList<MenuItem>();
		for (MenuItem item : MenuController.menuItems.getItems()) {
			if(item.getCategory().equals(category)){
				filter.add(item);
			}
		}
		MenuItems menuItems = new MenuItems();
		menuItems.setItems(filter);
		return new ResponseEntity<MenuItems>(menuItems,HttpStatus.OK);
		
	}
	
	@RequestMapping(path="/order",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PreparationTime> submitOrder(@RequestBody OrderResultObject menuIds){
		System.out.println("call POST - order");
		System.out.println(menuIds);
		for (Integer id : menuIds.getMenuIds()) {
			System.out.println("id: " + id);
		}
		PreparationTime prepTime = new PreparationTime();
		prepTime.setPrepTime(1000);
		return new ResponseEntity<PreparationTime>(prepTime,HttpStatus.OK);
	}
}

class PreparationTime {
	
	private Integer prepTime;
	
	public PreparationTime() {
		// TODO Auto-generated constructor stub
	}

	public PreparationTime(Integer prepTime) {
		super();
		this.prepTime = prepTime;
	}

	public Integer getPrepTime() {
		return prepTime;
	}

	public void setPrepTime(Integer prepTime) {
		this.prepTime = prepTime;
	}
	
}

class MenuItems {
	private List<MenuItem> items;

	public MenuItems() {
		// TODO Auto-generated constructor stub
	}
	
	public MenuItems(List<MenuItem> items) {
		super();
		this.items = items;
	}

	public List<MenuItem> getItems() {
		return items;
	}

	public void setItems(List<MenuItem> items) {
		this.items = items;
	}
	
	
}

class MenuItem {
	
	private Integer id;
	private String name;
	private String description;
	private Double price;
	private String category;
	private String imageName;
	
	
	public MenuItem() {
		
	}
	
	public MenuItem(Integer id, String name, String description, Double price, String category, String imageName) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
		this.imageName = imageName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
}


class ResultForCategories {
	
	public List<String> categories;

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	
	
}

class OrderResultObject {
	
	private List<Integer> menuIds;

	public List<Integer> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(List<Integer> menuIds) {
		this.menuIds = menuIds;
	}
	
	
}