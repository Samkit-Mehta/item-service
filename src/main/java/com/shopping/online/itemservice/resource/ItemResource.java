package com.shopping.online.itemservice.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.shopping.online.itemservice.model.Item;
import com.shopping.online.itemservice.repository.ItemRepository;

@RestController
@RequestMapping("/itemservice")
public class ItemResource {
	
	@Autowired
	private RestTemplate restTemplate;

	private ItemRepository itemRepository;
	
	public ItemResource(ItemRepository itemRepository){
		this.itemRepository = itemRepository;
	}
	
	@GetMapping("/items")
	public List<Item> getItems(){
		return itemRepository.findAll();	
	}
	
	@PostMapping("/items")
	public Item addItem(@RequestBody Item item){
		return itemRepository.save(item);	
	}
	
	@GetMapping("/items/{itemName}")
	public List<Item> getItem(@PathVariable("itemName") String itemName){
		 return itemRepository.findByName(itemName);
	}
	
	@PutMapping("items/{id}")
	public String updateItem(@RequestBody Item newItem,@PathVariable("id") Integer id) {
		Optional<Item> items = itemRepository.findById(id);
	    if (items != null) {
	    	Item item = items.get();
	    	item.setName(newItem.getName());
	    	item.setDescription(newItem.getDescription());
	    	item.setPrice(newItem.getPrice());
	        
	        if (itemRepository.save(item).getId().equals(id)) {
	            return "Item updated successfully";
	        }
	    }
	    return "Error updating Item";
	}
	
	@DeleteMapping("items/{id}")
	public String deleteItem(@PathVariable("id") Integer id) {   
	        try{
	        	itemRepository.deleteById(id);
	        	return "Item deleted successfully";
	        }catch(Exception e){
	        	return "Error Deleting Item";
	        }
	}
	
	@GetMapping("/customers")
	public List<String> getAllCustomerList() {
		ResponseEntity<List<String>> exchange = restTemplate.exchange("http://customer-service/customerservice/customersfirstname", HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>(){});
		return exchange.getBody();
	}
}
